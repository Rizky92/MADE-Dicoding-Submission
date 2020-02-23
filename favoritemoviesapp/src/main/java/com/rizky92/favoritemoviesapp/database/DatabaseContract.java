package com.rizky92.favoritemoviesapp.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String MOVIE_AUTHORITY = "com.rizky92.madedicodingsubmission2movie";
    public static final String TV_AUTHORITY = "com.rizky92.madedicodingsubmission2tv";
    public static final String MOVIES_TABLE = "favorites_movie";
    public static final String TVS_TABLE = "favorite_tv";
    private static final String SCHEME = "content";

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
                .authority(MOVIE_AUTHORITY)
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
                .authority(TV_AUTHORITY)
                .appendPath(TVS_TABLE)
                .build();
    }
}
