package com.document.allreader.allofficefilereader.interfaces;

import android.view.View;


public interface OnRecyclerItemClickLister {
    void onItemCheckBoxClicked(View view, int i);

    void onItemClicked(int i);

    void onItemDeleteClicked(int i);

    void onItemLongClicked(int i);

    void onItemRenameClicked(int i);

    void onItemShareClicked(int i);
}
