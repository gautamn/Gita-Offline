package com.gita.holybooks.bhagwatgita.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.util.DataUtil;

public class ShlokaOfTheDayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            Log.i( "dd","Extra:" + extras.getString("shloka_id") );

        Toast.makeText(getApplicationContext(),
                "Do Something NOW" + extras.getString("shloka_id"),
                Toast.LENGTH_LONG).show();
        String shlokaId = extras.getString("shloka_id");
        setContentView(R.layout.shloka_of_the_day);

        String shlokaText = DataUtil.shlokaTextMap.get(shlokaId);

        TextView textView=(TextView) findViewById(R.id.shlokaOfTheDayId);
        textView.setText(shlokaId);
        textView=(TextView) findViewById(R.id.shlokaOfTheDayContentId);
        textView.setText(shlokaText);


    }
}
