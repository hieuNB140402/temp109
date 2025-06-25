

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import java.util.Arrays;

import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.BitFieldFactory;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



@ Internal
public final class Ffn
{
    private int _cbFfnM1;//total length of FFN - 1.
    private byte _info;
    private static BitField _prq = BitFieldFactory.getInstance(0x0003);// pitch request
    private static BitField _fTrueType = BitFieldFactory.getInstance(0x0004);// when 1, font is a TrueType font
    private static BitField _ff = BitFieldFactory.getInstance(0x0070);
    private short _wWeight;
    private byte _chs;
    private byte _ixchSzAlt;

    private byte[] _panose = new byte[10];//????
    private byte[] _fontSig = new byte[24];//????

    private char[] _xszFfn;


    private int _xszFfnLength;

    public Ffn(byte[] buf, int offset)
    {
        int offsetTmp = offset;

        _cbFfnM1 = LittleEndian.getUnsignedByte(buf, offset);
        offset += LittleEndian.BYTE_SIZE;
        _info = buf[offset];
        offset += LittleEndian.BYTE_SIZE;
        _wWeight = LittleEndian.getShort(buf, offset);
        offset += LittleEndian.SHORT_SIZE;
        _chs = buf[offset];
        offset += LittleEndian.BYTE_SIZE;
        _ixchSzAlt = buf[offset];
        offset += LittleEndian.BYTE_SIZE;

        // read panose and fs so we can write them back out.
        System.arraycopy(buf, offset, _panose, 0, _panose.length);
        offset += _panose.length;
        System.arraycopy(buf, offset, _fontSig, 0, _fontSig.length);
        offset += _fontSig.length;

        offsetTmp = offset - offsetTmp;
        _xszFfnLength = (this.getSize() - offsetTmp) / 2;
        _xszFfn = new char[_xszFfnLength];

        for (int i = 0; i < _xszFfnLength; i++)
        {
            _xszFfn[i] = (char)LittleEndian.getShort(buf, offset);
            offset += LittleEndian.SHORT_SIZE;
        }

    }

    public int get_cbFfnM1()
    {
        return _cbFfnM1;
    }

    public short getWeight()
    {
        return _wWeight;
    }

    public byte getChs()
    {
        return _chs;
    }

    public byte[] getPanose()
    {
        return _panose;
    }

    public byte[] getFontSig()
    {
        return _fontSig;
    }

    public int getSize()
    {
        return (_cbFfnM1 + 1);
    }

    public String getMainFontName()
    {
        int index = 0;
        for (; index < _xszFfnLength; index++)
        {
            if (_xszFfn[index] == '\0')
            {
                break;
            }
        }
        return new String(_xszFfn, 0, index);
    }

    public String getAltFontName()
    {
        int index = _ixchSzAlt;
        for (; index < _xszFfnLength; index++)
        {
            if (_xszFfn[index] == '\0')
            {
                break;
            }
        }
        return new String(_xszFfn, _ixchSzAlt, index);

    }

    public void set_cbFfnM1(int _cbFfnM1)
    {
        this._cbFfnM1 = _cbFfnM1;
    }

    // changed protected to public
    public byte[] toByteArray()
    {
        int offset = 0;
        byte[] buf = new byte[this.getSize()];

        buf[offset] = (byte)_cbFfnM1;
        offset += LittleEndian.BYTE_SIZE;
        buf[offset] = _info;
        offset += LittleEndian.BYTE_SIZE;
        LittleEndian.putShort(buf, offset, _wWeight);
        offset += LittleEndian.SHORT_SIZE;
        buf[offset] = _chs;
        offset += LittleEndian.BYTE_SIZE;
        buf[offset] = _ixchSzAlt;
        offset += LittleEndian.BYTE_SIZE;

        System.arraycopy(_panose, 0, buf, offset, _panose.length);
        offset += _panose.length;
        System.arraycopy(_fontSig, 0, buf, offset, _fontSig.length);
        offset += _fontSig.length;

        for (int i = 0; i < _xszFfn.length; i++)
        {
            LittleEndian.putShort(buf, offset, (short)_xszFfn[i]);
            offset += LittleEndian.SHORT_SIZE;
        }

        return buf;

    }

    public boolean equals(Object o)
    {
        boolean retVal = true;

        if (((Ffn)o).get_cbFfnM1() == _cbFfnM1)
        {
            if (((Ffn)o)._info == _info)
            {
                if (((Ffn)o)._wWeight == _wWeight)
                {
                    if (((Ffn)o)._chs == _chs)
                    {
                        if (((Ffn)o)._ixchSzAlt == _ixchSzAlt)
                        {
                            if (Arrays.equals(((Ffn)o)._panose, _panose))
                            {
                                if (Arrays.equals(((Ffn)o)._fontSig, _fontSig))
                                {
                                    if (!(Arrays.equals(((Ffn)o)._xszFfn, _xszFfn)))
                                        retVal = false;
                                }
                                else
                                    retVal = false;
                            }
                            else
                                retVal = false;
                        }
                        else
                            retVal = false;
                    }
                    else
                        retVal = false;
                }
                else
                    retVal = false;
            }
            else
                retVal = false;
        }
        else
            retVal = false;

        return retVal;
    }

}
