package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.activity.NewsSearchActivity;
import com.iwangcn.qingkong.ui.activity.TagEditActivity;
import com.iwangcn.qingkong.ui.adapter.FollowTabAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class FollowFragment extends BaseFragment {

    @BindView(R.id.iv_processed)
    ImageView iv_processed;
    @BindView(R.id.iv_sift)
    ImageView iv_sift;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @Override
    protected int layoutResID() {
        return R.layout.fragment_follow;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        final FollowTabAdapter tabLayoutAdapter = new FollowTabAdapter(getContext(), getChildFragmentManager());
        viewPager.setAdapter(tabLayoutAdapter);
        mTabLayout.setupWithViewPager(viewPager);
    }

    private void initData() {


    }

    @OnClick(R.id.iv_processed)//搜索按钮
    public void onProcessed() {
        Intent intent = new Intent(getActivity(), NewsSearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.iv_sift)//搜索按钮
    public void onSift() {
        Intent intent = new Intent(getActivity(), TagEditActivity.class);
        startActivity(intent);
    }

}
