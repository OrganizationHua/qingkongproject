package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.sp.SpConstant;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;


public class BGABannerActivity extends BaseActivity {

    private BGABanner mContentBanner;
    private Context context = this;
    private Button mBtnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bgabanner);
        mContentBanner = (BGABanner) findViewById(R.id.banner_guide_content);
        mBtnEnter = (Button) findViewById(R.id.btn_guide_enter);
        init();
    }

    private void init() {
        final List<View> views = new ArrayList<>();
        views.add(BGABannerUtil.getItemImageView(this, R.drawable.ic_guide_1));
        views.add(BGABannerUtil.getItemImageView(this, R.drawable.ic_guide_2));
        views.add(BGABannerUtil.getItemImageView(this, R.drawable.ic_guide_3));
        views.add(BGABannerUtil.getItemImageView(this, R.drawable.ic_guide_4));
        mContentBanner.setData(views);
        mContentBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                SpUtils.put(context, SpConstant.IS_FIRST_START, true);
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        });
        mContentBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == views.size() - 1) {
                    mBtnEnter.setVisibility(View.VISIBLE);
                } else {
                    mBtnEnter.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
