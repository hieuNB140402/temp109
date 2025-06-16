

        

package com.document.allreader.allofficefilereader.fc.hssf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;

/**
 * Title:        Precision Record<P>
 * Description:  defines whether to store with full precision or what's displayed by the gui
 *               (meaning have really screwed up and skewed figures or only think you do!)<P>
 * REFERENCE:  PG 372 Microsoft Excel 97 Developer's Kit (ISBN: 1-57231-498-2)<P>
 * @author Andrew C. Oliver (acoliver at apache dot org)
 * @version 2.0-pre
 */

public final class PrecisionRecord
    extends StandardRecord
{
    public final static short sid = 0xE;
    public short              field_1_precision;

    public PrecisionRecord()
    {
    }

    public PrecisionRecord(RecordInputStream in)
    {
        field_1_precision = in.readShort();
    }

    /**
     * set whether to use full precision or just skew all you figures all to hell.
     *
     * @param fullprecision - or not
     */

    public void setFullPrecision(boolean fullprecision)
    {
        if (fullprecision == true)
        {
            field_1_precision = 1;
        }
        else
        {
            field_1_precision = 0;
        }
    }

    /**
     * get whether to use full precision or just skew all you figures all to hell.
     *
     * @return fullprecision - or not
     */

    public boolean getFullPrecision()
    {
        return (field_1_precision == 1);
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[PRECISION]\n");
        buffer.append("    .precision       = ").append(getFullPrecision())
            .append("\n");
        buffer.append("[/PRECISION]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(field_1_precision);
    }

    protected int getDataSize() {
        return 2;
    }

    public short getSid()
    {
        return sid;
    }
}
