package com.on.qingkong.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.on.qingkong.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        com.orhanobut.logger.Logger.e("......");
    }
}
