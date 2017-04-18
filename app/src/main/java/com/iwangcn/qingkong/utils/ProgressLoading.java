package com.iwangcn.qingkong.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;

/**
 * 作者：fjg on 2017/4/15 20:32
 */

public class ProgressLoading {
    private Context context;
    private Dialog loadingDialog;

    public ProgressLoading(Context context) {
        this.context = context;
    }

    public Dialog createLoadingDialog(String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.load_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);

        tipTextView.setText(msg);// 设置加载信息

        loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;
    }

    public void showLoadingView() {
        if (loadingDialog == null || loadingDialog.isShowing()) return;
        loadingDialog.show();
    }

    public void dismissLoadingView() {
        if (loadingDialog == null) return;
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }

    }
}