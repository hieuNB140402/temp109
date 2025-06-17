package com.artifex.mupdfdemo;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MuPDFReflowAdapter extends BaseAdapter {
    private final Context mContext;
    private final MuPDFCore mCore;

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public MuPDFReflowAdapter(Context context, MuPDFCore muPDFCore) {
        this.mContext = context;
        this.mCore = muPDFCore;
    }

    public int getCount() {
        return this.mCore.countPages();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        MuPDFReflowView muPDFReflowView;
        if (view == null) {
            muPDFReflowView = new MuPDFReflowView(this.mContext, this.mCore, new Point(viewGroup.getWidth(), viewGroup.getHeight()));
        } else {
            muPDFReflowView = (MuPDFReflowView) view;
        }
        muPDFReflowView.setPage(i, new PointF());
        return muPDFReflowView;
    }
}
