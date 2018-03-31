package com.gita.holybooks.bhagwatgita.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.dto.Chapter;
import com.gita.holybooks.bhagwatgita.fragment.ChapterSliderFragment;
import com.gita.holybooks.bhagwatgita.util.DataUtil;
import com.gita.holybooks.bhagwatgita.util.FileUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChapterSlidePagerActivity extends FragmentActivity{

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_slide_pager);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        String currentShloka = DataUtil.shlokaId;
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),
                currentShloka.split("_")[0],
                getShlokaOfChapter(currentShloka.split("_")[0]));
        mPager.setAdapter(mPagerAdapter);

        if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        String chapterNumber = currentShloka.split("_")[0];
        String chapterName = ((Chapter)DataUtil.chapters.get(Integer.valueOf(chapterNumber)-1)).getTitle();

        if(getActionBar()!=null){
            getActionBar().setTitle(chapterName);
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setDisplayShowHomeEnabled(true);
        }

        if (DataUtil.shlokaTextMap.isEmpty())
            FileUtil.loadShlokaInMemory(getApplicationContext(), R.raw.shloka);

        if (DataUtil.englishTransTextMap.isEmpty())
            FileUtil.loadEnglishTransInMemory(getApplicationContext(), R.raw.english_translation);

        Log.d("ChapterSlider", "mPager.getCurrentItem=" + mPager.getCurrentItem());
        if (mPager.getCurrentItem() > 0) {
            String[] arr = currentShloka.split("_");
            DataUtil.shlokaId = arr[0] + "_" + mPager.getCurrentItem();
        }

        //bookMarkButton = (Button) findViewById(R.id.bt_bookmark);
        //b.setOnClickListener(this);
        bookMarkButton = (Button) findViewById(R.id.bt_bookmark);

    }

    private List<String> getShlokaOfChapter(String chapterId) {

        List<String> shlokas = new ArrayList<>();
        int numberOfShlokas = DataUtil.SHLOKAS_IN_CHAPTER[Integer.parseInt(chapterId) - 1];
        for (int i = 0; i < numberOfShlokas; i++) {
            shlokas.add(DataUtil.shlokaTextMap.get(chapterId + "_" + i));
        }

        return shlokas;
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void shareApp(View view) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Read Bhagwad Gita in English for free. Download now http://abc.com");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

   /* @Override
    public void onClick(View view) {

        int id = view.getId();
        if(id==R.id.bt_bookmark)
            Toast.makeText(this, "bookmark", Toast.LENGTH_SHORT).show();


        //bookMarkButton = (Button) view.findViewById(R.id.bt_bookmark);


    }*/

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private List<String> shlokaInChapter;

        public ScreenSlidePagerAdapter(FragmentManager fm, String chapterNumber, List<String> shlokaInChapter) {
            super(fm);
            this.shlokaInChapter = shlokaInChapter;
            Log.d("ScreenSlidePagerAdapter", "chapterNumber=" + chapterNumber + " shloka number=" + shlokaInChapter.size());
        }

        @Override
        public Fragment getItem(int position) {
            return ChapterSliderFragment.newInstance("Title", String.valueOf(position));
        }

        @Override
        public int getCount() {
            return shlokaInChapter.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }

    Button bookMarkButton;
    List<String> bookMarkedShlokas;
    public void bookMarkShloka(View view) {
        bookMarkButton = (Button) view.findViewById(R.id.bt_bookmark);
        bookMarkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String currentShloka=DataUtil.shlokaId;
                bookMarkedShlokas = new ArrayList<>();
                Gson gson = new Gson();
                SharedPreferences prefs = getSharedPreferences("USER_PROFILE", MODE_PRIVATE);
                String restoredText = prefs.getString("bookMarkedShloka", null);
                if (restoredText != null) {
                    bookMarkedShlokas = gson.fromJson(restoredText, ArrayList.class);
                    if(bookMarkedShlokas!=null && !bookMarkedShlokas.contains(currentShloka))
                        bookMarkedShlokas.add(currentShloka);
                }
                String json = gson.toJson(bookMarkedShlokas);
                SharedPreferences.Editor editor = getSharedPreferences("USER_PROFILE", MODE_PRIVATE).edit();
                editor.putString("bookMarkedShloka", json);
                editor.commit();

                Toast.makeText(ChapterSlidePagerActivity.this, "Shloka Bookmarked"+json, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
