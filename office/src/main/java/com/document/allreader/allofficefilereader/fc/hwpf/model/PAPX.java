

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.hwpf.sprm.ParagraphSprmUncompressor;
import com.document.allreader.allofficefilereader.fc.hwpf.sprm.SprmBuffer;
import com.document.allreader.allofficefilereader.fc.hwpf.sprm.SprmOperation;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.ParagraphProperties;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



@ Internal
@ SuppressWarnings("deprecation")
public final class PAPX extends BytePropertyNode<PAPX>
{

    private ParagraphHeight _phe;

    public PAPX(int fcStart, int fcEnd, CharIndexTranslator translator, byte[] papx,
        ParagraphHeight phe, byte[] dataStream)
    {
        super(fcStart, fcEnd, translator, new SprmBuffer(papx, 2));
        _phe = phe;
        SprmBuffer buf = findHuge(new SprmBuffer(papx, 2), dataStream);
        if (buf != null)
            _buf = buf;
    }

    public PAPX(int charStart, int charEnd, byte[] papx, ParagraphHeight phe, byte[] dataStream)
    {
        super(charStart, charEnd, new SprmBuffer(papx, 2));
        _phe = phe;
        SprmBuffer buf = findHuge(new SprmBuffer(papx, 2), dataStream);
        if (buf != null)
            _buf = buf;
    }

    @ Deprecated
    public PAPX(int fcStart, int fcEnd, CharIndexTranslator translator, SprmBuffer buf,
        byte[] dataStream)
    {
        super(fcStart, fcEnd, translator, buf);
        _phe = new ParagraphHeight();
        buf = findHuge(buf, dataStream);
        if (buf != null)
            _buf = buf;
    }

    public PAPX(int charStart, int charEnd, SprmBuffer buf)
    {
        super(charStart, charEnd, buf);
        _phe = new ParagraphHeight();
    }

    private SprmBuffer findHuge(SprmBuffer buf, byte[] datastream)
    {
        byte[] grpprl = buf.toByteArray();
        if (grpprl.length == 8 && datastream != null)
        {
            SprmOperation sprm = new SprmOperation(grpprl, 2);
            if ((sprm.getOperation() == 0x45 || sprm.getOperation() == 0x46)
                && sprm.getSizeCode() == 3)
            {
                int hugeGrpprlOffset = sprm.getOperand();
                if (hugeGrpprlOffset + 1 < datastream.length)
                {
                    int grpprlSize = LittleEndian.getShort(datastream, hugeGrpprlOffset);
                    if (hugeGrpprlOffset + grpprlSize < datastream.length)
                    {
                        byte[] hugeGrpprl = new byte[grpprlSize + 2];

                        hugeGrpprl[0] = grpprl[0];
                        hugeGrpprl[1] = grpprl[1];

                        System.arraycopy(datastream, hugeGrpprlOffset + 2, hugeGrpprl, 2,
                            grpprlSize);
                        return new SprmBuffer(hugeGrpprl, 2);
                    }
                }
            }
        }
        return null;
    }

    public ParagraphHeight getParagraphHeight()
    {
        return _phe;
    }

    public byte[] getGrpprl()
    {
        return ((SprmBuffer)_buf).toByteArray();
    }

    public short getIstd()
    {
        byte[] buf = getGrpprl();
        if (buf.length == 0)
        {
            return 0;
        }
        if (buf.length == 1)
        {
            return (short)LittleEndian.getUnsignedByte(buf, 0);
        }
        return LittleEndian.getShort(buf);
    }

    public SprmBuffer getSprmBuf()
    {
        return (SprmBuffer)_buf;
    }

    public ParagraphProperties getParagraphProperties(StyleSheet ss)
    {
        if (ss == null)
        {
            return new ParagraphProperties();
        }

        short istd = getIstd();
        ParagraphProperties baseStyle = ss.getParagraphStyle(istd);
        ParagraphProperties props = ParagraphSprmUncompressor.uncompressPAP(baseStyle, getGrpprl(),
            2);
        return props;
    }

    public boolean equals(Object o)
    {
        if (super.equals(o))
        {
            return _phe.equals(((PAPX)o)._phe);
        }
        return false;
    }

    public String toString()
    {
        return "PAPX from " + getStart() + " to " + getEnd() + " (in bytes " + getStartBytes()
            + " to " + getEndBytes() + ")";
    }
}
