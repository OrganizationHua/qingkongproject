package com.iwangcn.qingkong.dao.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public abstract class AbstractDao {
	private Context context;
	protected SQLiteOpenHelper db;
	boolean writeOpen = false;
	private SQLiteDatabase mDB;
	private final String PW = "111";

	public AbstractDao(Context context) {
		this.context = context;
		this.db = getDbHelper(context);
		// SQLiteDatabase.loadLibs(context);
	}

	abstract SQLiteOpenHelper getDbHelper(Context context);

	public Cursor query(String sql) {
		return mDB.rawQuery(sql, null);
	}

	public Cursor query(String sql, String[] selectionArgs) {
		return mDB.rawQuery(sql, selectionArgs);
	}

	public Cursor query(boolean distinct, String table, String[] columns,
						String selection, String[] selectionArgs, String groupBy) {
		return mDB.query(distinct, table, columns, selection, selectionArgs,
				groupBy, null, null, null);
	}

	public long insert(String table, String nullColumnHack, ContentValues cv) {
		return mDB.insert(table, nullColumnHack, cv);
	}

	public long update(String table, ContentValues cv, String whereClause,
					   String[] whereArgs) {
		return mDB.update(table, cv, whereClause, whereArgs);
	}

	public boolean delete(String table, String whereClause, String[] whereArgs) {
		return mDB.delete(table, whereClause, whereArgs) > 0;
	}

	public void openWritableDB() {
		try {
			writeOpen = true;
			// mDB = db.getWritableDatabase(PW);
			mDB = db.getWritableDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void openReadableDB() {
		try {
			// mDB = db.getReadableDatabase(PW);
			mDB = db.getReadableDatabase();
			writeOpen = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeDb(Cursor c) {
		if (c != null) {
			c.close();
		}
	}

	public void closeDB() {
		synchronized (SQLiteOpenHelper.class) {
			if (mDB != null)
				mDB.close();
		}
	}

	abstract String getTableName();

	public synchronized void clearData() {
		String tableName = getTableName();
		if (TextUtils.isEmpty(tableName)) {
			return;
		}
		try {
			openWritableDB();
			delete(tableName, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Context getContext() {
		return context;
	}

	public boolean checkCursorAvaible(Cursor cursor) {
		if (cursor != null && cursor.getCount() > 0 && !cursor.isClosed()) {
			return true;
		}
		return false;
	}
}
