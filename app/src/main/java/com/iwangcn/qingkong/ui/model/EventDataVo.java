package com.iwangcn.qingkong.ui.model;

/**
 * Created by czh on 2017/6/10.
 * 事件相关信息Model
 */

public class EventDataVo {
    long autoId;//  主键id
    long eventId;// 事件id
    int dataType;//原始数据类型
    long dataId;//原始数据id
    NewsInfo data;
    int validity;//暂不用
    String feature;//运营段处理后增加的关键词
    int status;//状态
    int userId;//处理该数据的用户id
    int process;//是否已处理
    long insertTime;
    long updateTime;
    String[] businessLabels;//业务标签
    String[] selfLabels;//自定义标签
    boolean followup;//是否跟进

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

    public String[] getBusinessLabels() {
        return businessLabels;
    }

    public void setBusinessLabels(String[] businessLabels) {
        this.businessLabels = businessLabels;
    }

    public String[] getSelfLabels() {
        return selfLabels;
    }

    public void setSelfLabels(String[] selfLabels) {
        this.selfLabels = selfLabels;
    }

    public boolean isFollowup() {
        return followup;
    }

    public void setFollowup(boolean followup) {
        this.followup = followup;
    }


}
