package com.iwangcn.qingkong.net;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.app.MobileApplication;

import rx.Subscriber;


/**
 * BaseSubscriber
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> implements CancelListener {

    private Context context;
    private ProgressDialogHelper mProgressDialogHandler;
    private boolean isShowDialog = true;
    private CancelListener cancelListener;

    /**
     * 配置是否显示进度条对话框
     */
    public BaseSubscriber(boolean isShowDialog) {
        this.context = MobileApplication.getInstance().getCurrentActivity();
        this.isShowDialog = isShowDialog;
        if (isShowDialog) {
            mProgressDialogHandler = new ProgressDialogHelper(context, this, true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(context)) {
            Toast.makeText(context, R.string.no_network, Toast.LENGTH_SHORT);
            onCompleted();
        } else {
            if (context != null) {
                showProgressDialog();
            }

        }
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            if (TextUtils.equals(NetConst.STATUS_SUCCESS, ((ExceptionHandle.ResponeThrowable) e).status)) {//通信正常时展示业务接口错误信息
                ((ExceptionHandle.ResponeThrowable) e).statusMsg = ((ExceptionHandle.ResponeThrowable) e).errorCodeMessage;
                if (!handleErrorCode(((ExceptionHandle.ResponeThrowable) e).errorCode, ((ExceptionHandle.ResponeThrowable) e).errorCodeMessage)) {
                    onError((ExceptionHandle.ResponeThrowable) e);
                }
            } else {//当status==1时，没必要展示业务接口错误信息
                ((ExceptionHandle.ResponeThrowable) e).errorCodeMessage = ((ExceptionHandle.ResponeThrowable) e).statusMsg;
                onError((ExceptionHandle.ResponeThrowable) e);
            }
        } else {//有可能是rx NullPointExceptionE错误
            onError(new ExceptionHandle.ResponeThrowable(NetConst.STATUS_FAILURE, ExceptionHandle.ERROR.tip_message, ExceptionHandle.ERROR.UNKNOWN, ExceptionHandle.ERROR.tip_message));
        }
    }

    public abstract void onError(ExceptionHandle.ResponeThrowable e);

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    private void showProgressDialog() {
        if (!isShowDialog) {
            return;
        }
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.showProgressDialog();
        }
    }

    private void dismissProgressDialog() {
        if (!isShowDialog) {
            return;
        }
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.dismissProgressDialog();
        }
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    private boolean handleErrorCode(String errorCode, String errorCodeMessage) {//公共异常信息处理
        return false;
    }

}
