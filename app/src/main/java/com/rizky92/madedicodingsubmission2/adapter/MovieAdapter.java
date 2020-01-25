package com.rizky92.madedicodingsubmission2.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizky92.madedicodingsubmission2.R;
import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<Movies> list = new ArrayList<>();

    public void setList(ArrayList<Movies> items) {
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView cardTitle, cardDesc, cardDate;
        ImageView cardPoster;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardTitle = itemView.findViewById(R.id.card_title);
            cardDesc = itemView.findViewById(R.id.card_desc);
            cardDate = itemView.findViewById(R.id.card_date);
            cardPoster = itemView.findViewById(R.id.card_poster);
        }

        void bind(Movies movies) {
            cardTitle.setText(movies.getTitle());
            cardDesc.setText(movies.getDesc());
            cardDate.setText(movies.getDate());
            Picasso.get()
                    .load(movies.getPoster())
                    .resize(202, 300)
                    .centerCrop()
                    .into(cardPoster);
        }
    }
}
