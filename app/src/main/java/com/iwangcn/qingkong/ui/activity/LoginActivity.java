package com.iwangcn.qingkong.ui.activity;

import android.os.Bundle;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.base.BaseActivity;

import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
