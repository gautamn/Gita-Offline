package com.gita.holybooks.bhagwatgita.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.util.DataUtil;
import com.gita.holybooks.bhagwatgita.util.FileUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    //private ListView listView;
    EditText editTextData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        if(DataUtil.shlokaTextMap.isEmpty())
            FileUtil.loadShlokaInMemory(getApplicationContext(), R.raw.shloka);

        String currentShlokaId = DataUtil.shlokaId;
        Log.d("HomePageActivity", "currentShlokaId="+currentShlokaId);
        currentShlokaId = (currentShlokaId==null)?"1_1":currentShlokaId;

        String shlokaText = DataUtil.shlokaTextMap.get(currentShlokaId);
        Log.d("HomePageActivity", "shlokaText="+shlokaText);
        shlokaText = (shlokaText==null)?"Hello World":shlokaText;

        TextView textView=(TextView) findViewById(R.id.shlokaNumber);
        textView.setText(currentShlokaId);
        textView=(TextView) findViewById(R.id.shlokaText);
        textView.setText(shlokaText);


        final Button nextButton = findViewById(R.id.next_button_id);
        final String finalCurrentShlokaId = currentShlokaId;
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, HomePageActivity.class);
                String nextShlokaId = getNextShlokaId(finalCurrentShlokaId);
                String nextShlokaText = DataUtil.shlokaTextMap.get(nextShlokaId);
                TextView textView=(TextView) findViewById(R.id.shlokaNumber);
                textView.setText(nextShlokaId);
                textView=(TextView) findViewById(R.id.shlokaText);
                textView.setText(nextShlokaText);
                startActivity(intent);
            }
        });

       /* final Button prevButton = findViewById(R.id.prev_button_id);
        prevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String previousShlokaId = getPreviousShlokaId(finalCurrentShlokaId);
                String prevButton = DataUtil.getShlokaTextMap().get("1_2");
                TextView textView=(TextView) findViewById(R.id.prev_button_id);
                textView.setText(prevButton==null?"Hello World!!!":prevButton);
            }
        });*/
    }

    private String getNextShlokaId(String finalCurrentShlokaId) {

        String[] arr = finalCurrentShlokaId.split("_");
        return arr[0]+"_"+(Integer.valueOf(arr[1])+1);
    }

    private String getPreviousShlokaId(String finalCurrentShlokaId) {

        String[] arr = finalCurrentShlokaId.split("_");
        return arr[0]+"_"+(Integer.valueOf(arr[1])+1);
    }



}
