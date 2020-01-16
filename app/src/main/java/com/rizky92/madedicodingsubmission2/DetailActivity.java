package com.rizky92.madedicodingsubmission2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rizky92.madedicodingsubmission2.pojo.Movies;
import com.rizky92.madedicodingsubmission2.pojo.Tvs;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class DetailActivity extends AppCompatActivity {

    TextView tvTitle, tvDesc, tvDate, tvRating, tvDirector, tvLength, tvPlatform, dPlatform, pDirector, pLength, pDate, tvAsterisk;
    ImageView ivPoster;
    Button btnTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tv_title);
        tvDesc = findViewById(R.id.desc);
        tvDate = findViewById(R.id.date);
        tvRating = findViewById(R.id.rating);
        tvDirector = findViewById(R.id.director);
        tvLength = findViewById(R.id.length);
        tvPlatform = findViewById(R.id.platform);
        tvAsterisk = findViewById(R.id.tv_asterisk);

        ivPoster = findViewById(R.id.detail_poster);
        btnTrailer = findViewById(R.id.btn_trailer);

        dPlatform = findViewById(R.id.tv_platform);

        pDate = findViewById(R.id.tv_date);
        pDirector = findViewById(R.id.tv_director);
        pLength = findViewById(R.id.tv_length);

        final Movies movies = getIntent().getParcelableExtra("movieList");
        final Tvs tvs = getIntent().getParcelableExtra("tvList");

        if (movies != null) {
            tvTitle.setText(movies.getTitle());
            tvDesc.setText(movies.getDesc());
            tvDate.setText(movies.getDate());
            tvRating.setText(movies.getRating());
            tvDirector.setText(movies.getDirector());
            tvLength.setText(movies.getLength());

            Picasso.get().load(movies.getPoster()).into(ivPoster);

            btnTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent yt = new Intent(Intent.ACTION_VIEW, Uri.parse(movies.getTrailer()));
                    startActivity(yt);
                }
            });

        } else if (tvs != null) {
            dPlatform.setVisibility(View.VISIBLE);
            tvPlatform.setVisibility(View.VISIBLE);

            pDirector.setText(getResources().getString(R.string.produced));
            pLength.setText(getResources().getString(R.string.episode));
            pDate.setText(getResources().getString(R.string.aired));

            String seasons = getResources().getQuantityString(R.plurals.seasons, tvs.getSeasons(), tvs.getSeasons());
            String episodes = getResources().getQuantityString(R.plurals.episodes, tvs.getTotal(), tvs.getTotal());
            String title = null;

            if (tvs.getTitle().contains("*")) {
                tvAsterisk.setVisibility(View.VISIBLE);
                title = tvs.getTitle().substring(0, tvs.getTitle().length() - 1);
            } else {
                tvAsterisk.setVisibility(View.GONE);
                title = tvs.getTitle();
            }

            tvTitle.setText(title);
            tvDesc.setText(tvs.getDesc());
            tvDate.setText(tvs.getDate());
            tvRating.setText(tvs.getRating());
            tvDirector.setText(tvs.getProducers());
            tvLength.setText(String.format("%s, %s", seasons, episodes));
            tvPlatform.setText(tvs.getPlatform());
            Picasso.get().load(tvs.getPoster()).into(ivPoster);

            btnTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent yt = new Intent(Intent.ACTION_VIEW, Uri.parse(tvs.getTrailer()));
                    startActivity(yt);
                }
            });
        }
    }
}
