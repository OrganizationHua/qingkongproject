package com.iwangcn.qingkong.dao.model;

import android.provider.BaseColumns;

import java.io.Serializable;

public class SearchModel implements BaseColumns, Serializable {
    public static final String TABLE_NAME = "search_table";

    private String content;
    private int searchID;
    public static final int ID = 0;
    public static final int CONTENT = 1;

    public static final String COLUMN_NAME[] = {_ID, "content"};
    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_NAME[ID] + " INTEGER PRIMARY KEY," + COLUMN_NAME[CONTENT] + " TEXT" + ");";


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSearchID() {
        return searchID;
    }

    public void setSearchID(int searchID) {
        this.searchID = searchID;
    }
}
