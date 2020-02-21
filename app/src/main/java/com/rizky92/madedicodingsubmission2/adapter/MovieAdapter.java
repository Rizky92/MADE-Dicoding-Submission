package com.rizky92.madedicodingsubmission2.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizky92.madedicodingsubmission2.DetailActivity;
import com.rizky92.madedicodingsubmission2.R;
import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CardViewHolder> {

    private ArrayList<Movies> list;

    public MovieAdapter(ArrayList<Movies> list) {
        this.list = list;
    }

    public void addItems(ArrayList<Movies> savedItems) {
        list = savedItems;
        this.notifyDataSetChanged();
    }

    public void setList(ArrayList<Movies> items) {
        this.list.clear();
        this.list.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.CardViewHolder holder, int position) {
        final Movies movies = list.get(position);

        holder.cardTitle.setText(movies.getTitle());
        holder.cardDesc.setText(movies.getDesc());
        holder.cardDate.setText(String.format("%s%s",holder.itemView.getResources().getString(R.string.date), movies.getDate()));
        Picasso.get()
                .load(movies.getPosterPath())
                .resize(202, 300)
                .centerCrop()
                .into(holder.cardPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIES, movies);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        TextView cardTitle;
        TextView cardDesc;
        TextView cardDate;
        ImageView cardPoster;

        CardViewHolder(View itemView) {
            super(itemView);

            cardTitle = itemView.findViewById(R.id.card_title);
            cardDesc = itemView.findViewById(R.id.card_desc);
            cardDate = itemView.findViewById(R.id.card_date);
            cardPoster = itemView.findViewById(R.id.card_poster);
        }
    }
}
