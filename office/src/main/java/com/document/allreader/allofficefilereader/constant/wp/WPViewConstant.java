
package com.document.allreader.allofficefilereader.constant.wp;

public final class WPViewConstant {

    public static final byte BREAK_NO = 0;

    public static final byte BREAK_LIMIT = 1;

    public static final byte BREAK_ENTER = 2;

    public static final byte BREAK_PAGE = 3;


    public static final short PAGE_SPACE = 5;


    public static final byte LAYOUT_FLAG_KEEPONE = 0;

    public static final byte LAYOUT_FLAG_DELLELINEVIEW = LAYOUT_FLAG_KEEPONE + 1;

    public static final byte LAYOUT_PARA_IN_TABLE = LAYOUT_FLAG_DELLELINEVIEW + 1;

    public static final byte LAYOUT_NOT_WRAP_LINE = LAYOUT_PARA_IN_TABLE + 1;


    public static final short PAGE_ROOT = 0;

    public static final short NORMAL_ROOT = PAGE_ROOT + 1;

    public static final short PRINT_ROOT = NORMAL_ROOT + 1;

    public static final short SIMPLE_ROOT = PRINT_ROOT + 1;
    public static final short PAGE_VIEW = SIMPLE_ROOT + 1;

    public static final short PARAGRAPH_VIEW = PAGE_VIEW + 1;

    public static final short LINE_VIEW = PARAGRAPH_VIEW + 1;

    public static final short LEAF_VIEW = LINE_VIEW + 1;

    public static final short OBJ_VIEW = LEAF_VIEW + 1;

    public static final short TABLE_VIEW = OBJ_VIEW + 1;

    public static final short TABLE_ROW_VIEW = TABLE_VIEW + 1;

    public static final short TABLE_CELL_VIEW = TABLE_ROW_VIEW + 1;

    public static final short TITLE_VIEW = TABLE_CELL_VIEW + 1; // 12

    public static final short BN_VIEW = TITLE_VIEW + 1; // 13

    public static final short SHAPE_VIEW = TITLE_VIEW + 1; // 14

    public static final short ENCLOSE_CHARACTER_VIEW = SHAPE_VIEW + 1; //15


    public static final byte X_AXIS = 0;

    public static final byte Y_AXIS = X_AXIS + 1;

}
