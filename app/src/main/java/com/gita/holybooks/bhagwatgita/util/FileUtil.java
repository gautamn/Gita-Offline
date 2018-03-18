package com.gita.holybooks.bhagwatgita.util;

import android.content.Context;
import android.util.Log;

import com.gita.holybooks.bhagwatgita.dto.Chapter;

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
                String str = null;
                String chapterNumber = null;
                if (line.contains(",")) chapterNumber = line.substring(0, line.indexOf(","));
                str = line.substring(line.indexOf(",") + 1);
                String[] arr = str.split("-");
                String title = arr[0];
                String desc = arr[1];
                Log.d("FileUtil", "loadChaptersInMemory: chapterId=" + chapterNumber + " title=" + title + " text=" + desc);
                DataUtil.chapters.add(new Chapter(chapterNumber, title, desc));
            }
        } catch (IOException e) {
            Log.e("FileUtil", "loadChaptersInMemory: Unable to read from resource file=" + resId, e);
        }

    }


    public static void loadEnglishTransInMemory(Context ctx, int resId) {

        InputStream inputStream = ctx.getResources().openRawResource(resId);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputReader);
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }catch (IOException e) {
            Log.e("FileUtil", "loadTransInMemory: Unable to read from resource file=" + resId, e);
            return;
        }
        String content = sb.toString();
        int chapter = 1;
        int shloka = 1;
        int totalShlokasParsed = 0;
        String shlokaContent = null;
        while(totalShlokasParsed <=700 && chapter<=18){
            if((shloka) < DataUtil.SHLOKAS_IN_CHAPTER[chapter-1] && chapter<=18){
                shlokaContent = parseShloka(chapter, shloka, content, false);
                shlokaContent = shlokaContent.replace('\\', '\b');
                DataUtil.englishTransTextMap.put(chapter+"_"+shloka, shlokaContent);
                Log.d("FileUtil", "loadEnglishTransInMemory: shlokaNumber="+shloka+" shlokaContent="+shlokaContent);
                shloka = shloka + 1;
            }else if(shloka  == DataUtil.SHLOKAS_IN_CHAPTER[chapter-1]){

                shlokaContent = parseShloka(chapter, shloka, content, true);
                if(shlokaContent!=null)
                    shlokaContent = shlokaContent.replace('\\', '\b');
                DataUtil.englishTransTextMap.put(chapter+"_"+shloka, shlokaContent);
                Log.d("FileUtil", "loadEnglishTransInMemory: shlokaNumber="+shloka+" shlokaContent="+shlokaContent);
                chapter = chapter + 1;
                shloka = 1;
            }else{

                if(chapter>=18) break;
            }

            totalShlokasParsed++;
        }
    }

    private static String parseShloka(int currentChapter, int currentShloka, String content, boolean isChapterIncremented) {

        String shlokaNumber = currentChapter+"."+currentShloka;
        String nextShlokaNumber = currentChapter+"."+(currentShloka+1);

        int fromIndex = content.indexOf(shlokaNumber);
        int endIndex = content.indexOf(nextShlokaNumber);

        if(isChapterIncremented){
            nextShlokaNumber = (currentChapter+1)+"."+(1);
            endIndex = content.indexOf(nextShlokaNumber);
        }

        String shlokaContent = null;
        if(fromIndex>=0 && endIndex>0){
            shlokaContent = content.substring(fromIndex, endIndex);
        }

        return shlokaContent;
    }

}
