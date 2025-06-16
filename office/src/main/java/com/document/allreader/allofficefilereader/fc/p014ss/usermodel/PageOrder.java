package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.PageOrder */

public enum PageOrder {
    DOWN_THEN_OVER(1),
    OVER_THEN_DOWN(2);
    
    private static PageOrder[] _table = new PageOrder[3];
    private int order;

    static {
        PageOrder[] values = values();
        for (PageOrder pageOrder : values) {
            _table[pageOrder.getValue()] = pageOrder;
        }
    }

    private PageOrder(int i) {
        this.order = i;
    }

    public int getValue() {
        return this.order;
    }

    public static PageOrder valueOf(int i) {
        return _table[i];
    }
}
