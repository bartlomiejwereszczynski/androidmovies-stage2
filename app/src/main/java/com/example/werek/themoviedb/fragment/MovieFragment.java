package com.example.werek.themoviedb.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.werek.themoviedb.R;
import com.example.werek.themoviedb.activity.MainActivity;
import com.example.werek.themoviedb.databinding.FragmentMovieBinding;
import com.example.werek.themoviedb.model.Movie;

/**
 * Created by werek on 21.02.2017.
 */

public class MovieFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    public static final String TAG = MovieFragment.class.getSimpleName();
    FragmentMovieBinding mBinding;
    MovieFragmentPageStateAdapter mPageAdapter;
    Movie mMovie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false);
        if (getArguments() != null && getArguments().containsKey(MainActivity.MOVIE_EXTRA)) {
            Log.d(TAG, "onCreateView: loading movie from arguments");
            mMovie = getArguments().getParcelable(MainActivity.MOVIE_EXTRA);
        } else if (savedInstanceState != null && savedInstanceState.containsKey(MainActivity.MOVIE_EXTRA)) {
            Log.d(TAG, "onCreateView: loading movie from saved instance state");
            mMovie = savedInstanceState.getParcelable(MainActivity.MOVIE_EXTRA);
        }
        setupTabs();
        return mBinding.getRoot();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.i(TAG, "onAttachFragment: attached fragment "+childFragment.getClass().getName());
    }

    private void setupTabs() {
        TabLayout tabs = mBinding.tabLayout;
        tabs.addTab(tabs.newTab().setText(R.string.tab_details));
        tabs.addTab(tabs.newTab().setText(R.string.tab_reviews));
        tabs.addTab(tabs.newTab().setText(R.string.tab_videos));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.addOnTabSelectedListener(this);

        mPageAdapter = new MovieFragmentPageStateAdapter(getChildFragmentManager());
        if (getArguments() != null) {
            mPageAdapter.setArguments(getArguments());
        } else if (mMovie != null){
            Bundle arguments = new Bundle();
            arguments.putParcelable(MainActivity.MOVIE_EXTRA, mMovie);
            mPageAdapter.setArguments(arguments);
        }
        mBinding.viewPager.setAdapter(mPageAdapter);
        mBinding.viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabs)
        );

    }

    /**
     * Called when a tab enters the selected state.
     *
     * @param tab The tab that was selected
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.i(TAG, "onTabSelected: tab name is "+tab.getText());
        mBinding.viewPager.setCurrentItem(tab.getPosition());
    }

    /**
     * Called when a tab exits the selected state.
     *
     * @param tab The tab that was unselected
     */
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    /**
     * Called when a tab that is already selected is chosen again by the user. Some applications
     * may use this action to return to the top level of a category.
     *
     * @param tab The tab that was reselected.
     */
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
