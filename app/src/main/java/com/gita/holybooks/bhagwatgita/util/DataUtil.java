package com.gita.holybooks.bhagwatgita.util;

import android.widget.ListView;

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

    /*Information of Gita Chapters*/
    public static List<Chapter> chapters = new ArrayList<>(18);

    /*Current Shloka Id*/
    public static String shlokaId;
}
