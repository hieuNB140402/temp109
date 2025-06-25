

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.hwpf.model.io.HWPFOutputStream;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Internal
public final class PAPFormattedDiskPage extends FormattedDiskPage {
    private static final int BX_SIZE = 13;
    private static final int FC_SIZE = 4;

    private ArrayList<PAPX> _papxList = new ArrayList<PAPX>();
    private ArrayList<PAPX> _overFlow;


    public PAPFormattedDiskPage(byte[] dataStream) {
        this();
    }

    public PAPFormattedDiskPage() {
    }

    public PAPFormattedDiskPage(byte[] documentStream, byte[] dataStream,
                                int offset, int fcMin, TextPieceTable tpt) {
        this(documentStream, dataStream, offset, tpt);
    }


    public PAPFormattedDiskPage(byte[] documentStream, byte[] dataStream,
                                int offset, CharIndexTranslator translator) {
        super(documentStream, offset);
        for (int x = 0; x < _crun; x++) {
            int bytesStartAt = getStart(x);
            int bytesEndAt = getEnd(x);

            int charStartAt = translator.getCharIndex(bytesStartAt);
            int charEndAt = translator.getCharIndex(bytesEndAt, charStartAt);
            if (charStartAt > charEndAt) {
                continue;
            }
            PAPX papx = new PAPX(charStartAt, charEndAt, getGrpprl(x),
                    getParagraphHeight(x), dataStream);
            _papxList.add(papx);
        }
        _crun = _papxList.size();
        _fkp = null;
    }


    public void fill(List<PAPX> filler) {
        _papxList.addAll(filler);
    }

    ArrayList<PAPX> getOverflow() {
        return _overFlow;
    }


    public PAPX getPAPX(int index) {
        return _papxList.get(index);
    }

    public List<PAPX> getPAPXs() {
        return Collections.unmodifiableList(_papxList);
    }

    protected byte[] getGrpprl(int index) {
        int papxOffset = 2 * LittleEndian.getUnsignedByte(_fkp, _offset + (((_crun + 1) * FC_SIZE) + (index * BX_SIZE)));
        int size = 2 * LittleEndian.getUnsignedByte(_fkp, _offset + papxOffset);
        if (size == 0) {
            size = 2 * LittleEndian.getUnsignedByte(_fkp, _offset + ++papxOffset);
        } else {
            size--;
        }

        byte[] papx = new byte[size];
        System.arraycopy(_fkp, _offset + ++papxOffset, papx, 0, size);
        return papx;
    }


    protected byte[] toByteArray(HWPFOutputStream dataStream,
                                 CharIndexTranslator translator) throws IOException {
        byte[] buf = new byte[512];
        int size = _papxList.size();
        int grpprlOffset = 0;
        int bxOffset = 0;
        int fcOffset = 0;
        byte[] lastGrpprl = new byte[0];

        int totalSize = FC_SIZE;

        int index = 0;
        for (; index < size; index++) {
            byte[] grpprl = _papxList.get(index).getGrpprl();
            int grpprlLength = grpprl.length;

            if (grpprlLength > 488) {
                grpprlLength = 8;
            }

            int addition = 0;
            if (!Arrays.equals(grpprl, lastGrpprl)) {
                addition = (FC_SIZE + BX_SIZE + grpprlLength + 1);
            } else {
                addition = (FC_SIZE + BX_SIZE);
            }

            totalSize += addition;

            if (totalSize > 511 + (index % 2)) {
                totalSize -= addition;
                break;
            }

            if (grpprlLength % 2 > 0) {
                totalSize += 1;
            } else {
                totalSize += 2;
            }
            lastGrpprl = grpprl;
        }


        if (index != size) {
            _overFlow = new ArrayList<PAPX>();
            _overFlow.addAll(_papxList.subList(index, size));
        }

        buf[511] = (byte) index;

        bxOffset = (FC_SIZE * index) + FC_SIZE;
        grpprlOffset = 511;

        PAPX papx = null;
        lastGrpprl = new byte[0];
        for (int x = 0; x < index; x++) {
            papx = _papxList.get(x);
            byte[] phe = papx.getParagraphHeight().toByteArray();
            byte[] grpprl = papx.getGrpprl();

            if (grpprl.length > 488) {


                byte[] hugePapx = new byte[grpprl.length - 2];
                System.arraycopy(grpprl, 2, hugePapx, 0, grpprl.length - 2);
                int dataStreamOffset = dataStream.getOffset();
                dataStream.write(hugePapx);


                int istd = LittleEndian.getUShort(grpprl, 0);

                grpprl = new byte[8];
                LittleEndian.putUShort(grpprl, 0, istd);
                LittleEndian.putUShort(grpprl, 2, 0x6646);
                LittleEndian.putInt(grpprl, 4, dataStreamOffset);
            }

            boolean same = Arrays.equals(lastGrpprl, grpprl);
            if (!same) {
                grpprlOffset -= (grpprl.length + (2 - grpprl.length % 2));
                grpprlOffset -= (grpprlOffset % 2);
            }

            LittleEndian.putInt(buf, fcOffset,
                    translator.getByteIndex(papx.getStart()));
            buf[bxOffset] = (byte) (grpprlOffset / 2);
            System.arraycopy(phe, 0, buf, bxOffset + 1, phe.length);

            if (!same) {
                int copyOffset = grpprlOffset;
                if ((grpprl.length % 2) > 0) {
                    buf[copyOffset++] = (byte) ((grpprl.length + 1) / 2);
                } else {
                    buf[++copyOffset] = (byte) ((grpprl.length) / 2);
                    copyOffset++;
                }
                System.arraycopy(grpprl, 0, buf, copyOffset, grpprl.length);
                lastGrpprl = grpprl;
            }

            bxOffset += BX_SIZE;
            fcOffset += FC_SIZE;

        }

        LittleEndian.putInt(buf, fcOffset,
                translator.getByteIndex(papx.getEnd()));
        return buf;
    }

    private ParagraphHeight getParagraphHeight(int index) {
        int pheOffset = _offset + 1 + (((_crun + 1) * 4) + (index * 13));

        ParagraphHeight phe = new ParagraphHeight(_fkp, pheOffset);

        return phe;
    }
}
