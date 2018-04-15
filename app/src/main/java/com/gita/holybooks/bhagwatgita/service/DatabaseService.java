package com.gita.holybooks.bhagwatgita.service;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;

import com.gita.holybooks.bhagwatgita.dao.DataBaseHelper;
import com.gita.holybooks.bhagwatgita.dao.NoteDataBaseHelper;
import com.gita.holybooks.bhagwatgita.dto.Bookmark;
import com.gita.holybooks.bhagwatgita.dto.Note;
import com.gita.holybooks.bhagwatgita.util.DataUtil;
import com.gita.holybooks.bhagwatgita.util.GitaUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitin Gautam on 4/14/2018.
 */

public class DatabaseService {

    DataBaseHelper dataBaseHelper;
    NoteDataBaseHelper noteDataBaseHelper;
    List<Bookmark> bookmarks;
    List<Note> notes;

    public DatabaseService(FragmentActivity fragmentActivity){
        dataBaseHelper = new DataBaseHelper(fragmentActivity);
        noteDataBaseHelper = new NoteDataBaseHelper(fragmentActivity);
    }

    public List<Bookmark> getBookmarks(){
        bookmarks = new ArrayList<>();
        Cursor cursor = dataBaseHelper.getBookmarks();
        if(cursor.getCount()>0){
            StringBuffer sb = new StringBuffer();
            while ((cursor.moveToNext())){
                Bookmark bookmark = new Bookmark();
                String bookmarkId = cursor.getString(0);
                String shlokaId = cursor.getString(1);
                String[] arr = shlokaId.split("_");
                int chapterNumber = Integer.valueOf(arr[0]);
                int shlokaNumber = Integer.valueOf(arr[1]);
                String title = DataUtil.chapters.get(chapterNumber-1).getTitle() + " "+shlokaId.replace("_", ":");

                bookmark.setBookmarkId(bookmarkId);
                bookmark.setShlokaId(shlokaId);
                bookmark.setTitle(title);
                bookmark.setShlokaText(GitaUtil.cleanShlokaText(shlokaId));
                bookmarks.add(bookmark);
            }
        }
        return bookmarks;
    }

    public Integer deleteBookmark(String shlokaId){

        return dataBaseHelper.deleteBookmark(shlokaId);
    }


    public boolean insertBookmark(String shlokaId){

        Cursor cursor = dataBaseHelper.getBookmark(shlokaId);
        if(cursor.getCount()>0)
            return true;

        return dataBaseHelper.insertBookmark(shlokaId);
    }

    public boolean insertNote(String shlokaId, String note){

        Cursor cursor = noteDataBaseHelper.getNote(shlokaId);
        if(cursor.getCount()>0)
            noteDataBaseHelper.updateNote(shlokaId, note);

        return noteDataBaseHelper.insertNote(shlokaId, note);
    }

    public List<Note> getNotes(){
        notes = new ArrayList<>();
        Cursor cursor = noteDataBaseHelper.getNotes();
        if(cursor.getCount()>0){
            StringBuffer sb = new StringBuffer();
            while ((cursor.moveToNext())){
                Note note = new Note();
                String noteId = cursor.getString(0);
                String shlokaId = cursor.getString(1);
                String noteText = cursor.getString(2);
                String[] arr = shlokaId.split("_");
                int chapterNumber = Integer.valueOf(arr[0]);
                int shlokaNumber = Integer.valueOf(arr[1]);
                String title = DataUtil.chapters.get(chapterNumber-1).getTitle() + " "+shlokaId.replace("_", ":");

                note.setNoteId(noteId);
                note.setShlokaId(shlokaId);
                note.setTitle(title);
                note.setNote(noteText);
                notes.add(note);
            }
        }
        return notes;
    }
}
