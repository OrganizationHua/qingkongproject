package com.iwangcn.qingkong.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.adapter.FollowTabAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.iwangcn.qingkong.utils.TabLayoutUtil.setIndicator;

/**
 * 头条详情界面
 */
public class ProcessedActivity extends QkBaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @Override
    public void onStart() {
        super.onStart();
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mTabLayout, 66, 66);
            }
        });
    }
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
        Intent intent = new Intent(this, TagEditActivity.class);
        startActivity(intent);
    }
}
