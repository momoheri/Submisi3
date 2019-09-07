package com.momo.moviecatalogapi.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.momo.moviecatalogapi.Fragment.FragmentMovie;
import com.momo.moviecatalogapi.Fragment.TvShowFragment;
import com.momo.moviecatalogapi.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fm = null;

        switch (i) {
            case 0:
                fm = new FragmentMovie();
                break;
            case 1:
                fm = new TvShowFragment();
        }
        return fm;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int i) {
        return context.getResources().getString(TAB_TITLES[i]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
