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
import com.rizky92.madedicodingsubmission2.pojo.Tvs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TvFragment extends Fragment {

    ViewAdapter<Tvs> viewAdapter;
    private RecyclerView recyclerView;
    private Tvs tvs;
    private ArrayList<Tvs> list = new ArrayList<>();
    private String[] title, desc, date, rating, trailer, platform, producers, episodes;
    private int[] seasons, total;
    private TypedArray poster;

    public TvFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycle_viewers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        viewAdapter = new ViewAdapter<Tvs>(view.getContext(), list) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
                return new CardViewHolder(view);
            }

            @Override
            public void onBindItem(RecyclerView.ViewHolder viewHolder, Tvs model) {
                final Tvs tvs = model;

                final CardViewHolder holder = (CardViewHolder) viewHolder;

                String text = null;
                if (tvs.getTitle().contains("*")) {
                    text = tvs.getTitle().substring(0, tvs.getTitle().length() - 1);
                } else {
                    text = tvs.getTitle();
                }

                holder.cardTitle.setText(text);
                holder.cardDesc.setText(tvs.getDesc());
                holder.cardProducer.setText(String.format("%s%s", view.getResources().getString(R.string.produced), tvs.getProducers()));
                holder.cardDate.setText(String.format("%s%s", view.getResources().getString(R.string.released), tvs.getDate()));
                Picasso.get()
                        .load(tvs.getPoster())
                        .resize(202, 300)
                        .centerCrop()
                        .into(holder.cardPoster);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra("tvList", tvs);
                        startActivity(intent);
                    }
                });
            }

            class CardViewHolder extends RecyclerView.ViewHolder {
                TextView cardTitle, cardDesc, cardDate, cardProducer;
                ImageView cardPoster;

                public CardViewHolder(@NonNull View itemView) {
                    super(itemView);

                    cardTitle = itemView.findViewById(R.id.card_title);
                    cardDesc = itemView.findViewById(R.id.card_desc);
                    cardDate = itemView.findViewById(R.id.card_date);
                    cardProducer = itemView.findViewById(R.id.card_director);
                    cardPoster = itemView.findViewById(R.id.card_poster);
                }
            }
        };
        recyclerView.setAdapter(viewAdapter);

        prepare();
        add();
    }

    private void prepare() {
        title = getResources().getStringArray(R.array.tv_title);
        desc = getResources().getStringArray(R.array.tv_desc);
        date = getResources().getStringArray(R.array.tv_date);
        rating = getResources().getStringArray(R.array.tv_rating);
        trailer = getResources().getStringArray(R.array.tv_trailer);
        platform = getResources().getStringArray(R.array.tv_platform);
        producers = getResources().getStringArray(R.array.tv_producers);
        seasons = getResources().getIntArray(R.array.tv_seasons);
        total = getResources().getIntArray(R.array.tv_total);
        poster = getResources().obtainTypedArray(R.array.tv_poster);
    }

    private void add() {
        for (int i = 0; i < title.length; i++) {
            tvs = new Tvs();

            tvs.setTitle(title[i]);
            tvs.setDesc(desc[i]);
            tvs.setDate(date[i]);
            tvs.setRating(rating[i]);
            tvs.setTrailer(trailer[i]);
            tvs.setPlatform(platform[i]);
            tvs.setProducers(producers[i]);
            tvs.setSeasons(seasons[i]);
            tvs.setTotal(total[i]);
            tvs.setPoster(poster.getResourceId(i, -1));

            list.add(tvs);
        }
        viewAdapter.addItems(list);
    }
}

