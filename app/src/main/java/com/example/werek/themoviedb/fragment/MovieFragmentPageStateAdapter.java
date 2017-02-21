package com.example.werek.themoviedb.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;
import android.view.ViewGroup;

/**
 * Created by werek on 21.02.2017.
 */

public class MovieFragmentPageStateAdapter extends FragmentStatePagerAdapter {
    public static final int DETAILS = 0;
    public static final int REVIEWS = 1;
    public static final int VIDEOS = 2;

    ArrayMap<Integer,Fragment> mFragmentMap = new ArrayMap<>();

    public MovieFragmentPageStateAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case DETAILS:
                fragment = new MovieDetailsFragment();
                break;
            case REVIEWS:
                fragment = new MovieReviewsFragment();
                break;
            case VIDEOS:
                fragment = new MovieVideosFragment();
                break;
            default:
                return null;
        }
        mFragmentMap.put(position,fragment);
        return fragment;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mFragmentMap.remove(position);
    }

    public Fragment getFragment(int position) {
        return mFragmentMap.get(position);
    }
}
