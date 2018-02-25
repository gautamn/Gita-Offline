package com.gita.holybooks.bhagwatgita.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.util.DataUtil;
import com.gita.holybooks.bhagwatgita.util.FileUtil;

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


     /*   final Button nextButton = findViewById(R.id.next_button_id);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, HomePageActivity.class);
                String nextShloka = DataUtil.getShlokaTextMap().get("1_4");
                TextView textView=(TextView) findViewById(R.id.editText);
                textView.setText(nextShloka==null?"Hello World!!!":nextShloka);
                startActivity(intent);
            }
        });*/

        /*final Button prevButton = findViewById(R.id.prev_button_id);
        prevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String prevButton = DataUtil.getShlokaTextMap().get("1_2");
                TextView textView=(TextView) findViewById(R.id.prev_button_id);
                textView.setText(prevButton==null?"Hello World!!!":prevButton);
            }
        });*/
    }
}
