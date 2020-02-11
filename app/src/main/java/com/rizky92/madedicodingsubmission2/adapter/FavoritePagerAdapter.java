package com.rizky92.madedicodingsubmission2.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rizky92.madedicodingsubmission2.R;
import com.rizky92.madedicodingsubmission2.fragment.FavoriteMovieFragment;
import com.rizky92.madedicodingsubmission2.fragment.FavoriteTvFragment;

public class FavoritePagerAdapter extends FragmentPagerAdapter {

    private final Context context;

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.fav_tab_movies,
            R.string.fav_tab_tv
    };

    public FavoritePagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FavoriteMovieFragment();
                break;

            case 1:
                fragment = new FavoriteTvFragment();
                break;
        }
        return fragment;
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
