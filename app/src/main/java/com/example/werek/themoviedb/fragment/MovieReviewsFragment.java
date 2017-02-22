package com.example.werek.themoviedb.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.werek.themoviedb.BuildConfig;
import com.example.werek.themoviedb.R;
import com.example.werek.themoviedb.activity.MainActivity;
import com.example.werek.themoviedb.adapter.ReviewsAdapter;
import com.example.werek.themoviedb.databinding.FragmentMovieReviewsBinding;
import com.example.werek.themoviedb.model.Movie;
import com.example.werek.themoviedb.model.ReviewList;
import com.example.werek.themoviedb.task.ApiLoaderInterface;
import com.example.werek.themoviedb.task.AsyncReviewsTask;

public class MovieReviewsFragment extends Fragment implements ApiLoaderInterface<ReviewList>, MovieLoaderInterface {
    public static final String TAG = MovieReviewsFragment.class.getSimpleName();
    public static final String REVIEWS_LIST = BuildConfig.APPLICATION_ID + "reviewsList";
    FragmentMovieReviewsBinding mBinding;
    ReviewsAdapter mAdapter;
    ReviewList mList;
    Movie mMovie;
    private boolean isLoading=false;

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
        if (getArguments() != null && getArguments().containsKey(MainActivity.MOVIE_EXTRA)) {
            mMovie = getArguments().getParcelable(MainActivity.MOVIE_EXTRA);
            onLoadMovie(mMovie);
        } else if (savedInstanceState != null && savedInstanceState.containsKey(REVIEWS_LIST)) {
            Log.d(TAG, "recoverList: recovered list from saved instance bundle");
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
        isLoading = true;
        showLoading();
    }

    @Override
    public void onResponse(@Nullable ReviewList response) {
        Log.d(TAG, "onResponse: got response " + response);
        mList = response;
        isLoading = false;
        attemptToLoadList();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: attempting to load list");
        attemptToLoadList();
    }

    private void attemptToLoadList() {
        if (isLoading) {
            return;
        }
        if (mAdapter != null) {
            if (mList != null && !mList.equals(mAdapter.getReviewList())) {
                Log.d(TAG, "attemptToLoadList: loading list " + mList);
                mAdapter.setReviewList(mList);
                showResults();
            }
        }
        if (mList == null || (mList != null && mList.results.size() < 1)) {
            showError(R.string.error_no_results);
        }
    }

    /**
     * receives movie to load
     *
     * @param movie movie to load
     */
    @Override
    public void onLoadMovie(Movie movie) {
        Log.i(TAG, "onLoadMovie: loading reviews for movie " + movie);
        new AsyncReviewsTask(this).execute(movie.getId());
    }

    void showError(int stringResource) {
        mBinding.rvList.setVisibility(View.INVISIBLE);
        mBinding.pbLoading.setVisibility(View.INVISIBLE);
        mBinding.tvError.setText(stringResource);
        mBinding.tvError.setVisibility(View.VISIBLE);
    }

    void showLoading() {
        mBinding.rvList.setVisibility(View.INVISIBLE);
        mBinding.pbLoading.setVisibility(View.VISIBLE);
        mBinding.tvError.setVisibility(View.INVISIBLE);
    }

    void showResults() {
        mBinding.rvList.setVisibility(View.VISIBLE);
        mBinding.pbLoading.setVisibility(View.INVISIBLE);
        mBinding.tvError.setVisibility(View.INVISIBLE);
    }
}
