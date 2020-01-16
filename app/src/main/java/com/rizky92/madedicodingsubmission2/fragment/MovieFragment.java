package com.rizky92.madedicodingsubmission2.fragment;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizky92.madedicodingsubmission2.DetailActivity;
import com.rizky92.madedicodingsubmission2.R;
import com.rizky92.madedicodingsubmission2.adapter.ViewAdapter;
import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private ViewAdapter<Movies> viewAdapter;
    private String[] title, desc, date, length, rating, trailer, director;
    private TypedArray poster;
    private ArrayList<Movies> list = new ArrayList<>();

    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_viewers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        viewAdapter = new ViewAdapter<Movies>(view.getContext(), list) {

            @Override
            public RecyclerView.ViewHolder setViewHolder(final ViewGroup parent) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
                return new CardViewHolder(view);
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder viewHolder, Movies model) {
                final Movies movies = model;

                final CardViewHolder holder = (CardViewHolder) viewHolder;

                holder.cardTitle.setText(movies.getTitle());
                holder.cardDesc.setText(movies.getDesc());
                holder.cardDirector.setText(String.format("%s%s", view.getResources().getString(R.string.directed), movies.getDirector()));
                holder.cardDate.setText(String.format("%s%s", view.getResources().getString(R.string.released), movies.getDate()));
                Picasso.get()
                        .load(movies.getPoster())
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

            class CardViewHolder extends RecyclerView.ViewHolder {
                TextView cardTitle, cardDesc, cardDate, cardDirector;
                ImageView cardPoster;

                CardViewHolder(@NonNull View itemView) {
                    super(itemView);
                    cardTitle = itemView.findViewById(R.id.card_title);
                    cardDesc = itemView.findViewById(R.id.card_desc);
                    cardDate = itemView.findViewById(R.id.card_date);
                    cardDirector = itemView.findViewById(R.id.card_director);
                    cardPoster = itemView.findViewById(R.id.card_poster);
                }
            }
        };
        recyclerView.setAdapter(viewAdapter);

        prepare();
        add();
    }

    private void prepare() {
        title = getResources().getStringArray(R.array.mv_title);
        desc = getResources().getStringArray(R.array.mv_desc);
        date = getResources().getStringArray(R.array.mv_date);
        length = getResources().getStringArray(R.array.mv_length);
        rating = getResources().getStringArray(R.array.mv_rating);
        trailer = getResources().getStringArray(R.array.mv_trailer);
        director = getResources().getStringArray(R.array.mv_director);
        poster = getResources().obtainTypedArray(R.array.mv_poster);
    }

    private void add() {
        for (int i = 0; i < title.length; i++) {
            Movies movies = new Movies();

            movies.setTitle(title[i]);
            movies.setDesc(desc[i]);
            movies.setDate(date[i]);
            movies.setLength(length[i]);
            movies.setRating(rating[i]);
            movies.setTrailer(trailer[i]);
            movies.setDirector(director[i]);
            movies.setPoster(poster.getResourceId(i, -1));

            list.add(movies);
        }
        viewAdapter.addItems(list);
    }
}

