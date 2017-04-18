package com.iwangcn.qingkong.business;

public class Event {
    private int id;
    private Object object;
    private boolean isSuccess;
    private boolean isMore;//是否是加载更多

    public Event() {
    }

    public Event(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int status) {
        this.isSuccess = status == 1 ? true : false;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setIsMore(boolean isMore) {
        this.isMore = isMore;
    }
}
