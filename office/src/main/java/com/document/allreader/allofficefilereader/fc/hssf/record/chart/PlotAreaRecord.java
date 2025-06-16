

package com.document.allreader.allofficefilereader.fc.hssf.record.chart;


import com.document.allreader.allofficefilereader.fc.hssf.record.RecordInputStream;
import com.document.allreader.allofficefilereader.fc.hssf.record.StandardRecord;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * preceeds and identifies a frame as belonging to the plot area.<p/>
 * 
 * @author Andrew C. Oliver (acoliver at apache.org)
 */
public final class PlotAreaRecord extends StandardRecord {
    public final static short      sid                             = 0x1035;


    public PlotAreaRecord()
    {

    }

    /**
     * @param in unused (since this record has no data)
     */
    public PlotAreaRecord(RecordInputStream in)
    {

    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[PLOTAREA]\n");

        buffer.append("[/PLOTAREA]\n");
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
        PlotAreaRecord rec = new PlotAreaRecord();
    
        return rec;
    }
}
