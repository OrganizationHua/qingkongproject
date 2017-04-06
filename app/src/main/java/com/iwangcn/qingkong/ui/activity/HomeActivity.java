package com.iwangcn.qingkong.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.adapter.TabLayoutAdapter;
import com.iwangcn.qingkong.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        translucentStatusBar();
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        final TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(tabLayoutAdapter);
        mTabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setCustomView(tabLayoutAdapter.getTabView(i));

        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_btn_default).setSelected(true);
                tab.getCustomView().findViewById(R.id.tab_btn_title).setSelected(true);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_btn_default).setSelected(false);
                tab.getCustomView().findViewById(R.id.tab_btn_title).setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
