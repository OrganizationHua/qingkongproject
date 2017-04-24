package com.iwangcn.qingkong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.net.RetrofitInstance;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.sp.SpConstant;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.base.BaseActivity;
import com.iwangcn.qingkong.ui.model.UserInfo;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements NetConst {
    @BindView(R.id.login_ed_userName)
    EditText mEdUserName;
    @BindView(R.id.login_ed_pw)
    EditText mEdPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();
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
//        if (TextUtils.isEmpty(strUserName) || TextUtils.isEmpty(strPw)) {
//            ToastUtil.showToast(this, "用户名和密码不能为空");
//            return;
//        }
//        if (strPw.length() < 6) {
//            ToastUtil.showToast(this, "密码不能少于6位");
//            return;
//        }
        login(strUserName, strPw);
    }

    public void login(String userName, String strPw) {
        HashMap paratems = new HashMap();
//        paratems.put("uname",userName);
//        paratems.put("pwd",strPw);
        paratems.put("username", "test");
        paratems.put("pwd", "1");
        RetrofitInstance.getInstance().post(URL_LOGIN, paratems, UserInfo.class, new BaseSubscriber<UserInfo>(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(UserInfo o) {
                UserManager.setUserName(o);
                intentHome();
            }
        });
    }

    private void intentHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }
}

