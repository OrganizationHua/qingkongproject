package com.iwangcn.qingkong.ui.model;

import com.iwangcn.qingkong.net.BaseBean;

/**
 * 事件信息
 * Created by czh on 2017/4/12.
 */

public class EventInfoVo extends BaseBean {
    private EventInfo eventInfo;//事件对象
    private int favoriteFlag;//是否收藏0否1是
    private int favoriteId;//    收藏id
    private int infoCount;//数据总量
    private long lastestInfoTime;
    public EventInfo getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(EventInfo eventInfo) {
        this.eventInfo = eventInfo;
    }

    public int getFavoriteFlag() {
        return favoriteFlag;
    }

    public void setFavoriteFlag(int favoriteFlag) {
        this.favoriteFlag = favoriteFlag;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getInfoCount() {
        return infoCount;
    }

    public void setInfoCount(int infoCount) {
        this.infoCount = infoCount;
    }

    public long getLastestInfoTime() {
        return lastestInfoTime;
    }

    public void setLastestInfoTime(long lastestInfoTime) {
        this.lastestInfoTime = lastestInfoTime;
    }
}
