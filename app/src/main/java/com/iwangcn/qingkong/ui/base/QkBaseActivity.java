package com.iwangcn.qingkong.ui.base;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by matou0289 on 2016/10/20.
 */

public abstract class QkBaseActivity extends BaseActivity {

    public abstract int layoutChildResID();//不带top的资源ID

    public abstract void initView();

    @BindView(R.id.hl_base_lin_sub)
    LinearLayout mLinSub;//子布局

    @BindView(R.id.base_act_left_lin)
    protected LinearLayout mLinTopLeft;//左边布局titleTop

    @BindView(R.id.base_act_right_lin)
    LinearLayout mLinTopRight;//右边titleTop

    @BindView(R.id.base_tv_title)
    TextView mTvTitle;//标题

    @BindView(R.id.base_tv_right)
    TextView mTvRight;//右边文字
    @BindView(R.id.base_img_right)
    ImageView mImgRight;//右边图片

    @BindView(R.id.systemFailNetRootRel)
    RelativeLayout mRelFailNet;//网络加载失败

    @BindView(R.id.systemNoNetRoootRel)
    RelativeLayout mRelNoNet;//当前无网络

    @BindView(R.id.systemFailLin)
    LinearLayout mLinRefresh;//当前无网络

    @BindView(R.id.base_root_mark_rel)
    public RelativeLayout mRelMak;

    private LayoutInflater inflater;
    private LinearLayout.LayoutParams layoutParams = null;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.base_activity);
        //translucentStatusBar();
        initSubView();
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initSubView() {
        inflater = LayoutInflater.from(this);
        View view = null;
        if (layoutChildResID() != 0) {
            view = inflater.inflate(layoutChildResID(), null);
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

    @OnClick(R.id.base_act_left_lin)//APP信息
    public void goBack() {
        onBackPressed();
    }

    protected TextView setRightTitle(String title) {
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(title);
        return mTvTitle;
    }

    protected ImageView setRightImg(int resId) {
        mImgRight.setVisibility(View.VISIBLE);
        mImgRight.setBackgroundResource(resId);
        return mImgRight;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return false;
    }

    /**
     * 根据网络显示当前界面
     *
     * @param isNetWork
     */
    protected void setHasNetWork(boolean isNetWork) {
        if (isNetWork) {
            mRelFailNet.setVisibility(View.GONE);
        } else {
            mRelFailNet.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 加载失败重新刷新
     *
     * @param isFailNetWork
     * @param failRefreshListener
     */
    protected void setFailNetWork(boolean isFailNetWork, final View.OnClickListener failRefreshListener) {
        if (isFailNetWork) {
            mRelFailNet.setVisibility(View.VISIBLE);
            mLinRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    failRefreshListener.onClick(view);
                }
            });
        } else {
            mRelFailNet.setVisibility(View.GONE);
        }
    }

    @Override

    protected void onDestroy() {
        super.onDestroy();
    }
}
