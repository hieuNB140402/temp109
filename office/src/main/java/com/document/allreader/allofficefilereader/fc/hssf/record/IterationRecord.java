

package com.document.allreader.allofficefilereader.fc.hssf.record;

import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.BitFieldFactory;
import com.document.allreader.allofficefilereader.fc.util.HexDump;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;

/**
 * Title:        Iteration Record (0x0011) <p/>
 * Description:  Tells whether to iterate over forumla calculations or not
 *               (if a formula is dependant upon another formula's result)
 *               (odd feature for something that can only have 32 elements in
 *                a formula!)<P>
 * REFERENCE:  PG 325 Microsoft Excel 97 Developer's Kit (ISBN: 1-57231-498-2)<p/>
 * @author Andrew C. Oliver (acoliver at apache dot org)
 * @author Jason Height (jheight at chariot dot net dot au)
 */
public final class IterationRecord extends StandardRecord {
    public final static short sid = 0x0011;

    private static final BitField iterationOn = BitFieldFactory.getInstance(0x0001);

    private int _flags;

    public IterationRecord(boolean iterateOn) {
        _flags = iterationOn.setBoolean(0, iterateOn);
    }

    public IterationRecord(RecordInputStream in)
    {
        _flags = in.readShort();
    }

    /**
     * set whether or not to iterate for calculations
     * @param iterate or not
     */
    public void setIteration(boolean iterate) {
        _flags = iterationOn.setBoolean(_flags, iterate);
    }

    /**
     * get whether or not to iterate for calculations
     *
     * @return whether iterative calculations are turned off or on
     */
    public boolean getIteration() {
        return iterationOn.isSet(_flags);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[ITERATION]\n");
        buffer.append("    .flags      = ").append(HexDump.shortToHex(_flags)).append("\n");
        buffer.append("[/ITERATION]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(_flags);
    }

    protected int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }

    public Object clone() {
        return new IterationRecord(getIteration());
    }
}
