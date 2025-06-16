/*
 * 文件名称:          IControl.java
 *  
 * 编译器:            android2.2
 * 时间:              下午1:50:27
 */
package com.document.allreader.allofficefilereader.system;

import com.document.allreader.allofficefilereader.common.ICustomDialog;
import com.document.allreader.allofficefilereader.common.IOfficeToPicture;
import com.document.allreader.allofficefilereader.common.ISlideShow;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

/**
 * control接口
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            梁金晶
 * <p>
 * 日期:            2011-10-27
 * <p>
 * 负责人:          梁金晶
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public interface IControl
{

    public void layoutView(int x, int y, int w, int h);
    

    public void actionEvent(int actionID, Object obj);
    
    /**
     * 得到action的状态的值
     * 
     * @return obj
     */
    public Object getActionValue(int actionID, Object obj);    
       

    public int getCurrentViewIndex();
    
    /**
     * 获取应用组件
     */
    public View getView();
    
    
    public Dialog getDialog(Activity activity, int id);
    
    
    public IMainFrame getMainFrame();
    
    
    public Activity getActivity();
    
    /**
     * get find instance
     */
    public IFind getFind();
    
    
    public boolean isAutoTest();
    
    
    public IOfficeToPicture getOfficeToPicture();
    
    
    public ICustomDialog getCustomDialog();
    
    
    public boolean isSlideShow();
    
    /**
     * 
     * @return
     */
    public ISlideShow getSlideShow();
    
    
    public IReader getReader();
    
    
    public boolean openFile(String filePath);
    
    
    public byte getApplicationType();
    
    
    public SysKit getSysKit();
    
    /**
     * 释放内存
     */
    public void dispose();
}
