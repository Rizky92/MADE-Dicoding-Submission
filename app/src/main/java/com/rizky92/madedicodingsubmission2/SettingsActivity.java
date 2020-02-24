package com.rizky92.madedicodingsubmission2;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.rizky92.madedicodingsubmission2.notification.ReleaseNotificationReceiver;
import com.rizky92.madedicodingsubmission2.notification.ReminderNotificationReceiver;

import java.util.Objects;

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

        private boolean releaseStatus;
        private boolean reminderStatus;

        final ReleaseNotificationReceiver releaseNotificationReceiver = new ReleaseNotificationReceiver();
        final ReminderNotificationReceiver reminderNotificationReceiver = new ReminderNotificationReceiver();

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            String REMINDER = getResources().getString(R.string.key_reminder);
            String RELEASE = getResources().getString(R.string.key_release);

            SwitchPreferenceCompat reminderPref = findPreference(REMINDER);
            SwitchPreferenceCompat releasePref = findPreference(RELEASE);

            if (releasePref != null) {
                releasePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        releaseStatus = (boolean) newValue;
                        if (releaseStatus) {
                            releaseNotificationReceiver.enableReleases(Objects.requireNonNull(getActivity()));
                        } else {
                            releaseNotificationReceiver.disableReleases(Objects.requireNonNull(getActivity()));
                        }
                        Log.d("switch release", String.valueOf(releaseStatus));
                        return true;
                    }
                });
            }

            if (reminderPref != null) {
                reminderPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        reminderStatus = (boolean) newValue;
                        if (reminderStatus) {
                            reminderNotificationReceiver.enableReminder(Objects.requireNonNull(getActivity()));
                        } else {
                            reminderNotificationReceiver.disableReminder(Objects.requireNonNull(getActivity()));
                        }
                        Log.d("switch reminder", String.valueOf(reminderStatus));
                        return true;
                    }
                });
            }
        }
    }

}