package com.iwangcn.qingkong.ui.model;

import com.iwangcn.qingkong.net.BaseBean;

/**
 * Created by RF on 2017/4/24.
 */

public class EventDataProcess extends BaseBean {
    private long autoId;
    private long eventDataId;
    private int type;
    private int userId;
    private long pubtime;
    private String labels;
    private int top;
    private int display;
    private int status;
    private long updateTime;

    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }

    public long getEventDataId() {
        return eventDataId;
    }

    public void setEventDataId(long eventDataId) {
        this.eventDataId = eventDataId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getPubtime() {
        return pubtime;
    }

    public void setPubtime(long pubtime) {
        this.pubtime = pubtime;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
