
package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



@Internal
public class Colorref implements Cloneable
{
    private int value;

    public Colorref()
    {
        this.value = -1;
    }

    public Colorref( byte[] data, int offset )
    {
        this.value = LittleEndian.getInt( data, offset );
    }

    public Colorref( int value )
    {
        this.value = value;
    }

    @Override
    public Colorref clone() throws CloneNotSupportedException
    {
        return new Colorref( this.value );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Colorref other = (Colorref) obj;
        if ( value != other.value )
            return false;
        return true;
    }

    public int getValue()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        return value;
    }

    public boolean isEmpty()
    {
        return value == -1;
    }

    public void setValue( int value )
    {
        this.value = value;
    }

    public byte[] toByteArray()
    {
        if ( isEmpty() )
            throw new IllegalStateException(
                    "Structure state (EMPTY) is not good for serialization" );

        byte[] bs = new byte[4];
        LittleEndian.putInt( bs, 0, this.value );
        return bs;
    }

    @Override
    public String toString()
    {
        if ( isEmpty() )
            return "[COLORREF] EMPTY";

        return "[COLORREF] 0x" + Integer.toHexString( value );
    }
}
