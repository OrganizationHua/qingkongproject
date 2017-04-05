package com.iwangcn.qingkong.ui.activity;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;

/**
 * 系统设置
 */
public class SettingsActivity extends QkBaseActivity {

    @Override
    public int layoutResID() {
        return R.layout.activity_set;
    }

    @Override
    public void initView() {
        setTitle(getResources().getString(R.string.mine_system_set));
    }
}
