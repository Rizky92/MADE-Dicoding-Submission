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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizky92.madedicodingsubmission2.DetailActivity;
import com.rizky92.madedicodingsubmission2.R;
import com.rizky92.madedicodingsubmission2.adapter.MovieAdapter;
import com.rizky92.madedicodingsubmission2.adapter.ViewAdapter;
import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.rizky92.madedicodingsubmission2.viewModels.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private MovieAdapter adapter;
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

        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);

        final MovieViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        viewModel.setListItems();

        viewModel.getListItems().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> movies) {
                if (movies != null) {
                    adapter.setList(movies);
                }
            }
        });
    }
}

