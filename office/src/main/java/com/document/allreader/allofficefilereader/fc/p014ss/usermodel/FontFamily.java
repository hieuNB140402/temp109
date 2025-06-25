package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.FontFamily */

public enum FontFamily {
    NOT_APPLICABLE(0),
    ROMAN(1),
    SWISS(2),
    MODERN(3),
    SCRIPT(4),
    DECORATIVE(5);
    
    private static FontFamily[] _table = new FontFamily[6];
    private int family;

    static {
        FontFamily[] values = values();
        for (FontFamily fontFamily : values) {
            _table[fontFamily.getValue()] = fontFamily;
        }
    }

    private FontFamily(int i) {
        this.family = i;
    }

    public int getValue() {
        return this.family;
    }

    public static FontFamily valueOf(int i) {
        return _table[i];
    }
}
