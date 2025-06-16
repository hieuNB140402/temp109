

package com.document.allreader.allofficefilereader.fc.ddf;

import com.document.allreader.allofficefilereader.fc.util.HexDump;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public class EscherSimpleProperty extends EscherProperty
{
    protected int propertyValue;



    public EscherSimpleProperty( short id, int propertyValue )
    {
        super( id );
        this.propertyValue = propertyValue;
    }


    public EscherSimpleProperty( short propertyNumber, boolean isComplex, boolean isBlipId, int propertyValue )
    {
        super( propertyNumber, isComplex, isBlipId );
        this.propertyValue = propertyValue;
    }


    public int serializeSimplePart( byte[] data, int offset )
    {
        LittleEndian.putShort(data, offset, getId());
        LittleEndian.putInt(data, offset + 2, propertyValue);
        return 6;
    }


    public int serializeComplexPart( byte[] data, int pos )
    {
        return 0;
    }


    public int getPropertyValue()
    {
        return propertyValue;
    }


    public boolean equals( Object o )
    {
        if ( this == o ) return true;
        if ( !( o instanceof EscherSimpleProperty ) ) return false;

        final EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) o;

        if ( propertyValue != escherSimpleProperty.propertyValue ) return false;
        if ( getId() != escherSimpleProperty.getId() ) return false;

        return true;
    }


    public int hashCode()
    {
        return propertyValue;
    }


    public String toString()
    {
        return "propNum: " + getPropertyNumber()
                + ", RAW: 0x" + HexDump.toHex( getId() )
                + ", propName: " + EscherProperties.getPropertyName( getPropertyNumber() )
                + ", complex: " + isComplex()
                + ", blipId: " + isBlipId()
                + ", value: " + propertyValue + " (0x" + HexDump.toHex(propertyValue) + ")";
    }

}
