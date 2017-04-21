package com.iwangcn.qingkong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.sp.SpConstant;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    private int SPLASH_SCREEN_DELAY_MILLISECOND = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        boolean isLogin = (boolean) SpUtils.get(this, SpConstant.IS_LOGIN, false);
        if (isLogin) {
            pageDelay(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    SplashActivity.this.startActivity(intent);
                    SplashActivity.this.overridePendingTransition(R.anim.slide_in_right, 0);
                    SplashActivity.this.finish();
                }
            });
        } else {
            pageDelay(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(intent);
                    SplashActivity.this.overridePendingTransition(R.anim.slide_in_right, 0);
                    SplashActivity.this.finish();
                }
            });
        }
    }

    private void pageDelay(Runnable runnable) {
        new Handler().postDelayed(runnable,SPLASH_SCREEN_DELAY_MILLISECOND);
    }
}
