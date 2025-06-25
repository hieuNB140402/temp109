

package com.document.allreader.allofficefilereader.fc.hwpf.model.types;

import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



@ Internal
public abstract class BKFAbstractType
{

    protected short field_1_ibkl;
    protected short field_2_bkf_flags;
    private static BitField itcFirst = new BitField(0x007F);
    private static BitField fPub = new BitField(0x0080);
    private static BitField itcLim = new BitField(0x7F00);
    private static BitField fCol = new BitField(0x8000);

    protected BKFAbstractType()
    {
    }

    protected void fillFields(byte[] data, int offset)
    {
        field_1_ibkl = LittleEndian.getShort(data, 0x0 + offset);
        field_2_bkf_flags = LittleEndian.getShort(data, 0x2 + offset);
    }

    public void serialize(byte[] data, int offset)
    {
        LittleEndian.putShort(data, 0x0 + offset, field_1_ibkl);
        LittleEndian.putShort(data, 0x2 + offset, field_2_bkf_flags);
    }

    /**
     * Size of record
     */
    public static int getSize()
    {
        return 0 + 2 + 2;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[BKF]\n");
        builder.append("    .ibkl                 = ");
        builder.append(" (").append(getIbkl()).append(" )\n");
        builder.append("    .bkf_flags            = ");
        builder.append(" (").append(getBkf_flags()).append(" )\n");
        builder.append("         .itcFirst                 = ").append(getItcFirst()).append('\n');
        builder.append("         .fPub                     = ").append(isFPub()).append('\n');
        builder.append("         .itcLim                   = ").append(getItcLim()).append('\n');
        builder.append("         .fCol                     = ").append(isFCol()).append('\n');

        builder.append("[/BKF]\n");
        return builder.toString();
    }

    public short getIbkl()
    {
        return field_1_ibkl;
    }


    public void setIbkl(short field_1_ibkl)
    {
        this.field_1_ibkl = field_1_ibkl;
    }


    public short getBkf_flags()
    {
        return field_2_bkf_flags;
    }


    public void setBkf_flags(short field_2_bkf_flags)
    {
        this.field_2_bkf_flags = field_2_bkf_flags;
    }


    public void setItcFirst(byte value)
    {
        field_2_bkf_flags = (short)itcFirst.setValue(field_2_bkf_flags, value);
    }

    public byte getItcFirst()
    {
        return (byte)itcFirst.getValue(field_2_bkf_flags);
    }


    public void setFPub(boolean value)
    {
        field_2_bkf_flags = (short)fPub.setBoolean(field_2_bkf_flags, value);
    }


    public boolean isFPub()
    {
        return fPub.isSet(field_2_bkf_flags);
    }


    public void setItcLim(byte value)
    {
        field_2_bkf_flags = (short)itcLim.setValue(field_2_bkf_flags, value);
    }


    public byte getItcLim()
    {
        return (byte)itcLim.getValue(field_2_bkf_flags);
    }


    public void setFCol(boolean value)
    {
        field_2_bkf_flags = (short)fCol.setBoolean(field_2_bkf_flags, value);
    }

    public boolean isFCol()
    {
        return fCol.isSet(field_2_bkf_flags);
    }

}
