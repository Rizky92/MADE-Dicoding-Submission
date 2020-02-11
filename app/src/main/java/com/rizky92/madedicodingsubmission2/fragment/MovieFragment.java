package com.rizky92.madedicodingsubmission2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizky92.madedicodingsubmission2.DetailActivity;
import com.rizky92.madedicodingsubmission2.R;
import com.rizky92.madedicodingsubmission2.adapter.ViewAdapter;
import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.rizky92.madedicodingsubmission2.viewModels.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private ProgressBar progressBar;
    private ViewAdapter<Movies> adapter;
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
        progressBar = view.findViewById(R.id.progress_circular_item);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_viewers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new ViewAdapter<Movies>(view.getContext(), list) {

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
                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_MOVIES, movies);
                        startActivity(intent);
                    }
                });
            }

            class CardViewHolder extends RecyclerView.ViewHolder {
                TextView cardTitle, cardDesc, cardDate;
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

        final MovieViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        viewModel.setListItems();
        showLoading(true);

        viewModel.getListItems().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> movies) {
                if (movies != null) {
                    adapter.addItems(movies);
                    showLoading(false);
                }
            }
        });
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}

