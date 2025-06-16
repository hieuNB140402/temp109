

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.BitFieldFactory;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;

public final class ShadingDescriptor implements Cloneable
{
    public static final int SIZE = 2;

    private short _info;
    private final static BitField _icoFore = BitFieldFactory.getInstance(0x1f);
    private final static BitField _icoBack = BitFieldFactory.getInstance(0x3e0);
    private final static BitField _ipat = BitFieldFactory.getInstance(0xfc00);

    public ShadingDescriptor()
    {
    }

    public ShadingDescriptor(byte[] buf, int offset)
    {
        this(LittleEndian.getShort(buf, offset));
    }

    public ShadingDescriptor(short info)
    {
        _info = info;
    }

    public short toShort()
    {
        return _info;
    }

    public void serialize(byte[] buf, int offset)
    {
        LittleEndian.putShort(buf, offset, _info);
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public boolean isEmpty()
    {
        return _info == 0;
    }

    @ Override
    public String toString()
    {
        if (isEmpty())
            return "[SHD] EMPTY";

        return "[SHD] (cvFore: " + _icoFore.getShortValue(_info) + "; cvBack: "
            + _icoBack.getShortValue(_info) + "; iPat: " + _ipat.getShortValue(_info) + ")";
    }
}
