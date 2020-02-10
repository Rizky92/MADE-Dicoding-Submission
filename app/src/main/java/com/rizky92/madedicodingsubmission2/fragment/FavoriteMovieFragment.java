package com.rizky92.madedicodingsubmission2.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rizky92.madedicodingsubmission2.DetailActivity;
import com.rizky92.madedicodingsubmission2.R;
import com.rizky92.madedicodingsubmission2.adapter.ViewAdapter;
import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteMovieFragment extends Fragment {

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

            private Movies movies;

            class CardViewHolder extends RecyclerView.ViewHolder {
                TextView cardTitle, cardDesc, cardDate, cardProducer;
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
                this.movies = movies;
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
                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra("movieList", movies);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);

        // CONTENT OBSERVER HERE
    }
}

interface LoadMoviesCallback {
    void preExecute();
    void postExecute(ArrayList<Movies> listMovies);
}