package com.rizky92.madedicodingsubmission2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.rizky92.madedicodingsubmission2.adapter.SectionPagerAdapter;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = findViewById(R.id.pager);
        TabLayout tab = findViewById(R.id.tabMode);

        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(), this);

        pager.setAdapter(sectionPagerAdapter);
        tab.setupWithViewPager(pager);

        getSupportActionBar().setElevation(0);
    }
}
