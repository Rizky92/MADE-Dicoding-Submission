package com.rizky92.favoritemoviesapp.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rizky92.favoritemoviesapp.R;
import com.rizky92.favoritemoviesapp.fragment.MovieFragment;
import com.rizky92.favoritemoviesapp.fragment.TvFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private final Context context;

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_movies,
            R.string.tab_tv
    };

    public SectionPagerAdapter(FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MovieFragment();

            case 1:
                return new TvFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }
}
