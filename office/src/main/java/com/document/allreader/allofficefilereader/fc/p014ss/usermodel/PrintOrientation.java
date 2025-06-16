package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.PrintOrientation */

public enum PrintOrientation {
    DEFAULT(1),
    PORTRAIT(2),
    LANDSCAPE(3);
    
    private static PrintOrientation[] _table = new PrintOrientation[4];
    private int orientation;

    static {
        PrintOrientation[] values = values();
        for (PrintOrientation printOrientation : values) {
            _table[printOrientation.getValue()] = printOrientation;
        }
    }

    private PrintOrientation(int i) {
        this.orientation = i;
    }

    public int getValue() {
        return this.orientation;
    }

    public static PrintOrientation valueOf(int i) {
        return _table[i];
    }
}
