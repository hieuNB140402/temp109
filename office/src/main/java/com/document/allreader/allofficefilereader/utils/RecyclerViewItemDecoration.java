package com.document.allreader.allofficefilereader.utils;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public RecyclerViewItemDecoration(int i) {
        this.space = i;
    }

    @Override
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        rect.left = this.space;
        rect.right = this.space;
        rect.bottom = (int) (((double) this.space) / 1.2d);
        if (recyclerView.getChildLayoutPosition(view) == 0) {
            rect.top = 0;
        } else {
            rect.top = 0;
        }
    }
}
