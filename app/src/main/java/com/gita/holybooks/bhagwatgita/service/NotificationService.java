package com.gita.holybooks.bhagwatgita.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.activity.ShlokaOfTheDayActivity;
import com.gita.holybooks.bhagwatgita.util.DataUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    public static final long NOTIFY_INTERVAL =  1000*60*60*24;

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // cancel if already existed
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();
        }
        Calendar cal = Calendar.getInstance();
        //cal.set(Calendar.AM_PM, Calendar.PM);
        cal.set(Calendar.HOUR, 7);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        Date date = cal.getTime();

        mTimer.schedule(new TimeDisplayTimerTask(), date, NOTIFY_INTERVAL);
        //mTimer.schedule(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    sendShlokaOfTheDay();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onCreate();
        Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show();
        //sendShlokaOfTheDay();
    }

    private void sendShlokaOfTheDay() {

        int favShlokaIndex = getRandomNumberInRange(0, DataUtil.FAVOURITE_SHLOKAS.length - 1);
        String shlokaNumber = DataUtil.FAVOURITE_SHLOKAS[favShlokaIndex];
        String shlokaContent = DataUtil.shlokaTextMap.get(shlokaNumber);
        addNotification(shlokaNumber, shlokaContent);
    }

    private void addNotification(String shlokaNumber, String shlokaContent) {

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);
        contentView.setTextViewText(R.id.shlokaTextId, shlokaContent);
        contentView.setImageViewResource(R.id.image, R.drawable.ic_drawer);
        contentView.setTextViewText(R.id.title, "Shloka of the Day !!!");
        contentView.setTextViewText(R.id.text, "This is a custom layout");

        Notification.Builder builder =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            builder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_drawer)
                    .setContentTitle("Shloka Of The Day")
                    .setContentText(shlokaContent)
                    .setCustomContentView(contentView);
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
