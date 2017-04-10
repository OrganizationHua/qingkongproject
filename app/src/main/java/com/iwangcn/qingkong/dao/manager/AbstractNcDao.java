package com.iwangcn.qingkong.dao.manager;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class AbstractNcDao extends AbstractDao {

	public AbstractNcDao(Context context) {
		super(context);
	}

	SQLiteOpenHelper getDbHelper(Context context) {
		return new NcDbHelper(context);
	}

	public abstract String getTableName();
}
