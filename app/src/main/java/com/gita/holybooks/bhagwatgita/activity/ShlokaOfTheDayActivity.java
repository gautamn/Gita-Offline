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

        Toast.makeText(getApplicationContext(),"Do Something NOW" + extras.getString("shloka_id"),
                Toast.LENGTH_LONG).show();
        shlokaId = extras.getString("shloka_id");
        setContentView(R.layout.shloka_of_the_day);

        //shlokaText = DataUtil.shlokaTextMap.get(shlokaId); ---? for 11_3 shlokaText was null

        shlokaText = "Hello World";

        TextView textView=(TextView) findViewById(R.id.shlokaOfTheDayId);
        textView.setText(shlokaId);
        textView=(TextView) findViewById(R.id.shlokaOfTheDayContentId);
        textView.setText(shlokaText);


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
