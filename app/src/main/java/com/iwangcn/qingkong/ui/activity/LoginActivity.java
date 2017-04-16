package com.iwangcn.qingkong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.base.BaseActivity;
import com.iwangcn.qingkong.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_ed_userName)
    EditText mEdUserName;
    @BindView(R.id.login_ed_pw)
    EditText mEdPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_btn)//APP信息
    public void onBtLogin()
    {
        String strUserName=mEdUserName.getText().toString().trim();
        String strPw=mEdPw.getText().toString().trim();
        if(TextUtils.isEmpty(strUserName)||TextUtils.isEmpty(strPw)){
            ToastUtil.showToast(this, "用户名和密码不能为空");
            return;
        }
        if(strPw.length()<6){
            ToastUtil.showToast(this, "密码不能少于6位");
            return ;
        }
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
        this.finish();
    }
}
