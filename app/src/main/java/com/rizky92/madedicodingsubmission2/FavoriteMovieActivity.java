package com.rizky92.madedicodingsubmission2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rizky92.madedicodingsubmission2.adapter.MovieAdapter;
import com.rizky92.madedicodingsubmission2.database.DatabaseContract;
import com.rizky92.madedicodingsubmission2.helper.MappingHelper;
import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteMovieActivity extends AppCompatActivity implements LoadMoviesCallback {

    private static final String EXTRA_STATE_MOVIE = "extra_state_movie";

    ProgressBar progressBar;
    MovieAdapter adapter;
    ArrayList<Movies> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        progressBar = findViewById(R.id.progress_circular_item);

        RecyclerView recyclerView = findViewById(R.id.recycle_viewers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieAdapter(list);
        recyclerView.setAdapter(adapter);

        HandlerThread thread = new HandlerThread("MovieObserver");
        thread.start();
        Handler handler = new Handler(thread.getLooper());

        MovieObserver observer = new MovieObserver(handler, this);
        getContentResolver().registerContentObserver(DatabaseContract.MovieColumns.MOVIE_CONTENT_URI, true, observer);

        if (savedInstanceState == null) {
            new LoadMoviesAsync(this, this).execute();
        } else {
            ArrayList<Movies> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE_MOVIE);
            if (list != null) {
                adapter.setList(list);
            }
        }
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Movies> listMovies) {
        progressBar.setVisibility(View.INVISIBLE);
        if (listMovies.size() > 0) {
            adapter.setList(listMovies);
        } else {
            adapter.setList(new ArrayList<Movies>());
        }
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<Movies>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        LoadMoviesAsync(Context context, LoadMoviesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movies> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor cursor = context.getContentResolver().query(DatabaseContract.MovieColumns.MOVIE_CONTENT_URI, null, null, null, null);
            Log.d("cursor", String.valueOf(cursor));
            if (cursor != null) {
                return MappingHelper.mapMovieCursorToArrayList(cursor);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

    static class MovieObserver extends ContentObserver {

        final Context context;

        MovieObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMoviesAsync(context, (LoadMoviesCallback) context).execute();
        }
    }
}

interface LoadMoviesCallback {
    void preExecute();
    void postExecute(ArrayList<Movies> listMovies);
}