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
import com.rizky92.madedicodingsubmission2.pojo.Tvs;
import com.rizky92.madedicodingsubmission2.viewModels.TvViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TvFragment extends Fragment {

    private ProgressBar progressBar;
    private ViewAdapter<Tvs> viewAdapter;
    private ArrayList<Tvs> list = new ArrayList<>();

    public TvFragment() {
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

        viewAdapter = new ViewAdapter<Tvs>(view.getContext(), list) {
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
        recyclerView.setAdapter(viewAdapter);

        final TvViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvViewModel.class);
        viewModel.setListItems();
        showLoading(true);

        viewModel.getListItems().observe(getViewLifecycleOwner(), new Observer<ArrayList<Tvs>>() {
            @Override
            public void onChanged(ArrayList<Tvs> tvs) {
                if (tvs != null) {
                    viewAdapter.addItems(tvs);
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

