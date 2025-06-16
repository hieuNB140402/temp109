

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import java.util.ArrayList;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.hwpf.sprm.SprmBuffer;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


@ Internal
public final class CHPFormattedDiskPage extends FormattedDiskPage
{
    private static final int FC_SIZE = 4;

    private ArrayList<CHPX> _chpxList = new ArrayList<CHPX>();
    private ArrayList<CHPX> _overFlow;

    public CHPFormattedDiskPage()
    {
    }


    @ SuppressWarnings("unused")
    public CHPFormattedDiskPage(byte[] documentStream, int offset, int fcMin, TextPieceTable tpt)
    {
        this(documentStream, offset, tpt);
    }


    public CHPFormattedDiskPage(byte[] documentStream, int offset, CharIndexTranslator translator)
    {
        super(documentStream, offset);

        for (int x = 0; x < _crun; x++)
        {
            int bytesStartAt = getStart(x);
            int bytesEndAt = getEnd(x);

            int charStartAt = translator.getCharIndex(bytesStartAt);
            int charEndAt = translator.getCharIndex(bytesEndAt, charStartAt);


            if (charStartAt > charEndAt)
            {
                continue;
            }
            CHPX chpx = new CHPX(charStartAt, charEndAt, new SprmBuffer(getGrpprl(x), 0));
            _chpxList.add(chpx);
        }
        _crun = _chpxList.size();
    }

    public CHPX getCHPX(int index)
    {
        return _chpxList.get(index);
    }

    public void fill(List<CHPX> filler)
    {
        _chpxList.addAll(filler);
    }

    public ArrayList<CHPX> getOverflow()
    {
        return _overFlow;
    }


    protected byte[] getGrpprl(int index)
    {
        int chpxOffset = 2 * LittleEndian.getUnsignedByte(_fkp, _offset
            + (((_crun + 1) * 4) + index));

        if (chpxOffset == 0)
        {
            return new byte[0];
        }

        int size = LittleEndian.getUnsignedByte(_fkp, _offset + chpxOffset);

        byte[] chpx = new byte[size];

        System.arraycopy(_fkp, _offset + ++chpxOffset, chpx, 0, size);
        return chpx;
    }


    @ Deprecated
    @ SuppressWarnings("unused")
    protected byte[] toByteArray(CharIndexTranslator translator, int fcMin)
    {
        return toByteArray(translator);
    }

    protected byte[] toByteArray(CharIndexTranslator translator)
    {
        byte[] buf = new byte[512];
        int size = _chpxList.size();
        int grpprlOffset = 511;
        int offsetOffset = 0;
        int fcOffset = 0;

        int totalSize = FC_SIZE + 2;

        int index = 0;
        for (; index < size; index++)
        {
            int grpprlLength = (_chpxList.get(index)).getGrpprl().length;

            totalSize += (FC_SIZE + 2 + grpprlLength);

            if (totalSize > 511 + (index % 2))
            {
                totalSize -= (FC_SIZE + 2 + grpprlLength);
                break;
            }
    if ((1 + grpprlLength) % 2 > 0)
            {
                totalSize += 1;
            }
        }

        if (index != size)
        {
            _overFlow = new ArrayList<CHPX>();
            _overFlow.addAll(_chpxList.subList(index, size));
        }

        buf[511] = (byte)index;

        offsetOffset = (FC_SIZE * index) + FC_SIZE;

        CHPX chpx = null;
        for (int x = 0; x < index; x++)
        {
            chpx = _chpxList.get(x);
            byte[] grpprl = chpx.getGrpprl();

            LittleEndian.putInt(buf, fcOffset, translator.getByteIndex(chpx.getStart()));

            grpprlOffset -= (1 + grpprl.length);
            grpprlOffset -= (grpprlOffset % 2);
            buf[offsetOffset] = (byte)(grpprlOffset / 2);
            buf[grpprlOffset] = (byte)grpprl.length;
            System.arraycopy(grpprl, 0, buf, grpprlOffset + 1, grpprl.length);

            offsetOffset += 1;
            fcOffset += FC_SIZE;
        }
        LittleEndian.putInt(buf, fcOffset, translator.getByteIndex(chpx.getEnd()));
        return buf;
    }

}
