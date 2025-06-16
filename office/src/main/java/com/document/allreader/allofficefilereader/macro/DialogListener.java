/*
 * 文件名称:          DialogListener.java
 *  
 * 编译器:            android2.2
 * 时间:              上午10:35:58
 */
package com.document.allreader.allofficefilereader.macro;

import com.document.allreader.allofficefilereader.common.ICustomDialog;

/**
 * TODO: 文件注释
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            jqin
 * <p>
 * 日期:            2012-12-17
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:           
 * <p>
 * <p>
 */
public interface DialogListener
{

    public static final byte DIALOGTYPE_LOADING = ICustomDialog.DIALOGTYPE_LOADING;


    public static final byte DIALOGTYPE_FIND = ICustomDialog.DIALOGTYPE_FIND;
    
    


    public void showDialog(byte type);
    
    /**
     * 
     * @param type
     */
    public void dismissDialog(byte type);
}
