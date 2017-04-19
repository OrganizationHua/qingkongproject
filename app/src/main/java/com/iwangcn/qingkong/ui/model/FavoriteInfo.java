package com.iwangcn.qingkong.ui.model;

/**
 * 收藏model
 * Created by czh on 2017/4/19.
 */

public class FavoriteInfo {
    private long autoId;
    private long userId;//用户id
    private long eventId;//事件id
    private EventInfo EventInfo;  //事件对象
    private int status;//状态
    private long updateTime;

    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public com.iwangcn.qingkong.ui.model.EventInfo getEventInfo() {
        return EventInfo;
    }

    public void setEventInfo(com.iwangcn.qingkong.ui.model.EventInfo eventInfo) {
        EventInfo = eventInfo;
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
