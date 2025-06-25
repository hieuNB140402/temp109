

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public class InteractiveInfoAtom extends RecordAtom {


    public static final byte ACTION_NONE = 0;
    public static final byte ACTION_MACRO = 1;
    public static final byte ACTION_RUNPROGRAM = 2;
    public static final byte ACTION_JUMP = 3;
    public static final byte ACTION_HYPERLINK = 4;
    public static final byte ACTION_OLE = 5;
    public static final byte ACTION_MEDIA = 6;
    public static final byte ACTION_CUSTOMSHOW = 7;


    public static final byte JUMP_NONE = 0;
    public static final byte JUMP_NEXTSLIDE = 1;
    public static final byte JUMP_PREVIOUSSLIDE = 2;
    public static final byte JUMP_FIRSTSLIDE = 3;
    public static final byte JUMP_LASTSLIDE = 4;
    public static final byte JUMP_LASTSLIDEVIEWED = 5;
    public static final byte JUMP_ENDSHOW = 6;


    public static final byte LINK_NextSlide = 0x00;
    public static final byte LINK_PreviousSlide = 0x01;
    public static final byte LINK_FirstSlide = 0x02;
    public static final byte LINK_LastSlide = 0x03;
    public static final byte LINK_CustomShow = 0x06;
    public static final byte LINK_SlideNumber = 0x07;
    public static final byte LINK_Url = 0x08;
    public static final byte LINK_OtherPresentation = 0x09;
    public static final byte LINK_OtherFile = 0x0A;
    public static final byte LINK_NULL = (byte)0xFF;

    private byte[] _header;


    private byte[] _data;


    protected InteractiveInfoAtom() {
        _header = new byte[8];
        _data = new byte[16];

        LittleEndian.putShort(_header, 2, (short)getRecordType());
        LittleEndian.putInt(_header, 4, _data.length);

        // It is fine for the other values to be zero
    }


    public int getHyperlinkID() {
        return LittleEndian.getInt(_data,4);
    }


    public void setHyperlinkID(int number) {
        LittleEndian.putInt(_data,4,number);
    }

    public int getSoundRef() {
        return LittleEndian.getInt(_data,0);
    }

    public void setSoundRef(int val) {
    	LittleEndian.putInt(_data, 0, val);
    }


    public byte getAction() {
        return _data[8];
    }

    public void setAction(byte val) {
    	_data[8] = val;
    }


    public byte getOleVerb() {
        return _data[9];
    }

    public void setOleVerb(byte val) {
    	_data[9] = val;
    }

    public byte getJump() {
        return _data[10];
    }


    public void setJump(byte val) {
    	_data[10] = val;
    }


    public byte getFlags() {
        return _data[11];
    }

    public void setFlags(byte val) {
    	_data[11] = val;
    }


    public byte getHyperlinkType() {
        return _data[12];
    }

    public void setHyperlinkType(byte val) {
    	_data[12] = val;
    }


    public long getRecordType() { return RecordTypes.InteractiveInfoAtom.typeID; }


    public void dispose()
    {
        _header = null;
        _data = null;
    }
}
