package com.hu_sir.provinecitylibrary.interfaces;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface OnItemClickListener {
    void onItemClick(RecyclerView.Adapter adapter, View view, int position);
}
