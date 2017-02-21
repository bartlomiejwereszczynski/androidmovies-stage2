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
import com.example.werek.themoviedb.adapter.VideosAdapter;
import com.example.werek.themoviedb.databinding.FragmentMovieVideosBinding;
import com.example.werek.themoviedb.model.VideosList;
import com.example.werek.themoviedb.task.ApiLoaderInterface;

/**
 * Created by werek on 21.02.2017.
 */

public class MovieVideosFragment extends Fragment implements ApiLoaderInterface<VideosList>{
    FragmentMovieVideosBinding mBinding;
    VideosAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_videos, container, false);
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter =  new VideosAdapter();
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
    public void onResponse(@Nullable VideosList response) {
        mAdapter.setVideoList(response);
    }
}
