package com.iwangcn.qingkong.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matou0289 on 2016/10/20.
 */

public abstract class QkBaseActivity extends BaseActivity {

    public abstract int layoutResID();

    public abstract void initView();

    @BindView(R.id.hl_base_lin_sub)
    LinearLayout mLinSub;//子布局

    @BindView(R.id.base_act_left_lin)
    LinearLayout mLinTopLeft;//左边布局titleTop

    @BindView(R.id.base_act_right_lin)
    LinearLayout mLinTopRight;//右边titleTop

    @BindView(R.id.base_tv_title)
    TextView mTvTitle;//标题


    private LayoutInflater inflater;
    private LinearLayout.LayoutParams layoutParams = null;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.base_activity);
        //translucentStatusBar();
        ButterKnife.bind(this);
        initSubView();
        initView();
        initData();
    }

    private void initSubView() {
        inflater = LayoutInflater.from(this);
        View view = null;
        if (layoutResID() != 0) {
            view = inflater.inflate(layoutResID(), null);
        }
        mLinSub = (LinearLayout) findViewById(R.id.hl_base_lin_sub);
        layoutParams = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        if (view != null) {
            mLinSub.addView(view, layoutParams);
        }
    }

    public void initData() {
    }

    protected void setTitle(String title) {
        mTvTitle.setText(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
