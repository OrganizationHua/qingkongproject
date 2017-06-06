package com.iwangcn.qingkong.ui.model;

import com.iwangcn.qingkong.net.BaseBean;

/**
 * Created by RF on 2017/4/24.
 */

public class HelperFeedbackDetail extends BaseBean {
    private long autoId;
    private long feedbackId;
    private String userName;
    private int userId;
    private String message;
    private long updateTime;

    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }

    public long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
