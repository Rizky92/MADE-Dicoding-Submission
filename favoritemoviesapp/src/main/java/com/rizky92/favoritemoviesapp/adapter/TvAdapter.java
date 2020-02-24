package com.rizky92.favoritemoviesapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizky92.favoritemoviesapp.DetailActivity;
import com.rizky92.favoritemoviesapp.R;
import com.rizky92.favoritemoviesapp.pojo.Tvs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.CardViewHolder> {

    private ArrayList<Tvs> list;

    public TvAdapter(ArrayList<Tvs> list) {
        this.list = list;
    }

    public void addItems(ArrayList<Tvs> savedItems) {
        list = savedItems;
        this.notifyDataSetChanged();
    }

    public void setList(ArrayList<Tvs> items) {
        this.list.clear();
        this.list.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvAdapter.CardViewHolder holder, int position) {
        final Tvs tvs = list.get(position);

        holder.cardTitle.setText(tvs.getTitle());
        holder.cardDesc.setText(tvs.getDesc());
        holder.cardDate.setText(String.format("%s%s", holder.itemView.getResources().getString(R.string.date), tvs.getDate()));
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
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        final TextView cardTitle;
        final TextView cardDesc;
        final TextView cardDate;
        final ImageView cardPoster;

        CardViewHolder(View itemView) {
            super(itemView);

            cardTitle = itemView.findViewById(R.id.card_title);
            cardDesc = itemView.findViewById(R.id.card_desc);
            cardDate = itemView.findViewById(R.id.card_date);
            cardPoster = itemView.findViewById(R.id.card_poster);
        }
    }
}
