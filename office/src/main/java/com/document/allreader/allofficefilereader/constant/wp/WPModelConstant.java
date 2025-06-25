
package com.document.allreader.allofficefilereader.constant.wp;


public final class WPModelConstant
{




    public static final long MAIN = 0x0000000000000000L;

    public static final long HEADER = 0x1000000000000000L;

    public static final long FOOTER = 0x2000000000000000L;

    public static final long FOOTNOTE = 0x3000000000000000L;

    public static final long ENDNOTE = 0x4000000000000000L;

    public static final long TEXTBOX = 0x5000000000000000L;

    public static final long AREA_MASK = 0xF000000000000000L;

    public static final long TEXTBOX_MASK = 0x0FFFFFFF00000000L;

    public static final short SECTION_ELEMENT = 0; // 0

    public static final short PARAGRAPH_ELEMENT = SECTION_ELEMENT + 1; // 1

    public static final short LEAF_ELEMENT = SECTION_ELEMENT + 1; // 2

    public static final short TABLE_ELEMENT = LEAF_ELEMENT + 1;// 3

    public static final short TABLE_ROW_ELEMENT = TABLE_ELEMENT + 1; // 4

    public static final short TABLE_CELL_ELEMENT = TABLE_ROW_ELEMENT + 1; // 5

    public static final short HEADER_ELEMENT = TABLE_CELL_ELEMENT + 1; // 6

    public static final short FOOTER_ELEMENT = HEADER_ELEMENT + 1; // 7
    

    public static final short SECTION_COLLECTION = 0;

    public static final short HEADER_COLLECTION = SECTION_COLLECTION + 1;// 1

    public static final short FOOTER_COLLECTION = HEADER_COLLECTION + 1;// 2

    public static final short FOOTNOTE_COLLECTION = FOOTER_COLLECTION + 1; // 3

    public static final short ENDNOTE_COLLECTION = FOOTNOTE_COLLECTION + 1; // 4

    public static final short TEXTBOX_COLLECTION = ENDNOTE_COLLECTION + 1; // 5

    public static final byte HF_FIRST = 0; 

    public static final byte HF_ODD = HF_FIRST + 1; // 1

    public static final byte HF_EVEN = HF_ODD + 1; // 2 
    

    public static final byte PN_PAGE_NUMBER = 1;

    public static final byte PN_TOTAL_PAGES = 2;

    public static final byte ENCLOSURE_TYPE_ROUND = 0;

    public static final byte ENCLOSURE_TYPE_SQUARE = ENCLOSURE_TYPE_ROUND + 1;

    public static final byte ENCLOSURE_TYPE_TRIANGLE = ENCLOSURE_TYPE_SQUARE + 1;

    public static final byte ENCLOSURE_TYPE_RHOMBUS = ENCLOSURE_TYPE_TRIANGLE + 1;
    
}
