

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.BitFieldFactory;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;

public final class BorderCode implements Cloneable
{

    public static final int SIZE = 4;

    private short _info;
    private static final BitField _dptLineWidth = BitFieldFactory.getInstance(0x00ff);
    private static final BitField _brcType = BitFieldFactory.getInstance(0xff00);

    private short _info2;
    private static final BitField _ico = BitFieldFactory.getInstance(0x00ff);
    private static final BitField _dptSpace = BitFieldFactory.getInstance(0x1f00);
    private static final BitField _fShadow = BitFieldFactory.getInstance(0x2000);
    private static final BitField _fFrame = BitFieldFactory.getInstance(0x4000);

    public BorderCode()
    {
    }

    public BorderCode(byte[] buf, int offset)
    {
        _info = LittleEndian.getShort(buf, offset);
        _info2 = LittleEndian.getShort(buf, offset + LittleEndian.SHORT_SIZE);
    }

    public void serialize(byte[] buf, int offset)
    {
        LittleEndian.putShort(buf, offset, _info);
        LittleEndian.putShort(buf, offset + LittleEndian.SHORT_SIZE, _info2);
    }

    public int toInt()
    {
        byte[] buf = new byte[4];
        serialize(buf, 0);
        return LittleEndian.getInt(buf);
    }

    public boolean isEmpty()
    {
        return _info == 0 && _info2 == 0;
    }

    public boolean equals(Object o)
    {
        BorderCode brc = (BorderCode)o;
        return _info == brc._info && _info2 == brc._info2;
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }


    public int getLineWidth()
    {
        return _dptLineWidth.getShortValue(_info);
    }

    public void setLineWidth(int lineWidth)
    {
        _dptLineWidth.setValue(_info, lineWidth);
    }


    public int getBorderType()
    {
        return _brcType.getShortValue(_info);
    }

    public void setBorderType(int borderType)
    {
        _brcType.setValue(_info, borderType);
    }

    public short getColor()
    {
        return _ico.getShortValue(_info2);
    }

    public void setColor(short color)
    {
        _ico.setValue(_info2, color);
    }


    public int getSpace()
    {
        return _dptSpace.getShortValue(_info2);
    }

    public void setSpace(int space)
    {
        _dptSpace.setValue(_info2, space);
    }

    public boolean isShadow()
    {
        return _fShadow.getValue(_info2) != 0;
    }

    public void setShadow(boolean shadow)
    {
        _fShadow.setValue(_info2, shadow ? 1 : 0);
    }

    public boolean isFrame()
    {
        return _fFrame.getValue(_info2) != 0;
    }

    public void setFrame(boolean frame)
    {
        _fFrame.setValue(_info2, frame ? 1 : 0);
    }

    @ Override
    public String toString()
    {
        if (isEmpty())
            return "[BRC] EMPTY";

        StringBuffer buffer = new StringBuffer();

        buffer.append("[BRC]\n");

        buffer.append("        .dptLineWidth         = ");
        buffer.append(" (").append(getLineWidth()).append(" )\n");

        buffer.append("        .brcType              = ");
        buffer.append(" (").append(getBorderType()).append(" )\n");

        buffer.append("        .ico                  = ");
        buffer.append(" (").append(getColor()).append(" )\n");

        buffer.append("        .dptSpace             = ");
        buffer.append(" (").append(getSpace()).append(" )\n");

        buffer.append("        .fShadow              = ");
        buffer.append(" (").append(isShadow()).append(" )\n");

        buffer.append("        .fFrame               = ");
        buffer.append(" (").append(isFrame()).append(" )\n");

        return buffer.toString();
    }

}
