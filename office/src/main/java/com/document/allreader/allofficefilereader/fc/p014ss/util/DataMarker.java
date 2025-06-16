package com.document.allreader.allofficefilereader.fc.p014ss.util;

import com.document.allreader.allofficefilereader.fc.p014ss.usermodel.Sheet;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.DataMarker */

public class DataMarker {
    private HSSFCellRangeAddress range;
    private Sheet sheet;

    public String formatAsString() {
        return null;
    }

    public DataMarker(Sheet sheet, HSSFCellRangeAddress hSSFCellRangeAddress) {
        this.sheet = sheet;
        this.range = hSSFCellRangeAddress;
    }

    public Sheet getSheet() {
        return this.sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public HSSFCellRangeAddress getRange() {
        return this.range;
    }

    public void setRange(HSSFCellRangeAddress hSSFCellRangeAddress) {
        this.range = hSSFCellRangeAddress;
    }
}
