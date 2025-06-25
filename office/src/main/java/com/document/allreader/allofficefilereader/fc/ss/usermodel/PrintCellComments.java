

package com.document.allreader.allofficefilereader.fc.ss.usermodel;

/**
 * These enumerations specify how cell comments shall be displayed for paper printing purposes.
 *
 * @author Gisella Bronzetti
 */
public enum PrintCellComments {

    /**
     * Do not print cell comments.
     */
    NONE(1),
    /**
     * Print cell comments as displayed.
     */
    AS_DISPLAYED(2),
    /**
     * Print cell comments at end of document.
     */
    AT_END(3);


    private int comments;

    private PrintCellComments(int comments) {
        this.comments = comments;
    }

    public int getValue() {
        return comments;
    }

    private static PrintCellComments[] _table = new PrintCellComments[4];
    static {
        for (PrintCellComments c : values()) {
            _table[c.getValue()] = c;
        }
    }

    public static PrintCellComments valueOf(int value){
        return _table[value];
    }
}
