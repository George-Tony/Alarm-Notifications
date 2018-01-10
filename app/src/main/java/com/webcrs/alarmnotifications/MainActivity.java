package com.webcrs.alarmnotifications;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

    Context context;

    private Notification.Builder notificationBuilder;
    private int currentNotificationID = 0;
    private NotificationManager notificationManager;
    private Bitmap icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        icon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_launcher);

        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        //setNotification(getNotification("Notification Delayed for 5 Second"), 5000);

    }

    private void setNotification(Notification notification, int delay) {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(pendingIntent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        currentNotificationID++;
        int notificationId = currentNotificationID;
        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0;

        notificationManager.notify(notificationId, notification);

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss");

        Date parsedTimeStamp = null;
        try {
            parsedTimeStamp = dateFormat.parse("2018-01-06 15:03:00");

        } catch (ParseException e) {
            e.printStackTrace();
        }


        Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
        Log.e("parsedTimeStamp", ""+ parsedTimeStamp.getTime());
        Log.e("timestamp :", "" + timestamp.getTime());
        Log.e("System Time: ", ""+SystemClock.elapsedRealtime() );

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedTimeStamp);

        Log.e("Calendar Time: ", ""+calendar.getTimeInMillis());


        long futureInMillis = timestamp.getTime();//SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {

        notificationBuilder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle("Local Notification")
                .setStyle(new Notification.BigTextStyle().bigText("THIS IS A TEXT NOTIFICATION"))
                .setPriority(Notification.PRIORITY_MAX)
                .setContentText("THIS IS A TEXT NOTIFICATION");

       // sendNotification();

        return notificationBuilder.build();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_5:
                setNotification(getNotification("Notification Delayed for 5 Second"), 5000);
                return true;
            case R.id.action_10:
                //setNotification(getNotification("Notification Delayed for 10 Second"), 10000);
                return true;
            case R.id.action_30:
                //setNotification(getNotification("Notification Delayed for 30 Second"), 30000);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void clearAllNotifications() {
        if (notificationManager != null) {
            currentNotificationID = 0;
            notificationManager.cancelAll();
        }
    }


}
