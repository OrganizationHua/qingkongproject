package com.iwangcn.qingkong.ui.model;

import com.iwangcn.qingkong.net.BaseBean;

/**
 * Created by czh on 2017/6/10.
 * 事件相关信息Model
 */

public class EventDataVo extends BaseBean {
    private long autoId;//  主键id
    private long eventDataId;// 事件id
    private EventData eventData;
    private int type;
    private String[] businessLabels;//业务标签
    private String[] selfLabels;//自定义标签
    private int process;//是否已处理
    private long updateTime;
    private int top;
    private int display;
    private int status;//状态
    private boolean isSelect;//是否被选中

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

    public EventData getEventData() {
        return eventData;
    }

    public void setEventData(EventData eventData) {
        this.eventData = eventData;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
