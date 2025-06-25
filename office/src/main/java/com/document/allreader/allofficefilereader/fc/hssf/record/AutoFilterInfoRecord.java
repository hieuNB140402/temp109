



package com.document.allreader.allofficefilereader.fc.hssf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;

/**
 * The AutoFilterInfo record specifies the number of columns that have AutoFilter enabled
 * and indicates the beginning of the collection of AutoFilter records.
 *
 * @author Yegor Kozlov
 */

public final class AutoFilterInfoRecord
    extends StandardRecord
{
    public final static short sid = 0x9D;
    /**
     * Number of AutoFilter drop-down arrows on the sheet
     */
    private short             _cEntries;   // = 0;

    public AutoFilterInfoRecord()
    {
    }

    public AutoFilterInfoRecord(RecordInputStream in)
    {
        _cEntries = in.readShort();
    }

    /**
     * set the number of AutoFilter drop-down arrows on the sheet
     *
     * @param num  the number of AutoFilter drop-down arrows on the sheet
     */

    public void setNumEntries(short num)
    {
        _cEntries = num;
    }

    /**
     * get the number of AutoFilter drop-down arrows on the sheet
     *
     * @return the number of AutoFilter drop-down arrows on the sheet
     */

    public short getNumEntries()
    {
        return _cEntries;
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[AUTOFILTERINFO]\n");
        buffer.append("    .numEntries          = ")
            .append(_cEntries).append("\n");
        buffer.append("[/AUTOFILTERINFO]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(_cEntries);
    }

    protected int getDataSize() {
        return 2;
    }

    public short getSid()
    {
        return sid;
    }

    @Override
    public Object clone()
    {
    	return cloneViaReserialise();
    }
    
}
