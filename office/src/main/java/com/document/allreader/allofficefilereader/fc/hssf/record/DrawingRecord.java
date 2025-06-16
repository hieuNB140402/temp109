

package com.document.allreader.allofficefilereader.fc.hssf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;
/**
 * DrawingRecord (0x00EC)<p/>
 *
 */
public final class DrawingRecord extends StandardRecord {
    public static final short sid = 0x00EC;

	private static final byte[] EMPTY_BYTE_ARRAY = { };

    private byte[] recordData;
    private byte[] contd;

    public DrawingRecord() {
    	recordData = EMPTY_BYTE_ARRAY;
    }

    public DrawingRecord(RecordInputStream in) {
      recordData = in.readRemainder();
    }

    public void processContinueRecord(byte[] record) 
    {
        //don't merge continue record with the drawing record, it must be serialized separately
    	if(contd ==  null)
    	{
    		contd = record;
    	}
    	else
    	{
    		// DrawingRecord followed by more than one ContinueRecords, 
    		//like DrawingRecord, ContinueRecord, ContinueRecord...
    		byte[] newBuffer = new byte[contd.length + record.length];
    		System.arraycopy( contd, 0, newBuffer, 0, contd.length );
    		System.arraycopy( record, 0, newBuffer, contd.length, record.length);
    		
    		contd = newBuffer;
    	}
    }

    public void serialize(LittleEndianOutput out) {
        out.write(recordData);
    }
    protected int getDataSize() {
        return recordData.length;
    }

    public int getDataLength() 
    {
        if(contd != null)
        {
            return recordData.length +  contd.length;
        }
        else
        {            
            return recordData.length;
        }
    }
    
    public short getSid() {
        return sid;
    }

    public byte[] getData() {
        if(contd != null) {
            byte[] newBuffer = new byte[ recordData.length + contd.length ];
            System.arraycopy( recordData, 0, newBuffer, 0, recordData.length );
            System.arraycopy( contd, 0, newBuffer, recordData.length, contd.length);
            return newBuffer;
        }
        return recordData;
    }

    public void setData(byte[] thedata) {
    	if (thedata == null) {
    		throw new IllegalArgumentException("data must not be null");
    	}
        recordData = thedata;
    }

    public Object clone() {
    	DrawingRecord rec = new DrawingRecord();
    	
    	rec.recordData = recordData.clone();
    	if (contd != null) {
	    	// TODO - this code probably never executes
	    	rec.contd = contd.clone();
    	}
    	
    	return rec;
    }
}
