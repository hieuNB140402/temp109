

package com.document.allreader.allofficefilereader.fc.hwpf.model.types;

import com.document.allreader.allofficefilereader.fc.util.Internal;

@Internal
public abstract class HRESIAbstractType
{

    protected byte field_1_hres;
    public final static byte HRES_NO = 0;
    public final static byte HRES_NORMAL = 1;
    public final static byte HRES_ADD_LETTER_BEFORE = 2;
    public final static byte HRES_CHANGE_LETTER_BEFORE = 3;
    public final static byte HRES_DELETE_LETTER_BEFORE = 4;
    public final static byte HRES_CHANGE_LETTER_AFTER = 5;
    public final static byte HRES_DELETE_BEFORE_CHANGE_BEFORE = 6;
    protected byte field_2_chHres;

    protected HRESIAbstractType()
    {
    }

    protected void fillFields( byte[] data, int offset )
    {
        field_1_hres                   = data[ 0x0 + offset ];
        field_2_chHres                 = data[ 0x1 + offset ];
    }

    public void serialize( byte[] data, int offset )
    {
        data[ 0x0 + offset] = field_1_hres;
        data[ 0x1 + offset] = field_2_chHres;
    }


    public static int getSize()
    {
        return 4 +  + 1 + 1;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[HRESI]\n");
        builder.append("    .hres                 = ");
        builder.append(" (").append(getHres()).append(" )\n");
        builder.append("    .chHres               = ");
        builder.append(" (").append(getChHres()).append(" )\n");

        builder.append("[/HRESI]\n");
        return builder.toString();
    }


    public byte getHres()
    {
        return field_1_hres;
    }


    public void setHres( byte field_1_hres )
    {
        this.field_1_hres = field_1_hres;
    }


    public byte getChHres()
    {
        return field_2_chHres;
    }


    public void setChHres( byte field_2_chHres )
    {
        this.field_2_chHres = field_2_chHres;
    }

}
