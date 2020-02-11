package com.rizky92.madedicodingsubmission2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.rizky92.madedicodingsubmission2.adapter.FavoritePagerAdapter;

import java.util.Objects;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tabMode);

        FavoritePagerAdapter favoritePagerAdapter = new FavoritePagerAdapter(getSupportFragmentManager(), this);

        viewPager.setAdapter(favoritePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
