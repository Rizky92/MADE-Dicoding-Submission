package com.rizky92.madedicodingsubmission2.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rizky92.madedicodingsubmission2.DetailActivity;
import com.rizky92.madedicodingsubmission2.R;
import com.rizky92.madedicodingsubmission2.adapter.ViewAdapter;
import com.rizky92.madedicodingsubmission2.database.DatabaseContract;
import com.rizky92.madedicodingsubmission2.helper.MappingHelper;
import com.rizky92.madedicodingsubmission2.pojo.Tvs;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteTvFragment extends Fragment implements LoadTvCallback {

    private static final String EXTRA_STATE = "extra_state";
    ProgressBar progressBar;
    ViewAdapter<Tvs> adapter;
    ArrayList<Tvs> list = new ArrayList<>();

    public FavoriteTvFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progress_circular_item);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_viewers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new ViewAdapter<Tvs>(view.getContext(), list) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
                return new CardViewHolder(view);
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder viewHolder, Tvs tvs1) {
                final Tvs tvs = tvs1;

                final CardViewHolder holder = (CardViewHolder) viewHolder;

                holder.cardTitle.setText(tvs.getTitle());
                holder.cardDesc.setText(tvs.getDesc());
                holder.cardDate.setText(String.format("%s%s", view.getResources().getString(R.string.aired), tvs.getDate()));
                Picasso.get()
                        .load(tvs.getPosterPath())
                        .resize(202, 300)
                        .centerCrop()
                        .into(holder.cardPoster);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_TVS, tvs);
                        startActivity(intent);
                    }
                });
            }

            class CardViewHolder extends RecyclerView.ViewHolder {
                TextView cardTitle;
                TextView cardDesc;
                TextView cardDate;
                ImageView cardPoster;

                CardViewHolder(@NonNull View itemView) {
                    super(itemView);

                    cardTitle = itemView.findViewById(R.id.card_title);
                    cardDesc = itemView.findViewById(R.id.card_desc);
                    cardDate = itemView.findViewById(R.id.card_date);
                    cardPoster = itemView.findViewById(R.id.card_poster);
                }
            }
        };
        recyclerView.setAdapter(adapter);

        HandlerThread thread = new HandlerThread("TvObserver");
        thread.start();
        Handler handler = new Handler(thread.getLooper());

        TvObserver observer = new TvObserver(handler, getContext());
        getContext().getContentResolver().registerContentObserver(DatabaseContract.TvColumns.TV_CONTENT_URI, true, observer);

        if (savedInstanceState == null) {
            new LoadTvAsync(getContext(), this).execute();
        } else {
            ArrayList<Tvs> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
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
        private final WeakReference<LoadTvCallback> weakCallback;

        LoadTvAsync(Context context, LoadTvCallback callback) {
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
            return MappingHelper.mapTvCursorToArrayList(cursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Tvs> tvs) {
            super.onPostExecute(tvs);
            weakCallback.get().postExecute(tvs);
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
            new LoadTvAsync(context, (LoadTvCallback) context).execute();
        }
    }
}

interface LoadTvCallback {
    void preExecute();
    void postExecute(ArrayList<Tvs> listTvs);
}