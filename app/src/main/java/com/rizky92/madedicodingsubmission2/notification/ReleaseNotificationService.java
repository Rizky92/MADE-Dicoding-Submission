package com.rizky92.madedicodingsubmission2.notification;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class ReleaseNotificationService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
