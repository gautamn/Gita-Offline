package com.gita.holybooks.bhagwatgita.dto;

/**
 * Created by Nitin Gautam on 4/14/2018.
 */

public class Note {

    private String noteId;
    private String shlokaId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private String chapterName;
    private String note;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
