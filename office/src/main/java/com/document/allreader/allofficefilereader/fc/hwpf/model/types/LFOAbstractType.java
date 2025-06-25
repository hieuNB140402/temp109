
package com.document.allreader.allofficefilereader.fc.hwpf.model.types;

import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



@Internal
public abstract class LFOAbstractType
{

    protected int field_1_lsid;
    protected int field_2_reserved1;
    protected int field_3_reserved2;
    protected byte field_4_clfolvl;
    protected byte field_5_ibstFltAutoNum;
    protected byte field_6_grfhic;
    private static BitField fHtmlChecked = new BitField( 0x01 );
    private static BitField fHtmlUnsupported = new BitField( 0x02 );
    private static BitField fHtmlListTextNotSharpDot = new BitField( 0x04 );
    private static BitField fHtmlNotPeriod = new BitField( 0x08 );
    private static BitField fHtmlFirstLineMismatch = new BitField( 0x10 );
    private static BitField fHtmlTabLeftIndentMismatch = new BitField(
            0x20 );
    private static BitField fHtmlHangingIndentBeneathNumber = new BitField(
            0x40 );
    private static BitField fHtmlBuiltInBullet = new BitField( 0x80 );
    protected byte field_7_reserved3;

    protected LFOAbstractType()
    {
    }

    protected void fillFields( byte[] data, int offset )
    {
        field_1_lsid = LittleEndian.getInt( data, 0x0 + offset );
        field_2_reserved1 = LittleEndian.getInt( data, 0x4 + offset );
        field_3_reserved2 = LittleEndian.getInt( data, 0x8 + offset );
        field_4_clfolvl = data[0xc + offset];
        field_5_ibstFltAutoNum = data[0xd + offset];
        field_6_grfhic = data[0xe + offset];
        field_7_reserved3 = data[0xf + offset];
    }

    public void serialize( byte[] data, int offset )
    {
        LittleEndian.putInt( data, 0x0 + offset, field_1_lsid );
        LittleEndian.putInt( data, 0x4 + offset, field_2_reserved1 );
        LittleEndian.putInt( data, 0x8 + offset, field_3_reserved2 );
        data[0xc + offset] = field_4_clfolvl;
        data[0xd + offset] = field_5_ibstFltAutoNum;
        data[0xe + offset] = field_6_grfhic;
        data[0xf + offset] = field_7_reserved3;
    }

    public static int getSize()
    {
        return 0 + 4 + 4 + 4 + 1 + 1 + 1 + 1;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "[LFO]\n" );
        builder.append( "    .lsid                 = " );
        builder.append( " (" ).append( getLsid() ).append( " )\n" );
        builder.append( "    .reserved1            = " );
        builder.append( " (" ).append( getReserved1() ).append( " )\n" );
        builder.append( "    .reserved2            = " );
        builder.append( " (" ).append( getReserved2() ).append( " )\n" );
        builder.append( "    .clfolvl              = " );
        builder.append( " (" ).append( getClfolvl() ).append( " )\n" );
        builder.append( "    .ibstFltAutoNum       = " );
        builder.append( " (" ).append( getIbstFltAutoNum() ).append( " )\n" );
        builder.append( "    .grfhic               = " );
        builder.append( " (" ).append( getGrfhic() ).append( " )\n" );
        builder.append( "         .fHtmlChecked             = " )
                .append( isFHtmlChecked() ).append( '\n' );
        builder.append( "         .fHtmlUnsupported         = " )
                .append( isFHtmlUnsupported() ).append( '\n' );
        builder.append( "         .fHtmlListTextNotSharpDot     = " )
                .append( isFHtmlListTextNotSharpDot() ).append( '\n' );
        builder.append( "         .fHtmlNotPeriod           = " )
                .append( isFHtmlNotPeriod() ).append( '\n' );
        builder.append( "         .fHtmlFirstLineMismatch     = " )
                .append( isFHtmlFirstLineMismatch() ).append( '\n' );
        builder.append( "         .fHtmlTabLeftIndentMismatch     = " )
                .append( isFHtmlTabLeftIndentMismatch() ).append( '\n' );
        builder.append( "         .fHtmlHangingIndentBeneathNumber     = " )
                .append( isFHtmlHangingIndentBeneathNumber() ).append( '\n' );
        builder.append( "         .fHtmlBuiltInBullet       = " )
                .append( isFHtmlBuiltInBullet() ).append( '\n' );
        builder.append( "    .reserved3            = " );
        builder.append( " (" ).append( getReserved3() ).append( " )\n" );

