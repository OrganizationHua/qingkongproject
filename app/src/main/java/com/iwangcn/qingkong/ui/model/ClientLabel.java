package com.iwangcn.qingkong.ui.model;

import com.iwangcn.qingkong.net.BaseBean;

/**
 * 标签管理
 * Created by czh on 2017/5/4.
 */

public class ClientLabel extends BaseBean {
    private long autoId;
    private long clientId;
    private long status;
    private String name;
    private long userId;
    private long updateTime;
    private boolean isSelect;


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
