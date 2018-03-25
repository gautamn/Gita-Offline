package com.gita.holybooks.bhagwatgita.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.util.DataUtil;

public class ShlokaOfTheDayActivity extends AppCompatActivity {

    String shlokaId = null;
    String shlokaText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            Log.i( "dd","Extra:" + extras.getString("shloka_id") );

        shlokaId = extras.getString("shloka_id");
        setContentView(R.layout.chapter_slider_page);

        TextView tvShlokaNumber=(TextView) findViewById(R.id.shlokaNumber);
        TextView tvShlokaText=(TextView) findViewById(R.id.shlokaText);
        TextView tvTransText=(TextView) findViewById(R.id.shlokaTrans);

        String currentShlokaId = extras.getString("shloka_id");;
        String shlokaText = DataUtil.shlokaTextMap.get(currentShlokaId);
        shlokaText = (shlokaText==null)?"Hello World":shlokaText;

        String[] arr = currentShlokaId.split("_");
        tvShlokaNumber.setText("Chapter "+ arr[0]+" Shloka "+arr[1]);
        tvShlokaText.setText(shlokaText);
        tvTransText.setText(DataUtil.englishTransTextMap.get(currentShlokaId));
    }

    public void shareContent(View view) {

        if(shlokaId!=null && shlokaText!=null){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

    }
}
