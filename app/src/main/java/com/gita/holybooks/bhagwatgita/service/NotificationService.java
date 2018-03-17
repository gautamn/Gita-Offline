package com.gita.holybooks.bhagwatgita.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.activity.ShlokaOfTheDayActivity;
import com.gita.holybooks.bhagwatgita.util.DataUtil;

import java.util.Random;

public class NotificationService extends Service {

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"Service created", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service destroyed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onCreate();
        Toast.makeText(this,"Service started", Toast.LENGTH_LONG).show();
        sendShlokaOfTheDay();
    }

    private void sendShlokaOfTheDay() {

        int favShlokaIndex = getRandomNumberInRange(0, DataUtil.FAVOURITE_SHLOKAS.length);
        String shlokaNumber = DataUtil.FAVOURITE_SHLOKAS[favShlokaIndex];
        String shlokaContent = DataUtil.shlokaTextMap.get(shlokaNumber);
        addNotification(shlokaNumber, shlokaContent);
    }

    private void addNotification(String shlokaNumber, String shlokaContent) {


        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification);
        expandedView.setTextViewText(R.id.shlokaText, shlokaContent);


        Notification.Builder builder =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            builder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_drawer)
                    .setContentTitle("Shloka Of The Day")
                    .setContentText(shlokaContent)
                    .setCustomBigContentView(expandedView);
        }

        Intent notificationIntent = new Intent(this, ShlokaOfTheDayActivity.class);
        notificationIntent.putExtra("shloka_id", shlokaNumber); // <-- HERE I PUT THE EXTRA VALUE
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);


        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            manager.notify(0, builder.build());
        }

    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
