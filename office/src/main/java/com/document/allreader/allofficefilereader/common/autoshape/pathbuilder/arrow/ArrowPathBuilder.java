/*
 * 文件名称:          SimpleArrow.java
 *  
 * 编译器:            android2.2
 * 时间:              下午3:07:55
 */
package com.document.allreader.allofficefilereader.common.autoshape.pathbuilder.arrow;

import android.graphics.Rect;

import com.document.allreader.allofficefilereader.common.shape.AutoShape;


public class ArrowPathBuilder
{    

    public static Object getArrowPath(AutoShape shape, Rect rect)
    {
        if(shape.isAutoShape07())
        {
            return LaterArrowPathBuilder.getArrowPath(shape, rect);
        }
        else
        {
            return EarlyArrowPathBuilder.getArrowPath(shape, rect);
        }
    }
}
