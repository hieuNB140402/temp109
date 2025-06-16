

package com.document.allreader.allofficefilereader.fc.ss.usermodel;


public interface BorderFormatting {

    final static short    BORDER_NONE                = 0x0;
    /** Thin border */
    final static short    BORDER_THIN                = 0x1;
    /** Medium border */
    final static short    BORDER_MEDIUM              = 0x2;

    final static short    BORDER_DASHED              = 0x3;

    final static short    BORDER_HAIR                = 0x4;

    final static short    BORDER_THICK               = 0x5;
    /** double-line border */
    final static short    BORDER_DOUBLE              = 0x6;
    /** hair-line border */
    final static short    BORDER_DOTTED              = 0x7;
    /** Medium dashed border */
    final static short    BORDER_MEDIUM_DASHED       = 0x8;
    /** dash-dot border */
    final static short    BORDER_DASH_DOT            = 0x9;
    /** medium dash-dot border */
    final static short    BORDER_MEDIUM_DASH_DOT     = 0xA;
    /** dash-dot-dot border */
    final static short    BORDER_DASH_DOT_DOT        = 0xB;
    /** medium dash-dot-dot border */
    final static short    BORDER_MEDIUM_DASH_DOT_DOT = 0xC;
    /** slanted dash-dot border */
    final static short    BORDER_SLANTED_DASH_DOT    = 0xD;

    short getBorderBottom();

    short getBorderDiagonal();

    short getBorderLeft();

    short getBorderRight();

    short getBorderTop();

    short getBottomBorderColor();

    short getDiagonalBorderColor();

    short getLeftBorderColor();

    short getRightBorderColor();

    short getTopBorderColor();

    void setBorderBottom(short border);

    /**
     * Set diagonal border.
     *
     * @param border  MUST be a BORDER_* constant
     */
    void setBorderDiagonal(short border);

    /**
     * Set left border.
     *
     * @param border  MUST be a BORDER_* constant
     */
    void setBorderLeft(short border);

    /**
     * Set right border.
     *
     * @param border  MUST be a BORDER_* constant
     */
    void setBorderRight(short border);

    /**
     * Set top border.
     *
     * @param border  MUST be a BORDER_* constant
     */
    void setBorderTop(short border);

    void setBottomBorderColor(short color);

    void setDiagonalBorderColor(short color);

    void setLeftBorderColor(short color);

    void setRightBorderColor(short color);

    void setTopBorderColor(short color);
}
