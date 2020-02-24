package com.rizky92.madedicodingsubmission2.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizky92.madedicodingsubmission2.R;
import com.rizky92.madedicodingsubmission2.adapter.MovieAdapter;
import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.rizky92.madedicodingsubmission2.viewModel.MovieViewModel;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private ProgressBar progressBar;
    private MovieAdapter adapter;
    private MovieViewModel viewModel;
    private final ArrayList<Movies> list = new ArrayList<>();
    private String searchQuery = "";

    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progress_circular_item);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_viewers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new MovieAdapter(list);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        viewModel.setListItems(searchQuery);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager manager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);

        if (manager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    searchQuery = s;
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    viewModel.setListItems(s);
                    return true;
                }
            });
        }
    }
}

