package com.rizky92.madedicodingsubmission2.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.rizky92.madedicodingsubmission2.database.MovieHelper;

import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.AUTHORITY;
import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.MOVIES_TABLE;
import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.MovieColumns.MOVIE_CONTENT_URI;

public class FavoriteMovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private MovieHelper helper;

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(AUTHORITY, MOVIES_TABLE, MOVIE);
        matcher.addURI(AUTHORITY, MOVIES_TABLE + "/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        helper = MovieHelper.getInstance(getContext());
        helper.open();
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (matcher.match(uri)) {
            case MOVIE:
                cursor = helper.queryAll();
                break;

            case MOVIE_ID:
                cursor = helper.queryById(uri.getLastPathSegment());
                break;

            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;
        switch (matcher.match(uri)) {
            case MOVIE:
                added = helper.insert(values);
                break;

            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(MOVIE_CONTENT_URI, null);
        return Uri.parse(MOVIE_CONTENT_URI + "/" + added);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated;
        switch (matcher.match(uri)) {
            case MOVIE_ID:
                updated = helper.update(uri.getLastPathSegment(), values);
                break;

            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(MOVIE_CONTENT_URI, null);
        return updated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (matcher.match(uri)) {
            case MOVIE_ID:
                deleted = helper.deleteById(uri.getLastPathSegment());
                break;

            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(MOVIE_CONTENT_URI, null);
        return deleted;
    }
}
