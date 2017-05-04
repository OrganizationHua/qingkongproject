package com.iwangcn.qingkong.ui.model;

import com.iwangcn.qingkong.net.BaseBean;

/**
 * Created by czh on 2017/5/4.
 */

public class LoginInfo extends BaseBean{
    private UserInfo userInfo;
//

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
