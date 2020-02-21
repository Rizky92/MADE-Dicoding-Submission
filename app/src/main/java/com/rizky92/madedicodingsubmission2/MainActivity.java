package com.rizky92.madedicodingsubmission2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rizky92.madedicodingsubmission2.adapter.SectionPagerAdapter;
import com.rizky92.madedicodingsubmission2.notification.ReleaseNotificationReceiver;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

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

        ReleaseNotificationReceiver notificationReceiver = new ReleaseNotificationReceiver();
        notificationReceiver.setReleaseAlarm(this, ReleaseNotificationReceiver.TYPE_ONE_TIME, "17:10", "test", "test description");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
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
