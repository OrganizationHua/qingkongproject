package com.iwangcn.qingkong.ui.view.TagWidget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.utils.AbAppUtil;

public class LastViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageview;
    private EditText mEiditView;

    LastViewHolder(View itemView) {
        super(itemView);
        imageview = (ImageView) itemView.findViewById(R.id.imageview);
        mEiditView = (EditText) itemView.findViewById(R.id.editview);
    }

    void bindTo(boolean isAdd) {
        if (isAdd) {
            mEiditView.setVisibility(View.VISIBLE);
            mEiditView.requestFocus();
        } else {
            mEiditView.setVisibility(View.GONE);
        }

//        ViewGroup.LayoutParams lp = mImageView.getLayoutParams();
//
//        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
//            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
//            flexboxLp.setFlexGrow(1.0f);
//        }
    }

    void bindTo(TextView.OnEditorActionListener listener, boolean isAdd,Context context) {
        if (isAdd) {
            mEiditView.setFocusable(true);
            mEiditView.setFocusableInTouchMode(true);

            mEiditView.requestFocus();
            AbAppUtil.showSoftInput(context);
        } else {
            mEiditView.setFocusable(false);
            mEiditView.setText("");
            AbAppUtil.closeSoftInput(context);
        }
        //  mEiditView.requestFocus();
        if (listener != null) {
            mEiditView.setOnEditorActionListener(listener);
        }

//        ViewGroup.LayoutParams lp = mImageView.getLayoutParams();
//
//        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
//            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
//            flexboxLp.setFlexGrow(1.0f);
//        }
    }
}