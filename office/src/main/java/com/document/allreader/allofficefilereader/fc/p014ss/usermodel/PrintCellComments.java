package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.PrintCellComments */

public enum PrintCellComments {
    NONE(1),
    AS_DISPLAYED(2),
    AT_END(3);
    
    private static PrintCellComments[] _table = new PrintCellComments[4];
    private int comments;

    static {
        PrintCellComments[] values = values();
        for (PrintCellComments printCellComments : values) {
            _table[printCellComments.getValue()] = printCellComments;
        }
    }

    private PrintCellComments(int i) {
        this.comments = i;
    }

    public int getValue() {
        return this.comments;
    }

    public static PrintCellComments valueOf(int i) {
        return _table[i];
    }
}
