

package com.document.allreader.allofficefilereader.fc.hssf.record;



import java.util.Iterator;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.ddf.EscherRecord;
import com.document.allreader.allofficefilereader.fc.ddf.NullEscherSerializationListener;
import com.document.allreader.allofficefilereader.fc.util.ArrayUtil;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public final class DrawingGroupRecord extends AbstractEscherHolderRecord {
    public static final short sid = 0xEB;

    static final int MAX_RECORD_SIZE = 8228;
    private static final int MAX_DATA_SIZE = MAX_RECORD_SIZE - 4;

    public DrawingGroupRecord()
    {
    }

    public DrawingGroupRecord( RecordInputStream in )
    {
        super( in );
    }

    protected String getRecordName()
    {
        return "MSODRAWINGGROUP";
    }

    public short getSid()
    {
        return sid;
    }

    public int serialize(int offset, byte[] data)
    {
        byte[] rawData = getRawData();
        if (getEscherRecords().size() == 0 && rawData != null)
        {
            return writeData( offset, data, rawData );
        }
        byte[] buffer = new byte[getRawDataSize()];
        int pos = 0;
        for ( Iterator iterator = getEscherRecords().iterator(); iterator.hasNext(); )
        {
            EscherRecord r = (EscherRecord) iterator.next();
            pos += r.serialize(pos, buffer, new NullEscherSerializationListener() );
        }

        return writeData( offset, data, buffer );
    }

    /**
     * Process the bytes into escher records.
     * (Not done by default in case we break things,
     *  unless you set the "poi.deserialize.escher"
     *  system property)
     */
    public void processChildRecords() {
    	convertRawBytesToEscherRecords();
    }

    public int getRecordSize() {
        // TODO - convert this to a RecordAggregate
        return grossSizeFromDataSize(getRawDataSize());
    }

    private int getRawDataSize() {
        List escherRecords = getEscherRecords();
        byte[] rawData = getRawData();
        if (escherRecords.size() == 0 && rawData != null)
        {
            return rawData.length;
        }
        int size = 0;
        for ( Iterator iterator = escherRecords.iterator(); iterator.hasNext(); )
        {
            EscherRecord r = (EscherRecord) iterator.next();
            size += r.getRecordSize();
        }
        return size;
    }

    static int grossSizeFromDataSize(int dataSize)
    {
        return dataSize + ( (dataSize - 1) / MAX_DATA_SIZE + 1 ) * 4;
    }

    private int writeData( int offset, byte[] data, byte[] rawData )
    {
        int writtenActualData = 0;
        int writtenRawData = 0;
        while (writtenRawData < rawData.length)
        {
            int segmentLength = Math.min( rawData.length - writtenRawData, MAX_DATA_SIZE);
            if (writtenRawData / MAX_DATA_SIZE >= 2)
                writeContinueHeader( data, offset, segmentLength );
            else
                writeHeader( data, offset, segmentLength );
            writtenActualData += 4;
            offset += 4;
            ArrayUtil.arraycopy( rawData, writtenRawData, data, offset, segmentLength );
            offset += segmentLength;
            writtenRawData += segmentLength;
            writtenActualData += segmentLength;
        }
        return writtenActualData;
    }

    private void writeHeader( byte[] data, int offset, int sizeExcludingHeader )
    {
        LittleEndian.putShort(data, 0 + offset, getSid());
        LittleEndian.putShort(data, 2 + offset, (short) sizeExcludingHeader);
    }

    private void writeContinueHeader( byte[] data, int offset, int sizeExcludingHeader )
    {
        LittleEndian.putShort(data, 0 + offset, ContinueRecord.sid);
        LittleEndian.putShort(data, 2 + offset, (short) sizeExcludingHeader);
    }
}
