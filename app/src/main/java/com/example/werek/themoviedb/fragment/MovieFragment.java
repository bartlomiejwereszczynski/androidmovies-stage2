package com.example.werek.themoviedb.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.werek.themoviedb.R;
import com.example.werek.themoviedb.databinding.FragmentMovieBinding;
import com.example.werek.themoviedb.model.Movie;

/**
 * Created by werek on 21.02.2017.
 */

public class MovieFragment extends Fragment implements TabLayout.OnTabSelectedListener, MovieLoaderInterface{
    FragmentMovieBinding mBinding;
    MovieFragmentPageStateAdapter mPageAdapter;
    Movie mMovie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false);
        setupTabs();
        return mBinding.getRoot();
    }

    private void setupTabs() {
        TabLayout tabs = mBinding.tabLayout;
        tabs.addTab(tabs.newTab().setText(R.string.tab_details));
        tabs.addTab(tabs.newTab().setText(R.string.tab_reviews));
        tabs.addTab(tabs.newTab().setText(R.string.tab_videos));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        mPageAdapter = new MovieFragmentPageStateAdapter(getChildFragmentManager());
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
        mBinding.viewPager.setCurrentItem(tab.getPosition());
        Fragment fm = mPageAdapter.getFragment(tab.getPosition());
        if (fm != null && fm instanceof MovieLoaderInterface) {
            ((MovieLoaderInterface) fm).onLoadMovie(mMovie);
        }
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

    /**
     * receives movie to load
     *
     * @param movie movie to load
     */
    @Override
    public void onLoadMovie(Movie movie) {
        mMovie = movie;
        int current = mBinding.viewPager.getCurrentItem();
        Fragment fm = mPageAdapter.getFragment(current);
        if (fm != null && fm instanceof MovieLoaderInterface) {
            ((MovieLoaderInterface) fm).onLoadMovie(mMovie);
        }
    }
}
