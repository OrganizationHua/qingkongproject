package com.iwangcn.qingkong.ui.view.TagWidget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.iwangcn.qingkong.R;

public class LastViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;

    LastViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.imageview);
    }

    void bindTo(String string) {
        ViewGroup.LayoutParams lp = mImageView.getLayoutParams();

        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
//            flexboxLp.setFlexGrow(1.0f);
        }
    }

}