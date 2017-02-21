package com.example.werek.themoviedb.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.werek.themoviedb.R;
import com.example.werek.themoviedb.adapter.ReviewsAdapter;
import com.example.werek.themoviedb.databinding.FragmentMovieReviewsBinding;
import com.example.werek.themoviedb.model.ReviewList;
import com.example.werek.themoviedb.task.ApiLoaderInterface;

/**
 * Created by werek on 21.02.2017.
 */

public class MovieReviewsFragment extends Fragment implements ApiLoaderInterface<ReviewList> {
    FragmentMovieReviewsBinding mBinding;
    ReviewsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_reviews, container, false);
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ReviewsAdapter();
        mBinding.rvList.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    /**
     * executed right before
     */
    @Override
    public void onPreExecute() {

    }

    @Override
    public void onResponse(@Nullable ReviewList response) {
        mAdapter.setReviewList(response);
    }
}
