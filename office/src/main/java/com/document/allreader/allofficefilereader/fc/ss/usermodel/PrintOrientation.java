

package com.document.allreader.allofficefilereader.fc.ss.usermodel;

/**
 * The enumeration value indicating the print orientation for a sheet.
 *
 * @author Gisella Bronzetti
 */
public enum PrintOrientation {

    /**
     * orientation not specified
     */
    DEFAULT(1),
    /**
     * portrait orientation
     */
    PORTRAIT(2),
    /**
     * landscape orientations
     */
    LANDSCAPE(3);


    private int orientation;

    private PrintOrientation(int orientation) {
        this.orientation = orientation;
    }


    public int getValue() {
        return orientation;
    }


    private static PrintOrientation[] _table = new PrintOrientation[4];
    static {
        for (PrintOrientation c : values()) {
            _table[c.getValue()] = c;
        }
    }

    public static PrintOrientation valueOf(int value){
        return _table[value];
    }
}
