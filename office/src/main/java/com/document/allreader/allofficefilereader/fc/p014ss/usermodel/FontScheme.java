package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.FontScheme */

public enum FontScheme {
    NONE(1),
    MAJOR(2),
    MINOR(3);
    
    private static FontScheme[] _table = new FontScheme[4];
    private int value;

    static {
        FontScheme[] values = values();
        for (FontScheme fontScheme : values) {
            _table[fontScheme.getValue()] = fontScheme;
        }
    }

    private FontScheme(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static FontScheme valueOf(int i) {
        return _table[i];
    }
}
