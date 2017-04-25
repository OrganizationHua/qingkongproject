package com.iwangcn.qingkong.providers;

import com.google.gson.Gson;
import com.iwangcn.qingkong.app.MobileApplication;
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
        UserManager.userInfo=userInfo;
        SpUtils.put(MobileApplication.getInstance(), SpConstant.USER_INFO, new Gson().toJson(userInfo));
        SpUtils.put(MobileApplication.getInstance(), SpConstant.IS_LOGIN, true);
        SpUtils.put(MobileApplication.getInstance(), SpConstant.CACHE_USERNAME, userInfo.getName());
    }

    public static UserInfo getUserInfo() {
        if (userInfo == null) {
            String strUserInfo = (String) SpUtils.get(MobileApplication.getInstance(), SpConstant.USER_INFO, "");
            userInfo = new Gson().fromJson(strUserInfo, UserInfo.class);
        }
        return userInfo;
    }

    /**
     * 清除用户信息
     */
    public static void clearUserInfo() {
        userInfo = null;
        SpUtils.put(MobileApplication.getInstance(), SpConstant.USER_INFO, "");
    }

}
