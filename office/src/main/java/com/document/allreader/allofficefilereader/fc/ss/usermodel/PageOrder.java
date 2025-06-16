

package com.document.allreader.allofficefilereader.fc.ss.usermodel;

/**
 * Specifies printed page order.
 *
 * @author Gisella Bronzetti
 */
public enum PageOrder {

    /**
     * Order pages vertically first, then move horizontally.
     */
    DOWN_THEN_OVER(1),
    /**
     * Order pages horizontally first, then move vertically
     */
    OVER_THEN_DOWN(2);


    private int order;


    private PageOrder(int order) {
        this.order = order;
    }

    public int getValue() {
        return order;
    }


    private static PageOrder[] _table = new PageOrder[3];
    static {
        for (PageOrder c : values()) {
            _table[c.getValue()] = c;
        }
    }

    public static PageOrder valueOf(int value){
        return _table[value];
    }
}
