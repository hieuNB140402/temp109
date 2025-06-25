

package com.document.allreader.allofficefilereader.fc.hssf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;

/**
 * Title: Uncalced Record
 * <P>
 * If this record occurs in the Worksheet Substream, it indicates that the formulas have not 
 * been recalculated before the document was saved.
 * 
 * @author Olivier Leprince
 */
public final class UncalcedRecord extends StandardRecord  {
	public final static short sid = 0x005E;

    private short _reserved;

	public UncalcedRecord() {
        _reserved = 0;
	}

	public short getSid() {
		return sid;
	}

	public UncalcedRecord(RecordInputStream in) {
		_reserved = in.readShort(); // unused
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[UNCALCED]\n");
        buffer.append("    _reserved: ").append(_reserved).append('\n');
		buffer.append("[/UNCALCED]\n");
		return buffer.toString();
	}

	public void serialize(LittleEndianOutput out) {
		out.writeShort(_reserved);
	}

	protected int getDataSize() {
		return 2;
	}

	public static int getStaticRecordSize() {
		return 6;
	}
}
