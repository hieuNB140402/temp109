

package com.document.allreader.allofficefilereader.fc.ss.usermodel;


/**
 * The font family this font belongs to. A font family is a set of fonts having common stroke width and serif
 * characteristics. The font name overrides when there are conflicting values.
 *
 * @author Gisella Bronzetti
 */
public enum FontFamily {

    NOT_APPLICABLE(0),
    ROMAN(1),
    SWISS(2),
    MODERN(3),
    SCRIPT(4),
    DECORATIVE(5);

    private int family;

    private FontFamily(int value) {
        family = value;
    }

    /**
     * Returns index of this font family
     *
     * @return index of this font family
     */
    public int getValue() {
        return family;
    }

    private static FontFamily[] _table = new FontFamily[6];

    static {
        for (FontFamily c : values()) {
            _table[c.getValue()] = c;
        }
    }

    public static FontFamily valueOf(int family) {
        return _table[family];
    }
}
