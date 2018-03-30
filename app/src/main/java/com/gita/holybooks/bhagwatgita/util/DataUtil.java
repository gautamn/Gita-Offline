package com.gita.holybooks.bhagwatgita.util;

import android.content.Context;
import android.widget.ListView;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.dto.Chapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nitin Gautam on 2/17/2018.
 */

public class DataUtil {

    public static Map<String, String> shlokaTextMap = new LinkedHashMap<>();
    public static Map<String, String> englishTransTextMap = new LinkedHashMap<>();

    /*Information of Gita Chapters*/
    public static List<Chapter> chapters = new ArrayList<>(18);

    /*Current Shloka Id*/
    public static String shlokaId;

    public static final int[] SHLOKAS_IN_CHAPTER = {47, 72, 43, 42, 29, 47, 30, 28, 34, 42, 55, 20, 21, 27, 20, 24, 28, 78};

    public static final String[] FAVOURITE_SHLOKAS = {"1_1", "11_3", "2_4", "2_1", "2_2", "2_3"};

    public static void loadMasterDataInMemory(Context context){
        FileUtil.loadShlokaInMemory(context, R.raw.shloka);
        FileUtil.loadChaptersInMemory(context, R.raw.chapters);
        FileUtil.loadEnglishTransInMemory(context, R.raw.english_translation);
    }
}
