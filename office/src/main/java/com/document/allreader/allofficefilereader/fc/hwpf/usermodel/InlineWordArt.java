package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import java.util.ArrayList;

import com.document.allreader.allofficefilereader.fc.ddf.DefaultEscherRecordFactory;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherRecordFactory;
import com.document.allreader.allofficefilereader.fc.hwpf.model.PictureDescriptor;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;

public class InlineWordArt extends PictureDescriptor
{
	private ArrayList<EscherRecord> escherRecords;
	
	public InlineWordArt(byte[] _dataStream, int dataBlockStartOffset)
    {
        super(_dataStream, dataBlockStartOffset);
        
		int dataBlockSize = LittleEndian.getInt(_dataStream, dataBlockStartOffset);
		final int dataBlockEndOffset = dataBlockSize + dataBlockStartOffset;
		  

	    int PICTFBlockSize = LittleEndian.getShort(_dataStream, dataBlockStartOffset
	            + 4);

	    int PICTF1BlockOffset = dataBlockStartOffset + PICTFBlockSize + 4;
	    short MM_TYPE = LittleEndian.getShort(_dataStream, dataBlockStartOffset
	            + 4 + 2);
	    if (MM_TYPE == 0x66)
	    {
	        int cchPicName = LittleEndian.getUnsignedByte(_dataStream, PICTF1BlockOffset);
	        PICTF1BlockOffset += 1 + cchPicName;
	    }
	    int PICTF1BlockSize = LittleEndian.getShort(_dataStream, PICTF1BlockOffset);

	    int unknownHeaderOffset = (PICTF1BlockSize + PICTF1BlockOffset) < dataBlockEndOffset
	            ? (PICTF1BlockSize + PICTF1BlockOffset) : PICTF1BlockOffset;
	        
	    escherRecords = 
	    		fillEscherRecords(_dataStream, PICTF1BlockOffset - 4, unknownHeaderOffset - PICTF1BlockOffset + 4);	    
    }
	
	public HWPFShape getInlineWordArt()
	{
		if(escherRecords != null && escherRecords.size() > 0 && escherRecords.get(0) instanceof EscherContainerRecord)
	    {
	    	return HWPFShapeFactory.createShape((EscherContainerRecord)(escherRecords.get(0)), null);
	    }
		return null;
	}
	

    public int getHorizontalScalingFactor()
    {
        return mx;
    }
    

    public int getVerticalScalingFactor()
    {
        return my;
    }
    

    public int getDxaGoal()
    {
        return dxaGoal;
    }


    public int getDyaGoal()
    {
        return dyaGoal;
    }
    
	private ArrayList<EscherRecord> fillEscherRecords(byte[] data, int offset, int size)
	{
		ArrayList<EscherRecord> escherRecords = new ArrayList<EscherRecord>();
		EscherRecordFactory recordFactory = new DefaultEscherRecordFactory();
		try
		{
			int pos = offset;
			while (pos < offset + size)
			{
				EscherRecord r = recordFactory.createRecord(data, pos);
				escherRecords.add(r);
				int bytesRead = r.fillFields(data, pos, recordFactory);
				pos += bytesRead;
			}
			return escherRecords;
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
