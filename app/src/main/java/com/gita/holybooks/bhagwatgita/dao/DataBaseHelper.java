package com.gita.holybooks.bhagwatgita.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by Nitin Gautam on 4/12/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "gita.db";
    public static final String BOOKMARKS_TABLE = "bookmark";

    public static final String BOOKMARK_COL_1 = "id";
    public static final String BOOKMARK_COL_2 = "shloka_id";
    public static final String BOOKMARK_COL_3 = "date";

    SQLiteDatabase sqLiteDatabase;


    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + BOOKMARKS_TABLE +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, shloka_id TEXT, DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BOOKMARKS_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertBookmark(String shlokaId) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOKMARK_COL_2, shlokaId);
        contentValues.put(BOOKMARK_COL_3, new Date().toString());
        long result = sqLiteDatabase.insert(BOOKMARKS_TABLE, null, contentValues);

        return result < 0 ? false : true;
    }

    public Cursor getBookmarks() {

        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + BOOKMARKS_TABLE, null);
    }

    public Cursor getBookmark(String shlokaId) {

        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + BOOKMARKS_TABLE
                + " WHERE shloka_id='"+shlokaId+"'", null);
    }

    public Integer deleteBookmark(String shlokaId) {

        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(BOOKMARKS_TABLE, "shloka_id=?", new String[]{shlokaId});
    }
}
