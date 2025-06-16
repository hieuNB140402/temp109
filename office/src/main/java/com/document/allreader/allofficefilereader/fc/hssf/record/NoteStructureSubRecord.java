

package com.document.allreader.allofficefilereader.fc.hssf.record;

import com.document.allreader.allofficefilereader.fc.util.HexDump;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianInput;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;

/**
 * ftNts (0x000D)<p/>
 * Represents a NoteStructure sub record.
 *
 * <p>
 * The docs say nothing about it. The length of this record is always 26 bytes.
 * </p>
 *
 * @author Yegor Kozlov
 */
public final class NoteStructureSubRecord extends SubRecord {
    public final static short sid = 0x0D;
    private static final int ENCODED_SIZE = 22;

    private byte[] reserved;

    /**
     * Construct a new <code>NoteStructureSubRecord</code> and
     * fill its data with the default values
     */
    public NoteStructureSubRecord()
    {
        //all we know is that the the length of <code>NoteStructureSubRecord</code> is always 22 bytes
        reserved = new byte[ENCODED_SIZE];
    }

    /**
     * Read the record data from the supplied <code>RecordInputStream</code>
     */
    public NoteStructureSubRecord(LittleEndianInput in, int size) {
        if (size != ENCODED_SIZE) {
            throw new RecordFormatException("Unexpected size (" + size + ")");
        }
        //just grab the raw data
        byte[] buf = new byte[size];
        in.readFully(buf);
        reserved = buf;
    }

    /**
     * Convert this record to string.
     * Used by BiffViewer and other utilities.
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[ftNts ]").append("\n");
        buffer.append("  size     = ").append(getDataSize()).append("\n");
        buffer.append("  reserved = ").append(HexDump.toHex(reserved)).append("\n");
        buffer.append("[/ftNts ]").append("\n");
        return buffer.toString();
    }

    /**
     * Serialize the record data into the supplied array of bytes
     *
     * @param out the stream to serialize into
     */
    public void serialize(LittleEndianOutput out) {
        out.writeShort(sid);
        out.writeShort(reserved.length);
        out.write(reserved);
    }

	protected int getDataSize() {
        return reserved.length;
    }

    /**
     * @return id of this record.
     */
    public short getSid()
    {
        return sid;
    }

    public Object clone() {
        NoteStructureSubRecord rec = new NoteStructureSubRecord();
        byte[] recdata = new byte[reserved.length];
        System.arraycopy(reserved, 0, recdata, 0, recdata.length);
        rec.reserved = recdata;
        return rec;
    }

}


