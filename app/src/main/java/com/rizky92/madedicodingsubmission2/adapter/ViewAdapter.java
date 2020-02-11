package com.rizky92.madedicodingsubmission2.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Reusable RecyclerView
// TODO: Reusable fragment?

public abstract class ViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<T> items;

    protected ViewAdapter(Context context, ArrayList<T> items) {
        this.items = items;
    }

    public void setList(ArrayList<T> list) {
        this.items.clear();
        this.items.addAll(list);
        notifyDataSetChanged();
    }

    protected abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);

    protected abstract void onBindItem(RecyclerView.ViewHolder holder, T t);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return setViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindItem(holder, items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(ArrayList<T> savedItems) {
        items = savedItems;
        this.notifyDataSetChanged();
    }

    public T getItem(int position) {
        return items.get(position);
    }
}