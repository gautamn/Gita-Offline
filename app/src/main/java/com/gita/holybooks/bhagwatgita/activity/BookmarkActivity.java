package com.gita.holybooks.bhagwatgita.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.util.DataUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {

    List<String> bookMarkedShlokas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_list);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setToolbar(toolbar, "List Of Bookmarks");

        Gson gson = new Gson();
        SharedPreferences prefs = getSharedPreferences("USER_PROFILE", MODE_PRIVATE);
        String restoredText = prefs.getString("bookMarkedShloka", null);
        if (restoredText != null) {
            bookMarkedShlokas = gson.fromJson(restoredText, ArrayList.class);
        }

        List<String> tmpList = new ArrayList<>(bookMarkedShlokas.size());
        for (String bookmark: bookMarkedShlokas) {

            String[] arr = bookmark.split("_");
            StringBuilder sb = new StringBuilder();
            sb.append("Chapter ").append(arr[0]).append(" Shloka ").append(arr[1]);
            tmpList.add(sb.toString());

        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.layout_list_view_bookmark, tmpList.toArray(new String[0]));

        ListView listView = (ListView) findViewById(R.id.bookmark_list);
        listView.setAdapter(adapter);
    }

    private void setToolbar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);

    }

}
