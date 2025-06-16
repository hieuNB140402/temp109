

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;




public final class HeadersFootersAtom extends RecordAtom {


    public static final int fHasDate = 1;

    public static final int fHasTodayDate = 2;


     public static final int fHasUserDate = 4;

    public static final int fHasSlideNumber = 8;

    public static final int fHasHeader = 16;


    public static final int fHasFooter = 32;


    private byte[] _header;

	private byte[] _recdata;


	protected HeadersFootersAtom(byte[] source, int start, int len) {
		// Get the header
		_header = new byte[8];
		System.arraycopy(source,start,_header,0,8);

		// Grab the record data
		_recdata = new byte[len-8];
		System.arraycopy(source,start+8,_recdata,0,len-8);
	}


    public HeadersFootersAtom() {
        _recdata = new byte[4];

        _header = new byte[8];
        LittleEndian.putShort(_header, 2, (short)getRecordType());
        LittleEndian.putInt(_header, 4, _recdata.length);
    }

    public long getRecordType() {
        return RecordTypes.HeadersFootersAtom.typeID;
    }


	public void writeOut(OutputStream out) throws IOException {
		out.write(_header);
		out.write(_recdata);
	}


    public int getFormatId(){
        return LittleEndian.getShort(_recdata, 0);
    }

    public void setFormatId(int formatId){
         LittleEndian.putUShort(_recdata, 0, formatId);
    }


    public int getMask(){
        return LittleEndian.getShort(_recdata, 2);
    }

    public void setMask(int mask){
        LittleEndian.putUShort(_recdata, 2, mask);
    }


    public boolean getFlag(int bit){
        return (getMask() & bit) != 0;
    }


    public void setFlag(int bit, boolean value){
        int mask = getMask();
        if(value) mask |= bit;
        else mask &= ~bit;
        setMask(mask);
    }

    public String toString(){
        StringBuffer buf = new StringBuffer();
        buf.append("HeadersFootersAtom\n");
        buf.append("\tFormatId: " + getFormatId() + "\n");
        buf.append("\tMask    : " + getMask() + "\n");
        buf.append("\t  fHasDate        : " + getFlag(fHasDate) + "\n");
        buf.append("\t  fHasTodayDate   : " + getFlag(fHasTodayDate) + "\n");
        buf.append("\t  fHasUserDate    : " + getFlag(fHasUserDate) + "\n");
        buf.append("\t  fHasSlideNumber : " + getFlag(fHasSlideNumber) + "\n");
        buf.append("\t  fHasHeader      : " + getFlag(fHasHeader) + "\n");
        buf.append("\t  fHasFooter      : " + getFlag(fHasFooter) + "\n");
        return buf.toString();
    }
    
    

    public void dispose()
    {
        _header = null;
        _recdata = null;
    }
}
