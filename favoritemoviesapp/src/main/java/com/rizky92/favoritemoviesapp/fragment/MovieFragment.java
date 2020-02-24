package com.rizky92.favoritemoviesapp.fragment;

import android.content.Context;
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
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizky92.favoritemoviesapp.R;
import com.rizky92.favoritemoviesapp.adapter.MovieAdapter;
import com.rizky92.favoritemoviesapp.database.DatabaseContract;
import com.rizky92.favoritemoviesapp.helper.MappingHelper;
import com.rizky92.favoritemoviesapp.pojo.Movies;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

interface LoadMoviesCallback {
    void preExecute();

    void postExecute(ArrayList<Movies> listMovies);
}

public class MovieFragment extends Fragment implements LoadMoviesCallback {

    private static final String EXTRA_STATE_MOVIE = "extra_state_movie";
    private ProgressBar progressBar;
    private MovieAdapter adapter;
    private final ArrayList<Movies> list = new ArrayList<>();

    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progress_circular_item);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_viewers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new MovieAdapter(list);
        recyclerView.setAdapter(adapter);


        HandlerThread thread = new HandlerThread("MovieObserver");
        thread.start();
        Handler handler = new Handler(thread.getLooper());

        MovieObserver observer = new MovieObserver(handler, getContext());
        getActivity().getContentResolver().registerContentObserver(DatabaseContract.MovieColumns.MOVIE_CONTENT_URI, true, observer);

        if (savedInstanceState == null) {
            new LoadMoviesAsync(getContext(), this).execute();
        } else {
            ArrayList<Movies> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE_MOVIE);
            if (list != null) {
                adapter.setList(list);
            }
        }
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Movies> listMovies) {
        progressBar.setVisibility(View.INVISIBLE);
        if (listMovies != null && listMovies.size() > 0) {
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
        }
    }
}