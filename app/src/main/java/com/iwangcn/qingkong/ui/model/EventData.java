package com.iwangcn.qingkong.ui.model;

import com.iwangcn.qingkong.net.BaseBean;

/**
 * 事件搜索模型
 * Created by czh on 2017/5/4.
 */

public class EventData extends BaseBean {
    private long autoId;
    private long eventId;
    private int dataType;
    private long dataId;
    private NewsInfo data;
    private int validity;
    private String feature;
    private int status;
    private int userId;
    private int process;
    private long insertTime;
    private long updateTime;

    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    public NewsInfo getData() {
        return data;
    }

    public void setData(NewsInfo data) {
        this.data = data;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public long getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(long insertTime) {
        this.insertTime = insertTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
