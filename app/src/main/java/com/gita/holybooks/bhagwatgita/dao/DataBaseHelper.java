package com.gita.holybooks.bhagwatgita.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gita.holybooks.bhagwatgita.dto.ShlokaContract;

/**
 * Created by Nitin Gautam on 2/17/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "gita.db";
    public static final String TABLE_NAME = "shloka";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ShlokaContract.ShlokaEntry.TABLE_NAME + " (" +
                    ShlokaContract.ShlokaEntry._ID + " INTEGER PRIMARY KEY," +
                    ShlokaContract.ShlokaEntry.COLUMN_NAME_SHLOKA_ID + " SHLOKA_ID," +
                    ShlokaContract.ShlokaEntry.COLUMN_NAME_TEXT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ShlokaContract.ShlokaEntry.TABLE_NAME;

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
