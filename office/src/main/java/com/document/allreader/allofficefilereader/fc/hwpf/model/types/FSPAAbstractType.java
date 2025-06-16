
package com.document.allreader.allofficefilereader.fc.hwpf.model.types;


import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


@Internal
public abstract class FSPAAbstractType
{

    protected int field_1_spid;
    protected int field_2_xaLeft;
    protected int field_3_yaTop;
    protected int field_4_xaRight;
    protected int field_5_yaBottom;
    protected short field_6_flags;
    private static BitField fHdr = new BitField(0x0001);
    private static BitField bx = new BitField(0x0006);
    private static BitField by = new BitField(0x0018);
    private static BitField wr = new BitField(0x01E0);
    private static BitField wrk = new BitField(0x1E00);
    private static BitField fRcaSimple = new BitField(0x2000);
    private static BitField fBelowText = new BitField(0x4000);
    private static BitField fAnchorLock = new BitField(0x8000);
    protected int field_7_cTxbx;

    protected FSPAAbstractType()
    {
    }

    protected void fillFields( byte[] data, int offset )
    {
        field_1_spid                   = LittleEndian.getInt(data, 0x0 + offset);
        field_2_xaLeft                 = LittleEndian.getInt(data, 0x4 + offset);
        field_3_yaTop                  = LittleEndian.getInt(data, 0x8 + offset);
        field_4_xaRight                = LittleEndian.getInt(data, 0xc + offset);
        field_5_yaBottom               = LittleEndian.getInt(data, 0x10 + offset);
        field_6_flags                  = LittleEndian.getShort(data, 0x14 + offset);
        field_7_cTxbx                  = LittleEndian.getInt(data, 0x16 + offset);
    }

    public void serialize( byte[] data, int offset )
    {
        LittleEndian.putInt(data, 0x0 + offset, field_1_spid);
        LittleEndian.putInt(data, 0x4 + offset, field_2_xaLeft);
        LittleEndian.putInt(data, 0x8 + offset, field_3_yaTop);
        LittleEndian.putInt(data, 0xc + offset, field_4_xaRight);
        LittleEndian.putInt(data, 0x10 + offset, field_5_yaBottom);
        LittleEndian.putShort(data, 0x14 + offset, (short)field_6_flags);
        LittleEndian.putInt(data, 0x16 + offset, field_7_cTxbx);
    }

    /**
     * Size of record
     */
    public static int getSize()
    {
        return 0 + 4 + 4 + 4 + 4 + 4 + 2 + 4;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[FSPA]\n");
        builder.append("    .spid                 = ");
        builder.append(" (").append(getSpid()).append(" )\n");
        builder.append("    .xaLeft               = ");
        builder.append(" (").append(getXaLeft()).append(" )\n");
        builder.append("    .yaTop                = ");
        builder.append(" (").append(getYaTop()).append(" )\n");
        builder.append("    .xaRight              = ");
        builder.append(" (").append(getXaRight()).append(" )\n");
        builder.append("    .yaBottom             = ");
        builder.append(" (").append(getYaBottom()).append(" )\n");
        builder.append("    .flags                = ");
        builder.append(" (").append(getFlags()).append(" )\n");
        builder.append("         .fHdr                     = ").append(isFHdr()).append('\n');
        builder.append("         .bx                       = ").append(getBx()).append('\n');
        builder.append("         .by                       = ").append(getBy()).append('\n');
        builder.append("         .wr                       = ").append(getWr()).append('\n');
        builder.append("         .wrk                      = ").append(getWrk()).append('\n');
        builder.append("         .fRcaSimple               = ").append(isFRcaSimple()).append('\n');
        builder.append("         .fBelowText               = ").append(isFBelowText()).append('\n');
        builder.append("         .fAnchorLock              = ").append(isFAnchorLock()).append('\n');
        builder.append("    .cTxbx                = ");
        builder.append(" (").append(getCTxbx()).append(" )\n");

        builder.append("[/FSPA]\n");
        return builder.toString();
    }


    @Internal
    public int getSpid()
    {
        return field_1_spid;
    }


    @Internal
    public void setSpid( int field_1_spid )
    {
        this.field_1_spid = field_1_spid;
    }


    @Internal
    public int getXaLeft()
    {
        return field_2_xaLeft;
    }


    @Internal
    public void setXaLeft( int field_2_xaLeft )
    {
        this.field_2_xaLeft = field_2_xaLeft;
    }

    @Internal
    public int getYaTop()
    {
        return field_3_yaTop;
    }

    @Internal
    public void setYaTop( int field_3_yaTop )
    {
        this.field_3_yaTop = field_3_yaTop;
    }

    @Internal
    public int getXaRight()
    {
        return field_4_xaRight;
    }


    @Internal
    public void setXaRight( int field_4_xaRight )
    {
        this.field_4_xaRight = field_4_xaRight;
    }


    @Internal
    public int getYaBottom()
    {
        return field_5_yaBottom;
    }


    @Internal
    public void setYaBottom( int field_5_yaBottom )
    {
        this.field_5_yaBottom = field_5_yaBottom;
    }

    @Internal
    public short getFlags()
    {
        return field_6_flags;
    }

    @Internal
    public void setFlags( short field_6_flags )
    {
        this.field_6_flags = field_6_flags;
    }

    @Internal
    public int getCTxbx()
    {
        return field_7_cTxbx;
    }

    @Internal
    public void setCTxbx( int field_7_cTxbx )
    {
        this.field_7_cTxbx = field_7_cTxbx;
    }


    @Internal
    public void setFHdr( boolean value )
    {
        field_6_flags = (short)fHdr.setBoolean(field_6_flags, value);
    }


    @Internal
    public boolean isFHdr()
    {
        return fHdr.isSet(field_6_flags);
    }


    @Internal
    public void setBx( byte value )
    {
        field_6_flags = (short)bx.setValue(field_6_flags, value);
    }


    @Internal
    public byte getBx()
    {
        return ( byte )bx.getValue(field_6_flags);
    }

    @Internal
    public void setBy( byte value )
    {
        field_6_flags = (short)by.setValue(field_6_flags, value);
    }

    @Internal
    public byte getBy()
    {
        return ( byte )by.getValue(field_6_flags);
    }


    @Internal
    public void setWr( byte value )
    {
        field_6_flags = (short)wr.setValue(field_6_flags, value);
    }


    @Internal
    public byte getWr()
    {
        return ( byte )wr.getValue(field_6_flags);
    }


    @Internal
    public void setWrk( byte value )
    {
        field_6_flags = (short)wrk.setValue(field_6_flags, value);
    }

    @Internal
    public byte getWrk()
    {
        return ( byte )wrk.getValue(field_6_flags);
    }


    @Internal
    public void setFRcaSimple( boolean value )
    {
        field_6_flags = (short)fRcaSimple.setBoolean(field_6_flags, value);
    }


    @Internal
    public boolean isFRcaSimple()
    {
        return fRcaSimple.isSet(field_6_flags);
    }

    @Internal
    public void setFBelowText( boolean value )
    {
        field_6_flags = (short)fBelowText.setBoolean(field_6_flags, value);
    }

    @Internal
    public boolean isFBelowText()
    {
        return fBelowText.isSet(field_6_flags);
    }


    @Internal
    public void setFAnchorLock( boolean value )
    {
        field_6_flags = (short)fAnchorLock.setBoolean(field_6_flags, value);
    }


    @Internal
    public boolean isFAnchorLock()
    {
        return fAnchorLock.isSet(field_6_flags);
    }

}
