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
import android.util.Log;
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
import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteMovieFragment extends Fragment implements LoadMoviesCallback {

    private static final String EXTRA_STATE = "extra_state";
    ProgressBar progressBar;
    ViewAdapter<Movies> adapter;
    ArrayList<Movies> list = new ArrayList<>();

    public FavoriteMovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progress_circular_item);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_viewers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new ViewAdapter<Movies>(view.getContext(), list) {

            class CardViewHolder extends RecyclerView.ViewHolder {
                TextView cardTitle;
                TextView cardDesc;
                TextView cardDate;
                ImageView cardPoster;

                CardViewHolder(View view) {
                    super(view);

                    cardTitle = view.findViewById(R.id.card_title);
                    cardDesc = view.findViewById(R.id.card_desc);
                    cardDate = view.findViewById(R.id.card_date);
                    cardPoster = view.findViewById(R.id.card_poster);
                }
            }

            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
                return new CardViewHolder(view);
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder viewHolder, final Movies movies) {
                final CardViewHolder holder = (CardViewHolder) viewHolder;

                holder.cardTitle.setText(movies.getTitle());
                holder.cardDesc.setText(movies.getDesc());
                holder.cardDate.setText(String.format("%s%s", view.getResources().getString(R.string.date), movies.getDate()));
                Picasso.get()
                        .load(movies.getPosterPath())
                        .resize(202, 300)
                        .centerCrop()
                        .into(holder.cardPoster);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_MOVIES, movies);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);

        HandlerThread thread = new HandlerThread("MovieObserver");
        thread.start();
        Handler handler = new Handler(thread.getLooper());

        MovieObserver observer = new MovieObserver(handler, getContext());
        getContext().getContentResolver().registerContentObserver(DatabaseContract.MovieColumns.MOVIE_CONTENT_URI, true, observer);

        if (savedInstanceState == null) {
            new LoadMoviesAsync(getContext(), this).execute();
        } else {
            ArrayList<Movies> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
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