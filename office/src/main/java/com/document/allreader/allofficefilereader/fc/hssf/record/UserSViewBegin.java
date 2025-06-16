

package com.document.allreader.allofficefilereader.fc.hssf.record;



import com.document.allreader.allofficefilereader.fc.util.HexDump;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;

/**
 * The UserSViewBegin record specifies settings for a custom view associated with the sheet.
 * This record also marks the start of custom view records, which save custom view settings.
 * Records between {@link UserSViewBegin} and {@link UserSViewEnd} contain settings for the custom view,
 * not settings for the sheet itself.
 *
 * @author Yegor Kozlov
 */
public final class UserSViewBegin extends StandardRecord {

    public final static short sid = 0x01AA;
	private byte[] _rawData;

    public UserSViewBegin(byte[] data) {
        _rawData = data;
    }

	/**
	 * construct an UserSViewBegin record.  No fields are interpreted and the record will
	 * be serialized in its original form more or less
	 * @param in the RecordInputstream to read the record from
	 */
	public UserSViewBegin(RecordInputStream in) {
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
     * @return Globally unique identifier for the custom view
     */
    public byte[] getGuid(){
        byte[] guid = new byte[16];
        System.arraycopy(_rawData, 0, guid, 0, guid.length);
        return guid;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("[").append("USERSVIEWBEGIN").append("] (0x");
        sb.append(Integer.toHexString(sid).toUpperCase() + ")\n");
        sb.append("  rawData=").append(HexDump.toHex(_rawData)).append("\n");
        sb.append("[/").append("USERSVIEWBEGIN").append("]\n");
        return sb.toString();
    }

    //HACK: do a "cheat" clone, see Record.java for more information
    public Object clone() {
        return cloneViaReserialise();
    }
 
}
