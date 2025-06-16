
package com.document.allreader.allofficefilereader.wp.control;


import android.util.Log;

public class StatusManage
{


    public boolean isSelectTextStatus()
    {
        return selectText;
    }


    public void setSelectTextStatus(boolean selectText)
    {
        this.selectText = selectText;
    }
    

    public long getPressOffset()
    {
        return pressOffset;
    }


    public void setPressOffset(long pressOffset)
    {
        this.pressOffset = pressOffset;
    }


    public boolean isTouchDown()
    {
        return isTouchDown;
    }


    public void setTouchDown(boolean isTouchDown)
    {
        this.isTouchDown = isTouchDown;
    }
    

    public void dispose()
    {
        Log.e("dddd","");
    }


    private boolean selectText;
    private long pressOffset;
    private boolean isTouchDown;

}
