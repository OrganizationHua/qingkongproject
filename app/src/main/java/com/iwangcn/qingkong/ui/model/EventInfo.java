package com.iwangcn.qingkong.ui.model;

import android.provider.BaseColumns;

import com.iwangcn.qingkong.net.BaseBean;

import java.io.Serializable;


/**
 * 事件信息
 * Created by czh on 2017/4/12.
 */

public class EventInfo extends BaseBean implements BaseColumns, Serializable {

    public static final String TABLE_NAME = "EventInfo";
    private boolean isSelect;//是否被选中
    private long autoId;
    private String name;//名称
    private String disc;//    简介
    private long stime;//开始关注时间
    private long etime;//截止关注时间
    private String picUrl;//事件图片
    private String location;//区域
    private String entity;// 涉事人物、主题
    private String keyword;//关键词
    private String classification;//分类
    private int status;//状态
    private String createUid;//创建事件的用户id
    private long insertTime;
    private long updateTime;

    public static final int ID = 0;
    public static final int AUTOID = 1;
    public static final int NAME = 2;
    public static final int DISC = 3;
    public static final int STIME = 4;
    public static final int ETIME = 5;
    public static final int PICURL = 6;
    public static final int LOCATION = 7;
    public static final int ENTITY = 8;
    public static final int KEYWORD = 9;
    public static final int CLASSIFICATION = 10;
    public static final int STATUS = 11;
    public static final int CREATEUID = 12;
    public static final int INSERTTIME = 13;
    public static final int UPDATETIME = 14;

    public static final String COLUMN_NAME[] = {_ID, "autoId", "name", "disc",
            "stime", "etime", "picUrl", "location", "entity", "keyword", "classification", "status", "createUid", "insertTime", "updateTime"};
    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_NAME[ID] + " INTEGER PRIMARY KEY," + COLUMN_NAME[AUTOID]
            + " TEXT," + COLUMN_NAME[NAME] + " TEXT," + COLUMN_NAME[DISC]
            + " TEXT," + COLUMN_NAME[STIME] + " TEXT," + COLUMN_NAME[ETIME]
            + " TEXT," + COLUMN_NAME[PICURL] + " TEXT," + COLUMN_NAME[LOCATION] +
            " TEXT," + COLUMN_NAME[ENTITY] + " TEXT," + COLUMN_NAME[KEYWORD] +
            " TEXT," + COLUMN_NAME[CLASSIFICATION] + " TEXT," + COLUMN_NAME[STATUS] +
            " INTEGER," + COLUMN_NAME[CREATEUID] + " TEXT," + COLUMN_NAME[INSERTTIME] +
            " TEXT," + COLUMN_NAME[UPDATETIME] + " TEXT" + ");";

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public long getStime() {
        return stime;
    }

    public void setStime(long stime) {
        this.stime = stime;
    }

    public long getEtime() {
        return etime;
    }

    public void setEtime(long etime) {
        this.etime = etime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
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
