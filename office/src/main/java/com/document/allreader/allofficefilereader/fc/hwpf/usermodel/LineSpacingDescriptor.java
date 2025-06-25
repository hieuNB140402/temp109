

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;

public final class LineSpacingDescriptor implements Cloneable
{
    short _dyaLine;
    short _fMultiLinespace;

    public LineSpacingDescriptor()
    {
        //see page 181
        _dyaLine = 240;
        _fMultiLinespace = 1;
    }

    public LineSpacingDescriptor(byte[] buf, int offset)
    {
        _dyaLine = LittleEndian.getShort(buf, offset);
        _fMultiLinespace = LittleEndian.getShort(buf, offset + LittleEndian.SHORT_SIZE);
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }


    public short getDyaLine()
    {
        return _dyaLine;
    }
    

    public short getMultiLinespace()
    {
        return _fMultiLinespace;
    }
    

    public void setMultiLinespace(short fMultiLinespace)
    {
        _fMultiLinespace = fMultiLinespace;
    }

    public int toInt()
    {
        byte[] intHolder = new byte[4];
        serialize(intHolder, 0);
        return LittleEndian.getInt(intHolder);
    }

    public void serialize(byte[] buf, int offset)
    {
        LittleEndian.putShort(buf, offset, _dyaLine);
        LittleEndian.putShort(buf, offset + LittleEndian.SHORT_SIZE, _fMultiLinespace);
    }

    public void setDyaLine(short dyaLine)
    {
        _dyaLine = dyaLine;
    }

    public boolean equals(Object o)
    {
        LineSpacingDescriptor lspd = (LineSpacingDescriptor)o;

        return _dyaLine == lspd._dyaLine && _fMultiLinespace == lspd._fMultiLinespace;
    }

    public boolean isEmpty()
    {
        return _dyaLine == 0 && _fMultiLinespace == 0;
    }

    @ Override
    public String toString()
    {
        if (isEmpty())
            return "[LSPD] EMPTY";

        return "[LSPD] (dyaLine: " + _dyaLine + "; fMultLinespace: " + _fMultiLinespace + ")";
    }
}
