

package com.document.allreader.allofficefilereader.fc.hssf.record;

import com.document.allreader.allofficefilereader.fc.util.HexDump;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;

/**
 * Title:        Label SST Record<P>
 * Description:  Refers to a string in the shared string table and is a column
 *               value.  <P>
 * REFERENCE:  PG 325 Microsoft Excel 97 Developer's Kit (ISBN: 1-57231-498-2)<P>
 * @author Andrew C. Oliver (acoliver at apache dot org)
 * @author Jason Height (jheight at chariot dot net dot au)
 */
public final class LabelSSTRecord extends CellRecord {
    public final static short sid = 0xfd;
    private int field_4_sst_index;

    public LabelSSTRecord() {
    	// fields uninitialised
    }

    public LabelSSTRecord(RecordInputStream in) {
        super(in);
        field_4_sst_index = in.readInt();
    }

    /**
     * set the index to the string in the SSTRecord
     *
     * @param index - of string in the SST Table
     * @see com.document.allreader.allofficefilereader.fc.hssf.record.SSTRecord
     */
    public void setSSTIndex(int index) {
        field_4_sst_index = index;
    }


    /**
     * get the index to the string in the SSTRecord
     *
     * @return index of string in the SST Table
     * @see com.document.allreader.allofficefilereader.fc.hssf.record.SSTRecord
     */
    public int getSSTIndex() {
        return field_4_sst_index;
    }
    
    @Override
    protected String getRecordName() {
    	return "LABELSST";
    }

    @Override
    protected void appendValueText(StringBuilder sb) {
		sb.append("  .sstIndex = ");
    	sb.append(HexDump.shortToHex(getXFIndex()));
    }
    @Override
    protected void serializeValue(LittleEndianOutput out) {
        out.writeInt(getSSTIndex());
    }

    @Override
    protected int getValueDataSize() {
        return 4;
    }

    public short getSid() {
        return sid;
    }

    public Object clone() {
      LabelSSTRecord rec = new LabelSSTRecord();
      copyBaseFields(rec);
      rec.field_4_sst_index = field_4_sst_index;
      return rec;
    }
}
