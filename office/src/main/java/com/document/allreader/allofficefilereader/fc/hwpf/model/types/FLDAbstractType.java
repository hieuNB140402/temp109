

package com.document.allreader.allofficefilereader.fc.hwpf.model.types;

import com.document.allreader.allofficefilereader.fc.hwpf.model.HDFType;
import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.Internal;


@Internal
public abstract class FLDAbstractType implements HDFType
{

    protected byte field_1_chHolder;
    private static BitField ch = new BitField( 0x1f );
    private static BitField reserved = new BitField( 0xe0 );
    protected byte field_2_flt;
    private static BitField fDiffer = new BitField( 0x01 );
    private static BitField fZombieEmbed = new BitField( 0x02 );
    private static BitField fResultDirty = new BitField( 0x04 );
    private static BitField fResultEdited = new BitField( 0x08 );
    private static BitField fLocked = new BitField( 0x10 );
    private static BitField fPrivateResult = new BitField( 0x20 );
    private static BitField fNested = new BitField( 0x40 );
    private static BitField fHasSep = new BitField( 0x40 );

    public FLDAbstractType()
    {

    }

    protected void fillFields( byte[] data, int offset )
    {
        field_1_chHolder = data[0x0 + offset];
        field_2_flt = data[0x1 + offset];
    }

    public void serialize( byte[] data, int offset )
    {
        data[0x0 + offset] = field_1_chHolder;
        data[0x1 + offset] = field_2_flt;
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append( "[FLD]\n" );

        buffer.append( "    .chHolder             = " );
        buffer.append( " (" ).append( getChHolder() ).append( " )\n" );
        buffer.append( "         .ch                       = " )
                .append( getCh() ).append( '\n' );
        buffer.append( "         .reserved                 = " )
                .append( getReserved() ).append( '\n' );

        buffer.append( "    .flt                  = " );
        buffer.append( " (" ).append( getFlt() ).append( " )\n" );
        buffer.append( "         .fDiffer                  = " )
                .append( isFDiffer() ).append( '\n' );
        buffer.append( "         .fZombieEmbed             = " )
                .append( isFZombieEmbed() ).append( '\n' );
        buffer.append( "         .fResultDirty             = " )
                .append( isFResultDirty() ).append( '\n' );
        buffer.append( "         .fResultEdited            = " )
                .append( isFResultEdited() ).append( '\n' );
        buffer.append( "         .fLocked                  = " )
                .append( isFLocked() ).append( '\n' );
        buffer.append( "         .fPrivateResult           = " )
                .append( isFPrivateResult() ).append( '\n' );
        buffer.append( "         .fNested                  = " )
                .append( isFNested() ).append( '\n' );
        buffer.append( "         .fHasSep                  = " )
                .append( isFHasSep() ).append( '\n' );

        buffer.append( "[/FLD]\n" );
        return buffer.toString();
    }


    public static int getSize()
    {
        return 4 + +1 + 1;
    }

    public byte getChHolder()
    {
        return field_1_chHolder;
    }


    public void setChHolder( byte field_1_chHolder )
    {
        this.field_1_chHolder = field_1_chHolder;
    }


    public byte getFlt()
    {
        return field_2_flt;
    }

    public void setFlt( byte field_2_flt )
    {
        this.field_2_flt = field_2_flt;
    }


    public void setCh( byte value )
    {
        field_1_chHolder = (byte) ch.setValue( field_1_chHolder, value );

    }


    public byte getCh()
    {
        return (byte) ch.getValue( field_1_chHolder );

    }


    public void setReserved( byte value )
    {
        field_1_chHolder = (byte) reserved.setValue( field_1_chHolder, value );

    }

    public byte getReserved()
    {
        return (byte) reserved.getValue( field_1_chHolder );

    }

    public void setFDiffer( boolean value )
    {
        field_2_flt = (byte) fDiffer.setBoolean( field_2_flt, value );

    }

    public boolean isFDiffer()
    {
        return fDiffer.isSet( field_2_flt );

    }


    public void setFZombieEmbed( boolean value )
    {
        field_2_flt = (byte) fZombieEmbed.setBoolean( field_2_flt, value );

    }


    public boolean isFZombieEmbed()
    {
        return fZombieEmbed.isSet( field_2_flt );

    }


    public void setFResultDirty( boolean value )
    {
        field_2_flt = (byte) fResultDirty.setBoolean( field_2_flt, value );

    }


    public boolean isFResultDirty()
    {
        return fResultDirty.isSet( field_2_flt );

    }


    public void setFResultEdited( boolean value )
    {
        field_2_flt = (byte) fResultEdited.setBoolean( field_2_flt, value );

    }


    public boolean isFResultEdited()
    {
        return fResultEdited.isSet( field_2_flt );

    }

    public void setFLocked( boolean value )
    {
        field_2_flt = (byte) fLocked.setBoolean( field_2_flt, value );

    }


    public boolean isFLocked()
    {
        return fLocked.isSet( field_2_flt );

    }


    public void setFPrivateResult( boolean value )
    {
        field_2_flt = (byte) fPrivateResult.setBoolean( field_2_flt, value );

    }


    public boolean isFPrivateResult()
    {
        return fPrivateResult.isSet( field_2_flt );

    }


    public void setFNested( boolean value )
    {
        field_2_flt = (byte) fNested.setBoolean( field_2_flt, value );

    }



    public boolean isFNested()
    {
        return fNested.isSet( field_2_flt );

    }

    public void setFHasSep( boolean value )
    {
        field_2_flt = (byte) fHasSep.setBoolean( field_2_flt, value );

    }


    public boolean isFHasSep()
    {
        return fHasSep.isSet( field_2_flt );

    }
}

