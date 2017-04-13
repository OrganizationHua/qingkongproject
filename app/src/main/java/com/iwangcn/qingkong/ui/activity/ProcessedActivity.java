package com.iwangcn.qingkong.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.adapter.FollowTabAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 头条详情界面
 */
public class ProcessedActivity extends QkBaseActivity {
    //    @BindView(R.id.iv_sift)
//    ImageView iv_sift;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_processed;
    }

    @Override
    public void initView() {
        setTitle("已处理");
        setRightImg(R.drawable.genjin_btn_filter_2);
        final FollowTabAdapter tabLayoutAdapter = new FollowTabAdapter(this, getSupportFragmentManager(), 2);
        viewPager.setAdapter(tabLayoutAdapter);
        mTabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.base_act_right_lin)//APP信息
    public void onSift() {
        ToastUtil.showToast(this, "查看原文");
    }
}
