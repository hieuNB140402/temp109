

package com.document.allreader.allofficefilereader.fc.hssf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndianByteArrayOutputStream;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;

/**
 * Subclasses of this class (the majority of BIFF records) are non-continuable.  This allows for
 * some simplification of serialization logic
 *
 * @author Josh Micich
 */
public abstract class StandardRecord extends Record {
	protected abstract int getDataSize();
	public final int getRecordSize() {
		return 4 + getDataSize();
	}
	@Override
	public final int serialize(int offset, byte[] data) {
		int dataSize = getDataSize();
		int recSize = 4 + dataSize;
		LittleEndianByteArrayOutputStream out = new LittleEndianByteArrayOutputStream(data, offset, recSize);
		out.writeShort(getSid());
		out.writeShort(dataSize);
		serialize(out);
		if (out.getWriteIndex() - offset != recSize) {
			throw new IllegalStateException("Error in serialization of (" + getClass().getName() + "): "
					+ "Incorrect number of bytes written - expected "
					+ recSize + " but got " + (out.getWriteIndex() - offset));
		}
		return recSize;
	}

	/**
	 * Write the data content of this BIFF record.  The 'ushort sid' and 'ushort size' header fields
	 * have already been written by the superclass.<br/>
	 *
	 * The subclass must write the exact number of bytes as reported by {@link com.document.allreader.allofficefilereader.fc.hssf.record.Record#getRecordSize()}}
	 */
	protected abstract void serialize(LittleEndianOutput out);
}
