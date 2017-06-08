package com.iwangcn.qingkong.ui.model;

/**
 * Created by czh on 2017/6/8.
 */

public class SearchResultVo {
    long dataType;//数据类型
    String title;//标题
    String content;//内容
    String author;//作者
    String url;
    String webSite;//来源网站
    String keywords;//关键词
    long pubtime;// 发布时间
    boolean isFollowUp;//是否跟进：1是，0否
    int eventId;//  关联的事件id
    String eventName;//事件名称

    public long getDataType() {
        return dataType;
    }

    public void setDataType(long dataType) {
        this.dataType = dataType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public long getPubtime() {
        return pubtime;
    }

    public void setPubtime(long pubtime) {
        this.pubtime = pubtime;
    }

    public boolean isFollowUp() {
        return isFollowUp;
    }

    public void setFollowUp(boolean followUp) {
        isFollowUp = followUp;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
