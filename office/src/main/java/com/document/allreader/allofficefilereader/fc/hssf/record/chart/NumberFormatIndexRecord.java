

package com.document.allreader.allofficefilereader.fc.hssf.record.chart;


import com.document.allreader.allofficefilereader.fc.hssf.record.RecordInputStream;
import com.document.allreader.allofficefilereader.fc.hssf.record.StandardRecord;
import com.document.allreader.allofficefilereader.fc.util.HexDump;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * The number format index record indexes format table.  This applies to an axis.<p/>
 * 
 * @author Glen Stampoultzis (glens at apache.org)
 */
public final class NumberFormatIndexRecord extends StandardRecord {
    public final static short      sid                             = 0x104E;
    private  short      field_1_formatIndex;


    public NumberFormatIndexRecord()
    {

    }

    public NumberFormatIndexRecord(RecordInputStream in)
    {
        field_1_formatIndex            = in.readShort();
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[IFMT]\n");
        buffer.append("    .formatIndex          = ")
            .append("0x").append(HexDump.toHex(  getFormatIndex ()))
            .append(" (").append( getFormatIndex() ).append(" )");
        buffer.append(System.getProperty("line.separator")); 

        buffer.append("[/IFMT]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(field_1_formatIndex);
    }

    protected int getDataSize() {
        return 2;
    }

    public short getSid()
    {
        return sid;
    }

    public Object clone() {
        NumberFormatIndexRecord rec = new NumberFormatIndexRecord();
    
        rec.field_1_formatIndex = field_1_formatIndex;
        return rec;
    }




    /**
     * Get the format index field for the NumberFormatIndex record.
     */
    public short getFormatIndex()
    {
        return field_1_formatIndex;
    }

    /**
     * Set the format index field for the NumberFormatIndex record.
     */
    public void setFormatIndex(short field_1_formatIndex)
    {
        this.field_1_formatIndex = field_1_formatIndex;
    }
}
