package com.rizky92.madedicodingsubmission2;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rizky92.madedicodingsubmission2.database.DatabaseContract;
import com.rizky92.madedicodingsubmission2.helper.MappingHelper;
import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.rizky92.madedicodingsubmission2.pojo.Tvs;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    // TODO: baca ID genre

    public static final String EXTRA_MOVIES = "extra_movie";
    public static final String EXTRA_TVS = "extra_tvs";

    private View layoutDetail;
    private ProgressBar progressBar;
    private Button btnFavorite;

    private Movies movies, favMovie;
    private Tvs tvs, favTv;

    private boolean isFavorite = false;
    private Uri uriMovies;
    private Uri uriTvs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btnFavorite = findViewById(R.id.btn_favorite);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvDesc = findViewById(R.id.desc);
        TextView tvDate = findViewById(R.id.date);
        TextView tvRating = findViewById(R.id.rating);
        TextView tvLanguage = findViewById(R.id.language);
        TextView tvPop = findViewById(R.id.pop);
        TextView tvAdult = findViewById(R.id.adult);
        TextView dAdult = findViewById(R.id.tv_adult);

        ImageView tvPoster = findViewById(R.id.detail_poster);

        layoutDetail = findViewById(R.id.layout_detail);

        progressBar = findViewById(R.id.progress_circular_detail);
        showLoading(true);

        movies = getIntent().getParcelableExtra(EXTRA_MOVIES);
        tvs = getIntent().getParcelableExtra(EXTRA_TVS);

        Cursor cursor;
        if (movies != null) {

            tvTitle.setText(movies.getTitle());
            tvDesc.setText(movies.getDesc());
            tvDate.setText(movies.getDate());
            tvRating.setText(String.format("%s%s\n%s%s", movies.getVoteAverage(), "/10", movies.getVoteCount(), getResources().getString(R.string.voters)));
            tvLanguage.setText(movies.getLanguage());
            tvPop.setText(movies.getPopularity());

            dAdult.setVisibility(View.VISIBLE);
            tvAdult.setVisibility(View.VISIBLE);
            if (movies.isAdult()) {
                tvAdult.setText(getResources().getString(R.string.yes));
            } else {
                tvAdult.setText(getResources().getString(R.string.no));
            }

            Picasso.get()
                    .load(movies.getPosterPath())
                    .into(tvPoster);

            showLoading(false);

            // check if movie id exist in database first.
            // if not then "add to favorites"

            uriMovies = Uri.parse(DatabaseContract.MovieColumns.MOVIE_CONTENT_URI + "/" + movies.getMovieId());
            if (uriMovies != null) {
                cursor = getContentResolver().query(uriMovies, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    favMovie = MappingHelper.mapMovieCursorToObject(cursor);
                    cursor.close();
                }
            }
            if (favMovie != null) {
                if (movies.getMovieId() == favMovie.getMovieId()) {
                    btnFavorite.setText(getResources().getString(R.string.remove_favorite));
                    isFavorite = true;
                }
            } else {
                btnFavorite.setText(getResources().getString(R.string.add_favorite));
            }

            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isFavorite) {
                        getContentResolver().delete(uriMovies, null, null);
                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.removed_favorites), Toast.LENGTH_SHORT).show();
                        btnFavorite.setText(getResources().getString(R.string.add_favorite));
                        isFavorite = false;
                    } else {
                        ContentValues values = new ContentValues();

                        values.put(DatabaseContract.MovieColumns.MOVIE_ID, movies.getMovieId());
                        values.put(DatabaseContract.MovieColumns.TITLE, movies.getTitle());
                        values.put(DatabaseContract.MovieColumns.DESCRIPTION, movies.getDesc());
                        values.put(DatabaseContract.MovieColumns.DATE, movies.getDate());
                        values.put(DatabaseContract.MovieColumns.POSTER_PATH, movies.getPosterPath());
                        values.put(DatabaseContract.MovieColumns.LANGUAGE, movies.getLanguage());
                        values.put(DatabaseContract.MovieColumns.POPULARITY, movies.getPopularity());
                        values.put(DatabaseContract.MovieColumns.VOTE_AVERAGE, movies.getVoteAverage());
                        values.put(DatabaseContract.MovieColumns.VOTE_COUNT, movies.getVoteCount());
                        values.put(DatabaseContract.MovieColumns.IS_ADULT, movies.isAdult());

                        getContentResolver().insert(DatabaseContract.MovieColumns.MOVIE_CONTENT_URI, values);
                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.added_favorites), Toast.LENGTH_SHORT).show();
                        btnFavorite.setText(getResources().getString(R.string.remove_favorite));
                        Log.d("Insert", String.valueOf(values));
                        isFavorite = true;
                    }
                }
            });

        } else if (tvs != null) {

            tvTitle.setText(tvs.getTitle());
            tvDesc.setText(tvs.getDesc());
            tvDate.setText(tvs.getDate());
            tvRating.setText(String.format("%s%s\n%s%s", tvs.getVoteAverage(), "/10", tvs.getVoteCount(), getResources().getString(R.string.voters)));
            tvLanguage.setText(tvs.getLanguage());
            tvPop.setText(tvs.getPopularity());
            tvAdult.setVisibility(View.GONE);
            dAdult.setVisibility(View.GONE);

            Picasso.get()
                    .load(tvs.getPosterPath())
                    .into(tvPoster);

            showLoading(false);

            uriTvs = Uri.parse(DatabaseContract.TvColumns.TV_CONTENT_URI + "/" + tvs.getTvId());
            if (uriTvs != null) {
                cursor = getContentResolver().query(uriTvs, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    favTv = MappingHelper.mapTvCursorToObject(cursor);
                    cursor.close();
                }
            }
            if (favTv != null) {
                if (tvs.getTvId() == favTv.getTvId()) {
                    btnFavorite.setText(getResources().getString(R.string.remove_favorite));
                    isFavorite = true;
                }
            } else {
                btnFavorite.setText(getResources().getString(R.string.add_favorite));
            }

            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isFavorite) {
                        getContentResolver().delete(uriTvs, null, null);
                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.removed_favorites), Toast.LENGTH_SHORT).show();
                        btnFavorite.setText(getResources().getString(R.string.add_favorite));
                        isFavorite = false;
                    } else {
                        ContentValues values = new ContentValues();

                        values.put(DatabaseContract.TvColumns.TV_ID, tvs.getTvId());
                        values.put(DatabaseContract.TvColumns.TITLE, tvs.getTitle());
                        values.put(DatabaseContract.TvColumns.DESCRIPTION, tvs.getDesc());
                        values.put(DatabaseContract.TvColumns.DATE, tvs.getDate());
                        values.put(DatabaseContract.TvColumns.POSTER_PATH, tvs.getPosterPath());
                        values.put(DatabaseContract.TvColumns.LANGUAGE, tvs.getLanguage());
                        values.put(DatabaseContract.TvColumns.POPULARITY, tvs.getPopularity());
                        values.put(DatabaseContract.TvColumns.VOTE_AVERAGE, tvs.getVoteAverage());
                        values.put(DatabaseContract.TvColumns.VOTE_COUNT, tvs.getVoteCount());

                        getContentResolver().insert(DatabaseContract.TvColumns.TV_CONTENT_URI, values);
                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.added_favorites), Toast.LENGTH_SHORT).show();
                        btnFavorite.setText(getResources().getString(R.string.remove_favorite));
                        Log.d("Insert", String.valueOf(values));
                        isFavorite = true;
                    }
                }
            });
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(tvTitle.getText());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            layoutDetail.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            layoutDetail.setVisibility(View.VISIBLE);
        }
    }
}