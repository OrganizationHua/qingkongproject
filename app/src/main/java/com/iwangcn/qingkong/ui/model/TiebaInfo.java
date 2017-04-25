package com.iwangcn.qingkong.ui.model;

import com.iwangcn.qingkong.net.BaseBean;

/**
 * Created by RF on 2017/4/24.
 */

public class TiebaInfo extends BaseBean {
    private long autoId;
    private String title;
    private String content;
    private String url;
    private String urlMd5;
    private long pubtime;
    private String author;
    private String authorUrl;
    private String authorUrlMd5;
    private String keywords;
    private String baname;
    private int commentNum;
    private long updateTime;

    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long autoId) {
        this.autoId = autoId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public void setUrlMd5(String urlMd5) {
        this.urlMd5 = urlMd5;
    }

    public long getPubtime() {
        return pubtime;
    }

    public void setPubtime(long pubtime) {
        this.pubtime = pubtime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getAuthorUrlMd5() {
        return authorUrlMd5;
    }

    public void setAuthorUrlMd5(String authorUrlMd5) {
        this.authorUrlMd5 = authorUrlMd5;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getBaname() {
        return baname;
    }

    public void setBaname(String baname) {
        this.baname = baname;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
