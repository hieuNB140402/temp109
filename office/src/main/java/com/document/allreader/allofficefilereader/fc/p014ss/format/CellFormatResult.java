package com.document.allreader.allofficefilereader.fc.p014ss.format;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.format.CellFormatResult */

public class CellFormatResult {
    public final boolean applies;
    public final String text;
    public final int textColor;

    public CellFormatResult(boolean z, String str, int i) {
        this.applies = z;
        this.text = str;
        this.textColor = !z ? -1 : i;
    }
}
