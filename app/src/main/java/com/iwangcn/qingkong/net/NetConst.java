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

    String URL_UPDATE_PWD = "user/updatePwd";//修改密码
    String URL_CHECK_VERSION = "user/checkVersion";//1.1.1	查询是新版本

    /**
     * 助手模块
     */
    String URL_EVENT_HELP = "aide/index";//2.3.1网信助手首页
    String URL_EVENT_DOFOllOW = "aide/doFollow";//2.3.2网信助手跟进
    String URL_EVENT_DELETE = "aide/delete";//2.3.3网信助手--删除消息
    /**
     * 跟进模块
     */
    String URL_EVENT_FOLLOWUP = "followup/event";//2.4.1头条跟进列表
    String URL_EVENT_FOLLOWUP_CANCELFOLLOW = "followup/event/cancelFollow";//2.4.2头条跟进--取消跟进
    String URL_EVENT_FOLLOWUP_SETTOP = "followup/event/setTop";//2.4.3头条跟进--置顶
    String URL_EVENT_FOLLOWUP_CANCELTOP = "followup/event/cancelTop";//2.4.4头条跟进--取消置顶
    String URL_EVENT_FOLLOWUP_DONE = "followup/event/done";//2.4.5头条跟进--处理完毕
    String URL_EVENT_FOLLOWUP_DETAIL = "followup/event/detail";//2.4.6头条跟进--跟进消息详情
    String URL_EVENT_FOLLOWUP_DETAIL_COMMENT = "followup/event/detail/comment";//2.4.7头条跟进--跟进消息详情--获取评论
    String URL_EVENT_FOLLOWUP_AIDE = "followup/aide";//2.4.8助手跟进列表
    String URL_EVENT_AIDE_CANCELFOLLOW = "followup/aide/cancelFollow";//2.4.9助手跟进--取消跟进
    String URL_EVENT_AIDE_SETTOP = "followup/aide/setTop";//2.4.10助手跟进--置顶
    String URL_EVENT_AIDE_CANCELTOP = "followup/aide/cancelTop";//2.4.11助手跟进--取消置顶
    String URL_EVENT_AIDE_DONE = "followup/aide/done";//2.4.12助手跟进--处理完毕
    String URL_EVENT_AIDE_DETAIL = "followup/aide/detail";//2.4.13助手跟进--助手跟进详情
    String URL_EVENT_AIDE_SUBMIT = "followup/aide/submitMessage";//2.4.14 助手跟进--提交留言
    String URL_EVENT_AIDE_MESSAGELIST = "followup/aide/messageList";//2.4.15助手跟进--留言列表


}
