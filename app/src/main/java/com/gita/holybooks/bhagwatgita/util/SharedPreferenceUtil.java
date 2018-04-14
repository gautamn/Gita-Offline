package com.gita.holybooks.bhagwatgita.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Nitin Gautam on 4/11/2018.
 */

public class SharedPreferenceUtil {

    private static final String BOOKMARK_SHLOKA_KEY = "BOOK_MARKED_SHLOKA";
    private static final String GITA_PREFERENCE = "USER_PROFILE";

    public static void bookmarkShloka(FragmentActivity fragmentActivity, String shlokaId) {

        Set bookMarkedShlokas = new TreeSet();
        Gson gson = new Gson();
        SharedPreferences prefs = fragmentActivity.getSharedPreferences(GITA_PREFERENCE, Context.MODE_PRIVATE);
        String restoredText = prefs.getString(BOOKMARK_SHLOKA_KEY, null);
        if (restoredText != null) {
            bookMarkedShlokas = gson.fromJson(restoredText, TreeSet.class);
            if (bookMarkedShlokas != null && !bookMarkedShlokas.contains(shlokaId))
                bookMarkedShlokas.add(shlokaId);
        }

        String json = gson.toJson(bookMarkedShlokas);
        SharedPreferences.Editor editor = fragmentActivity.getSharedPreferences(GITA_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(BOOKMARK_SHLOKA_KEY, json);
        editor.commit();

        Toast.makeText(fragmentActivity, "Bookmarks:" + json, Toast.LENGTH_SHORT).show();
    }

    public static List<String> getBookmarkedShlokas(Activity activity) {

        Set<String> bookMarkedShlokas = new TreeSet();
        Gson gson = new Gson();
        SharedPreferences prefs = activity.getSharedPreferences(GITA_PREFERENCE, Context.MODE_PRIVATE);
        String restoredText = prefs.getString(BOOKMARK_SHLOKA_KEY, null);
        if (restoredText != null) {
            bookMarkedShlokas = gson.fromJson(restoredText, TreeSet.class);
        }

        List<String> tmpList = new ArrayList<>(bookMarkedShlokas.size());
        for (String bookmark : bookMarkedShlokas) {

            String[] arr = bookmark.split("_");
            StringBuilder sb = new StringBuilder();
            sb.append("Chapter ").append(arr[0]).append(" Shloka ").append(arr[1]);
            tmpList.add(sb.toString());

        }

        return tmpList;
    }
}
