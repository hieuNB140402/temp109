/*
 * 文件名称:          	SSEditor.java
 *  
 * 编译器:            android2.2
 * 时间:             	上午4:13:44
 */
package com.document.allreader.allofficefilereader.ss.control;

import com.document.allreader.allofficefilereader.common.shape.IShape;
import com.document.allreader.allofficefilereader.constant.MainConstant;
import com.document.allreader.allofficefilereader.java.awt.Rectangle;
import com.document.allreader.allofficefilereader.pg.animate.IAnimation;
import com.document.allreader.allofficefilereader.simpletext.control.IHighlight;
import com.document.allreader.allofficefilereader.simpletext.control.IWord;
import com.document.allreader.allofficefilereader.simpletext.model.IDocument;
import com.document.allreader.allofficefilereader.system.IControl;


public class SSEditor implements IWord
{

    public SSEditor(Spreadsheet ss)
    {
        this.ss = ss;
    }
    
    public IHighlight getHighlight()
    {
        
        return null;
    }

    @ Override
    public Rectangle modelToView(long offset, Rectangle rect, boolean isBack)
    {
        
        return null;
    }

    @ Override
    public IDocument getDocument()
    {
        
        return null;
    }

    @ Override
    public String getText(long start, long end)
    {
        
        return "";
    }

    @ Override
    public long viewToModel(int x, int y, boolean isBack)
    {
        
        return 0;
    }

    @ Override
    public byte getEditType()
    {
        return MainConstant.APPLICATION_TYPE_SS;
    }

    @ Override
    public IAnimation getParagraphAnimation(int pargraphID)
    {
        
        return null;
    }

    @ Override
    public IShape getTextBox()
    {
        
        return null;
    }

    @ Override
    public IControl getControl()
    {
        
        return ss.getControl();
    }

    @ Override
    public void dispose()
    {
        ss = null;
    }
    
    private Spreadsheet ss;
}
