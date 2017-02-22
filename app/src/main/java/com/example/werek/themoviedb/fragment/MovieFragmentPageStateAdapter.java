package com.example.werek.themoviedb.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.ViewGroup;

import com.example.werek.themoviedb.model.Movie;

/**
 * Created by werek on 21.02.2017.
 */

public class MovieFragmentPageStateAdapter extends FragmentStatePagerAdapter implements MovieLoaderInterface {
    public static final String TAG = MovieFragmentPageStateAdapter.class.getSimpleName();
    public static final int DETAILS = 0;
    public static final int REVIEWS = 1;
    public static final int VIDEOS = 2;
    private Movie mMovie;
    Bundle mArguments;

    ArrayMap<Integer, Fragment> mFragmentMap = new ArrayMap<>();

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
        if (mFragmentMap.containsKey(position)) {
            Log.d(TAG, "getItem: returning fragment stored at position " + position + ": " + mFragmentMap.get(position).getClass().getName());
            return mFragmentMap.get(position);
        }
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
        Log.d(TAG, "getItem: created fragment " + fragment.getClass().getName());
        if (mArguments != null) {
            Log.i(TAG, "getItem: passing arguments to " + fragment.getClass().getName());
            fragment.setArguments(mArguments);
        }
        mFragmentMap.put(position, fragment);
        return fragment;
    }

    public void setArguments(Bundle mArguments) {
        this.mArguments = mArguments;
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

    /**
     * receives movie to load
     *
     * @param movie movie to load
     */
    @Override
    public void onLoadMovie(Movie movie) {
        mMovie = movie;
        for (Fragment fm : mFragmentMap.values()) {
            if (fm instanceof MovieLoaderInterface) {
                Log.i(TAG, "onLoadMovie: passed movie to " + fm.getClass().getName());
                ((MovieLoaderInterface) fm).onLoadMovie(mMovie);
            }
        }
    }
}
