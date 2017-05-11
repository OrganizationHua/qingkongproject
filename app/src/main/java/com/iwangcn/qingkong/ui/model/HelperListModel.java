package com.iwangcn.qingkong.ui.model;

import com.iwangcn.qingkong.net.BaseBean;

import java.io.Serializable;

/**
 * Created by RF on 2017/4/24.
 */

public class HelperListModel extends BaseBean {

    /**
     * helperInfo : {"autoId":22,"clientId":1,"userId":2,"userName":null,"title":"新闻标题wqewew","content":null,"url":"http://www.123.com","urlMd5":null,"pubtime":"2017-01-10","source":"新浪","dataType":1,"labels":"[\"\"]","pics":"[]","updateTime":"2017-05-10","labelsList":null,"helperFeedbackDetail":null}
     * helperProcess : {"autoId":23,"helperId":22,"userId":1,"type":0,"labels":null,"top":0,"display":1,"status":1,"updateTime":"2017-05-10","helperFeedbackDetail":null}
     */

    private HelperInfo helperInfo;
    private HelperProcess helperProcess;

    public HelperInfo getHelperInfo() {
        return helperInfo;
    }

    public void setHelperInfo(HelperInfo helperInfo) {
        this.helperInfo = helperInfo;
    }

    public HelperProcess getHelperProcess() {
        return helperProcess;
    }

    public void setHelperProcess(HelperProcess helperProcess) {
        this.helperProcess = helperProcess;
    }

    public static class HelperInfo implements Serializable {
        /**
         * autoId : 22
         * clientId : 1
         * userId : 2
         * userName : null
         * title : 新闻标题wqewew
         * content : null
         * url : http://www.123.com
         * urlMd5 : null
         * pubtime : 2017-01-10
         * source : 新浪
         * dataType : 1
         * labels : [""]
         * pics : []
         * updateTime : 2017-05-10
         * labelsList : null
         * helperFeedbackDetail : null
         */

        private int autoId;
        private int clientId;
        private int userId;
        private String userName;
        private String title;
        private String content;
        private String url;
        private String urlMd5;
        private String pubtime;
        private String source;
        private int dataType;
        private String labels;
        private String pics;
        private String updateTime;
        private String labelsList;
        private String helperFeedbackDetail;

        public int getAutoId() {
            return autoId;
        }

        public void setAutoId(int autoId) {
            this.autoId = autoId;
        }

        public int getClientId() {
            return clientId;
        }

        public void setClientId(int clientId) {
            this.clientId = clientId;
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

        public String getPubtime() {
            return pubtime;
        }

        public void setPubtime(String pubtime) {
            this.pubtime = pubtime;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public String getLabels() {
            return labels;
        }

        public void setLabels(String labels) {
            this.labels = labels;
        }

        public String getPics() {
            return pics;
        }

        public void setPics(String pics) {
            this.pics = pics;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getLabelsList() {
            return labelsList;
        }

        public void setLabelsList(String labelsList) {
            this.labelsList = labelsList;
        }

        public String getHelperFeedbackDetail() {
            return helperFeedbackDetail;
        }

        public void setHelperFeedbackDetail(String helperFeedbackDetail) {
            this.helperFeedbackDetail = helperFeedbackDetail;
        }
    }

    public static class HelperProcess {
        /**
         * autoId : 23
         * helperId : 22
         * userId : 1
         * type : 0
         * labels : null
         * top : 0
         * display : 1
         * status : 1
         * updateTime : 2017-05-10
         * helperFeedbackDetail : null
         */

        private int autoId;
        private int helperId;
        private int userId;
        private int type;
        private String labels;
        private int top;
        private int display;
        private int status;
        private String updateTime;
        private String helperFeedbackDetail;

        public int getAutoId() {
            return autoId;
        }

        public void setAutoId(int autoId) {
            this.autoId = autoId;
        }

        public int getHelperId() {
            return helperId;
        }

        public void setHelperId(int helperId) {
            this.helperId = helperId;
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

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getHelperFeedbackDetail() {
            return helperFeedbackDetail;
        }

        public void setHelperFeedbackDetail(String helperFeedbackDetail) {
            this.helperFeedbackDetail = helperFeedbackDetail;
        }
    }
}
