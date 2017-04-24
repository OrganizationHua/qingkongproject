package com.iwangcn.qingkong.net;

/**
 * Created by RF on 2017/1/11.
 */

public interface NetConst {
    String STATUS_SUCCESS = "0";
    String STATUS_FAILURE = "1";
    String CODE_SUCCESS = "0";
    public static int page = 15;
    public static String USER_ID = "userId";

    String URL_LOGIN = "login";//登录
    String URL_EVENT = "event/index";//首页事件
    String URL_EVENT_HELP = "aide/index";//助手事件
    String URL_UPDATE_PWD = "user/updatePwd";//修改密码
    String URL_CHECK_VERSION = "user/checkVersion";//1.1.1	查询是新版本

}
