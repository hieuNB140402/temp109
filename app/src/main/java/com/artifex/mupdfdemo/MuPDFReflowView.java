package com.artifex.mupdfdemo;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Base64;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MuPDFReflowView extends WebView implements MuPDFView {
    public int mContentHeight;
    public final MuPDFCore mCore;
    public final Handler mHandler = new Handler();
    AsyncTask<Void, Void, byte[]> mLoadHTML;
    public int mPage;
    private final Point mParentSize;
    public float mScale;

    public void blank(int i) {
    }

    public void cancelDraw() {
    }

    public void continueDraw(float f, float f2) {
    }

    public boolean copySelection() {
        return false;
    }

    public void deleteSelectedAnnotation() {
    }

    public void deselectAnnotation() {
    }

    public void deselectText() {
    }

    public LinkInfo hitLink(float f, float f2) {
        return null;
    }

    public boolean markupSelection(Annotation.Type type) {
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public void releaseBitmaps() {
    }

    public void removeHq() {
    }

    public boolean saveDraw() {
        return false;
    }

    public void selectText(float f, float f2, float f3, float f4) {
    }

    public void setChangeReporter(Runnable runnable) {
    }

    public void setLinkHighlighting(boolean z) {
    }

    public void setSearchBoxes(RectF[] rectFArr) {
    }

    public void setSearchBoxesPrim(RectF[] rectFArr) {
    }

    public void startDraw(float f, float f2) {
    }

    public void update() {
    }

    public void updateHq(boolean z) {
    }

    public MuPDFReflowView(Context context, MuPDFCore muPDFCore, Point point) {
        super(context);
        this.mCore = muPDFCore;
        this.mParentSize = point;
        this.mScale = 1.0f;
        this.mContentHeight = point.y;
        getSettings().setJavaScriptEnabled(true);
        addJavascriptInterface(new Object() {
            public void reportContentHeight(String str) {
                MuPDFReflowView.this.mContentHeight = (int) Float.parseFloat(str);
                MuPDFReflowView.this.mHandler.post(new Runnable() {
                    public void run() {
                        MuPDFReflowView.this.requestLayout();
                    }
                });
            }
        }, "HTMLOUT");
        setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView webView, String str) {
                MuPDFReflowView muPDFReflowView = MuPDFReflowView.this;
                muPDFReflowView.setScale(muPDFReflowView.mScale);
            }
        });
    }

    private void requestHeight() {
        loadUrl("javascript:elem=document.getElementById('content');window.HTMLOUT.reportContentHeight(" + this.mParentSize.x + "*elem.offsetHeight/elem.offsetWidth)");
    }

    public void setPage(int i, PointF pointF) {
        this.mPage = i;
        AsyncTask<Void, Void, byte[]> asyncTask = this.mLoadHTML;
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
        AsyncTask r1 = new AsyncTask<Void, Void, byte[]>() {
            public byte[] doInBackground(Void... voidArr) {
                return MuPDFReflowView.this.mCore.html(MuPDFReflowView.this.mPage);
            }

            public void onPostExecute(byte[] bArr) {
                MuPDFReflowView.this.loadData(Base64.encodeToString(bArr, 0), "text/html; charset=utf-8", "base64");
            }
        };
        this.mLoadHTML = r1;
        r1.execute( new Void[0]);
    }

    public int getPage() {
        return this.mPage;
    }

    public void setScale(float f) {
        this.mScale = f;
        loadUrl("javascript:document.getElementById('content').style.zoom=\"" + ((int) (this.mScale * 100.0f)) + "%\"");
        requestHeight();
    }

    public Hit passClickEvent(float f, float f2) {
        return Hit.Nothing;
    }

    public void releaseResources() {
        AsyncTask<Void, Void, byte[]> asyncTask = this.mLoadHTML;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.mLoadHTML = null;
        }
    }

    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        if (MeasureSpec.getMode(i) != MeasureSpec.UNSPECIFIED) {
            i3 = MeasureSpec.getSize(i);
        } else {
            i3 = this.mParentSize.x;
        }
        if (MeasureSpec.getMode(i2) != MeasureSpec.UNSPECIFIED) {
            i4 = MeasureSpec.getSize(i2);
        } else {
            i4 = this.mContentHeight;
        }
        setMeasuredDimension(i3, i4);
    }
}
