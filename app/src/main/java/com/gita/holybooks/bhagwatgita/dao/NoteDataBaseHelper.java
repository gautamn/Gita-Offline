package com.gita.holybooks.bhagwatgita.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by Nitin Gautam on 4/12/2018.
 */

public class NoteDataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "gita_2.db";
    public static final String NOTE_TABLE = "note";

    public static final String NOTE_COL_1 = "id";
    public static final String NOTE_COL_2 = "shloka_id";
    public static final String NOTE_COL_3 = "note";
    public static final String NOTE_COL_4 = "date";

    SQLiteDatabase sqLiteDatabase;


    public NoteDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public NoteDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + NOTE_TABLE +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, shloka_id TEXT, note TEXT, DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertNote(String shlokaId, String note) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_COL_2, shlokaId);
        contentValues.put(NOTE_COL_3, note);
        contentValues.put(NOTE_COL_4, new Date().toString());
        long result = sqLiteDatabase.insert(NOTE_TABLE, null, contentValues);

        return result < 0 ? false : true;
    }

    public Cursor getNotes() {

        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + NOTE_TABLE, null);
    }

    public Cursor getNote(String shlokaId) {

        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM note WHERE shloka_id="+shlokaId, new String[] {shlokaId});
    }

    public int updateNote(String shlokaId, String note){

        ContentValues cv = new ContentValues();
        cv.put(NOTE_COL_3,note);
        cv.put(NOTE_COL_4, new Date().toString());
        return sqLiteDatabase.update(NOTE_TABLE, cv, "shloka_id = ?", new String[]{shlokaId});
    }
}
