
package com.document.allreader.allofficefilereader.fc.hwpf.model.types;

import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


@Internal
public abstract class FRDAbstractType
{

    protected short field_1_nAuto;

    protected FRDAbstractType()
    {
    }

    protected void fillFields( byte[] data, int offset )
    {
        field_1_nAuto = LittleEndian.getShort( data, 0x0 + offset );
    }

    public void serialize( byte[] data, int offset )
    {
        LittleEndian.putShort( data, 0x0 + offset, field_1_nAuto );
    }


    public static int getSize()
    {
        return 0 + 2;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "[FRD]\n" );
        builder.append( "    .nAuto                = " );
        builder.append( " (" ).append( getNAuto() ).append( " )\n" );

        builder.append( "[/FRD]\n" );
        return builder.toString();
    }


    public short getNAuto()
    {
        return field_1_nAuto;
    }


    public void setNAuto( short field_1_nAuto )
    {
        this.field_1_nAuto = field_1_nAuto;
    }

}
