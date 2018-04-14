package com.gita.holybooks.bhagwatgita.dto;

/**
 * Created by Nitin Gautam on 4/14/2018.
 */

public class Bookmark {

    private String bookmarkId;
    private String title;
    private String shlokaId;
    private String chapterName;
    private String shlokaText;

    public String getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(String bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getShlokaId() {
        return shlokaId;
    }

    public void setShlokaId(String shlokaId) {
        this.shlokaId = shlokaId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getShlokaText() {
        return shlokaText;
    }

    public void setShlokaText(String shlokaText) {
        this.shlokaText = shlokaText;
    }
}
