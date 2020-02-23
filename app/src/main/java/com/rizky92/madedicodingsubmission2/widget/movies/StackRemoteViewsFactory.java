package com.rizky92.madedicodingsubmission2.widget.movies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rizky92.madedicodingsubmission2.R;
import com.rizky92.madedicodingsubmission2.database.DatabaseContract;
import com.rizky92.madedicodingsubmission2.helper.MappingHelper;
import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> items = new ArrayList<>();
    private ArrayList<Movies> list = new ArrayList<>();
    private Context context;
    private Bitmap bitmap;

    StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        Cursor cursor = context.getContentResolver().query(DatabaseContract.MovieColumns.MOVIE_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            list = MappingHelper.mapMovieCursorToArrayList(cursor);
        }
    }

    @Override
    public void onDataSetChanged() {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                bitmap = null;
                try {
                    bitmap = Picasso.get()
                            .load(list.get(i).getPosterPath())
                            .get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                items.add(bitmap);
            }
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_items);
        remoteViews.setImageViewBitmap(R.id.image_view_widget, items.get(i));

        Bundle extras = new Bundle();
        extras.putInt(FavoriteMoviesWidget.EXTRA_ITEM, i);
        Intent fill = new Intent();
        fill.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.image_view_widget, fill);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
