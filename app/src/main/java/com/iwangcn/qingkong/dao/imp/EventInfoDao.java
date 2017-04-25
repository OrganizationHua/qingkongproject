package com.iwangcn.qingkong.dao.imp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.iwangcn.qingkong.dao.manager.AbstractNcDao;
import com.iwangcn.qingkong.ui.model.EventInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史搜索
 */
public class EventInfoDao extends AbstractNcDao {

    public EventInfoDao(Context context) {
        super(context);
    }

    @Override
    public String getTableName() {
        return EventInfo.TABLE_NAME;
    }

    public void addList(List<EventInfo> list) {
        if (list == null || list.size() <= 0)
            return;
        try {
            openWritableDB();
            for (EventInfo model : list) {
                ContentValues contentValues = buildContentValues(model);
                insert(EventInfo.TABLE_NAME, null, contentValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有
     */
    public List<EventInfo> getList() {
        try {
            openReadableDB();
            List<EventInfo> list = new ArrayList<EventInfo>();
            String sql = "select * from " + EventInfo.TABLE_NAME;
            Cursor mCursor = query(sql);
            if (mCursor != null) {
                while (mCursor.moveToNext()) {
                    EventInfo model = createEventInfo(mCursor);
                    list.add(model);
                }
            }
            closeDb(mCursor);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private EventInfo createEventInfo(Cursor mCursor) {
        EventInfo mEventInfo = new EventInfo();
        try {
            mEventInfo.setAutoId(mCursor.getLong(mCursor
                    .getColumnIndex(EventInfo.COLUMN_NAME[EventInfo.AUTOID])));
            mEventInfo.setName(mCursor.getString(mCursor
                    .getColumnIndex(EventInfo.COLUMN_NAME[EventInfo.NAME])));
            mEventInfo.setDisc(mCursor.getString(mCursor
                    .getColumnIndex(EventInfo.COLUMN_NAME[EventInfo.DISC])));
            mEventInfo.setStime(mCursor.getLong(mCursor
                    .getColumnIndex(EventInfo.COLUMN_NAME[EventInfo.STIME])));
            mEventInfo.setEtime(mCursor.getLong(mCursor
                    .getColumnIndex(EventInfo.COLUMN_NAME[EventInfo.ETIME])));
            mEventInfo.setPicUrl(mCursor.getString(mCursor
                    .getColumnIndex(EventInfo.COLUMN_NAME[EventInfo.PICURL])));
            mEventInfo.setLocation(mCursor.getString(mCursor
                    .getColumnIndex(EventInfo.COLUMN_NAME[EventInfo.LOCATION])));
            mEventInfo.setKeywords(mCursor.getString(mCursor
                    .getColumnIndex(EventInfo.COLUMN_NAME[EventInfo.KEYWORD])));
            mEventInfo.setClassification(mCursor.getString(mCursor
                    .getColumnIndex(EventInfo.COLUMN_NAME[EventInfo.CLASSIFICATION])));
            mEventInfo.setStatus(mCursor.getInt(mCursor
                    .getColumnIndex(EventInfo.COLUMN_NAME[EventInfo.STATUS])));
            mEventInfo.setCreateUid(mCursor.getString(mCursor
                    .getColumnIndex(EventInfo.COLUMN_NAME[EventInfo.CREATEUID])));
            mEventInfo.setInsertTime(mCursor.getLong(mCursor
                    .getColumnIndex(EventInfo.COLUMN_NAME[EventInfo.INSERTTIME])));
            mEventInfo.setUpdateTime(mCursor.getLong(mCursor
                    .getColumnIndex(EventInfo.COLUMN_NAME[EventInfo.UPDATETIME])));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mEventInfo;
    }


    private ContentValues buildContentValues(EventInfo model) {
        ContentValues mValues = new ContentValues();
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.AUTOID], model.getAutoId());
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.NAME], model.getName());
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.DISC], model.getDisc());
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.STIME], model.getStime());
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.ETIME], model.getEtime());
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.PICURL], model.getPicUrl());
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.LOCATION], model.getLocation());
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.ENTITY], model.getEntity());
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.KEYWORD], model.getKeywords());
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.CLASSIFICATION], model.getClassification());
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.STATUS], model.getStatus());
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.CREATEUID], model.getCreateUid());
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.INSERTTIME], model.getInsertTime());
        mValues.put(EventInfo.COLUMN_NAME[EventInfo.UPDATETIME], model.getUpdateTime());
        return mValues;
    }

    public boolean deleteTable() {
        boolean result = false;
        try {
            openWritableDB();
            result = delete(EventInfo.TABLE_NAME, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

