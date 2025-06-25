

package com.document.allreader.allofficefilereader.officereader.beans;


import com.document.allreader.allofficefilereader.constant.MainConstant;
import com.document.allreader.allofficefilereader.system.IControl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;


public class AImageTextButton extends AImageButton
{

    public static final int TEXT_TOP = 0;// 0

    public static final int TEXT_BOTTOM = TEXT_TOP + 1; // 1

    public static final int TEXT_LEFT = TEXT_BOTTOM + 1;// 2

    public static final int TEXT_RIGHT = TEXT_LEFT + 1;// 3

    public static final int GAP = MainConstant.GAP;
    


    public AImageTextButton(Context context, IControl control, String text, String toolstip, 
        int iconResID, int iconResIdDisable, int actionID, int textGravity, int fontSize)
    {
        super(context, control, toolstip, iconResID, iconResIdDisable, actionID);
        setEnabled(true);
        this.text = text;
        paint = new Paint();

        if (textGravity >= TEXT_TOP &&  textGravity <= TEXT_RIGHT)
        {
            this.textGravity = textGravity;
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
            paint.setTextSize(fontSize);
            if (text != null && text.length() > 0)
            {
                textWidth = (int)paint.measureText(text);
                textHeight = (int)Math.ceil((paint.descent() - paint.ascent()));
            }
        }

    }


    public void onDraw(Canvas canvas)
    {
        Rect clip = canvas.getClipBounds();

        int w = clip.right - clip.left;

        int h = clip.bottom - clip.top;

        int bW = bitmap.getWidth();

        int bH = bitmap.getHeight();
        int x;
        int y;

        if (textGravity == TEXT_TOP)
        {
            x = w - textWidth / 2;
            y = (h - bH - GAP * 2 - textHeight) / 2;
            
            canvas.drawText(text, x, y - paint.ascent(), paint);
            y = h - bH - GAP;
            x = (w - bW) / 2;
            canvas.drawBitmap(bitmap, x, y, paint);
        }

        else if (textGravity == TEXT_BOTTOM)
        {
        	topIndent = (h - bH - GAP * 6 - textHeight) / 2;
            x = (w - bW) / 2;
            y = topIndent;
            canvas.drawBitmap(bitmap, x, y, paint);
            
            y = bH  + topIndent + GAP * 6;
            x = (w - textWidth) / 2;
            canvas.drawText(text, x, y - paint.ascent(), paint);
        }

        else if (textGravity == TEXT_LEFT)
        {
            x = (w - textWidth - bW - GAP * 2) / 2;
            y = (h - textHeight) / 2;
            canvas.drawText(text, x, y - paint.ascent(), paint);
            
            y = (h - bH) / 2;
            x = w - bW - GAP;
            canvas.drawBitmap(bitmap, x, y, paint);
        }

        else if (textGravity == TEXT_RIGHT)
        {
        	leftIndent = w / 10;
            y = (h - bH) / 2;
            x = leftIndent;
            canvas.drawBitmap(bitmap, x, y, paint);
            
            y = (h - textHeight) / 2;
            x = bW + leftIndent + GAP * 6;
            canvas.drawText(text, x, y - paint.ascent(), paint);
        }
    }


    public int getTopIndent()
    {
        return topIndent;
    }

    /**
     * @param topIndent The topIndent to set.
     */
    public void setTopIndent(int topIndent)
    {
        this.topIndent = topIndent;
    }

    /**
     * @return Returns the bottomIndent.
     */
    public int getBottomIndent()
    {
        return bottomIndent;
    }

    /**
     * @param bottomIndent The bottomIndent to set.
     */
    public void setBottomIndent(int bottomIndent)
    {
        this.bottomIndent = bottomIndent;
    }

    /**
     * @return Returns the leftIndent.
     */
    public int getLeftIndent()
    {
        return leftIndent;
    }

    /**
     * @param leftIndent The leftIndent to set.
     */
    public void setLeftIndent(int leftIndent)
    {
        this.leftIndent = leftIndent;
    }

    /**
     * @return Returns the rightIndent.
     */
    public int getRightIndent()
    {
        return rightIndent;
    }

    /**
     * @param rightIndent The rightIndent to set.
     */
    public void setRightIndent(int rightIndent)
    {
        this.rightIndent = rightIndent;
    }
    
    
    public void dispose()
    {
        super.dispose();
        text = null;
    }

    // 见常量定义
    private int textGravity = -1;
    //
    private int textWidth;
    //
    private int textHeight;

    private int topIndent;

    private int bottomIndent;

    private int leftIndent;

    private int rightIndent;
    // 显示文本
    private String text;
    
    private Paint paint;
}
