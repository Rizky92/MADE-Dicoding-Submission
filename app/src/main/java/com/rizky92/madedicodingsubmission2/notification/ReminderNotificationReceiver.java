package com.rizky92.madedicodingsubmission2.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.rizky92.madedicodingsubmission2.MainActivity;
import com.rizky92.madedicodingsubmission2.R;

import java.util.Calendar;

public class ReminderNotificationReceiver extends BroadcastReceiver {

    private final int REMINDER_ID = 10;

    private static final String CHANNEL_ID = "channel_2";
    private static final String CHANNEL_NAME = "reminder_channel";

    private static final int REQUEST_CODE = 200;

    @Override
    public void onReceive(Context context, Intent intent) {
        setReminder(context);
    }

    private void setReminder(Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingTaskIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getResources().getString(R.string.reminder_title))
                .setContentText(context.getResources().getString(R.string.reminder_notification))
                .setSmallIcon(R.drawable.ic_movie_creation_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_creation_black_24dp))
                .setContentIntent(pendingTaskIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);

            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (manager != null) {
            manager.notify(REMINDER_ID, notification);
        }
    }

    public void enableReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReminderNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REMINDER_ID, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 53);
        calendar.set(Calendar.SECOND, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void disableReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReleaseNotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REMINDER_ID, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
