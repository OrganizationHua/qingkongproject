package com.iwangcn.qingkong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.jpush.NotifyEvent;
import com.iwangcn.qingkong.ui.adapter.TabLayoutAdapter;
import com.iwangcn.qingkong.ui.base.BaseActivity;
import com.iwangcn.qingkong.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        EventBus.getDefault().register(this);
    }

    private void initViewPager() {
        final TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(tabLayoutAdapter);
        mTabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setCustomView(tabLayoutAdapter.getTabView(i));

        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_btn_default).setSelected(true);
                tab.getCustomView().findViewById(R.id.tab_btn_title).setSelected(true);
                tab.getCustomView().findViewById(R.id.tab_unread_notify).setVisibility(View.INVISIBLE);
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

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) {
                isExit = false;
            }
        }
    };
    private boolean isExit = false;

    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.showToast(getBaseContext(), getString(R.string.msg_back_to_logout));
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(10, 2000);
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        intentToFragment();
    }

    private void intentToFragment() {
        Intent intent = getIntent();
        if (intent != null) {
            int type = intent.getIntExtra("type", 0);
            if (type != 0) {
                viewPager.setCurrentItem(type);
            }
        }
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof NotifyEvent) {
            Intent intent = (Intent) event.getObject();
            if (intent != null) {
                int type = intent.getIntExtra("type", 0);
                if (type != 0) {
                    mTabLayout.getTabAt(type).getCustomView().findViewById(R.id.tab_unread_notify).setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
