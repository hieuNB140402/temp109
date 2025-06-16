

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.HexDump;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;
import com.document.allreader.allofficefilereader.fc.util.StringUtil;



public final class TextBytesAtom extends RecordAtom
{
	private byte[] _header;
	private static long _type = 4008l;

	private byte[] _text;

	public String getText() {
		return StringUtil.getFromCompressedUnicode(_text,0,_text.length);
	}

	public void setText(byte[] b) {
		_text = b;

		LittleEndian.putInt(_header,4,_text.length);
	}

	protected TextBytesAtom(byte[] source, int start, int len) {

		if(len < 8) { len = 8; }
		_header = new byte[8];
		System.arraycopy(source,start,_header,0,8);

		_text = new byte[len-8];
		System.arraycopy(source,start+8,_text,0,len-8);
	}

	public TextBytesAtom() {
		_header = new byte[8];
		LittleEndian.putUShort(_header, 0, 0);
		LittleEndian.putUShort(_header, 2, (int)_type);
		LittleEndian.putInt(_header, 4, 0);

		_text = new byte[]{};
	}


	public long getRecordType() { return _type; }


	public String toString() {
        StringBuffer out = new StringBuffer();
        out.append( "TextBytesAtom:\n");
		out.append( HexDump.dump(_text, 0, 0) );
		return out.toString();
	}

	public void dispose()
	{
	    _header = null;
	    _text = null;
	}
}
