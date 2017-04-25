package com.iwangcn.qingkong.ui.model;

import com.iwangcn.qingkong.net.BaseBean;

/**
 * 收藏model
 * Created by czh on 2017/4/19.
 */

public class FavoriteInfo extends BaseBean{
    private long autoId;
    private long userId;//用户id
    private long eventId;//事件id
    private EventInfo event;  //事件对象
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

    public com.iwangcn.qingkong.ui.model.EventInfo getEvent() {
        return event;
    }

    public void setEvent(com.iwangcn.qingkong.ui.model.EventInfo event) {
        this.event = event;
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
