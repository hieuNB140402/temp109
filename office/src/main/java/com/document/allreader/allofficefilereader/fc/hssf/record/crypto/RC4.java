

package com.document.allreader.allofficefilereader.fc.hssf.record.crypto;

import com.document.allreader.allofficefilereader.fc.util.HexDump;

/**
 * Simple implementation of the alleged RC4 algorithm.
 *
 * Inspired by <A HREF="http://en.wikipedia.org/wiki/RC4">wikipedia's RC4 article</A>
 *
 * @author Josh Micich
 */
final class RC4 {

    private int _i, _j;
	private final byte[] _s = new byte[256];

	public RC4(byte[] key) {
		int key_length = key.length;

	    for (int i = 0; i < 256; i++)
	        _s[i] = (byte)i;

	    for (int i=0, j=0; i < 256; i++) {
	        byte temp;

	        j = (j + key[i % key_length] + _s[i]) & 255;
	        temp = _s[i];
	        _s[i] = _s[j];
	        _s[j] = temp;
	    }

	    _i = 0;
	    _j = 0;
	}

	public byte output() {
	    byte temp;
	    _i = (_i + 1) & 255;
	    _j = (_j + _s[_i]) & 255;

	    temp = _s[_i];
	    _s[_i] = _s[_j];
	    _s[_j] = temp;

	    return _s[(_s[_i] + _s[_j]) & 255];
	}

	public void encrypt(byte[] in) {
		for (int i = 0; i < in.length; i++) {
			in[i] = (byte) (in[i] ^ output());
		}
	}
	public void encrypt(byte[] in, int offset, int len) {
		int end = offset+len;
		for (int i = offset; i < end; i++) {
			in[i] = (byte) (in[i] ^ output());
		}

	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(getClass().getName()).append(" [");
		sb.append("i=").append(_i);
		sb.append(" j=").append(_j);
		sb.append("]");
		sb.append("\n");
		sb.append(HexDump.dump(_s, 0, 0));

		return sb.toString();
	}
}
