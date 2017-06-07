package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.QueryDataTypeEvent;
import com.iwangcn.qingkong.sp.SpConstant;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.base.BaseActivity;
import com.iwangcn.qingkong.ui.model.QueryDataType;

public class SplashActivity extends BaseActivity {
    private int SPLASH_SCREEN_DELAY_MILLISECOND = 2000;
    Handler mHandler = new Handler();
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        isFirstStart();
        new QueryDataTypeEvent().queeryDataType();
    }

    /**
     * 如果是第一次启动
     */
    private void isFirstStart() {
        boolean isFirst = (boolean) SpUtils.get(this, SpConstant.IS_FIRST_START, false);
        if (!isFirst) {
            pageDelay(1);
        } else {
            isHasLogin();
        }
    }

    /**
     * 是否已经登录成功过
     */
    private void isHasLogin() {
        boolean isLogin = (boolean) SpUtils.get(this, SpConstant.IS_LOGIN, false);
        if (isLogin) {
            pageDelay(2);
        } else {
            pageDelay(3);
        }
    }

    private void pageDelay(final int enterrId) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                switch (enterrId) {
                    case 1:
                        intent = new Intent(mContext, BGABannerActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        intent = new Intent(mContext, HomeActivity.class);
                        SplashActivity.this.startActivity(intent);
                        SplashActivity.this.overridePendingTransition(R.anim.slide_in_right, 0);
                        SplashActivity.this.finish();
                        break;
                    case 3:
                        intent = new Intent(mContext, LoginActivity.class);
                        SplashActivity.this.startActivity(intent);
                        SplashActivity.this.overridePendingTransition(R.anim.slide_in_right, 0);
                        SplashActivity.this.finish();
                        break;
                }
            }
        }, SPLASH_SCREEN_DELAY_MILLISECOND);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, SPLASH_SCREEN_DELAY_MILLISECOND);
    }
}
