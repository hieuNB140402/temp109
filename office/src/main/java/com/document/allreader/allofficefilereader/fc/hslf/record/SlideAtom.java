

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;




public final class SlideAtom extends RecordAtom
{
	private byte[] _header;
	private static long _type = 1007l;
	public static final int MASTER_SLIDE_ID = 0;
	public static final int USES_MASTER_SLIDE_ID = -2147483648;

	private int masterID;
	private int notesID;

	private boolean followMasterObjects;
	private boolean followMasterScheme;
	private boolean followMasterBackground;
	private SSlideLayoutAtom layoutAtom;
	private byte[] reserved;


	public int getMasterID() { return masterID; }
    public void setMasterID(int id) { masterID = id; }
	public int getNotesID()  { return notesID; }
	public SSlideLayoutAtom getSSlideLayoutAtom() { return layoutAtom; }

	public void setNotesID(int id) { notesID = id; }

	public boolean getFollowMasterObjects()    { return followMasterObjects; }
	public boolean getFollowMasterScheme()     { return followMasterScheme; }
	public boolean getFollowMasterBackground() { return followMasterBackground; }
	public void setFollowMasterObjects(boolean flag)    { followMasterObjects = flag; }
	public void setFollowMasterScheme(boolean flag)     { followMasterScheme = flag; }
	public void setFollowMasterBackground(boolean flag) { followMasterBackground = flag; }




	public SlideAtom(){
		_header = new byte[8];
		LittleEndian.putUShort(_header, 0, 2);
		LittleEndian.putUShort(_header, 2, (int)_type);
		LittleEndian.putInt(_header, 4, 24);

		byte[] ssdate = new byte[12];
		layoutAtom = new SSlideLayoutAtom(ssdate);
		layoutAtom.setGeometryType(SSlideLayoutAtom.BLANK_SLIDE);

		followMasterObjects = true;
		followMasterScheme = true;
		followMasterBackground = true;
		masterID = -2147483648;
		notesID = 0;
		reserved = new byte[2];
	}


	public long getRecordType() { return _type; }

	public void writeOut(OutputStream out) throws IOException {

		out.write(_header);

		layoutAtom.writeOut(out);

		writeLittleEndian(masterID,out);
		writeLittleEndian(notesID,out);

		short flags = 0;
		if(followMasterObjects)    { flags += 1; }
		if(followMasterScheme)     { flags += 2; }
		if(followMasterBackground) { flags += 4; }
		writeLittleEndian(flags,out);

		out.write(reserved);
	}



	public class SSlideLayoutAtom {
		// The different kinds of geometry
		public static final int TITLE_SLIDE = 0;
		public static final int TITLE_BODY_SLIDE = 1;
		public static final int TITLE_MASTER_SLIDE = 2;
		public static final int MASTER_SLIDE = 3;
		public static final int MASTER_NOTES = 4;
		public static final int NOTES_TITLE_BODY = 5;
		public static final int HANDOUT = 6; // Only header, footer and date placeholders
		public static final int TITLE_ONLY = 7;
		public static final int TITLE_2_COLUMN_BODY = 8;
		public static final int TITLE_2_ROW_BODY = 9;
		public static final int TITLE_2_COLUNM_RIGHT_2_ROW_BODY = 10;
		public static final int TITLE_2_COLUNM_LEFT_2_ROW_BODY = 11;
		public static final int TITLE_2_ROW_BOTTOM_2_COLUMN_BODY = 12;
		public static final int TITLE_2_ROW_TOP_2_COLUMN_BODY = 13;
		public static final int FOUR_OBJECTS = 14;
		public static final int BIG_OBJECT = 15;
		public static final int BLANK_SLIDE = 16;
		public static final int VERTICAL_TITLE_BODY_LEFT = 17;
		public static final int VERTICAL_TITLE_2_ROW_BODY_LEFT = 17;


		private int geometry;
		private byte[] placeholderIDs;

		public int getGeometryType() { return geometry; }
		public void setGeometryType(int geom) { geometry = geom; }


		public SSlideLayoutAtom(byte[] data) {
			if(data.length != 12) {
				throw new RuntimeException("SSlideLayoutAtom created with byte array not 12 bytes long - was " + data.length + " bytes in size");
			}

			geometry = LittleEndian.getInt(data,0);
			placeholderIDs = new byte[8];
			System.arraycopy(data,4,placeholderIDs,0,8);
		}


		public void writeOut(OutputStream out) throws IOException {

			writeLittleEndian(geometry,out);
			out.write(placeholderIDs);
		}
		

		public void dispose()
		{
		    placeholderIDs = null;
		}
	}
	

	public void dispose()
	{
	    _header = null;
	    if (layoutAtom != null)
	    {
	        layoutAtom.dispose();
	        layoutAtom = null;
	    }
	    reserved = null;
	}
}
