package com.gita.holybooks.bhagwatgita.dto;

/**
 * Created by Nitin Gautam on 2/25/2018.
 */

public class Chapter {

    private String chapterNumber;
    private String title;
    private String description;

    public Chapter(String chapterNumber, String title, String description) {
        this.chapterNumber = chapterNumber;
        this.title = title;
        this.description = description;
    }

    public String getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
