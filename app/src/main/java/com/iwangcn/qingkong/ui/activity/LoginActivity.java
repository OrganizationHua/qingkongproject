package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.sp.SpConstant;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.base.BaseActivity;
import com.iwangcn.qingkong.ui.model.ClientUserInfoVo;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static cn.jpush.android.api.JPushInterface.getRegistrationID;

public class LoginActivity extends BaseActivity implements NetConst {
    @BindView(R.id.login_ed_userName)
    EditText mEdUserName;
    @BindView(R.id.login_ed_pw)
    EditText mEdPw;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();
        //获取极光推送的RegistrationID，发送请求保存或者更新后台
        String registrationID = getRegistrationID(mContext);
        Logger.e("registrationID", registrationID);
    }

    private void initData() {
        //默认保存用户名
        String defaultName = (String) SpUtils.get(this, SpConstant.CACHE_USERNAME, "");
        mEdUserName.setText(defaultName);

    }

    @OnClick(R.id.login_btn)//APP信息
    public void onBtLogin() {
        String strUserName = mEdUserName.getText().toString().trim();
        String strPw = mEdPw.getText().toString().trim();
        if (TextUtils.isEmpty(strUserName) || TextUtils.isEmpty(strPw)) {
            ToastUtil.showToast(this, "用户名和密码不能为空");
            return;
        }
        if (strPw.length() < 6) {
            ToastUtil.showToast(this, "密码不能少于6位");
            return;
        }
        login(strUserName, strPw);
    }

    public void login(String userName, String strPw) {
        HashMap paratems = new HashMap();
        paratems.put("username", userName);
        paratems.put("pwd", strPw);
        RetrofitInstance.getInstance().post(URL_LOGIN, paratems, ClientUserInfoVo.class, new BaseSubscriber<NetResponse<ClientUserInfoVo>>(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(NetResponse<ClientUserInfoVo> o) {
                ClientUserInfoVo loginInfo = o.getDataObject();
                if (loginInfo != null) {
                    UserManager.setUserName(loginInfo);
                    intentHome();
                }
            }
        });
    }

    private void intentHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }
}

