

package com.document.allreader.allofficefilereader.fc.hssf.record;

import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.BitFieldFactory;
import com.document.allreader.allofficefilereader.fc.util.HexDump;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;

/**
 * Title: Double Stream Flag Record (0x0161)<p/>
 * Description:  tells if this is a double stream file. (always no for HSSF generated files)<p/>
 *               Double Stream files contain both BIFF8 and BIFF7 workbooks.<p/>
 * REFERENCE:  PG 305 Microsoft Excel 97 Developer's Kit (ISBN: 1-57231-498-2)<p/>
 * @author Andrew C. Oliver (acoliver at apache dot org)
 */
public final class DSFRecord extends StandardRecord {
    public final static short sid = 0x0161;

    private static final BitField biff5BookStreamFlag = BitFieldFactory.getInstance(0x0001);

    private int _options;

    private DSFRecord(int options) {
        _options = options;
    }
    public DSFRecord(boolean isBiff5BookStreamPresent) {
        this(0);
        _options = biff5BookStreamFlag.setBoolean(0, isBiff5BookStreamPresent);
    }

    public DSFRecord(RecordInputStream in) {
        this(in.readShort());
    }

    public boolean isBiff5BookStreamPresent() {
        return biff5BookStreamFlag.isSet(_options);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[DSF]\n");
        buffer.append("    .options = ").append(HexDump.shortToHex(_options)).append("\n");
        buffer.append("[/DSF]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(_options);
    }

    protected int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }
}
