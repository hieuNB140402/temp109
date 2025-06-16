package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

import com.document.allreader.allofficefilereader.common.shape.ShapeTypes;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.FontCharset */

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
    RUSSIAN(ShapeTypes.RightTriangle),
    THAI(ShapeTypes.Teardrop),
    EASTEUROPE(ShapeTypes.Star10),
    OEM(255);
    
    private static FontCharset[] _table = new FontCharset[256];
    private int charset;

    static {
        FontCharset[] values = values();
        for (FontCharset fontCharset : values) {
            _table[fontCharset.getValue()] = fontCharset;
        }
    }

    private FontCharset(int i) {
        this.charset = i;
    }

    public int getValue() {
        return this.charset;
    }

    public static FontCharset valueOf(int i) {
        FontCharset[] fontCharsetArr = _table;
        if (i >= fontCharsetArr.length) {
            return null;
        }
        return fontCharsetArr[i];
    }
}
