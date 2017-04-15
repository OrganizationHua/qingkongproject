package com.iwangcn.qingkong.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iwangcn.qingkong.R;


/**
 * 通用dialog
 * Created by czh on 2016/3/2.
 */
public class MyCommonDialog extends Dialog {
    public interface DialogInterface {
        void onCancle();

        void onConfirm();
    }

    private Button btnConfirm;
    private Button btnCanle;
    private TextView tvContent;
    private TextView tvTitle;
    private Context mContext;
    private DialogInterface onDialogClick;
    private View viewLine;

    public MyCommonDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
        init();
    }

    public MyCommonDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        init();
    }


    public void setOnDialogClick(DialogInterface onDialogClick) {
        this.onDialogClick = onDialogClick;
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_dialog, null);
        tvContent = (TextView) view.findViewById(R.id.common_tv);
        tvTitle = (TextView) view.findViewById(R.id.common_tv_title);
        btnConfirm = (Button) view.findViewById(R.id.dialog_btn_confirm);
        btnCanle = (Button) view.findViewById(R.id.dialog_btn_cancle);
        viewLine = view.findViewById(R.id.dialog_line_view);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onDialogClick.onConfirm();
            }
        });
        btnCanle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onDialogClick.onCancle();
            }
        });
        setContentView(view);
    }

    public void setTitle(CharSequence text) {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(text);
    }

    public void setContent(CharSequence text) {
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setText(text);
    }

    public void setContent(CharSequence text, int gravity) {
        tvContent.setVisibility(View.VISIBLE);
        tvContent.setText(text);
        tvContent.setGravity(gravity);
    }

    public void setOnlyConfirmBtn() {
        btnCanle.setVisibility(View.GONE);
        viewLine.setVisibility(View.GONE);
    }

    public MyCommonDialog setCancleText(CharSequence text) {
        btnCanle.setText(text);
        return this;
    }

    public MyCommonDialog setConfirmText(CharSequence text) {
        btnConfirm.setText(text);
        return this;
    }
}
