

package com.document.allreader.allofficefilereader.fc.util;
/**
 *
 * @author Josh Micich
 */
public interface LittleEndianOutput {
	void writeByte(int v);
	void writeShort(int v);
	void writeInt(int v);
	void writeLong(long v);
	void writeDouble(double v);
	void write(byte[] b);
	void write(byte[] b, int offset, int len);
}
