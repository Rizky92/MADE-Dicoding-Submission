package com.rizky92.madedicodingsubmission2.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.rizky92.madedicodingsubmission2";
    private static final String SCHEME = "content";

    public static final String MOVIES_TABLE = "favorites_movie";
    public static final String TVS_TABLE = "favorite_tv";

    public static final class MovieColumns implements BaseColumns {
        public static final String MOVIE_ID = "movie_id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String DATE = "date";
        public static final String POSTER_PATH = "poster_path";
        public static final String LANGUAGE = "language";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String VOTE_COUNT = "vote_count";
        public static final String IS_ADULT = "adult";

        public static final Uri MOVIE_CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(MOVIES_TABLE)
                .build();
    }

    public static final class TvColumns implements BaseColumns {
        public static final String TV_ID = "tv_id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String DATE = "date";
        public static final String POSTER_PATH = "poster_path";
        public static final String LANGUAGE = "language";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String VOTE_COUNT = "vote_count";

        public static final Uri TV_CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TVS_TABLE)
                .build();
    }
}
