

package com.document.allreader.allofficefilereader.fc.hssf.record.chart;


import com.document.allreader.allofficefilereader.fc.hssf.record.RecordInputStream;
import com.document.allreader.allofficefilereader.fc.hssf.record.StandardRecord;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * The begin record defines the start of a block of records for a (grpahing
 * data object. This record is matched with a corresponding EndRecord.
 *
 * @see EndRecord
 *
 * @author Glen Stampoultzis (glens at apache.org)
 */
public final class BeginRecord extends StandardRecord {
    public static final short sid = 0x1033;

    public BeginRecord()
    {
    }

    /**
     * @param in unused (since this record has no data)
     */
    public BeginRecord(RecordInputStream in)
    {
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[BEGIN]\n");
        buffer.append("[/BEGIN]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
    }

    protected int getDataSize() {
        return 0;
    }

    public short getSid()
    {
        return sid;
    }
    
    public Object clone() {
       BeginRecord br = new BeginRecord();
       // No data so nothing to copy
       return br;
    }
}
