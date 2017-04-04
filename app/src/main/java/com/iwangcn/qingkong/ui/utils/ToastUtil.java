package com.iwangcn.qingkong.ui.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iwangcn.qingkong.app.MobileApplication;
import com.iwangcn.qingkong.R;


/**
 * TOAST 优化
 * Created by czh on 2016/1/12.
 */
public class ToastUtil {
    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showToast(Context context, String s) {
        if (toast == null) {
//            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();

            View layout = LayoutInflater.from(MobileApplication.getInstance()).inflate(R.layout.custom_toast,
                    null);
            // set a message
            TextView toastText = (TextView) layout.findViewById(R.id.toasttext);
            toastText.setText(s);
            // Toast...
            toast = new Toast(MobileApplication.getInstance());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setView(layout);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                View v = toast.getView();
                TextView tv = (TextView) v.findViewById(R.id.toasttext);
                tv.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }

    private static Toast createToast(Context context, String text) {
        View layout = LayoutInflater.from(context).inflate(R.layout.custom_toast,
                null);
        // set a message
        TextView toastText = (TextView) layout.findViewById(R.id.toasttext);
        toastText.setText(text);
        // Toast...
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
        return toast;
    }

    public static void cancleToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
