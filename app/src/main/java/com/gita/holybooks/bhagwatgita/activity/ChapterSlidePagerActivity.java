package com.gita.holybooks.bhagwatgita.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.fragment.ChapterSliderFragment;
import com.gita.holybooks.bhagwatgita.util.DataUtil;
import com.gita.holybooks.bhagwatgita.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class ChapterSlidePagerActivity extends FragmentActivity {

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

        if(getActionBar()!=null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        if(DataUtil.shlokaTextMap.isEmpty())
            FileUtil.loadShlokaInMemory(getApplicationContext(), R.raw.shloka);

        Log.d("ChapterSlider", "mPager.getCurrentItem="+mPager.getCurrentItem());
        if(mPager.getCurrentItem()>0) {
            String[] arr = currentShloka.split("_");
            DataUtil.shlokaId = arr[0]+"_"+mPager.getCurrentItem();
        }


        // Attach the page change listener inside the activity
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int lastPage = 0;
            int newShlokaId = 0;

            @Override
            public void onPageSelected(int position) {

                /*String[] arr = DataUtil.shlokaId.split("_");
                int numberOfShlokas = DataUtil.SHLOKAS_IN_CHAPTER[Integer.valueOf(arr[0])-1];
                newShlokaId = Integer.valueOf(arr[1]);
                if(lastPage > position){
                    //scrolled left
                    newShlokaId = position - 1;
                    if(newShlokaId<1) newShlokaId = 1;
                }
                else if(lastPage<position){
                    //scrolled right
                    newShlokaId = position + 1;

                    if(newShlokaId>numberOfShlokas)
                        newShlokaId = numberOfShlokas;
                }*/
/*
                Toast.makeText(ChapterSlidePagerActivity.this,"Selected page position: " + position
                        +" DataUtil.shlokaId:"+DataUtil.shlokaId,
                        Toast.LENGTH_SHORT).show();*/

               /* DataUtil.shlokaId =  arr[0] + "_" + (newShlokaId);
                lastPage=position;*/
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /*Toast.makeText(ChapterSlidePagerActivity.this,
                        "scrolled Selected page positionOffset: " + position, Toast.LENGTH_SHORT).show();*/
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                //Toast.makeText(ChapterSlidePagerActivity.this,"Selected page  scroll state" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> getShlokaOfChapter(String chapterId){

        List<String> shlokas = new ArrayList<>();
        int numberOfShlokas = DataUtil.SHLOKAS_IN_CHAPTER[Integer.parseInt(chapterId)-1];
        for(int i=0; i< numberOfShlokas; i++){
            shlokas.add(DataUtil.shlokaTextMap.get(chapterId+"_"+i));
        }

        return shlokas;
    }

    @Override
    public boolean onNavigateUp(){
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

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private List<String> shlokaInChapter;

        public ScreenSlidePagerAdapter(FragmentManager fm, String chapterNumber, List<String> shlokaInChapter) {
            super(fm);
            this.shlokaInChapter = shlokaInChapter;
            Log.d("ScreenSlidePagerAdapter", "chapterNumber="+chapterNumber+" shloka number="+shlokaInChapter.size());
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
}
