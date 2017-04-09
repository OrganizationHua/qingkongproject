package com.iwangcn.qingkong.dao.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NcDbHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "ybz.db3";
	public static final int DATABASE_VERSITON = 1;

	public NcDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSITON);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			for (String createSql : DaoFactory.getTableCreateSql()) {
				db.execSQL(createSql);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
