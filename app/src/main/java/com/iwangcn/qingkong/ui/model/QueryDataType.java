package com.iwangcn.qingkong.ui.model;

/**
 * Created by zzjs on 2017/6/7.
 */

public class QueryDataType {

    /**
     * autoId : 1
     * name : 新闻
     * userId : 0
     * userName : null
     * typeCode : 1
     * status : 1
     * updateTime : 1495814034000
     */

    private int autoId;
    private String name;
    private int userId;
    private String userName;
    private int typeCode;
    private int status;
    private long updateTime;

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
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
