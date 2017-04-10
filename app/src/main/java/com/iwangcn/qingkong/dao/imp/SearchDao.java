package com.iwangcn.qingkong.dao.imp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.iwangcn.qingkong.dao.manager.AbstractNcDao;
import com.iwangcn.qingkong.dao.model.SearchModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史搜索
 */
public class SearchDao extends AbstractNcDao {

    public SearchDao(Context context) {
        super(context);
    }

    @Override
    public String getTableName() {
        return SearchModel.TABLE_NAME;
    }

    public void addList(List<SearchModel> list) {
        if (list == null || list.size() <= 0)
            return;
        try {
            openWritableDB();
            for (SearchModel model : list) {
                ContentValues contentValues = buildContentValues(model);
                insert(SearchModel.TABLE_NAME, null, contentValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateAndAddModel(SearchModel model) {
        if (model == null)
            return;
        try {
            openWritableDB();
            ContentValues contentValues = buildContentValues(model);
            if (!isInTable(model.getContent())) {//如果没有存过就保存
                insert(SearchModel.TABLE_NAME, null, contentValues);
            }
           // insert(SearchModel.TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有
     */
    public List<SearchModel> getList() {
        try {
            openReadableDB();
            List<SearchModel> list = new ArrayList<SearchModel>();
            String sql = "select * from " + SearchModel.TABLE_NAME;
            Cursor mCursor = query(sql);
            if (mCursor != null) {
                while (mCursor.moveToNext()) {
                    SearchModel model = createSearchModel(mCursor);
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

    public SearchModel getSingleModel(String searchId) {
        try {
            openReadableDB();
            SearchModel SearchModel = new SearchModel();
            String sql = "select * from " + SearchModel.TABLE_NAME + " where "
                    + SearchModel.COLUMN_NAME[SearchModel.ID] + "=" + searchId;
            Cursor mCursor = query(sql);
            if (mCursor != null) {
                while (mCursor.moveToNext()) {
                    SearchModel = createSearchModel(mCursor);
                }
            }
            closeDb(mCursor);
            return SearchModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private SearchModel createSearchModel(Cursor mCursor) {
        SearchModel mSearchModel = new SearchModel();
        try {
            mSearchModel.setContent(mCursor.getString(mCursor
                    .getColumnIndex(SearchModel.COLUMN_NAME[SearchModel.CONTENT])));
            mSearchModel.setSearchID(mCursor.getInt(mCursor
                    .getColumnIndex(SearchModel.COLUMN_NAME[SearchModel.ID])));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mSearchModel;
    }

    private ContentValues buildContentValues(SearchModel model) {
        ContentValues mValues = new ContentValues();
        mValues.put(SearchModel.COLUMN_NAME[SearchModel.CONTENT], model.getContent());
        return mValues;
    }

    public boolean deleteTable() {
        boolean result = false;
        try {
            openWritableDB();
            result = delete(SearchModel.TABLE_NAME, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean isInTable(String content) {
        Cursor mCursor = null;
        boolean result = false;
        try {
            openReadableDB();
            String querySql = buildQuerySql(content);
            mCursor = query(querySql);
            if (checkCursorAvaible(mCursor)) {
                if (mCursor.moveToFirst()) {
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
        }
        return result;
    }

    private String buildQuerySql(String type) {
        StringBuilder queryStrBuilder = new StringBuilder("select * from ");
        queryStrBuilder.append(SearchModel.TABLE_NAME).append(" where ")
                .append(SearchModel.COLUMN_NAME[SearchModel.CONTENT]).append("=")
                .append("'"+type+"'");
        return queryStrBuilder.toString();
    }
}
