package com.iwangcn.qingkong.providers;

import com.iwangcn.qingkong.app.MobileApplication;
import com.iwangcn.qingkong.net.ACache;
import com.iwangcn.qingkong.sp.SpConstant;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.model.UserInfo;

/**
 * 用户信息管理
 * Created by czh on 2017/4/4.
 */

public class UserManager {
    private static UserManager instance;
    private static UserInfo userInfo;

    public static void setUserName(UserInfo userInfo) {
        UserManager.userInfo = userInfo;
        ACache.get(MobileApplication.getInstance()).put(SpConstant.USER_INFO, userInfo);
        SpUtils.put(MobileApplication.getInstance(), SpConstant.IS_LOGIN, true);
        SpUtils.put(MobileApplication.getInstance(), SpConstant.CACHE_USERNAME, userInfo.getName());
    }

    public static UserInfo getUserInfo() {
        if (userInfo == null) {
            userInfo = (UserInfo) ACache.get(MobileApplication.getInstance()).getAsObject(SpConstant.USER_INFO);
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
