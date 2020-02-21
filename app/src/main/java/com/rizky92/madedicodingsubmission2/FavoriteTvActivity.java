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

import com.rizky92.madedicodingsubmission2.adapter.TvAdapter;
import com.rizky92.madedicodingsubmission2.database.DatabaseContract;
import com.rizky92.madedicodingsubmission2.helper.MappingHelper;
import com.rizky92.madedicodingsubmission2.pojo.Tvs;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteTvActivity extends AppCompatActivity implements LoadTvsCallback {

    private static final String EXTRA_STATE_TV = "extra_state_tv";

    ProgressBar progressBar;
    TvAdapter adapter;
    ArrayList<Tvs> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        progressBar = findViewById(R.id.progress_circular_item);

        RecyclerView recyclerView = findViewById(R.id.recycle_viewers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TvAdapter(list);
        recyclerView.setAdapter(adapter);

        HandlerThread thread = new HandlerThread("MovieObserver");
        thread.start();
        Handler handler = new Handler(thread.getLooper());

        TvObserver observer = new TvObserver(handler, this);
        getContentResolver().registerContentObserver(DatabaseContract.TvColumns.TV_CONTENT_URI, true, observer);

        if (savedInstanceState == null) {
            new LoadTvAsync(this, this).execute();
        } else {
            ArrayList<Tvs> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE_TV);
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
    public void postExecute(ArrayList<Tvs> listTvs) {
        progressBar.setVisibility(View.INVISIBLE);
        if (listTvs.size() > 0) {
            adapter.setList(listTvs);
        } else {
            adapter.setList(new ArrayList<Tvs>());
        }
    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<Tvs>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadTvsCallback> weakCallback;

        LoadTvAsync(Context context, LoadTvsCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Tvs> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor cursor = context.getContentResolver().query(DatabaseContract.TvColumns.TV_CONTENT_URI, null, null, null, null);
            Log.d("cursor", String.valueOf(cursor));
            if (cursor != null) {
                return MappingHelper.mapTvCursorToArrayList(cursor);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Tvs> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

    static class TvObserver extends ContentObserver {

        final Context context;

        TvObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadTvAsync(context, (LoadTvsCallback) context).execute();
        }
    }
}

interface LoadTvsCallback {
    void preExecute();
    void postExecute(ArrayList<Tvs> listTvs);
}