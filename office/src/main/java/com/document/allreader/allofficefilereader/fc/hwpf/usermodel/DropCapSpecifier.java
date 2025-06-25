

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.BitFieldFactory;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public final class DropCapSpecifier implements Cloneable
{
    private short _fdct;
    private static BitField _lines = BitFieldFactory.getInstance(0xf8);
    private static BitField _type = BitFieldFactory.getInstance(0x07);

    public DropCapSpecifier()
    {
        this._fdct = 0;
    }

    public DropCapSpecifier(byte[] buf, int offset)
    {
        this(LittleEndian.getShort(buf, offset));
    }

    public DropCapSpecifier(short fdct)
    {
        this._fdct = fdct;
    }

    @ Override
    public DropCapSpecifier clone()
    {
        return new DropCapSpecifier(_fdct);
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
        DropCapSpecifier other = (DropCapSpecifier)obj;
        if (_fdct != other._fdct)
            return false;
        return true;
    }

    public byte getCountOfLinesToDrop()
    {
        return (byte)_lines.getValue(_fdct);
    }

    public byte getDropCapType()
    {
        return (byte)_type.getValue(_fdct);
    }

    @ Override
    public int hashCode()
    {
        return _fdct;
    }

    public boolean isEmpty()
    {
        return _fdct == 0;
    }

    public void setCountOfLinesToDrop(byte value)
    {
        _fdct = (short)_lines.setValue(_fdct, value);
    }

    public void setDropCapType(byte value)
    {
        _fdct = (short)_type.setValue(_fdct, value);
    }

    public short toShort()
    {
        return _fdct;
    }

    @ Override
    public String toString()
    {
        if (isEmpty())
            return "[DCS] EMPTY";

        return "[DCS] (type: " + getDropCapType() + "; count: " + getCountOfLinesToDrop() + ")";
    }
}
