package com.gita.holybooks.bhagwatgita.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Nitin Gautam on 2/17/2018.
 */

public class ShlokaService extends IntentService {

    public ShlokaService(){
        super("ShlokaService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


    }
}
