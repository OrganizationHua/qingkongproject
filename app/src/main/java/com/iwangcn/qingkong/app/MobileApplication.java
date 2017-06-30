package com.iwangcn.qingkong.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import cn.jpush.android.api.JPushInterface;

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
        initJpush();
    }

    // 初始化 JPush
    private void initJpush() {
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
//        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
//        builder.statusBarDrawable = R.drawable.ic_launcher;
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为自动消失
//        JPushInterface.setPushNotificationBuilder(1, builder);
//        CustomPushNotificationBuilder builder = new
//                CustomPushNotificationBuilder(this,
//                R.layout.customer_notitfication_layout,
//                R.id.icon,
//                R.id.title,
//                R.id.text);
//        // 指定定制的 Notification Layout
//        builder.statusBarDrawable = R.drawable.zhushou_btn_close;
//        // 指定最顶层状态栏小图标
//        builder.layoutIconDrawable = R.drawable.zhushou_btn_close;
//        // 指定下拉状态栏时显示的通知图标
//        JPushInterface.setPushNotificationBuilder(2, builder);

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
            AbActivityManager.getInstance().addActivity(activity);
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
            AbActivityManager.getInstance().removeActivity(activity);
        }
    };

}
