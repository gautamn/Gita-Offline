package com.gita.holybooks.bhagwatgita.util;

/**
 * Created by Nitin Gautam on 4/14/2018.
 */

public class GitaUtil {

    public static String cleanShlokaText(String currentShlokaId){

        String strToRemove = currentShlokaId.replace("_",".");
        String shlokaText = DataUtil.shlokaTextMap.get(currentShlokaId);
        shlokaText = (shlokaText==null)?"Hello World":shlokaText;
        shlokaText = shlokaText.replace(".."+strToRemove+"..", "");

        return shlokaText.trim();
    }
}
