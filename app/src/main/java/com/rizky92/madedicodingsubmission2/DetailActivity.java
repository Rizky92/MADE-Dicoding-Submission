package com.rizky92.madedicodingsubmission2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.rizky92.madedicodingsubmission2.pojo.Tvs;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    //TODO: fix rating
    //TODO: baca ID genre

    TextView tvTitle, tvDesc, tvDate, tvRating, tvLanguage, tvGenre, tvPop, tvAdult, dAdult;
    ImageView tvPoster;
    View layoutDetail;
    ProgressBar progressBar;

    Movies movies;
    Tvs tvs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
