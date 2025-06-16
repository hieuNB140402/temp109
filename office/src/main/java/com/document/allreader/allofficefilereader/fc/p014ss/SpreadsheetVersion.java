package com.document.allreader.allofficefilereader.fc.p014ss;

import com.document.allreader.allofficefilereader.fc.p014ss.util.CellReference;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.SpreadsheetVersion */

public enum SpreadsheetVersion {
    EXCEL97(65536, 256, 30, 3, 32767),
    EXCEL2007(1048576, 16384, 255, Integer.MAX_VALUE, 32767);
    
    private final int _maxColumns;
    private final int _maxCondFormats;
    private final int _maxFunctionArgs;
    private final int _maxRows;
    private final int _maxTextLength;

    private SpreadsheetVersion(int i, int i2, int i3, int i4, int i5) {
        this._maxRows = i;
        this._maxColumns = i2;
        this._maxFunctionArgs = i3;
        this._maxCondFormats = i4;
        this._maxTextLength = i5;
    }

    public int getMaxRows() {
        return this._maxRows;
    }

    public int getLastRowIndex() {
        return this._maxRows - 1;
    }

    public int getMaxColumns() {
        return this._maxColumns;
    }

    public int getLastColumnIndex() {
        return this._maxColumns - 1;
    }

    public int getMaxFunctionArgs() {
        return this._maxFunctionArgs;
    }

    public int getMaxConditionalFormats() {
        return this._maxCondFormats;
    }

    public String getLastColumnName() {
        return CellReference.convertNumToColString(getLastColumnIndex());
    }

    public int getMaxTextLength() {
        return this._maxTextLength;
    }
}
