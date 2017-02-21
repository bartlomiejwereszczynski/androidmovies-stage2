package com.example.werek.themoviedb.fragment;

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
        Log.i(TAG, "getItem: requested item at position " + position);
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
        if (mMovie != null) {
            Log.i(TAG, "getItem: passing movie to " + fragment.getClass().getName());
            ((MovieLoaderInterface) fragment).onLoadMovie(mMovie);
        }
        mFragmentMap.put(position, fragment);
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
        Log.i(TAG, "destroyItem: destroying item at position " + position);
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
