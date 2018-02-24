package com.gita.holybooks.bhagwatgita.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nitin Gautam on 2/17/2018.
 */

public class FileUtil {

    public static String readRawTextFile(Context ctx, int resId) {
        InputStream inputStream = ctx.getResources().openRawResource(resId);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputReader);
        String line;
        StringBuilder text = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }

    public static void loadShlokaInMemory(Context ctx, int resId) {

        InputStream inputStream = ctx.getResources().openRawResource(resId);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputReader);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                StringBuilder text = new StringBuilder();
                String shlokaId = null;
                if (line.contains(",")) shlokaId = line.substring(0, line.indexOf(","));
                text.append(line.substring(line.indexOf(",") + 1).replace('\\', '\n'));

                do {
                    line = bufferedReader.readLine();
                    text.append(line.replace('\\', '\n'));
                } while (!line.contains(".."));
                Log.d("FileUtil", "loadShlokaInMemory: shlokaId=" + shlokaId + " text=" + text);
                DataUtil.shlokaTextMap.put(shlokaId, text.toString());
            }
        } catch (IOException e) {
            Log.e("FileUtil", "loadShlokaInMemory: Unable to read from resource file=" + resId, e);
        }

    }

    public static void loadChaptersInMemory(Context ctx, int resId) {

        InputStream inputStream = ctx.getResources().openRawResource(resId);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputReader);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                StringBuilder text = new StringBuilder();
                String chapterId = null;
                if (line.contains(",")) chapterId = line.substring(0, line.indexOf(","));
                text.append(line.substring(line.indexOf(",") + 1));
                Log.d("FileUtil", "loadChaptersInMemory: chapterId=" + chapterId + " text=" + text);
                DataUtil.chapterTextMap.put(chapterId, text.toString());
            }
        } catch (IOException e) {
            Log.e("FileUtil", "loadChaptersInMemory: Unable to read from resource file=" + resId, e);
        }

    }
}
