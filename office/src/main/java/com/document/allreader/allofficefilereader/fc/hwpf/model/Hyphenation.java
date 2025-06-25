

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.hwpf.model.types.HRESIAbstractType;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



@ Internal
public final class Hyphenation extends HRESIAbstractType implements Cloneable
{
    public Hyphenation()
    {
        super();
    }

    public Hyphenation(short hres)
    {
        byte[] data = new byte[2];
        LittleEndian.putShort(data, hres);
        fillFields(data, 0);
    }

    public Hyphenation clone()
    {
        try
        {
            return (Hyphenation)super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            throw new RuntimeException(e);
        }
    }

    @ Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Hyphenation other = (Hyphenation)obj;
        if (field_1_hres != other.field_1_hres)
            return false;
        if (field_2_chHres != other.field_2_chHres)
            return false;
        return true;
    }

    public short getValue()
    {
        byte[] data = new byte[2];
        serialize(data, 0);
        return LittleEndian.getShort(data);
    }

    @ Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + field_1_hres;
        result = prime * result + field_2_chHres;
        return result;
    }

    public boolean isEmpty()
    {
        return field_1_hres == 0 && field_2_chHres == 0;
    }

    @ Override
    public String toString()
    {
        if (isEmpty())
            return "[HRESI] EMPTY";

        return super.toString();
    }
}
