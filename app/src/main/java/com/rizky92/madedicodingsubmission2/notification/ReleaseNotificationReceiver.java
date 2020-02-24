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

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rizky92.madedicodingsubmission2.DetailActivity;
import com.rizky92.madedicodingsubmission2.R;
import com.rizky92.madedicodingsubmission2.pojo.Movies;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ReleaseNotificationReceiver extends BroadcastReceiver {

    private static final String API_KEY = "4b71618fab6c4526517f0f17c5809762";

    private static final String EXTRA_ID = "extra_id";
    private static final int RELEASE_ID = 20;
    private ArrayList<Movies> items;

    @Override
    public void onReceive(final Context context, Intent intent) {
        final int id = intent.getIntExtra(EXTRA_ID, RELEASE_ID);
        SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
        String today = day.format(new Date());

        if (id == RELEASE_ID) {
            AsyncHttpClient client = new AsyncHttpClient();
            items = new ArrayList<>();
            String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" + today + "&primary_release_date.lte=" + today;
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject object = new JSONObject(result);
                        JSONArray array = object.getJSONArray("results");
                        Log.d("Response: ", String.format("%s", statusCode));
                        Log.d("Results", result);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject movie = array.getJSONObject(i);

                            Movies movies = new Movies();
                            movies.setTitle(movie.getString("title"));
                            movies.setDate(movie.getString("release_date"));
                            movies.setDesc(movie.getString("overview"));
                            movies.setPopularity(movie.getString("popularity"));
                            movies.setLanguage(movie.getString("original_language"));
                            movies.setVoteAverage((float) movie.getDouble("vote_average"));
                            movies.setVoteCount(movie.getInt("vote_count"));
                            movies.setAdult(movie.getBoolean("adult"));
                            movies.setMovieId(movie.getInt("id"));
                            movies.setPosterPath(String.format("%s%s", "https://image.tmdb.org/t/p/w780", movie.getString("poster_path")));

                            createNotification(context, i, movies);
                            items.add(movies);
                        }
                    } catch (Exception e) {
                        Log.d("Exception", e.getMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("Fail ", error.getMessage());
                }
            });
        }
    }

    public void enableReleases(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReleaseNotificationReceiver.class);
        intent.putExtra(EXTRA_ID, RELEASE_ID);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RELEASE_ID, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void disableReleases(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReleaseNotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RELEASE_ID, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void createNotification(Context context, int notificationId, @Nullable Movies movies) {
        String CHANNEL_ID = "channel_1";
        String CHANNEL_NAME = "release_channel";

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        if (items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                inboxStyle.addLine(items.get(i).getTitle());
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_creation_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_creation_black_24dp))
                .setContentTitle(context.getResources().getString(R.string.release_title))
                .setStyle(inboxStyle);

        PendingIntent pendingIntent = getPendingIntent(context, notificationId, movies);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);

            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (manager != null) {
            manager.notify(RELEASE_ID, notification);
        }
    }

    private PendingIntent getPendingIntent(Context context, int id, Movies movies) {
        Intent intent;
        if (id == RELEASE_ID) {
            intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_MOVIES, movies);
        } else {
            return null;
        }
        return PendingIntent.getActivity(context, id, intent, 0);
    }
}
