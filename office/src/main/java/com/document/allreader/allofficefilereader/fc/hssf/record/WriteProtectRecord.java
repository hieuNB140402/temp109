

package com.document.allreader.allofficefilereader.fc.hssf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;

/**
 * Title:        Write Protect Record<P>
 * Description:  Indicated that the sheet/workbook is write protected. 
 * REFERENCE:  PG 425 Microsoft Excel 97 Developer's Kit (ISBN: 1-57231-498-2)<P>
 * @version 3.0-pre
 */
public final class WriteProtectRecord extends StandardRecord {
    public final static short sid = 0x86;

    public WriteProtectRecord()
    {
    }

    /**
     * @param in unused (since this record has no data)
     */
    public WriteProtectRecord(RecordInputStream in)
    {
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[WRITEPROTECT]\n");
        buffer.append("[/WRITEPROTECT]\n");
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
}
