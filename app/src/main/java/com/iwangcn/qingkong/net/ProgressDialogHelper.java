package com.iwangcn.qingkong.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.iwangcn.qingkong.R;


/**
 * 作者：fjg on 16/9/8 22:00
 */
public class ProgressDialogHelper {

    private ProgressDialog pd;
    private Context context;
    private boolean cancelable;
    private CancelListener cancelListener;

    public ProgressDialogHelper(Context context, CancelListener cancelListener,
                                 boolean cancelable) {
        super();
        this.context = context;
        this.cancelListener = cancelListener;
        this.cancelable = cancelable;
    }

    public void showProgressDialog() {
        if (context != null && pd == null) {
            pd = new ProgressDialog(context, R.style.Widget_AppCompat_ProgressBar);
            pd.setCancelable(cancelable);
            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        cancelListener.onCancelProgress();
                    }
                });
                pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        cancelListener.onCancelProgress();
                    }
                });
            }

            if (!pd.isShowing() && context != null) {
                pd.show();
                pd.setCanceledOnTouchOutside(false);
            }

        }
    }

    public void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }
}
