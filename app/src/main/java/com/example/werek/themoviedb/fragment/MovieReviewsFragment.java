package com.example.werek.themoviedb.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.werek.themoviedb.BuildConfig;
import com.example.werek.themoviedb.R;
import com.example.werek.themoviedb.adapter.ReviewsAdapter;
import com.example.werek.themoviedb.databinding.FragmentMovieReviewsBinding;
import com.example.werek.themoviedb.model.Movie;
import com.example.werek.themoviedb.model.ReviewList;
import com.example.werek.themoviedb.task.ApiLoaderInterface;
import com.example.werek.themoviedb.task.AsyncReviewsTask;

public class MovieReviewsFragment extends Fragment implements ApiLoaderInterface<ReviewList>, MovieLoaderInterface {
    public static final String REVIEWS_LIST = BuildConfig.APPLICATION_ID + "reviewsList";
    FragmentMovieReviewsBinding mBinding;
    ReviewsAdapter mAdapter;
    ReviewList mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_reviews, container, false);
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ReviewsAdapter();
        recoverList(savedInstanceState);
        if (mList != null) {
            mAdapter.setReviewList(mList);
        }
        mBinding.rvList.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    private void recoverList(@Nullable Bundle savedInstanceState) {
        if (mList != null) {
            // list was set before view was created
            return;
        }
        if (getArguments() != null && getArguments().containsKey(REVIEWS_LIST)) {
            mList = getArguments().getParcelable(REVIEWS_LIST);
        } else if (savedInstanceState != null && savedInstanceState.containsKey(REVIEWS_LIST)) {
            mList = savedInstanceState.getParcelable(REVIEWS_LIST);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mList != null) {
            outState.putParcelable(REVIEWS_LIST, mList);
        }
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onResponse(@Nullable ReviewList response) {
        mList = response;
        if (mAdapter != null) {
            mAdapter.setReviewList(response);
        }
    }

    /**
     * receives movie to load
     *
     * @param movie movie to load
     */
    @Override
    public void onLoadMovie(Movie movie) {
        new AsyncReviewsTask(this).execute(movie.getId());
    }
}
