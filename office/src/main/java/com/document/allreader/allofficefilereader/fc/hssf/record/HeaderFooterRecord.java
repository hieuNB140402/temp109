

package com.document.allreader.allofficefilereader.fc.hssf.record;



import java.util.Arrays;

import com.document.allreader.allofficefilereader.fc.util.HexDump;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;

/**
 * The HEADERFOOTER record stores information added in Office Excel 2007 for headers/footers.
 *
 * @author Yegor Kozlov
 */
public final class HeaderFooterRecord extends StandardRecord {

    private static final byte[] BLANK_GUID = new byte[16];

    public final static short sid = 0x089C;
	private byte[] _rawData;

    public HeaderFooterRecord(byte[] data) {
        _rawData = data;
    }

	/**
	 * construct a HeaderFooterRecord record.  No fields are interpreted and the record will
	 * be serialized in its original form more or less
	 * @param in the RecordInputstream to read the record from
	 */
	public HeaderFooterRecord(RecordInputStream in) {
		_rawData = in.readRemainder();
	}

	/**
	 * spit the record out AS IS. no interpretation or identification
	 */
	public void serialize(LittleEndianOutput out) {
		out.write(_rawData);
	}

	protected int getDataSize() {
		return _rawData.length;
	}
    
    public short getSid()
    {
        return sid;
    }

    /**
     * If this header belongs to a specific sheet view , the sheet view?s GUID will be saved here.
     * <p>
     * If it is zero, it means the current sheet. Otherwise, this field MUST match the guid field
     * of the preceding {@link UserSViewBegin} record.
     *
     * @return the sheet view?s GUID
     */
    public byte[] getGuid(){
        byte[] guid = new byte[16];
        System.arraycopy(_rawData, 12, guid, 0, Math.min(guid.length, _rawData.length - 12));
        return guid;
    }

    /**
     * @return whether this record belongs to the current sheet 
     */
    public boolean isCurrentSheet(){
        return Arrays.equals(getGuid(), BLANK_GUID);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("[").append("HEADERFOOTER").append("] (0x");
        sb.append(Integer.toHexString(sid).toUpperCase() + ")\n");
        sb.append("  rawData=").append(HexDump.toHex(_rawData)).append("\n");
        sb.append("[/").append("HEADERFOOTER").append("]\n");
        return sb.toString();
    }

    //HACK: do a "cheat" clone, see Record.java for more information
    public Object clone() {
                return cloneViaReserialise();
    }
    
 
}
