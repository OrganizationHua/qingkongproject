package com.iwangcn.qingkong.ui.model;

/**
 * Created by zzjs on 2017/6/7.
 */

public class QkTagModel {
    private int colorType = 0;
    private String tagText;
    private int type;
    private long processId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getProcessId() {
        return processId;
    }

    public void setProcessId(long processId) {
        this.processId = processId;
    }

    public QkTagModel(int colorType, String tagText, int type, long processId) {
        this.colorType = colorType;
        this.tagText = tagText;
        this.type = type;
        this.processId = processId;
    }

    public QkTagModel(int colorType, String tagText) {
        this.colorType = colorType;
        this.tagText = tagText;
    }

    public int getColorType() {
        return colorType;
    }

    public void setColorType(int colorType) {
        this.colorType = colorType;
    }

    public String getTagText() {
        return tagText;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }
}
