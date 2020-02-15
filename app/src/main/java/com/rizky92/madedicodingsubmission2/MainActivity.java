package com.rizky92.madedicodingsubmission2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rizky92.madedicodingsubmission2.adapter.SectionPagerAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final int NOTIFICATION_MOVIE_ID = 1;
    public static final String MOVIE_CHANNEL_ID = "channel_movie_01";
    public static final String MOVIE_CHANNEL_NAME = "movie channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tabMode);

        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(), this);

        viewPager.setAdapter(sectionPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MOVIE_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_local_movies_black_24dp))
                .setContentTitle(getResources().getString(R.string.fav_movies))
                .setContentText(getResources().getString(R.string.adesc))
                .setSubText(getResources().getString(R.string.date))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(MOVIE_CHANNEL_ID, MOVIE_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(MOVIE_CHANNEL_NAME.toString());
            builder.setChannelId(MOVIE_CHANNEL_ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_MOVIE_ID, notification);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.settings:
                intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;

            case R.id.menu_favorite_movies:
                intent = new Intent(MainActivity.this, FavoriteMovieActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_favorite_tvs:
                intent = new Intent(MainActivity.this, FavoriteTvActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
