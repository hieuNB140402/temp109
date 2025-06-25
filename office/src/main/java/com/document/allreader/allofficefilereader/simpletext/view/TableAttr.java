/*
 * 文件名称:          TableAttr.java
 *  
 * 编译器:            android2.2
 * 时间:              下午4:38:31
 */
package com.document.allreader.allofficefilereader.simpletext.view;

/**
 * table attribute
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-5-21
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class TableAttr
{


    public int topMargin;

    public int leftMargin;

    public int rightMargin;

    public int bottomMargin;
    // cell宽度
    public int cellWidth;
    // cell vertical align
    public byte cellVerticalAlign;
    // cell background
    public int cellBackground;
    

    public void reset()
    {
        topMargin = 0;
        leftMargin = 0;
        rightMargin = 0;
        bottomMargin = 0;
        cellVerticalAlign = 0;
        cellBackground = -1;
    }
    
    
}
