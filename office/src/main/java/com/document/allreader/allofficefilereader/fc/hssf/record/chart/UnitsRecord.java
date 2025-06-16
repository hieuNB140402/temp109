

package com.document.allreader.allofficefilereader.fc.hssf.record.chart;


import com.document.allreader.allofficefilereader.fc.hssf.record.RecordInputStream;
import com.document.allreader.allofficefilereader.fc.hssf.record.StandardRecord;
import com.document.allreader.allofficefilereader.fc.util.HexDump;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * The units record describes units.<p/>
 * 
 * @author Glen Stampoultzis (glens at apache.org)
 */
public final class UnitsRecord extends StandardRecord {
    public final static short      sid                             = 0x1001;
    private  short      field_1_units;


    public UnitsRecord()
    {

    }

    public UnitsRecord(RecordInputStream in)
    {
        field_1_units                  = in.readShort();

    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[UNITS]\n");
        buffer.append("    .units                = ")
            .append("0x").append(HexDump.toHex(  getUnits ()))
            .append(" (").append( getUnits() ).append(" )");
        buffer.append(System.getProperty("line.separator")); 

        buffer.append("[/UNITS]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(field_1_units);
    }

    protected int getDataSize() {
        return 2;
    }

    public short getSid()
    {
        return sid;
    }

    public Object clone() {
        UnitsRecord rec = new UnitsRecord();
    
        rec.field_1_units = field_1_units;
        return rec;
    }




    /**
     * Get the units field for the Units record.
     */
    public short getUnits()
    {
        return field_1_units;
    }

    /**
     * Set the units field for the Units record.
     */
    public void setUnits(short field_1_units)
    {
        this.field_1_units = field_1_units;
    }
}
