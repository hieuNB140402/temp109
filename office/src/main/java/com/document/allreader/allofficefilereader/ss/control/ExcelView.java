/*
 * 文件名称:          ExcelView.java
 *  
 * 编译器:            android2.2
 * 时间:              上午11:21:00
 */
package com.document.allreader.allofficefilereader.ss.control;

import com.document.allreader.allofficefilereader.constant.EventConstant;
import com.document.allreader.allofficefilereader.ss.model.baseModel.Sheet;
import com.document.allreader.allofficefilereader.ss.model.baseModel.Workbook;
import com.document.allreader.allofficefilereader.ss.sheetbar.SheetBar;
import com.document.allreader.allofficefilereader.ss.view.SheetView;
import com.document.allreader.allofficefilereader.system.IControl;

import android.content.Context;
import android.widget.RelativeLayout;


public class ExcelView extends RelativeLayout
{

    public ExcelView(Context context, String filepath, Workbook book, IControl control)
    {
        super(context);
        this.control = control;
        ss = new Spreadsheet(context, filepath, book, control, this);
       
        addView(ss, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }
    

    public void init()
    {
        ss.init();
        initSheetbar();
    }
    
    

    private void initSheetbar()
    {
        if (!isDefaultSheetBar)
        {
            return;
        }
        bar = new SheetBar(getContext(), control, getResources().getDisplayMetrics().widthPixels);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_BOTTOM);
        addView(bar, params);
    }
    

    public Spreadsheet getSpreadsheet()
    {
        return ss;
    }
    

    public void showSheet(int sheetIndex)
    {
        ss.showSheet(sheetIndex);

        if(isDefaultSheetBar)
        {
            bar.setFocusSheetButton(sheetIndex);
        }
        else
        {
            control.getMainFrame().doActionEvent(EventConstant.SS_CHANGE_SHEET, sheetIndex);
        }
    }
    
    /**
     * 显示指定的sheet
     * 
     * @param sheetName 要显示的sheet名称
     */
    public void showSheet(String sheetName)
    {
        ss.showSheet(sheetName);
        
        Sheet sheet = ss.getWorkbook().getSheet(sheetName);
        if (sheet == null)
        {
            return;
        }        
        int sheetIndex = ss.getWorkbook().getSheetIndex(sheet);
        if(isDefaultSheetBar)
        {
            bar.setFocusSheetButton(sheetIndex);
        }
        else
        {
            control.getMainFrame().doActionEvent(EventConstant.SS_CHANGE_SHEET, sheetIndex);
        }
    }
    
    /**
     * 得到sheetView视图
     */
    public SheetView getSheetView()
    {
        return ss.getSheetView();
    }
    

    public void removeSheetBar()
    {
        isDefaultSheetBar = false;
        removeView(bar);
    }
    
    /**
     * get sheet bar height
     * @return
     */
    public int getBottomBarHeight()
    {
        if(isDefaultSheetBar)
        {
            return bar.getHeight();
        }
        else
        {
            return control.getMainFrame().getBottomBarHeight();
        }
    }
    

    public int getCurrentViewIndex()
    {
    	return ss.getCurrentSheetNumber();
    }
    

    public void dispose()
    {
        control = null;
        if (ss != null)
        {
            ss.dispose();
        }
        bar = null;
    }
    
    
    //
    private boolean isDefaultSheetBar = true;
    //
    private Spreadsheet ss;
    //
    private SheetBar bar;
    //
    private IControl control;
    
}
