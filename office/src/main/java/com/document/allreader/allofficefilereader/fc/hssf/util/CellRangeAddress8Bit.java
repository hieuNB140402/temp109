

package com.document.allreader.allofficefilereader.fc.hssf.util;


import com.document.allreader.allofficefilereader.fc.ss.util.CellRangeAddressBase;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianByteArrayOutputStream;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianInput;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * See OOO documentation: excelfileformat.pdf sec 2.5.14 - 'Cell Range Address'<p/>
 *
 * Like {@link CellRangeAddress} except column fields are 8-bit.
 *
 * @author Josh Micich
 */
public final class CellRangeAddress8Bit extends CellRangeAddressBase {

	public static final int ENCODED_SIZE = 6;

	public CellRangeAddress8Bit(int firstRow, int lastRow, int firstCol, int lastCol) {
		super(firstRow, lastRow, firstCol, lastCol);
	}

	public CellRangeAddress8Bit(LittleEndianInput in) {
		super(readUShortAndCheck(in), in.readUShort(), in.readUByte(), in.readUByte());
	}

	private static int readUShortAndCheck(LittleEndianInput in) {
		if (in.available() < ENCODED_SIZE) {
			// Ran out of data
			throw new RuntimeException("Ran out of data reading CellRangeAddress");
		}
		return in.readUShort();
	}

	/**
	 * @deprecated use {@link #serialize(LittleEndianOutput)}
	 */
	public int serialize(int offset, byte[] data) {
		serialize(new LittleEndianByteArrayOutputStream(data, offset, ENCODED_SIZE));
		return ENCODED_SIZE;
	}
	public void serialize(LittleEndianOutput out) {
		out.writeShort(getFirstRow());
		out.writeShort(getLastRow());
		out.writeByte(getFirstColumn());
		out.writeByte(getLastColumn());
	}

	public CellRangeAddress8Bit copy() {
		return new CellRangeAddress8Bit(getFirstRow(), getLastRow(), getFirstColumn(), getLastColumn());
	}

	public static int getEncodedSize(int numberOfItems) {
		return numberOfItems * ENCODED_SIZE;
	}
}
