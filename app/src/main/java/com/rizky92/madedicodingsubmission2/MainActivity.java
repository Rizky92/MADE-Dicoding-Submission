package com.rizky92.madedicodingsubmission2;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rizky92.madedicodingsubmission2.adapter.SectionPagerAdapter;
import com.rizky92.madedicodingsubmission2.notification.ReleaseNotificationReceiver;
import com.rizky92.madedicodingsubmission2.notification.ReleaseNotificationService;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private int jobId = 10;

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

        startJob();
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

    private void startJob() {
        if (isJobRunning(this)) {
            Toast.makeText(this, getResources().getString(R.string.job_running), Toast.LENGTH_SHORT).show();
            return;
        } else {
            ComponentName service = new ComponentName(this, ReleaseNotificationService.class);
            JobInfo.Builder builder = new JobInfo.Builder(jobId, service);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            builder.setRequiresDeviceIdle(false);
            builder.setRequiresCharging(false);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setPeriodic(900000);
            } else {
                builder.setPeriodic(180000);
            }

            JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            scheduler.schedule(builder.build());
            Log.d("startJob", "JobService started");
        }
    }

    private boolean isJobRunning(Context context) {
        boolean isScheduled = false;
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (scheduler != null) {
            for (JobInfo info : scheduler.getAllPendingJobs()) {
                if (info.getId() == jobId) {
                    isScheduled = true;
                    break;
                }
            }
        }
        return isScheduled;
    }
}
