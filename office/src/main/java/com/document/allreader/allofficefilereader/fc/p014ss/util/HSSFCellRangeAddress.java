package com.document.allreader.allofficefilereader.fc.p014ss.util;

import com.document.allreader.allofficefilereader.fc.hssf.formula.SheetNameFormatter;
import com.document.allreader.allofficefilereader.fc.hssf.record.RecordInputStream;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianByteArrayOutputStream;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.HSSFCellRangeAddress */

public class HSSFCellRangeAddress extends CellRangeAddressBase {
    public static final int ENCODED_SIZE = 8;

    public static int getEncodedSize(int i) {
        return i * 8;
    }

    public HSSFCellRangeAddress(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
    }

    public int serialize(int i, byte[] bArr) {
        serialize(new LittleEndianByteArrayOutputStream(bArr, i, 8));
        return 8;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getFirstRow());
        littleEndianOutput.writeShort(getLastRow());
        littleEndianOutput.writeShort(getFirstColumn());
        littleEndianOutput.writeShort(getLastColumn());
    }

    public HSSFCellRangeAddress(RecordInputStream recordInputStream) {
        super(readUShortAndCheck(recordInputStream), recordInputStream.readUShort(), recordInputStream.readUShort(), recordInputStream.readUShort());
    }

    private static int readUShortAndCheck(RecordInputStream recordInputStream) {
        if (recordInputStream.remaining() >= 8) {
            return recordInputStream.readUShort();
        }
        throw new RuntimeException("Ran out of data reading CellRangeAddress");
    }

    public HSSFCellRangeAddress copy() {
        return new HSSFCellRangeAddress(getFirstRow(), getLastRow(), getFirstColumn(), getLastColumn());
    }

    public String formatAsString() {
        return formatAsString(null, false);
    }

    public String formatAsString(String str, boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str != null) {
            stringBuffer.append(SheetNameFormatter.format(str));
            stringBuffer.append("!");
        }
        CellReference cellReference = new CellReference(getFirstRow(), getFirstColumn(), z, z);
        CellReference cellReference2 = new CellReference(getLastRow(), getLastColumn(), z, z);
        stringBuffer.append(cellReference.formatAsString());
        if (!cellReference.equals(cellReference2)) {
            stringBuffer.append(':');
            stringBuffer.append(cellReference2.formatAsString());
        }
        return stringBuffer.toString();
    }

    public static HSSFCellRangeAddress valueOf(String str) {
        CellReference cellReference;
        CellReference cellReference2;
        int indexOf = str.indexOf(":");
        if (indexOf == -1) {
            cellReference2 = new CellReference(str);
            cellReference = cellReference2;
        } else {
            CellReference cellReference3 = new CellReference(str.substring(0, indexOf));
            cellReference = new CellReference(str.substring(indexOf + 1));
            cellReference2 = cellReference3;
        }
        return new HSSFCellRangeAddress(cellReference2.getRow(), cellReference.getRow(), cellReference2.getCol(), cellReference.getCol());
    }
}
