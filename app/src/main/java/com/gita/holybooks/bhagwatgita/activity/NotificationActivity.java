package com.gita.holybooks.bhagwatgita.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification_temp);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
            Log.i( "dd","Extra:" + extras.getString("item_id") );

        Toast.makeText(getApplicationContext(),
                "Do Something NOW" + extras.getString("item_id"),
                Toast.LENGTH_LONG).show();
    }
}
