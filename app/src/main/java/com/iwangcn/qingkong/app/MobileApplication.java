package com.iwangcn.qingkong.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by czh on 2017/3/30.
 */

public class MobileApplication extends Application {
    private static MobileApplication instance;
    private Activity currentActivity;
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
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }
    @Override
    public void onTerminate() {
        unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
        super.onTerminate();
    }

    /**
     * 获取当前运行Activity 支持API 14以上
     */
    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }
    /**
     * 声明一个监听Activity们生命周期的接口
     */
    private ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        /**
         * application下的每个Activity声明周期改变时，都会触发以下的函数。
         */
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            setCurrentActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            setCurrentActivity(activity);//防止没有走onActivityCreated 生命周期时
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };
}
