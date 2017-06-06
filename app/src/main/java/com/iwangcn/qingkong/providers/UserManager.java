package com.iwangcn.qingkong.providers;

import com.iwangcn.qingkong.app.MobileApplication;
import com.iwangcn.qingkong.net.ACache;
import com.iwangcn.qingkong.sp.SpConstant;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.model.ClientUserInfoVo;
import com.iwangcn.qingkong.ui.model.UserInfo;

/**
 * 用户信息管理
 * Created by czh on 2017/4/4.
 */

public class UserManager {
    private static UserInfo userInfo;
    private static ClientUserInfoVo loginInfo;

    public static void setUserName(ClientUserInfoVo loginInfo) {
        UserManager.loginInfo = loginInfo;
        UserManager.userInfo = loginInfo.getUserInfo();
        ACache.get(MobileApplication.getInstance()).put(SpConstant.USER_INFO, loginInfo);
        SpUtils.put(MobileApplication.getInstance(), SpConstant.IS_LOGIN, true);
        SpUtils.put(MobileApplication.getInstance(), SpConstant.CACHE_USERNAME, loginInfo.getUserInfo().getName());
    }

    public static ClientUserInfoVo getClientUserInfo() {
        if (loginInfo == null) {
            loginInfo = (ClientUserInfoVo) ACache.get(MobileApplication.getInstance()).getAsObject(SpConstant.USER_INFO);
        }
        return loginInfo;
    }

    public static UserInfo getUserInfo() {
        if (userInfo == null) {
            userInfo = getClientUserInfo().getUserInfo();
        }
        return userInfo;
    }

    /**
     * 清除用户信息
     */
    public static void clearUserInfo() {
        userInfo = null;
        ACache.get(MobileApplication.getInstance()).clear();
        SpUtils.put(MobileApplication.getInstance(), SpConstant.IS_LOGIN, false);
    }

}
