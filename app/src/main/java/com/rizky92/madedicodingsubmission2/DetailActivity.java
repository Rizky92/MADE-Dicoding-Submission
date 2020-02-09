package com.rizky92.madedicodingsubmission2;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.rizky92.madedicodingsubmission2.helper.MappingHelper;
import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.rizky92.madedicodingsubmission2.pojo.Tvs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.MovieColumns.DATE;
import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.MovieColumns.IS_ADULT;
import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.MovieColumns.LANGUAGE;
import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.MovieColumns.POPULARITY;
import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.MovieColumns.TITLE;
import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.rizky92.madedicodingsubmission2.database.DatabaseContract.MovieColumns.VOTE_COUNT;

public class DetailActivity extends AppCompatActivity {

    // TODO: fix rating
    // TODO: baca ID genre
    // TODO: Query Optimization

    // THE PRINCIPLE
    /*
     * 1. API loaded ke viewModel
     * 2. Data loaded ke detail
     * 3.
     */

    public static final String EXTRA_MOVIES = "extra_movie";
    public static final String EXTRA_TVS = "extra_tvs";

    TextView tvTitle, tvDesc, tvDate, tvRating, tvLanguage, tvGenre, tvPop, tvAdult, dAdult;
    ImageView tvPoster;
    View layoutDetail;
    ProgressBar progressBar;
    Button btnFavorite;

    Movies movies, moviesDb;
    Tvs tvs;

    boolean isFavorite;
    Uri uriMovies, uriTvs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btnFavorite = findViewById(R.id.btn_favorite);

        tvTitle = findViewById(R.id.tv_title);
        tvDesc = findViewById(R.id.desc);
        tvDate = findViewById(R.id.date);
        tvRating = findViewById(R.id.rating);
        tvLanguage = findViewById(R.id.language);
        tvPop = findViewById(R.id.pop);
        tvAdult = findViewById(R.id.adult);
        dAdult = findViewById(R.id.tv_adult);

        tvPoster = findViewById(R.id.detail_poster);

        layoutDetail = findViewById(R.id.layout_detail);

        progressBar = findViewById(R.id.progress_circular_detail);
        showLoading(true);

        movies = getIntent().getParcelableExtra("movieList");
        tvs = getIntent().getParcelableExtra("tvList");

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

            uriMovies = Uri.parse(CONTENT_URI + "/" + movies.getId());
            if (uriMovies != null) {
                ArrayList<Movies> list = new ArrayList<>();
                if (uriMovies != null) {
                    Cursor cursor = getContentResolver().query(uriMovies, null, null, null, null);
                    if (cursor != null) {
                        list = MappingHelper.mapMovieCursorToArrayList(cursor);
                        cursor.close();
                    }
                }

                if (list.contains(movies.getId())) {
                    btnFavorite.setText(getResources().getString(R.string.remove_favorite));
                    isFavorite = true;
                } else {
                    btnFavorite.setText(getResources().getString(R.string.add_favorite));
                    isFavorite = false;
                }
            }

            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isFavorite) {
                        ContentValues values = new ContentValues();

                        values.put(MOVIE_ID, movies.getId());
                        values.put(TITLE, movies.getTitle());
                        values.put(DESCRIPTION, movies.getDesc());
                        values.put(DATE, movies.getDate());
                        values.put(POSTER_PATH, movies.getPosterPath());
                        values.put(LANGUAGE, movies.getLanguage());
                        values.put(POPULARITY, movies.getPopularity());
                        values.put(VOTE_AVERAGE, movies.getVoteAverage());
                        values.put(VOTE_COUNT, movies.getVoteCount());
                        values.put(IS_ADULT, movies.isAdult());

                        getContentResolver().insert(CONTENT_URI, values);
                        Toast.makeText(DetailActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        getContentResolver().delete(uriMovies, null, null);
                        Toast.makeText(DetailActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
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
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(tvTitle.getText());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            layoutDetail.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            layoutDetail.setVisibility(View.VISIBLE);
        }
    }
}