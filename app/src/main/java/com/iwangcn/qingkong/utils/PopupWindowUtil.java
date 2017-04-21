package com.iwangcn.qingkong.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.view.TagWidget.PopTagAdapter;
import com.iwangcn.qingkong.ui.view.TagWidget.TagModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by czh on 2017/4/21.
 */

public class PopupWindowUtil {
    // 右上角弹窗

    /**
     * @param context
     * @param popupview         弹出按钮
     * @param rootView          弹出按钮 遮罩view
     * @param direct            0左边靠左弹出 其他数据靠右边弹出
     * @param listData          需要显示的数据
     * @param onConfirmListener 点击确认按钮回调
     */
    public static void showPopupWindow(final Context context, View popupview, final View rootView, int direct, final List<TagModel> listData, final View.OnClickListener onConfirmListener) {
        rootView.setVisibility(View.VISIBLE);
        final PopTagAdapter popTagAdapter = new PopTagAdapter(listData, context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.tag_popupwindow, null);
        LinearLayout linbg = (LinearLayout) view.findViewById(R.id.tag_flowlayout_lin);
        if (direct == 0) {
            linbg.setBackgroundResource(R.drawable.tag_popup_left_bg);
        } else {
            linbg.setBackgroundResource(R.drawable.tag_popup_right_bg);
        }
        Button btnConfig = (Button) view.findViewById(R.id.tag_btn_confirm);

        TagFlowLayout tagFlowLayout = (TagFlowLayout) view.findViewById(R.id.tag_flowlayout);
        // 创建一个PopuWidow对象
        tagFlowLayout.setAdapter(popTagAdapter);
        final PopupWindow popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        int popupWidth = view.getMeasuredWidth();
        final int popupHeight = view.getMeasuredHeight();
        int[] location = new int[2];
        popupview.getLocationOnScreen(location);
        popupWindow.showAtLocation(popupview, Gravity.NO_GRAVITY, (location[0] + popupview.getWidth() / 2) - popupWidth / 2,
                location[1] + popupview.getHeight() / 4 - popupHeight);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                rootView.setVisibility(View.GONE);
            }
        });
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ToastUtil.showToast(context, position + "position");
                TagModel selectModel = listData.get(position);
                selectModel.setSelect(!selectModel.isSelect());
                popTagAdapter.notifyDataChanged();
                return false;
            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                onConfirmListener.onClick(view);
            }
        });
    }
}
