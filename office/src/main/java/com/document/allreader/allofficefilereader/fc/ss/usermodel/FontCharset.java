

package com.document.allreader.allofficefilereader.fc.ss.usermodel;


/**
 * Charset represents the basic set of characters associated with a font (that it can display), and 
 * corresponds to the ANSI codepage (8-bit or DBCS) of that character set used by a given language. 
 * 
 * @author Gisella Bronzetti
 */
public enum FontCharset {

     ANSI(0),
     DEFAULT(1),
     SYMBOL(2),
     MAC(77),
     SHIFTJIS(128),
     HANGEUL(129),
     JOHAB(130),
     GB2312(134),
     CHINESEBIG5(136),
     GREEK(161),
     TURKISH(162),
     VIETNAMESE(163),
     HEBREW(177),
     ARABIC(178),
     BALTIC(186),
     RUSSIAN(204),
     THAI(222),
     EASTEUROPE(238),
     OEM(255);

    
    private int charset;

    private FontCharset(int value){
        charset = value;
    }

    /**
     * Returns value of this charset
     *
     * @return value of this charset
     */
    public int getValue(){
        return charset;
    }

    private static FontCharset[] _table = new FontCharset[256];
    static {
        for (FontCharset c : values()) {
            _table[c.getValue()] = c;
        }
    }

    public static FontCharset valueOf(int value){
        if(value >= _table.length)
           return null;
        return _table[value];
    }
}
