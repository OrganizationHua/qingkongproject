package com.iwangcn.qingkong.ui.activity;

import android.content.Context;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.net.NetworkUtil;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.view.MyCommonDialog;
import com.iwangcn.qingkong.utils.ToastUtil;

import butterknife.OnClick;

/**
 * 升级
 * Created by czh on 2017/4/12.
 */

public class UpdateActivity extends QkBaseActivity {
    private Context mContext = this;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_updata_version;
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.updata_version));
    }

    @OnClick(R.id.btn_update)//升级
    public void onBtnUpdate() {
        if (NetworkUtil.isWifi(this)) {
            ToastUtil.showToast(this, "正在升级");
        } else {
            final MyCommonDialog dialog = new MyCommonDialog(this);
            dialog.setContent(getString(R.string.updata_tips));
            dialog.setOnDialogClick(new MyCommonDialog.DialogInterface() {
                @Override
                public void onCancle() {

                }

                @Override
                public void onConfirm() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
