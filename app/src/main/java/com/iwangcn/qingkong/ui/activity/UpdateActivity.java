package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.net.NetworkUtil;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.view.MyCommonDialog;
import com.iwangcn.qingkong.utils.AbAppUtil;
import com.iwangcn.qingkong.utils.UpdateAppUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 升级
 * Created by czh on 2017/4/12.
 */

public class UpdateActivity extends QkBaseActivity {
    @BindView(R.id.update_current_version)
    TextView mTvCurrent;
    private Context mContext = this;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_updata_version;
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.updata_version));
        mTvCurrent.setText(AbAppUtil.getPackageInfo(this).versionName);
    }

    @OnClick(R.id.btn_update)//升级
    public void onBtnUpdate() {
        if (NetworkUtil.isWifi(this)) {
            //ToastUtil.showToast(this, "正在升级");
            checkUpdate(mContext);
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
                    checkUpdate(mContext);
                }
            });
            dialog.show();
        }
    }
    public static void checkUpdate(Context context) {
        //示例检查更新地址
        String url = "http://localhost:8081/view/app/pub?event=update&platform=android&versionCode=" + UpdateAppUtils.getVerCode(context);
        //实例化更新工具
        UpdateAppUtils versionUtils = new UpdateAppUtils(context, false);
        //检查更新
        versionUtils.checkUpdate(url);
    }

}
