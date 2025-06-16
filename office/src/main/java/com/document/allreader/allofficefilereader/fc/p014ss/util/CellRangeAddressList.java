package com.document.allreader.allofficefilereader.fc.p014ss.util;

import com.document.allreader.allofficefilereader.fc.hssf.record.RecordInputStream;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianByteArrayOutputStream;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.CellRangeAddressList */

public class CellRangeAddressList {
    protected final List _list;

    public CellRangeAddressList() {
        this._list = new ArrayList();
    }

    public CellRangeAddressList(int i, int i2, int i3, int i4) {
        this();
        addCellRangeAddress(i, i3, i2, i4);
    }

    public CellRangeAddressList(RecordInputStream recordInputStream) {
        this();
        int readUShort = recordInputStream.readUShort();
        for (int i = 0; i < readUShort; i++) {
            this._list.add(new HSSFCellRangeAddress(recordInputStream));
        }
    }

    public int countRanges() {
        return this._list.size();
    }

    public void addCellRangeAddress(int i, int i2, int i3, int i4) {
        addCellRangeAddress(new HSSFCellRangeAddress(i, i3, i2, i4));
    }

    public void addCellRangeAddress(HSSFCellRangeAddress hSSFCellRangeAddress) {
        this._list.add(hSSFCellRangeAddress);
    }

    public HSSFCellRangeAddress remove(int i) {
        if (this._list.isEmpty()) {
            throw new RuntimeException("List is empty");
        } else if (i >= 0 && i < this._list.size()) {
            return (HSSFCellRangeAddress) this._list.remove(i);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Range index (");
            sb.append(i);
            sb.append(") is outside allowable range (0..");
            sb.append(this._list.size() - 1);
            sb.append(")");
            throw new RuntimeException(sb.toString());
        }
    }

    public HSSFCellRangeAddress getCellRangeAddress(int i) {
        return (HSSFCellRangeAddress) this._list.get(i);
    }

    public int getSize() {
        return getEncodedSize(this._list.size());
    }

    public static int getEncodedSize(int i) {
        return HSSFCellRangeAddress.getEncodedSize(i) + 2;
    }

    public int serialize(int i, byte[] bArr) {
        int size = getSize();
        serialize(new LittleEndianByteArrayOutputStream(bArr, i, size));
        return size;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        int size = this._list.size();
        littleEndianOutput.writeShort(size);
        for (int i = 0; i < size; i++) {
            ((HSSFCellRangeAddress) this._list.get(i)).serialize(littleEndianOutput);
        }
    }

    public CellRangeAddressList copy() {
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList();
        int size = this._list.size();
        for (int i = 0; i < size; i++) {
            cellRangeAddressList.addCellRangeAddress(((HSSFCellRangeAddress) this._list.get(i)).copy());
        }
        return cellRangeAddressList;
    }

    public HSSFCellRangeAddress[] getCellRangeAddresses() {
        HSSFCellRangeAddress[] hSSFCellRangeAddressArr = new HSSFCellRangeAddress[this._list.size()];
        this._list.toArray(hSSFCellRangeAddressArr);
        return hSSFCellRangeAddressArr;
    }
}
