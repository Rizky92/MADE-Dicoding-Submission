package com.rizky92.madedicodingsubmission2.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.rizky92.madedicodingsubmission2.R;

import java.util.Calendar;

public class ReleaseNotificationReceiver extends BroadcastReceiver {

    private static final String RELEASE_NOTIFICATION_ALARM = "release notification alarm";
    private static final String RELEASE_CHANNEL = "channel_release";
    private static final String REMINDER_NOTIFICATION_ALARM = "reminder notification alarm";
    private static final String REMINDER_CHANNEL = "channel_reminder";

    public static final String EXTRA_TYPE = "extra_type";

    public static final String TYPE_ONE_TIME = "OneTimeAlarm";
    public static final String TYPE_REPEATING = "RepeatingAlarm";

    private final int ID_ONETIME = 100;
    private final int ID_REPEATING = 101;

    private int releaseNotificationId = 1;
    private int reminderNotificationId = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);

        String title = type.equalsIgnoreCase(TYPE_ONE_TIME) ? TYPE_ONE_TIME : TYPE_REPEATING;
        int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? ID_ONETIME : ID_REPEATING;

        Log.d(ReleaseNotificationReceiver.class.getSimpleName(), type);

        createReleaseNotification(context, "Ayam bakar goreng", "test deskripsi lorem ipsum dolor sit amet");
    }

    public void setReleaseAlarm(Context context, String type, String time, String title, String desc) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReleaseNotificationReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);

        String[] timeArr = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArr[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArr[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void createReleaseNotification(Context context, String title, String desc) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, RELEASE_CHANNEL)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_local_movies_black_24dp))
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(desc))
                .setContentText(desc);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(RELEASE_CHANNEL, RELEASE_NOTIFICATION_ALARM, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(RELEASE_NOTIFICATION_ALARM);
            builder.setChannelId(RELEASE_CHANNEL);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(releaseNotificationId, notification);
        }
    }
}
