

package com.document.allreader.allofficefilereader.fc.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Josh Micich
 */
public final class LittleEndianOutputStream extends FilterOutputStream implements LittleEndianOutput {
	public LittleEndianOutputStream(OutputStream out) {
		super(out);
	}

	public void writeByte(int v) {
		try {
			out.write(v);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void writeDouble(double v) {
		writeLong(Double.doubleToLongBits(v));
	}

	public void writeInt(int v) {
		int b3 = (v >>> 24) & 0xFF;
		int b2 = (v >>> 16) & 0xFF;
		int b1 = (v >>>  8) & 0xFF;
		int b0 = (v >>>  0) & 0xFF;
		try {
			out.write(b0);
			out.write(b1);
			out.write(b2);
			out.write(b3);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void writeLong(long v) {
		writeInt((int)(v >>  0));
		writeInt((int)(v >> 32));
	}

	public void writeShort(int v) {
		int b1 = (v >>>  8) & 0xFF;
		int b0 = (v >>>  0) & 0xFF;
		try {
			out.write(b0);
			out.write(b1);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public void write(byte[] b) {
		// suppress IOException for interface method
		try {
			super.write(b);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public void write(byte[] b, int off, int len) {
		// suppress IOException for interface method
		try {
			super.write(b, off, len);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
