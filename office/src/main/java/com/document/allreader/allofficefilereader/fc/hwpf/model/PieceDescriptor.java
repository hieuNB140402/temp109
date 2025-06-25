

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;

@ Internal
public final class PieceDescriptor
{

    short descriptor;

    int fc;
    PropertyModifier prm;
    boolean unicode;

    public PieceDescriptor(byte[] buf, int offset)
    {
        descriptor = LittleEndian.getShort(buf, offset);
        offset += LittleEndian.SHORT_SIZE;
        fc = LittleEndian.getInt(buf, offset);
        offset += LittleEndian.INT_SIZE;
        prm = new PropertyModifier(LittleEndian.getShort(buf, offset));

        // see if this piece uses unicode.
        if ((fc & 0x40000000) == 0)
        {
            unicode = true;
        }
        else
        {
            unicode = false;
            fc &= ~(0x40000000);//gives me FC in doc stream
            fc /= 2;
        }

    }

    public int getFilePosition()
    {
        return fc;
    }

    public void setFilePosition(int pos)
    {
        fc = pos;
    }

    public boolean isUnicode()
    {
        return unicode;
    }

    public PropertyModifier getPrm()
    {
        return prm;
    }

    protected byte[] toByteArray()
    {

        int tempFc = fc;
        if (!unicode)
        {
            tempFc *= 2;
            tempFc |= (0x40000000);
        }

        int offset = 0;
        byte[] buf = new byte[8];
        LittleEndian.putShort(buf, offset, descriptor);
        offset += LittleEndian.SHORT_SIZE;
        LittleEndian.putInt(buf, offset, tempFc);
        offset += LittleEndian.INT_SIZE;
        LittleEndian.putShort(buf, offset, prm.getValue());

        return buf;

    }

    public static int getSizeInBytes()
    {
        return 8;
    }

    @ Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + descriptor;
        result = prime * result + ((prm == null) ? 0 : prm.hashCode());
        result = prime * result + (unicode ? 1231 : 1237);
        return result;
    }

    @ Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PieceDescriptor other = (PieceDescriptor)obj;
        if (descriptor != other.descriptor)
            return false;
        if (prm == null)
        {
            if (other.prm != null)
                return false;
        }
        else if (!prm.equals(other.prm))
            return false;
        if (unicode != other.unicode)
            return false;
        return true;
    }

    @ Override
    public String toString()
    {
        return "PieceDescriptor (pos: " + getFilePosition() + "; "
            + (isUnicode() ? "unicode" : "non-unicode") + "; prm: " + getPrm() + ")";
    }
}
