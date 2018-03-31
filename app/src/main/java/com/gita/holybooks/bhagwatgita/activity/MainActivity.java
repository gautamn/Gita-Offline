package com.gita.holybooks.bhagwatgita.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.fragment.ChapterFragment;
import com.gita.holybooks.bhagwatgita.fragment.HomeFragment;
import com.gita.holybooks.bhagwatgita.util.DataUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_CHAPTER = "chapters";
    private static final String TAG_SHLOKA_OF_THE_DAY = "shloka_of_the_day";
    private static final String TAG_BOOKMARK = "bookmark";
    private static final String TAG_SHARE = "share";
    private static final String TAG_RATE = "rate";

    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Srimad Bhagavad Gita");
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mHandler = new Handler();
        configureNavigationDrawer();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
        DataUtil.loadMasterDataInMemory(getApplicationContext());
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }*/

    private void configureNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;

                    case R.id.nav_chapters:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_CHAPTER;
                        //break;
                        startActivity(new Intent(MainActivity.this, ChapterActivity.class));
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_shloka_of_the_day:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_SHLOKA_OF_THE_DAY;
                        startActivity(new Intent(MainActivity.this, ShlokaOfTheDayActivity.class));
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_bookmark:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_BOOKMARK;
                        startActivity(new Intent(MainActivity.this, BookmarkActivity.class));
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_share:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_SHARE;
                        startActivity(new Intent(MainActivity.this, ShareActivity.class));
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_rate:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_RATE;
                        startActivity(new Intent(MainActivity.this, RateAppActivity.class));
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_settings:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        drawerLayout.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);
                loadHomeFragment();

                return true;
            }
        });
    }


    private void loadHomeFragment() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawerLayout.closeDrawers();

            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getCurrentFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                if (getSupportFragmentManager().getFragments() != null) {
                    fragmentTransaction.addToBackStack(CURRENT_TAG);
                }
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
                //fragmentTransaction.commit();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawerLayout.closeDrawers();

        // refresh toolbar menu
        //invalidateOptionsMenu();

    }

    private Fragment getCurrentFragment() {

        Log.d("Fragment", "navItemIndex==" + navItemIndex);
        switch (navItemIndex) {

            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                if(getSupportActionBar()!=null){
                    getSupportActionBar().setTitle("List of Chapters");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                ChapterFragment chapterFragment = new ChapterFragment();
                return chapterFragment;

            default:
                return new HomeFragment();
        }
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

       /* final ShareAppFragment fragment = (ShareAppFragment) getSupportFragmentManager().findFragmentByTag(TAG_SHARE);

        if (fragment != null && fragment.allowBackPressed()) {
            super.onBackPressed();
        }*/

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}