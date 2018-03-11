package com.gita.holybooks.bhagwatgita.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.fragment.ChapterSliderFragment;
import com.gita.holybooks.bhagwatgita.util.DataUtil;
import com.gita.holybooks.bhagwatgita.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChapterSlidePagerActivity extends FragmentActivity {

    private static final int[] CHAPTER_SHLOKAS = {45,72,12,56};

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

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(ChapterSlidePagerActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Toast.makeText(ChapterSlidePagerActivity.this,
                        "scrolled Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                Toast.makeText(ChapterSlidePagerActivity.this,
                        "Selected page  scroll state" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> getShlokaOfChapter(String chapterId){

        List<String> shlokas = new ArrayList<>();
        int numberOfShlokas = CHAPTER_SHLOKAS[Integer.parseInt(chapterId)];
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

        private List<String> shlokaData;

        public ScreenSlidePagerAdapter(FragmentManager fm, List<String> shlokaData) {
            super(fm);
            this.shlokaData = shlokaData;
        }

        @Override
        public Fragment getItem(int position) {

            return ChapterSliderFragment.newInstance("1_1", "1_1");
        }

        @Override
        public int getCount() {
            return shlokaData.size();
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
