package com.sciters.mytheater.helper;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.sciters.mytheater.R;
import com.sciters.mytheater.ShowActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Gilang on 10/18/13.
 */
public class ReminderService extends IntentService {
    private static final int NOTIF_ID = 1;

    public ReminderService(){
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Creates an explicit intent for an Activity in your app

        try {
        Intent resultIntent = new Intent(this, ShowActivity.class);
        int notificationShowID = intent.getIntExtra("notificationShowID",320);
        String title = intent.getStringExtra("notificationShowTitle");
        String type = intent.getStringExtra("notificationShowType");

        resultIntent.putExtra("showID",Integer.toString(notificationShowID));

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.banner2)
                        .setContentTitle("Sudah bisa apply " + type +"!")
                        .setContentText("Tiket " + type +" show " + title + " sudah bisa dibeli. Klik di sini untuk apply");

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        //stackBuilder.addParentStack(ShowActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}