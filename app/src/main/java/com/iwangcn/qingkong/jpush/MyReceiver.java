package com.iwangcn.qingkong.jpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.sp.SpConstant;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.activity.HomeActivity;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                //cn.jpush.android.MESSAGE
                Logger.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                //	processCustomMessage(context, bundle);
                processCustomMessage(context, bundle);
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                //receivingNotification(context,bundle);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");

                //打开自定义的Activity
//				Intent i = new Intent(context, TestActivity.class);
//				i.putExtras(bundle);
//				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//				context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logger.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    private void receivingNotification(Context context, Bundle bundle) {
        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        // 使用notification
        // 使用广播或者通知进行内容的显示
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        builder.setContentText(message).setSmallIcon(R.drawable.ic_launcher).setContentTitle(JPushInterface.EXTRA_TITLE);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        manager.notify(1, builder.build());
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString("cn.jpush.android.MESSAGE");
        if (!TextUtils.isEmpty(message)) {
            try {
                JSONObject extraJson = new JSONObject(message);
                int type = extraJson.getInt("type");
                String content = extraJson.getString("content");
                String name = extraJson.getString("name");
                showNotification(context, type, content, name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void showNotification(Context context, int type, String content, String name) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("type", type);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        int requestID = (int) System.currentTimeMillis();
        PendingIntent pendingintent = PendingIntent.getActivity(context, requestID,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingintent);
        mBuilder.setContentTitle(name);//设置通知栏标题
        mBuilder.setContentText(content);
        mBuilder.setWhen(System.currentTimeMillis());//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
        mBuilder.setPriority(Notification.PRIORITY_DEFAULT); //设置该通知优先级
        mBuilder.setAutoCancel(true);//设置这个标志当用户单击面板就可以让通知将自动取消
        mBuilder.setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON
        Notification mNotification = mBuilder.build();
        mNotification.icon = R.drawable.ic_launcher;
        mNotification.when = System.currentTimeMillis();
        mNotification.tickerText = name;

        boolean isNotify = (boolean) SpUtils.get(context, SpConstant.IS_NOTIFY, false);
        boolean isSound = (boolean) SpUtils.get(context, SpConstant.IS_SOUND, false);
        boolean isVibrate = (boolean) SpUtils.get(context, SpConstant.IS_VIBRATE, false);
        if (isNotify) {
            if (isSound) {
                mNotification.defaults |= Notification.DEFAULT_SOUND;
            } else {
                mNotification.sound = null;
            }
            if (isVibrate) {
                mNotification.defaults |= Notification.DEFAULT_VIBRATE;
            } else {
                mNotification.vibrate = null;
            }
        } else {
            mNotification.sound = null;
            mNotification.vibrate = null;
        }
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击进入activity后自动消除
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(requestID, mNotification);
        NotifyEvent notifyEvent=new NotifyEvent();
        notifyEvent.setObject(intent);
        EventBus.getDefault().post(notifyEvent);
    }
}
