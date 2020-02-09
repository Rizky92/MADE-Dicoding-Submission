package com.rizky92.madedicodingsubmission2.helper;

import android.database.Cursor;

import com.rizky92.madedicodingsubmission2.database.DatabaseContract;
import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.rizky92.madedicodingsubmission2.pojo.Tvs;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Movies> mapMovieCursorToArrayList(Cursor cursor) {
        ArrayList<Movies> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Movies movies = new Movies();

            movies.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID)));
            movies.setMovieId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.MOVIE_ID)));
            movies.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE)));
            movies.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.DESCRIPTION)));
            movies.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.DATE)));
            movies.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER_PATH)));
            movies.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POPULARITY)));
            movies.setVoteAverage(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_AVERAGE)));
            movies.setVoteCount(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_COUNT)));
            movies.setAdult(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.IS_ADULT)) != 0);

            list.add(movies);
        }
        return list;
    }

    public static ArrayList<Tvs> mapTvCursorToArrayList(Cursor cursor) {
        ArrayList<Tvs> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Tvs tvs = new Tvs();

            tvs.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns._ID)));
            tvs.setTvId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.TV_ID)));
            tvs.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.TITLE)));
            tvs.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.DESCRIPTION)));
            tvs.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.DATE)));
            tvs.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.POSTER_PATH)));
            tvs.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.POPULARITY)));
            tvs.setVoteAverage(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.VOTE_AVERAGE)));
            tvs.setVoteCount(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.VOTE_COUNT)));

            list.add(tvs);
        }
        return list;
    }

    public static Movies mapMovieCursorToObject(Cursor cursor) {
        cursor.moveToFirst();

        Movies movies = new Movies();

        movies.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID)));
        movies.setMovieId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.MOVIE_ID)));
        movies.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE)));
        movies.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.DESCRIPTION)));
        movies.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.DATE)));
        movies.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER_PATH)));
        movies.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POPULARITY)));
        movies.setVoteAverage(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_AVERAGE)));
        movies.setVoteCount(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_COUNT)));
        movies.setAdult(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.IS_ADULT)) != 0);

        return movies;
    }

    public static Tvs mapTvCursorToObject(Cursor cursor) {
        cursor.moveToFirst();

        Tvs tvs = new Tvs();

        tvs.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns._ID)));
        tvs.setTvId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.TV_ID)));
        tvs.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.TITLE)));
        tvs.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.DESCRIPTION)));
        tvs.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.DATE)));
        tvs.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.POSTER_PATH)));
        tvs.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.POPULARITY)));
        tvs.setVoteAverage(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.VOTE_AVERAGE)));
        tvs.setVoteCount(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.VOTE_COUNT)));

        return tvs;
    }
}
