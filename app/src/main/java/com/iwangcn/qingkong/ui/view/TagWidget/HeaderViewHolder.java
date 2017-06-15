package com.iwangcn.qingkong.ui.view.TagWidget;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.iwangcn.qingkong.R;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    private TextView mImageView;
    private LinearLayout mHeadLine;

    HeaderViewHolder(View itemView) {
        super(itemView);
        mImageView = (TextView) itemView.findViewById(R.id.textview);
        mHeadLine = (LinearLayout) itemView.findViewById(R.id.head_line);
    }

    void bindTo(String string) {
        if (string.equals("推荐标签")) {
            mHeadLine.setVisibility(View.GONE);
        }else{
            mHeadLine.setVisibility(View.VISIBLE);
        }
        mImageView.setText(string);
        mImageView.setBackgroundColor(Color.TRANSPARENT);
        ViewGroup.LayoutParams lp = mImageView.getLayoutParams();

        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
            flexboxLp.setFlexGrow(1.0f);
        }
    }

}