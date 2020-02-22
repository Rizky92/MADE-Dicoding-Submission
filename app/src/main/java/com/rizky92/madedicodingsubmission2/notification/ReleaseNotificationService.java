package com.rizky92.madedicodingsubmission2.notification;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rizky92.madedicodingsubmission2.pojo.Movies;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class ReleaseNotificationService extends JobService {

    private static String TAG = ReleaseNotificationService.class.getSimpleName();
    private static final String API_KEY = "4b71618fab6c4526517f0f17c5809762";
    private ArrayList<Movies> list = new ArrayList<>();

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        getReleases(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private void getReleases(final JobParameters job) {
        AsyncHttpClient client = new AsyncHttpClient();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(calendar.getTime());
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

                        Log.d(TAG, "movies " + i + movies.getTitle());

                        ReleaseNotificationReceiver receiver = new ReleaseNotificationReceiver();


                        list.add(movies);
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }
}
