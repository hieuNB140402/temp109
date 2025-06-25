
package com.document.allreader.allofficefilereader.pdf;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatImageView;

import com.document.allreader.allofficefilereader.constant.EventConstant;
import com.document.allreader.allofficefilereader.constant.PDFConstant;
import com.document.allreader.allofficefilereader.fc.pdf.PDFLib;
import com.document.allreader.allofficefilereader.simpletext.control.SafeAsyncTask;
import com.document.allreader.allofficefilereader.system.IControl;
import com.document.allreader.allofficefilereader.system.beans.pagelist.APageListItem;
import com.document.allreader.allofficefilereader.system.beans.pagelist.APageListView;


public class PDFPageListItem extends APageListItem
{
    
    public PDFPageListItem(APageListView listView, IControl control, int pageWidth, int pageHeight)
    {
        super(listView, pageWidth, pageHeight);
        this.listView = listView;
        this.control = control;
        this.lib = (PDFLib)listView.getModel();
        this.isAutoTest = control.isAutoTest();
        this.setBackgroundColor(PDFConstant.BACKGROUND_COLOR);
    }


    @ Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        
        int w = right - left;
        int h = bottom - top;
        if (originalImageView != null)
        {
            originalImageView.layout(0, 0, w, h);
        }
        if (searchView != null)
        {
            searchView.layout(0, 0, w, h);
        }
        if (viewWidth != w || viewHeight != h)
        {
            viewWidth =  viewHeight = 0;
            repaintArea = null;
            if (repaintImageView != null)
            {
                repaintImageView.setImageBitmap(null);
            }
        }
        else
        {
            if (repaintImageView != null)
            {
                repaintImageView.layout(repaintArea.left, repaintArea.top, repaintArea.right, repaintArea.bottom);
            }
        }

