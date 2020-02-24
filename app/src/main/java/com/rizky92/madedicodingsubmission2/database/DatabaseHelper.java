package com.rizky92.madedicodingsubmission2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorites";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_MOVIES_TABLE = String.format(
                    "CREATE TABLE %s(" +
                            "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "%s INTEGER NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s REAL NOT NULL," +
                            "%s INTEGER NOT NULL," +
                            "%s INTEGER NOT NULL" +
                    ")",
            DatabaseContract.MOVIES_TABLE,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.MOVIE_ID,
            DatabaseContract.MovieColumns.TITLE,
            DatabaseContract.MovieColumns.DESCRIPTION,
            DatabaseContract.MovieColumns.DATE,
            DatabaseContract.MovieColumns.POSTER_PATH,
            DatabaseContract.MovieColumns.LANGUAGE,
            DatabaseContract.MovieColumns.POPULARITY,
            DatabaseContract.MovieColumns.VOTE_AVERAGE,
            DatabaseContract.MovieColumns.VOTE_COUNT,
            DatabaseContract.MovieColumns.IS_ADULT
    );
    private static final String CREATE_TVS_TABLE = String.format(
                    "CREATE TABLE %s(" +
                            "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "%s INTEGER NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s TEXT NOT NULL," +
                            "%s REAL NOT NULL," +
                            "%s INTEGER NOT NULL" +
                    ")",
            DatabaseContract.TVS_TABLE,
            DatabaseContract.TvColumns._ID,
            DatabaseContract.TvColumns.TV_ID,
            DatabaseContract.TvColumns.TITLE,
            DatabaseContract.TvColumns.DESCRIPTION,
            DatabaseContract.TvColumns.DATE,
            DatabaseContract.TvColumns.POSTER_PATH,
            DatabaseContract.TvColumns.LANGUAGE,
            DatabaseContract.TvColumns.POPULARITY,
            DatabaseContract.TvColumns.VOTE_AVERAGE,
            DatabaseContract.TvColumns.VOTE_COUNT
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sql) {
        sql.execSQL(CREATE_MOVIES_TABLE);
        sql.execSQL(CREATE_TVS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sql, int oldVer, int newVer) {
        sql.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.MOVIES_TABLE);
        sql.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TVS_TABLE);
        onCreate(sql);
    }
}
