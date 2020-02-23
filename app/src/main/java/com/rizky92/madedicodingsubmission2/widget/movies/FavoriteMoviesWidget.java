package com.rizky92.madedicodingsubmission2.widget.movies;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.rizky92.madedicodingsubmission2.R;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteMoviesWidget extends AppWidgetProvider {

    private static final String TOAST_ACTION = "com.rizky92.madedicodingsubmission2.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.rizky92.madedicodingsubmission2.EXTRA_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_movies);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty);

        Intent toast = new Intent(context, FavoriteMoviesWidget.class);
        toast.setAction(FavoriteMoviesWidget.TOAST_ACTION);
        toast.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        toast.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        //intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pendintToast = PendingIntent.getBroadcast(context, 0, toast, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, pendintToast);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                int index = intent.getIntExtra(EXTRA_ITEM, 0);
                Toast.makeText(context, "what?", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

