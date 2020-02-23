package com.rizky92.madedicodingsubmission2;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.rizky92.madedicodingsubmission2.notification.ReleaseNotificationService;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private SwitchPreferenceCompat reminderPref;
        private SwitchPreferenceCompat releasePref;

        private String REMINDER;
        private String RELEASE;

        private static final int RELEASE_JOB = 10;
        private static final int REMINDER_JOB = 20;

        private boolean releaseStatus;
        private boolean reminderStatus;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            REMINDER = getResources().getString(R.string.key_reminder);
            RELEASE = getResources().getString(R.string.key_release);

            reminderPref = findPreference(REMINDER);
            releasePref = findPreference(RELEASE);

            releasePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    releaseStatus = (boolean) newValue;
                    if (releaseStatus) {
                        startDailyReleaseSchedule();
                    } else {
                        stopDailyReleaseSchedule();
                    }
                    Log.d("switch release", String.valueOf(releaseStatus));
                    return true;
                }
            });

            reminderPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    reminderStatus = (boolean) newValue;
                    Log.d("switch reminder", String.valueOf(reminderStatus));
                    return true;
                }
            });
        }

        private void startDailyReleaseSchedule() {
            if (isScheduledRunning(getContext())) {
                Toast.makeText(getContext(), getResources().getString(R.string.job_running), Toast.LENGTH_SHORT).show();
                Log.d("release", "Release already running");
            } else {
                ComponentName service = new ComponentName(getContext(), ReleaseNotificationService.class);
                JobInfo.Builder builder = new JobInfo.Builder(RELEASE_JOB, service);
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
                builder.setRequiresDeviceIdle(false);
                builder.setRequiresCharging(false);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    builder.setPeriodic(900000);
                } else {
                    builder.setPeriodic(180000);
                }

                JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                scheduler.schedule(builder.build());
                Log.d("release", "Release enabled");
            }
        }

        private void stopDailyReleaseSchedule() {
            JobScheduler scheduler = (JobScheduler) getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
            scheduler.cancel(RELEASE_JOB);
            Log.d("release", "Release disabled");
        }

        private boolean isScheduledRunning(Context context) {
            boolean isScheduled = false;
            JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            if (scheduler != null) {
                for (JobInfo info : scheduler.getAllPendingJobs()) {
                    if (info.getId() == RELEASE_JOB) {
                        isScheduled = true;
                        break;
                    } else if (info.getId() == REMINDER_JOB) {
                        isScheduled = true;
                        break;
                    }
                }
            }
            return isScheduled;
        }
    }
}