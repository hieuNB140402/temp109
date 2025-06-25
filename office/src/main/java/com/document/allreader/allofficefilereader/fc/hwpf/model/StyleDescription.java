

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.CharacterProperties;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.ParagraphProperties;
import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.BitFieldFactory;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;


@Internal
public final class StyleDescription implements HDFType {

    private final static int PARAGRAPH_STYLE = 1;
    private final static int CHARACTER_STYLE = 2;

    private int _istd;
    private int _baseLength;
    private short _infoShort;
    private static BitField _sti = BitFieldFactory.getInstance(0xfff);
    private static BitField _fScratch = BitFieldFactory.getInstance(0x1000);
    private static BitField _fInvalHeight = BitFieldFactory.getInstance(0x2000);
    private static BitField _fHasUpe = BitFieldFactory.getInstance(0x4000);
    private static BitField _fMassCopy = BitFieldFactory.getInstance(0x8000);
    private short _infoShort2;
    private static BitField _styleTypeCode = BitFieldFactory.getInstance(0xf);
    private static BitField _baseStyle = BitFieldFactory.getInstance(0xfff0);
    private short _infoShort3;
    private static BitField _numUPX = BitFieldFactory.getInstance(0xf);
    private static BitField _nextStyle = BitFieldFactory.getInstance(0xfff0);
    private short _bchUpe;
    private short _infoShort4;
    private static BitField _fAutoRedef = BitFieldFactory.getInstance(0x1);
    private static BitField _fHidden = BitFieldFactory.getInstance(0x2);

    UPX[] _upxs;
    String _name;
    ParagraphProperties _pap;
    CharacterProperties _chp;



    public StyleDescription(byte[] std, int baseLength, int offset, boolean word9) {
        _baseLength = baseLength;
        int nameStart = offset + baseLength;
        _infoShort = LittleEndian.getShort(std, offset);
        offset += LittleEndian.SHORT_SIZE;
        _infoShort2 = LittleEndian.getShort(std, offset);
        offset += LittleEndian.SHORT_SIZE;
        _infoShort3 = LittleEndian.getShort(std, offset);
        offset += LittleEndian.SHORT_SIZE;
        _bchUpe = LittleEndian.getShort(std, offset);
        offset += LittleEndian.SHORT_SIZE;
        _infoShort4 = LittleEndian.getShort(std, offset);
        offset += LittleEndian.SHORT_SIZE;

        int nameLength = 0;
        int multiplier = 1;
        if (word9) {
            nameLength = LittleEndian.getShort(std, nameStart);
            multiplier = 2;
            nameStart += LittleEndian.SHORT_SIZE;
        } else {
            nameLength = std[nameStart];
        }

        try {
            _name = new String(std, nameStart, nameLength * multiplier, "UTF-16LE");
        } catch (UnsupportedEncodingException ignore) {


        }


        int grupxStart = ((nameLength + 1) * multiplier) + nameStart;


        int varOffset = grupxStart;
        int numUPX = _numUPX.getValue(_infoShort3);
        _upxs = new UPX[numUPX];
        for (int x = 0; x < numUPX; x++) {
            int upxSize = LittleEndian.getShort(std, varOffset);
            varOffset += LittleEndian.SHORT_SIZE;

            byte[] upx = new byte[upxSize];
            System.arraycopy(std, varOffset, upx, 0, upxSize);
            _upxs[x] = new UPX(upx);
            varOffset += upxSize;



            if (upxSize % 2 == 1) {
                ++varOffset;
            }

        }


    }

    public int getBaseStyle() {
        return _baseStyle.getValue(_infoShort2);
    }

    public byte[] getCHPX() {
        switch (_styleTypeCode.getValue(_infoShort2)) {
            case PARAGRAPH_STYLE:
                if (_upxs.length > 1) {
                    return _upxs[1].getUPX();
                }
                return null;
            case CHARACTER_STYLE:
                return _upxs[0].getUPX();
            default:
                return null;
        }

    }

    public byte[] getPAPX() {
        switch (_styleTypeCode.getValue(_infoShort2)) {
            case PARAGRAPH_STYLE:
                return _upxs[0].getUPX();
            default:
                return null;
        }
    }

    public ParagraphProperties getPAP() {
        return _pap;
    }

    public CharacterProperties getCHP() {
        return _chp;
    }

    void setPAP(ParagraphProperties pap) {
        _pap = pap;
    }

    void setCHP(CharacterProperties chp) {
        _chp = chp;
    }

    public String getName() {
        return _name;
    }

    public byte[] toByteArray() {

        int size = _baseLength + 2 + ((_name.length() + 1) * 2);

        size += _upxs[0].size() + 2;
        for (int x = 1; x < _upxs.length; x++) {
            size += _upxs[x - 1].size() % 2;
            size += _upxs[x].size() + 2;
        }


        byte[] buf = new byte[size];

        int offset = 0;
        LittleEndian.putShort(buf, offset, _infoShort);
        offset += LittleEndian.SHORT_SIZE;
        LittleEndian.putShort(buf, offset, _infoShort2);
        offset += LittleEndian.SHORT_SIZE;
        LittleEndian.putShort(buf, offset, _infoShort3);
        offset += LittleEndian.SHORT_SIZE;
        LittleEndian.putShort(buf, offset, _bchUpe);
        offset += LittleEndian.SHORT_SIZE;
        LittleEndian.putShort(buf, offset, _infoShort4);
        offset = _baseLength;

        char[] letters = _name.toCharArray();
        LittleEndian.putShort(buf, _baseLength, (short) letters.length);
        offset += LittleEndian.SHORT_SIZE;
        for (int x = 0; x < letters.length; x++) {
            LittleEndian.putShort(buf, offset, (short) letters[x]);
            offset += LittleEndian.SHORT_SIZE;
        }
        // get past the null delimiter for the name.
        offset += LittleEndian.SHORT_SIZE;

        for (int x = 0; x < _upxs.length; x++) {
            short upxSize = (short) _upxs[x].size();
            LittleEndian.putShort(buf, offset, upxSize);
            offset += LittleEndian.SHORT_SIZE;
            System.arraycopy(_upxs[x].getUPX(), 0, buf, offset, upxSize);
            offset += upxSize + (upxSize % 2);
        }

        return buf;
    }

    public boolean equals(Object o) {
        StyleDescription sd = (StyleDescription) o;
        if (sd._infoShort == _infoShort && sd._infoShort2 == _infoShort2 &&
                sd._infoShort3 == _infoShort3 && sd._bchUpe == _bchUpe &&
                sd._infoShort4 == _infoShort4 &&
                _name.equals(sd._name)) {

            if (!Arrays.equals(_upxs, sd._upxs)) {
                return false;
            }
            return true;
        }
        return false;
    }
}
