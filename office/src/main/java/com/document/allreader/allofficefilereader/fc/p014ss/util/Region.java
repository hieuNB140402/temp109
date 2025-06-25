package com.document.allreader.allofficefilereader.fc.p014ss.util;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.Region */

public class Region implements Comparable<Region> {
    private short _colFrom;
    private short _colTo;
    private int _rowFrom;
    private int _rowTo;

    public Region() {
    }

    public Region(int i, short s, int i2, short s2) {
        this._rowFrom = i;
        this._rowTo = i2;
        this._colFrom = s;
        this._colTo = s2;
    }

    public Region(String str) {
        CellReference cellReference = new CellReference(str.substring(0, str.indexOf(":")));
        CellReference cellReference2 = new CellReference(str.substring(str.indexOf(":") + 1));
        this._rowFrom = cellReference.getRow();
        this._colFrom = cellReference.getCol();
        this._rowTo = cellReference2.getRow();
        this._colTo = cellReference2.getCol();
    }

    public short getColumnFrom() {
        return this._colFrom;
    }

    public int getRowFrom() {
        return this._rowFrom;
    }

    public short getColumnTo() {
        return this._colTo;
    }

    public int getRowTo() {
        return this._rowTo;
    }

    public void setColumnFrom(short s) {
        this._colFrom = s;
    }

    public void setRowFrom(int i) {
        this._rowFrom = i;
    }

    public void setColumnTo(short s) {
        this._colTo = s;
    }

    public void setRowTo(int i) {
        this._rowTo = i;
    }

    public boolean contains(int i, short s) {
        return this._rowFrom <= i && this._rowTo >= i && this._colFrom <= s && this._colTo >= s;
    }

    public boolean equals(Region region) {
        return compareTo(region) == 0;
    }

    public int compareTo(Region region) {
        if (getRowFrom() == region.getRowFrom() && getColumnFrom() == region.getColumnFrom() && getRowTo() == region.getRowTo() && getColumnTo() == region.getColumnTo()) {
            return 0;
        }
        return (getRowFrom() < region.getRowFrom() || getColumnFrom() < region.getColumnFrom() || getRowTo() < region.getRowTo() || getColumnTo() < region.getColumnTo()) ? 1 : -1;
    }

    public int getArea() {
        return ((this._rowTo - this._rowFrom) + 1) * ((this._colTo - this._colFrom) + 1);
    }

    public static Region[] convertCellRangesToRegions(HSSFCellRangeAddress[] hSSFCellRangeAddressArr) {
        int length = hSSFCellRangeAddressArr.length;
        if (length < 1) {
            return new Region[0];
        }
        Region[] regionArr = new Region[length];
        for (int i = 0; i != length; i++) {
            regionArr[i] = convertToRegion(hSSFCellRangeAddressArr[i]);
        }
        return regionArr;
    }

    private static Region convertToRegion(HSSFCellRangeAddress hSSFCellRangeAddress) {
        return new Region(hSSFCellRangeAddress.getFirstRow(), (short) hSSFCellRangeAddress.getFirstColumn(), hSSFCellRangeAddress.getLastRow(), (short) hSSFCellRangeAddress.getLastColumn());
    }

    public static HSSFCellRangeAddress[] convertRegionsToCellRanges(Region[] regionArr) {
        int length = regionArr.length;
        if (length < 1) {
            return new HSSFCellRangeAddress[0];
        }
        HSSFCellRangeAddress[] hSSFCellRangeAddressArr = new HSSFCellRangeAddress[length];
        for (int i = 0; i != length; i++) {
            hSSFCellRangeAddressArr[i] = convertToCellRangeAddress(regionArr[i]);
        }
        return hSSFCellRangeAddressArr;
    }

    public static HSSFCellRangeAddress convertToCellRangeAddress(Region region) {
        return new HSSFCellRangeAddress(region.getRowFrom(), region.getRowTo(), region.getColumnFrom(), region.getColumnTo());
    }

    public String getRegionRef() {
        CellReference cellReference = new CellReference(this._rowFrom, this._colFrom);
        CellReference cellReference2 = new CellReference(this._rowTo, this._colTo);
        return cellReference.formatAsString() + ":" + cellReference2.formatAsString();
    }
}
