

package com.document.allreader.allofficefilereader.fc.hwpf.model.types;

import com.document.allreader.allofficefilereader.fc.hwpf.model.HDFType;
import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;

@Internal
public abstract class TLPAbstractType implements HDFType
{

    protected short field_1_itl;
    protected byte field_2_tlp_flags;
    private static BitField fBorders = new BitField( 0x0001 );
    private static BitField fShading = new BitField( 0x0002 );
    private static BitField fFont = new BitField( 0x0004 );
    private static BitField fColor = new BitField( 0x0008 );
    private static BitField fBestFit = new BitField( 0x0010 );
    private static BitField fHdrRows = new BitField( 0x0020 );
    private static BitField fLastRow = new BitField( 0x0040 );

    public TLPAbstractType()
    {

    }

    protected void fillFields( byte[] data, int offset )
    {
        field_1_itl = LittleEndian.getShort( data, 0x0 + offset );
        field_2_tlp_flags = data[0x2 + offset];
    }

    public void serialize( byte[] data, int offset )
    {
        LittleEndian.putShort( data, 0x0 + offset, field_1_itl );
        data[0x2 + offset] = field_2_tlp_flags;
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append( "[TLP]\n" );

        buffer.append( "    .itl                  = " );
        buffer.append( " (" ).append( getItl() ).append( " )\n" );

        buffer.append( "    .tlp_flags            = " );
        buffer.append( " (" ).append( getTlp_flags() ).append( " )\n" );
        buffer.append( "         .fBorders                 = " )
                .append( isFBorders() ).append( '\n' );
        buffer.append( "         .fShading                 = " )
                .append( isFShading() ).append( '\n' );
        buffer.append( "         .fFont                    = " )
                .append( isFFont() ).append( '\n' );
        buffer.append( "         .fColor                   = " )
                .append( isFColor() ).append( '\n' );
        buffer.append( "         .fBestFit                 = " )
                .append( isFBestFit() ).append( '\n' );
        buffer.append( "         .fHdrRows                 = " )
                .append( isFHdrRows() ).append( '\n' );
        buffer.append( "         .fLastRow                 = " )
                .append( isFLastRow() ).append( '\n' );

        buffer.append( "[/TLP]\n" );
        return buffer.toString();
    }

    public int getSize()
    {
        return 4 + +2 + 1;
    }


    public short getItl()
    {
        return field_1_itl;
    }


    public void setItl( short field_1_itl )
    {
        this.field_1_itl = field_1_itl;
    }


    public byte getTlp_flags()
    {
        return field_2_tlp_flags;
    }


    public void setTlp_flags( byte field_2_tlp_flags )
    {
        this.field_2_tlp_flags = field_2_tlp_flags;
    }


    public void setFBorders( boolean value )
    {
        field_2_tlp_flags = (byte) fBorders.setBoolean( field_2_tlp_flags,
                value );

    }


    public boolean isFBorders()
    {
        return fBorders.isSet( field_2_tlp_flags );

    }


    public void setFShading( boolean value )
    {
        field_2_tlp_flags = (byte) fShading.setBoolean( field_2_tlp_flags,
                value );

    }

    public boolean isFShading()
    {
        return fShading.isSet( field_2_tlp_flags );

    }


    public void setFFont( boolean value )
    {
        field_2_tlp_flags = (byte) fFont.setBoolean( field_2_tlp_flags, value );

    }


    public boolean isFFont()
    {
        return fFont.isSet( field_2_tlp_flags );

    }


    public void setFColor( boolean value )
    {
        field_2_tlp_flags = (byte) fColor.setBoolean( field_2_tlp_flags, value );

    }


    public boolean isFColor()
    {
        return fColor.isSet( field_2_tlp_flags );

    }

    public void setFBestFit( boolean value )
    {
        field_2_tlp_flags = (byte) fBestFit.setBoolean( field_2_tlp_flags,
                value );

    }



    public boolean isFBestFit()
    {
        return fBestFit.isSet( field_2_tlp_flags );

    }


    public void setFHdrRows( boolean value )
    {
        field_2_tlp_flags = (byte) fHdrRows.setBoolean( field_2_tlp_flags,
                value );

    }


    public boolean isFHdrRows()
    {
        return fHdrRows.isSet( field_2_tlp_flags );

    }


    public void setFLastRow( boolean value )
    {
        field_2_tlp_flags = (byte) fLastRow.setBoolean( field_2_tlp_flags,
                value );

    }


    public boolean isFLastRow()
    {
        return fLastRow.isSet( field_2_tlp_flags );

    }

}

