
package com.document.allreader.allofficefilereader.ss.sheetbar;

import java.util.ArrayList;
import java.util.Vector;

import com.document.allreader.allofficefilereader.R;
import com.document.allreader.allofficefilereader.constant.EventConstant;
import com.document.allreader.allofficefilereader.system.IControl;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;



public class SheetBar extends HorizontalScrollView implements OnClickListener
{

    public SheetBar(Context context)
    {
        super(context);
    }    
    


    public SheetBar(Context context, IControl control, int minimumWidth)
    {
        super(context);
        this.control = control;
        this.setVerticalFadingEdgeEnabled(false);
        this.setFadingEdgeLength(0);
        if (minimumWidth == getResources().getDisplayMetrics().widthPixels)
        {
            this.minimumWidth = -1;
        }
        else
        {
            this.minimumWidth = minimumWidth;
        }
        init();
    }
    
    private ArrayList<SheetButton> mSheetButtons = new ArrayList<>();

    public void onConfigurationChanged(Configuration newConfig)
    {
        sheetbarFrame.setMinimumWidth(minimumWidth == -1 ? getResources().getDisplayMetrics().widthPixels
            : minimumWidth);
    }
    

    private void init()
    {
        Context context = this.getContext();
        sheetbarFrame = new LinearLayout(context);
        sheetbarFrame.setGravity(Gravity.BOTTOM);
        
        
        sheetbarResManager = new SheetbarResManager(context);
     
        Drawable drawable = sheetbarResManager.getDrawable(SheetbarResConstant.RESID_SHEETBAR_BG);
//       // sheetbarFrame.setBackgroundDrawable(drawable);
        sheetbarFrame.setBackgroundColor(Color.WHITE);
        sheetbarFrame.setOrientation(LinearLayout.HORIZONTAL);
        sheetbarFrame.setMinimumWidth(minimumWidth == -1 ? getResources().getDisplayMetrics().widthPixels
            : minimumWidth);
        sheetbarHeight = 150;
        
        drawable = sheetbarResManager.getDrawable(SheetbarResConstant.RESID_SHEETBAR_SHADOW_LEFT);
        LayoutParams parmas = new LayoutParams(LayoutParams.WRAP_CONTENT,150);
        // 左边shadow
        View left = new View(context);
        left.setBackgroundDrawable(drawable);
        sheetbarFrame.addView(left, parmas);
        
        // sheetButton
        @ SuppressWarnings("unchecked")
        Vector<String> vec = (Vector<String>)control.getActionValue(EventConstant.SS_GET_ALL_SHEET_NAME, null);
        drawable = sheetbarResManager.getDrawable(SheetbarResConstant.RESID_SHEETBUTTON_NORMAL_LEFT);
        LayoutParams parmasButton = new LayoutParams(LayoutParams.WRAP_CONTENT, 130);
        parmasButton.setMargins(10, 10, 0, 10);
        int count = vec.size();
        for (int i = 0; i < count; i++)
        {
            SheetButton sb = new SheetButton(context, vec.get(i), i, sheetbarResManager);
            if (currentSheet == null)
            {
                currentSheet = sb;
                currentSheet.changeFocus(true);
            }
            sb.textView.setTextSize(9);
            sb.setOnClickListener(this);
            if(i==0) {
                sb.setBackgroundResource(R.drawable.bg_sheet_enable);
                sb.textView.setTextColor(Color.WHITE);
            }
            mSheetButtons.add(sb);
            sheetbarFrame.addView(sb, parmasButton);
            
            if (i < count - 1)
            {
                View view = new View(context);
                drawable = sheetbarResManager.getDrawable(SheetbarResConstant.RESID_SHEETBAR_SEPARATOR_H);
                view.setBackgroundDrawable(drawable);
                sheetbarFrame.addView(view, parmasButton);
            }
        }
        
        // 右边shadow
        View right = new View(context);
        drawable = sheetbarResManager.getDrawable(SheetbarResConstant.RESID_SHEETBAR_SHADOW_RIGHT);
        right.setBackgroundDrawable(drawable);
        sheetbarFrame.addView(right, parmas);
        
        //
        addView(sheetbarFrame, new LayoutParams(LayoutParams.WRAP_CONTENT, sheetbarHeight));
    }
    
    
    public void onClick(View v)
    {
        for (int i = 0; i < mSheetButtons.size(); i++)
        {
            SheetButton sbTemp = mSheetButtons.get(i);
            sbTemp.setBackgroundResource(R.drawable.bg_sheet_disable);
            sbTemp.textView.setTextColor(Color.BLACK);
            sbTemp.textView.setTextSize(9);
        }
        currentSheet.changeFocus(false);
        
        SheetButton sb = (SheetButton)v;
        sb.changeFocus(true);
        currentSheet = sb;
        currentSheet.setBackgroundResource(R.drawable.bg_sheet_enable);
        currentSheet.textView.setTextColor(Color.WHITE);
        
        control.actionEvent(EventConstant.SS_SHOW_SHEET, currentSheet.getSheetIndex());
    }

    /**
     * set focus sheet button(called when clicked document hyperlink)
     * @param index
     */
    public void setFocusSheetButton(int index)
    {
        if(currentSheet.getSheetIndex() == index)
        {
            return;
        }
        
        int count = sheetbarFrame.getChildCount();
        View view = null;
        for(int i = 0; i < count; i++)
        {
            view = sheetbarFrame.getChildAt(i);
            if (view instanceof SheetButton && ((SheetButton)view).getSheetIndex() == index)
            {
                currentSheet.changeFocus(false);
                
                currentSheet = (SheetButton)view;
                currentSheet.changeFocus(true);
                break;
            }
        }
        
        //sheetbar scrolled
        int screenWidth = control.getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int barWidth = sheetbarFrame.getWidth();
        if(barWidth > screenWidth)
        {
            int left = view.getLeft();
            int right = view.getRight();
            int off = (screenWidth - (right - left)) / 2;
            
            off = left - off;
            if(off < 0)
            {
                off = 0;
            }
            else if(off + screenWidth > barWidth)
            {
                off = barWidth - screenWidth;
            }
           
            scrollTo(off, 0);
        }
    }
    
    /**
     * @return Returns the sheetbarHeight.
     */
    public int getSheetbarHeight()
    {
        return sheetbarHeight;
    }
    

    public void dispose()
    {
        sheetbarResManager.dispose();
        sheetbarResManager = null;
        
        currentSheet = null;
        if(sheetbarFrame != null)
        {
            int count = sheetbarFrame.getChildCount();
            View v;
            for(int i = 0; i < count; i++)
            {
                v = sheetbarFrame.getChildAt(i);
                if(v instanceof SheetButton)
                {
                    ((SheetButton)v).dispose();
                }
            }
            sheetbarFrame = null;
        }
    }

    //
    private int minimumWidth;
    //
    private SheetbarResManager sheetbarResManager;
    //
    private int sheetbarHeight;
    //
    private SheetButton currentSheet;
    //
    private IControl control;
    //
    private LinearLayout sheetbarFrame;

}
