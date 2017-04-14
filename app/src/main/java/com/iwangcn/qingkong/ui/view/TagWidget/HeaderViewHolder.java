package com.iwangcn.qingkong.ui.view.TagWidget;

import com.google.android.flexbox.FlexboxLayoutManager;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwangcn.qingkong.R;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    private TextView mImageView;

    HeaderViewHolder(View itemView) {
        super(itemView);
        mImageView = (TextView) itemView.findViewById(R.id.imageview);
    }

    void bindTo(String string) {
        mImageView.setText(string);
        mImageView.setBackgroundColor(Color.TRANSPARENT);
        ViewGroup.LayoutParams lp = mImageView.getLayoutParams();

        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
            flexboxLp.setFlexGrow(1.0f);
        }
    }

}