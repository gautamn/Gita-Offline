package com.gita.holybooks.bhagwatgita.service;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;

import com.gita.holybooks.bhagwatgita.dao.DataBaseHelper;
import com.gita.holybooks.bhagwatgita.dto.Bookmark;
import com.gita.holybooks.bhagwatgita.util.DataUtil;
import com.gita.holybooks.bhagwatgita.util.GitaUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitin Gautam on 4/14/2018.
 */

public class DatabaseService {

    DataBaseHelper dataBaseHelper;
    List<Bookmark> bookmarks;

    public DatabaseService(FragmentActivity fragmentActivity){
        dataBaseHelper = new DataBaseHelper(fragmentActivity);
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
}
