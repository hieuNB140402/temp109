

package com.document.allreader.allofficefilereader.fc.hssf.record.common;


import com.document.allreader.allofficefilereader.fc.hssf.record.RecordInputStream;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * Title: FtrHeader (Future Record Header) common record part
 * <P>
 * This record part specifies a header for a Ftr (Future)
 *  style record, which includes extra attributes above and
 *  beyond those of a traditional record. 
 */
public final class FtrHeader {
	/** This MUST match the type on the containing record */
	private short recordType;
	/** This is a FrtFlags */
	private short grbitFrt;
	/** MUST be 8 bytes and all zero */
	private byte[] reserved;

	public FtrHeader() {
		reserved = new byte[8];
	}

	public FtrHeader(RecordInputStream in) {
		recordType = in.readShort();
		grbitFrt   = in.readShort();
		
		reserved = new byte[8];
		in.read(reserved, 0, 8);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" [FUTURE HEADER]\n");
		buffer.append("   Type " + recordType);
		buffer.append("   Flags " + grbitFrt);
		buffer.append(" [/FUTURE HEADER]\n");
		return buffer.toString();
	}

	public void serialize(LittleEndianOutput out) {
		out.writeShort(recordType);
		out.writeShort(grbitFrt);
		out.write(reserved);
	}

	public static int getDataSize() {
		return 12;
	}

	public short getRecordType() {
		return recordType;
	}
	public void setRecordType(short recordType) {
		this.recordType = recordType;
	}

	public short getGrbitFrt() {
		return grbitFrt;
	}
	public void setGrbitFrt(short grbitFrt) {
		this.grbitFrt = grbitFrt;
	}

	public byte[] getReserved() {
		return reserved;
	}
	public void setReserved(byte[] reserved) {
		this.reserved = reserved;
	}
}
