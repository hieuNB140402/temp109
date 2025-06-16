

package com.document.allreader.allofficefilereader.fc.ss.usermodel;


/**
 * Defines the font scheme to which this font belongs.
 * When a font definition is part of a theme definition, then the font is categorized as either a major or minor font scheme component.
 * When a new theme is chosen, every font that is part of a theme definition is updated to use the new major or minor font definition for that
 * theme.
 * Usually major fonts are used for styles like headings, and minor fonts are used for body & paragraph text.
 *
 * @author Gisella Bronzetti
 */
public enum FontScheme {


    NONE(1),
    MAJOR(2),
    MINOR(3);

    private int value;

    private FontScheme(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }

    private static FontScheme[] _table = new FontScheme[4];
    static {
        for (FontScheme c : values()) {
            _table[c.getValue()] = c;
        }
    }

    public static FontScheme valueOf(int value){
        return _table[value];
    }
}
