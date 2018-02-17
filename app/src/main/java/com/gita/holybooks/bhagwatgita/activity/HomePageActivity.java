package com.gita.holybooks.bhagwatgita.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        FileUtil.loadShlokaInMemory(getApplicationContext(), R.raw.shloka);

        String firstShloka = DataUtil.getShlokaTextMap().get("18_78");
        Log.d("HomePageActivity", "first shloka="+firstShloka);

        TextView textView=(TextView) findViewById(R.id.editText);
        textView.setText(firstShloka==null?"Hello World!!!":firstShloka);
    }
}
