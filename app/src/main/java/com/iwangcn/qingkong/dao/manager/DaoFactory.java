package com.iwangcn.qingkong.dao.manager;

import android.content.Context;

import com.iwangcn.qingkong.app.MobileApplication;
import com.iwangcn.qingkong.dao.imp.EventInfoDao;
import com.iwangcn.qingkong.dao.imp.SearchDao;
import com.iwangcn.qingkong.dao.model.SearchModel;
import com.iwangcn.qingkong.ui.model.EventInfo;

public class DaoFactory {
    private static final String TAG = DaoFactory.class.getSimpleName();

    public enum DaoType {
        SEAR_DAO,EVENT_INFO
    }
    private static SearchDao mSearchDao;
    private static EventInfoDao mEventInfoDao;
    private static Context mContext= MobileApplication.getInstance();
    public static AbstractDao createInstance(DaoType type) {
        AbstractDao dao = null;
        switch (type) {
            case SEAR_DAO:
                if (mSearchDao == null) {
                    synchronized (DaoFactory.class) {
                        if (mSearchDao == null) {
                            mSearchDao = new SearchDao(mContext);
                        }
                    }
                }
                dao = mSearchDao;
                break;
            case EVENT_INFO:
                if (mEventInfoDao == null) {
                    synchronized (DaoFactory.class) {
                        if (mEventInfoDao == null) {
                            mEventInfoDao = new EventInfoDao(mContext);
                        }
                    }
                }
                dao = mEventInfoDao;
                break;
            default:
                break;
        }
        return dao;
    }

    public static String[] getTables() {
        return new String[]{SearchModel.TABLE_NAME
                };
    }

    public static String[] getTableCreateSql() {
        return new String[]{SearchModel.CREATE_SQL, EventInfo.CREATE_SQL};
    }

    public static AbstractDao getDao(DaoType type) {
        return createInstance(type);
    }
    // public static void clearDataAll(Context context) {
    // SpUtils.clear(HFApplication.getContext());
    // GlobalHelperDao helperDao = new GlobalHelperDao(
    // HFApplication.getContext());
    // helperDao.clearAllTable();
    // }
}
