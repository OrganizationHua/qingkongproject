package com.iwangcn.qingkong.ui.model;

/**
 * Created by zzjs on 2017/6/7.
 */

public class QkTagModel {
    private int colorType = 0;
    private String tagText;

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
