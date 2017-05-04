package com.iwangcn.qingkong.ui.model;

import java.io.Serializable;

/**
 * 收藏状态类，因为首页和收藏页 事件不是同一个对象（不是我定的），事件详情页又需要这个东西
 * Created by czh on 2017/5/4.
 */

public class FavoriteStateModel implements Serializable{
    private long eventId;
    private int favoriteFlag;//0否1是

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public int getFavoriteFlag() {
        return favoriteFlag;
    }

    public void setFavoriteFlag(int favoriteFlag) {
        this.favoriteFlag = favoriteFlag;
    }
}