        {
            if (mBusyIndicator != null)
            {
                int x, y;
                if (w > listView.getWidth())
                {
                    x = (listView.getWidth() - PDFConstant.BUSY_SIZE) / 2 - left;
                }
                else
                {
                    x = (w - PDFConstant.BUSY_SIZE) / 2;
                }
                if (h > listView.getHeight())
                {
                    y = (listView.getHeight() - PDFConstant.BUSY_SIZE) / 2 - top;
                }
                else
                {
                    y = (h - PDFConstant.BUSY_SIZE) / 2;
                }
                mBusyIndicator.layout(x, y, x + PDFConstant.BUSY_SIZE, y + PDFConstant.BUSY_SIZE);
            }
        }
        
    }
    

    public void setPageItemRawData(final int pIndex, int pWidth, int pHeight)
    {
        super.setPageItemRawData(pIndex, pWidth, pHeight);
        isOriginalBitmapValid = false;

        if (darwOriginalPageTask != null)
        {
            darwOriginalPageTask.cancel(true);
            darwOriginalPageTask = null;
        }
        if (originalImageView == null)
        {
            originalImageView = new AppCompatImageView(listView.getContext())
            {
                public boolean isOpaque()
                {
                    return true;
                }
                
                public void onDraw(Canvas canvas)
                {
                    try
                    {
                        super.onDraw(canvas);
                    }
                    catch (Exception e)
                    {   
                    }
                }
            };
            originalImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            addView(originalImageView);
        }


        if (pageWidth <= 0 || pageHeight <= 0)
        {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE)
        {

            originalImageView.setImageBitmap(null);
        }

        float zoom = listView.getFitZoom();
        if (originalBitmap == null 
            || originalBitmap.getWidth() != (int)(pageWidth * zoom) 
            || originalBitmap.getHeight() != (int)(pageHeight * zoom))
        {
            int bW = (int)(pageWidth * zoom);
            int bH = (int)(pageHeight * zoom);
            try
            {
                if (!listView.isInitZoom())
                {
                    listView.setZoom(zoom, false);
                }
                if (originalBitmap != null)
                {
                	while(!lib.isDrawPageSyncFinished())
                	{
                		try
                		{
                			Thread.sleep(100);
                		}
                		catch(Exception e)
                		{
                			
                		}
                	}
                    originalBitmap.recycle();
                }
                originalBitmap = Bitmap.createBitmap(bW, bH, Bitmap.Config.ARGB_8888);
            }
            catch (OutOfMemoryError e)
            {
            	System.gc();
            	try
            	{
            		Thread.sleep(50);
            		originalBitmap = Bitmap.createBitmap(bW, bH, Bitmap.Config.ARGB_8888);
            	}
            	catch(Exception ee)
            	{
            		return;
            	}
            }
        }

        final APageListItem own = this;

        darwOriginalPageTask = new SafeAsyncTask<Void, Void, Bitmap>()
        {
            private boolean isCancel = false;

            protected Bitmap doInBackground(Void...v)
            {
                try
                {
                    if (originalBitmap == null)
                    {
                        return null;
                    }                    
                    Thread.sleep(pageIndex == listView.getCurrentPageNumber() - 1 ? 500 : 1000);
    
                    if (isCancel)
                    {
                        return null;
                    }
                    lib.drawPageSync(originalBitmap, pageIndex, 
                        originalBitmap.getWidth(), originalBitmap.getHeight(), 
                    0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), 1);
                    return originalBitmap;
                }
                catch (Exception e)
                {
                    return null;
                }
            }


            protected void onPreExecute()
            {
                originalImageView.setImageBitmap(null);
                if (mBusyIndicator == null)
                {
                    mBusyIndicator = new ProgressBar(getContext());
                    mBusyIndicator.setIndeterminate(true);
                    mBusyIndicator.setBackgroundResource(android.R.drawable.progress_horizontal);
                    addView(mBusyIndicator);
                    mBusyIndicator.setVisibility(VISIBLE);
                }
                else
                {
                    mBusyIndicator.setVisibility(VISIBLE);
                }
            }
            

            protected void onCancelled() 
            {
                isCancel = true;
            }


            protected void onPostExecute(Bitmap v)
            {
                try
                {
                    mIsBlank = false;
                    isOriginalBitmapValid = true;
                    if (listView != null)
                    {
                        if (mBusyIndicator != null)
                        {
                            mBusyIndicator.setVisibility(INVISIBLE);
                        }                        
                    }
                    listView.setDoRequstLayout(false);
                    originalImageView.setImageBitmap(originalBitmap);
                    listView.setDoRequstLayout(true);
                    invalidate();
                    if (listView != null)
                    {
                        if ((int)(listView.getZoom() * 100) == 100
                            || (isInit && pIndex == 0))
                        {
                            if (v != null)
                            {
                                if (isInit && pIndex == 0)
                                {
                                    listView.postRepaint(listView.getCurrentPageView());
                                }
                                else
                                {
                                    listView.exportImage(own, originalBitmap);
                                }
                            }
                        }
                        isInit = false;
                        if (isAutoTest)
                        {
                            control.actionEvent(EventConstant.SYS_AUTO_TEST_FINISH_ID, true);
                        }
                    }
                }
                catch (NullPointerException e)
                {
                    
                }
            }
        };

        darwOriginalPageTask.safeExecute();

        
        if (searchView == null)
        {
            searchView = new View(getContext())
            {

                protected void onDraw(Canvas canvas)
                {
                    super.onDraw(canvas);
                    PDFFind find = (PDFFind)control.getFind();
                    if (find != null && !mIsBlank)
                    {
                        find.drawHighlight(canvas, 0, 0, own);
                    }
                }
            };
            addView(searchView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        }
    }
    
    public void releaseResources()
    {
        super.releaseResources();
        isOriginalBitmapValid = false;

        if (darwOriginalPageTask != null)
        {
            darwOriginalPageTask.cancel(true);
            darwOriginalPageTask = null;
        }

        if (repaintSyncTask != null)
        {
            repaintSyncTask.cancel(true);
            repaintSyncTask = null;
        }
        if (originalImageView != null)
        {
            originalImageView.setImageBitmap(null);
        }

        if (repaintImageView != null)
        {
            repaintImageView.setImageBitmap(null);
        }
        
        if (control.getMainFrame().isShowProgressBar() || true)
        {
            if (mBusyIndicator != null)
            {
                mBusyIndicator.setVisibility(VISIBLE);
            }
        }
    }


    public void blank(int pIndex)
    {
        super.blank(pIndex);
        isOriginalBitmapValid = false;

        if (darwOriginalPageTask != null)
        {
            darwOriginalPageTask.cancel(true);
            darwOriginalPageTask = null;
        }

        if (repaintSyncTask != null)
        {
            repaintSyncTask.cancel(true);
            repaintSyncTask = null;
        }
        if (originalImageView != null)
        {   
            originalImageView.setImageBitmap(null);
        }

        if (repaintImageView != null)
        {   
            repaintImageView.setImageBitmap(null);
        }        
        if (mBusyIndicator != null)
        {
            mBusyIndicator.setVisibility(VISIBLE);
        }
    }
    

    public void setLinkHighlighting(boolean vlaue)
    {

    }
    
    

    public int getHyperlinkCount(float x, float y)
    {
        float scale = getWidth() / (float)pageWidth;
        float docRelX = (x - getLeft()) / scale;
        float docRelY = (y - getTop()) / scale;
        
        return lib.getHyperlinkCountSync(pageIndex, docRelX, docRelY);
    }


    protected void addRepaintImageView(Bitmap bmp)
    {
        Rect viewArea = new Rect(getLeft(), getTop(), getRight(), getBottom());

        if (viewArea.width() != pageWidth
            || viewArea.height() != pageHeight
            || (originalBitmap != null && ((int)listView.getZoom()) * 100 == 100
                && (originalBitmap.getWidth() != pageWidth
                                           || originalBitmap.getHeight() != pageHeight)))
        {
            Rect paintArea = new Rect(0, 0, listView.getWidth(), listView.getHeight());


            if (!paintArea.intersect(viewArea))
            {
                return;
            }


            paintArea.offset(-viewArea.left, -viewArea.top);

            if (paintArea.equals(repaintArea)
                && viewHeight == viewArea.width()
                && viewHeight == viewArea.height())
            {
                return;
            }


            if (repaintSyncTask != null)
            {
                repaintSyncTask.cancel(true);
                repaintSyncTask = null;
            }


            if (repaintImageView == null)
            {
                repaintImageView = new AppCompatImageView(listView.getContext())
                {
                    public boolean isOpaque()
                    {
                        return true;
                    }

                    public void onDraw(Canvas canvas)
                    {
                        try
                        {
                            super.onDraw(canvas);
                        }
                        catch (Exception e)
                        {                        
                        }
                    }
                };
                
                repaintImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                repaintImageView.setImageBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));

            }
            final PDFPageListItem own = this;
            repaintSyncTask = new SafeAsyncTask<RepaintAreaInfo, Void, RepaintAreaInfo>()
            {

                protected RepaintAreaInfo doInBackground(RepaintAreaInfo...v)
                {
                    try
                    {
                        lib.drawPageSync(v[0].bm, pageIndex, 
                            v[0].viewWidth, v[0].viewHeight,
                            v[0].repaintArea.left, v[0].repaintArea.top, 
                            v[0].repaintArea.width(), v[0].repaintArea.height(), 1);
                        return v[0];
                    }
                    catch (Exception e)
                    {
                        return null;
                    }
                }


                protected void onPostExecute(RepaintAreaInfo v)
                {
                    try
                    {
                        viewWidth = v.viewWidth;
                        viewHeight = v.viewHeight;
                        repaintArea = v.repaintArea;

                        Drawable d = repaintImageView.getDrawable();
                        if (d instanceof BitmapDrawable)
                        {
                            if (((BitmapDrawable)d).getBitmap() != null)
                            {
                            	while(!lib.isDrawPageSyncFinished())
                            	{
                            		try
                            		{
                            			Thread.sleep(100);
                            		}
                            		catch(Exception e)
                            		{
                            			
                            		}
                            	}
                                ((BitmapDrawable)d).getBitmap().recycle();
                            }
                            listView.setDoRequstLayout(false);
                            repaintImageView.setImageBitmap(null);
                            repaintImageView.setImageBitmap(v.bm);
                            listView.setDoRequstLayout(true);
                        }

                        repaintImageView.layout(repaintArea.left, repaintArea.top,
                            repaintArea.right, repaintArea.bottom);
                        if (repaintImageView.getParent() == null)
                        {
                            addView(repaintImageView);
                            if (searchView != null)
                            {
                                searchView.bringToFront();
                            }
                        }
                        invalidate();
                        if (listView != null)
                        {
                            listView.exportImage(own, v.bm);                        
                        }
                    }
                    catch (Exception e)
                    {
                        
                    }
                }
            };

            try
            {
                Bitmap bitmap = Bitmap.createBitmap(paintArea.width(), paintArea.height(), Bitmap.Config.ARGB_8888);
                repaintSyncTask.safeExecute(new RepaintAreaInfo(bitmap, viewArea.width(), viewArea.height(), paintArea));
            }
            catch (OutOfMemoryError e)
            {          
                if (repaintImageView != null)
                {
                    Drawable d = repaintImageView.getDrawable();
                    if (d instanceof BitmapDrawable)
                    {
                        if (((BitmapDrawable)d).getBitmap() != null)
                        {
                        	while(!lib.isDrawPageSyncFinished())
                        	{
                        		try
                        		{
                        			Thread.sleep(100);
                        		}
                        		catch(Exception ee)
                        		{
                        			
                        		}
                        	}
                            ((BitmapDrawable)d).getBitmap().recycle();
                        }
                    }
                }
                System.gc();
                try
                {
                    Thread.sleep(50);
                    Bitmap bitmap = Bitmap.createBitmap(paintArea.width(), paintArea.height(), Bitmap.Config.ARGB_8888);
                    repaintSyncTask.safeExecute(new RepaintAreaInfo(bitmap, viewArea.width(), viewArea.height(), paintArea));
                }
                catch (OutOfMemoryError e2)
                {
                    
                }
                catch (Exception  aa)
                {
                    
                }
            }
        }
        else if (!mIsBlank)
        {
            if (isOriginalBitmapValid)
            {
                listView.exportImage(this, originalBitmap);
            }
        }
    }


    protected void removeRepaintImageView()
    {
        if (repaintSyncTask != null)
        {
            repaintSyncTask.cancel(true);
            repaintSyncTask = null;
        }

        viewWidth = viewHeight = 0;
        repaintArea = null;
        if (repaintImageView != null)
        {
            repaintImageView.setImageBitmap(null);
        }
    }
    
    
    protected void drawSerachView(Canvas canvas)
    {
        if (searchView != null)
        {
            searchView.draw(canvas);
        }
    }
    
    
    public void dispose()
    {
        listView = null;
        if (darwOriginalPageTask != null)
        {
            darwOriginalPageTask.cancel(true);
            darwOriginalPageTask = null;
        }
        if (repaintSyncTask != null)
        {
            repaintSyncTask.cancel(true);
            repaintSyncTask = null;
        }
        if (originalImageView != null)
        {   
            Drawable d = originalImageView.getDrawable();
            if (d instanceof BitmapDrawable)
            {
                if (((BitmapDrawable)d).getBitmap() != null)
                {
                	while(!lib.isDrawPageSyncFinished())
                	{
                		try
                		{
                			Thread.sleep(100);
                		}
                		catch(Exception e)
                		{
                			
                		}
                	}
                    ((BitmapDrawable)d).getBitmap().recycle();
                }
            }
            originalImageView.setImageBitmap(null);
        }

        if (repaintImageView != null)
        {   
            Drawable d = repaintImageView.getDrawable();
            if (d instanceof BitmapDrawable)
            {
                if (((BitmapDrawable)d).getBitmap() != null)
                {
                	while(!lib.isDrawPageSyncFinished())
                	{
                		try
                		{
                			Thread.sleep(100);
                		}
                		catch(Exception e)
                		{
                			
                		}
                	}
                    ((BitmapDrawable)d).getBitmap().recycle();
                }
            }
            repaintImageView.setImageBitmap(null);
        }
    }

    private boolean isOriginalBitmapValid;

    private boolean isAutoTest;

    private int viewWidth;

    private int viewHeight;


    private ImageView originalImageView;
    private Bitmap originalBitmap;
    private SafeAsyncTask<Void, Void, Bitmap> darwOriginalPageTask;

    private ImageView repaintImageView;
    private SafeAsyncTask<RepaintAreaInfo, Void, RepaintAreaInfo> repaintSyncTask;    

    private Rect repaintArea;
    private View searchView;
    private final PDFLib lib;
    private ProgressBar mBusyIndicator;
    
}
