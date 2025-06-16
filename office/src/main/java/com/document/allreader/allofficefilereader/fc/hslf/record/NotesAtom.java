

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;





public final class NotesAtom extends RecordAtom
{
	private byte[] _header;
	private static long _type = 1009l;

	private int slideID;
	private boolean followMasterObjects;
	private boolean followMasterScheme;
	private boolean followMasterBackground;
	private byte[] reserved;


	public int getSlideID() { return slideID; }
	public void setSlideID(int id) { slideID = id; }

	public boolean getFollowMasterObjects()    { return followMasterObjects; }
	public boolean getFollowMasterScheme()     { return followMasterScheme; }
	public boolean getFollowMasterBackground() { return followMasterBackground; }
	public void setFollowMasterObjects(boolean flag)    { followMasterObjects = flag; }
	public void setFollowMasterScheme(boolean flag)     { followMasterScheme = flag; }
	public void setFollowMasterBackground(boolean flag) { followMasterBackground = flag; }



	protected NotesAtom(byte[] source, int start, int len) {

		if(len < 8) { len = 8; }

		_header = new byte[8];
		System.arraycopy(source,start,_header,0,8);

		slideID = LittleEndian.getInt(source,start+8);

		int flags = LittleEndian.getUShort(source,start+12);
		if((flags&4) == 4) {
			followMasterBackground = true;
		} else {
			followMasterBackground = false;
		}
		if((flags&2) == 2) {
			followMasterScheme = true;
		} else {
			followMasterScheme = false;
		}
		if((flags&1) == 1) {
			followMasterObjects = true;
		} else {
			followMasterObjects = false;
		}

		reserved = new byte[len-14];
		System.arraycopy(source,start+14,reserved,0,reserved.length);
	}


	public long getRecordType() { return _type; }


	public void writeOut(OutputStream out) throws IOException {

		out.write(_header);

		writeLittleEndian(slideID,out);

		short flags = 0;
		if(followMasterObjects)    { flags += 1; }
		if(followMasterScheme)     { flags += 2; }
		if(followMasterBackground) { flags += 4; }
		writeLittleEndian(flags,out);


		out.write(reserved);
	}

	public void dispose()
	{
	    _header = null;
	    reserved = null;
	}
}
