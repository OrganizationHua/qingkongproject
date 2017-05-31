package com.iwangcn.qingkong.ui.model;

import com.iwangcn.qingkong.net.BaseBean;

/**
 * 作者：fjg on 2017/5/31 21:51
 */

public class HeadLineModel extends BaseBean{



    private int autoId;
    private int eventDataId;
    private EventData eventData;
    private int userId;
    private int type;
    private String labels;
    private String businessLabels;
    private String selfLabels;
    private int top;
    private int display;
    private int status;
    private int process;
    private long updateTime;

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public int getEventDataId() {
        return eventDataId;
    }

    public void setEventDataId(int eventDataId) {
        this.eventDataId = eventDataId;
    }

    public EventData getEventData() {
        return eventData;
    }

    public void setEventData(EventData eventData) {
        this.eventData = eventData;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getBusinessLabels() {
        return businessLabels;
    }

    public void setBusinessLabels(String businessLabels) {
        this.businessLabels = businessLabels;
    }

    public String getSelfLabels() {
        return selfLabels;
    }

    public void setSelfLabels(String selfLabels) {
        this.selfLabels = selfLabels;
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

    public static class EventData {


        private int autoId;
        private int eventId;
        private int dataType;
        private int dataId;
        private Data data;
        private int validity;
        private String feature;
        private int status;
        private int userId;
        private int process;
        private long insertTime;
        private long updateTime;

        public int getAutoId() {
            return autoId;
        }

        public void setAutoId(int autoId) {
            this.autoId = autoId;
        }

        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public int getDataId() {
            return dataId;
        }

        public void setDataId(int dataId) {
            this.dataId = dataId;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
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

        public static class Data {

            private int autoId;
            private String title;
            private String content;
            private long pubtime;
            private String url;
            private String urlMd5;
            private String author;
            private String source;
            private String keywords;
            private int commentNum;
            private long updateTime;
            private String strPubtime;
            private String strUpdateTime;
            private int date;

            public int getAutoId() {
                return autoId;
            }

            public void setAutoId(int autoId) {
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

            public long getPubtime() {
                return pubtime;
            }

            public void setPubtime(long pubtime) {
                this.pubtime = pubtime;
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

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
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

            public String getStrPubtime() {
                return strPubtime;
            }

            public void setStrPubtime(String strPubtime) {
                this.strPubtime = strPubtime;
            }

            public String getStrUpdateTime() {
                return strUpdateTime;
            }

            public void setStrUpdateTime(String strUpdateTime) {
                this.strUpdateTime = strUpdateTime;
            }

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }
        }
    }
}