        builder.append( "[/LFO]\n" );
        return builder.toString();
    }


    public int getLsid()
    {
        return field_1_lsid;
    }


    public void setLsid( int field_1_lsid )
    {
        this.field_1_lsid = field_1_lsid;
    }


    public int getReserved1()
    {
        return field_2_reserved1;
    }

    public void setReserved1( int field_2_reserved1 )
    {
        this.field_2_reserved1 = field_2_reserved1;
    }


    public int getReserved2()
    {
        return field_3_reserved2;
    }


    public void setReserved2( int field_3_reserved2 )
    {
        this.field_3_reserved2 = field_3_reserved2;
    }


    public byte getClfolvl()
    {
        return field_4_clfolvl;
    }

    public void setClfolvl( byte field_4_clfolvl )
    {
        this.field_4_clfolvl = field_4_clfolvl;
    }


    public byte getIbstFltAutoNum()
    {
        return field_5_ibstFltAutoNum;
    }


    public void setIbstFltAutoNum( byte field_5_ibstFltAutoNum )
    {
        this.field_5_ibstFltAutoNum = field_5_ibstFltAutoNum;
    }


    public byte getGrfhic()
    {
        return field_6_grfhic;
    }


    public void setGrfhic( byte field_6_grfhic )
    {
        this.field_6_grfhic = field_6_grfhic;
    }


    public byte getReserved3()
    {
        return field_7_reserved3;
    }


    public void setReserved3( byte field_7_reserved3 )
    {
        this.field_7_reserved3 = field_7_reserved3;
    }


    public void setFHtmlChecked( boolean value )
    {
        field_6_grfhic = (byte) fHtmlChecked.setBoolean( field_6_grfhic, value );
    }


    public boolean isFHtmlChecked()
    {
        return fHtmlChecked.isSet( field_6_grfhic );
    }

    public void setFHtmlUnsupported( boolean value )
    {
        field_6_grfhic = (byte) fHtmlUnsupported.setBoolean( field_6_grfhic,
                value );
    }


    public boolean isFHtmlUnsupported()
    {
        return fHtmlUnsupported.isSet( field_6_grfhic );
    }


    public void setFHtmlListTextNotSharpDot( boolean value )
    {
        field_6_grfhic = (byte) fHtmlListTextNotSharpDot.setBoolean(
                field_6_grfhic, value );
    }


    public boolean isFHtmlListTextNotSharpDot()
    {
        return fHtmlListTextNotSharpDot.isSet( field_6_grfhic );
    }


    public void setFHtmlNotPeriod( boolean value )
    {
        field_6_grfhic = (byte) fHtmlNotPeriod.setBoolean( field_6_grfhic,
                value );
    }


    public boolean isFHtmlNotPeriod()
    {
        return fHtmlNotPeriod.isSet( field_6_grfhic );
    }


    public void setFHtmlFirstLineMismatch( boolean value )
    {
        field_6_grfhic = (byte) fHtmlFirstLineMismatch.setBoolean(
                field_6_grfhic, value );
    }


    public boolean isFHtmlFirstLineMismatch()
    {
        return fHtmlFirstLineMismatch.isSet( field_6_grfhic );
    }


    public void setFHtmlTabLeftIndentMismatch( boolean value )
    {
        field_6_grfhic = (byte) fHtmlTabLeftIndentMismatch.setBoolean(
                field_6_grfhic, value );
    }


    public boolean isFHtmlTabLeftIndentMismatch()
    {
        return fHtmlTabLeftIndentMismatch.isSet( field_6_grfhic );
    }


    public void setFHtmlHangingIndentBeneathNumber( boolean value )
    {
        field_6_grfhic = (byte) fHtmlHangingIndentBeneathNumber.setBoolean(
                field_6_grfhic, value );
    }


    public boolean isFHtmlHangingIndentBeneathNumber()
    {
        return fHtmlHangingIndentBeneathNumber.isSet( field_6_grfhic );
    }

    public void setFHtmlBuiltInBullet( boolean value )
    {
        field_6_grfhic = (byte) fHtmlBuiltInBullet.setBoolean( field_6_grfhic,
                value );
    }


    public boolean isFHtmlBuiltInBullet()
    {
        return fHtmlBuiltInBullet.isSet( field_6_grfhic );
    }

}
