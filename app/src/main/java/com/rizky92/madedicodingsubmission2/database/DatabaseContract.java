package com.rizky92.madedicodingsubmission2.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String MOVIE_AUTHORITY = "com.rizky92.madedicodingsubmission2movie";
    public static final String TV_AUTHORITY = "com.rizky92.madedicodingsubmission2tv";
    private static final String SCHEME = "content";

    public static final String MOVIES_TABLE = "favorites_movie";
    public static final String TVS_TABLE = "favorite_tv";

    public static final Uri MOVIE_CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(MOVIE_AUTHORITY)
            .appendPath(MOVIES_TABLE)
            .build();

    public static final Uri TV_CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(TV_AUTHORITY)
            .appendPath(TVS_TABLE)
            .build();
}
