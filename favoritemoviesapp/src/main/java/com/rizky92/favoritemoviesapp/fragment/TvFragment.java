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
import com.rizky92.favoritemoviesapp.adapter.TvAdapter;
import com.rizky92.favoritemoviesapp.database.DatabaseContract;
import com.rizky92.favoritemoviesapp.helper.MappingHelper;
import com.rizky92.favoritemoviesapp.pojo.Tvs;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TvFragment extends Fragment implements LoadTvsCallback {

    private static final String EXTRA_STATE_TV = "extra_state_tv";

    private ProgressBar progressBar;
    private TvAdapter adapter;
    private final ArrayList<Tvs> list = new ArrayList<>();

    public TvFragment() {
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
        adapter = new TvAdapter(list);
        recyclerView.setAdapter(adapter);

        HandlerThread thread = new HandlerThread("TvObserver");
        thread.start();
        Handler handler = new Handler(thread.getLooper());

        TvObserver observer = new TvObserver(handler, getContext());
        getActivity().getContentResolver().registerContentObserver(DatabaseContract.TvColumns.TV_CONTENT_URI, true, observer);

        if (savedInstanceState == null) {
            new LoadTvAsync(getContext(), this).execute();
        } else {
            ArrayList<Tvs> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE_TV);
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
        }
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}

interface LoadTvsCallback {
    void preExecute();
    void postExecute(ArrayList<Tvs> listTvs);
}
