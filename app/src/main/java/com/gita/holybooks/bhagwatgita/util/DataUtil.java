package com.gita.holybooks.bhagwatgita.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nitin Gautam on 2/17/2018.
 */

public class DataUtil {

    public static Map<String, String> getShlokaTextMap() {
        return shlokaTextMap;
    }

    public static void setShlokaTextMap(Map<String, String> shlokaTextMap) {
        shlokaTextMap = shlokaTextMap;
    }

    public static Map<String, String> shlokaTextMap = new HashMap<>();
}
