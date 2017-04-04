package com.iwangcn.qingkong.app;

import android.app.Application;

/**
 * Created by czh on 2017/3/30.
 */

public class MobileApplication extends Application {
    private static MobileApplication instance;

    public MobileApplication() {
        instance = this;
    }

    public static MobileApplication getInstance() {
        if (instance == null)
            throw new IllegalStateException();
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
