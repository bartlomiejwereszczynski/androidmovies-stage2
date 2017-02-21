package com.example.werek.themoviedb.fragment;

import android.content.Context;
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
import com.example.werek.themoviedb.adapter.VideosAdapter;
import com.example.werek.themoviedb.databinding.FragmentMovieVideosBinding;
import com.example.werek.themoviedb.model.Movie;
import com.example.werek.themoviedb.model.VideosList;
import com.example.werek.themoviedb.task.ApiLoaderInterface;
import com.example.werek.themoviedb.task.AsyncVideosTask;

public class MovieVideosFragment extends Fragment implements ApiLoaderInterface<VideosList>, MovieLoaderInterface {
    public static final String TAG = MovieVideosFragment.class.getSimpleName();
    public static final String VIDEOS_LIST = BuildConfig.APPLICATION_ID + "videosList";
    FragmentMovieVideosBinding mBinding;
    VideosAdapter mAdapter;
    VideosList mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_videos, container, false);
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new VideosAdapter();
        recoverList(savedInstanceState);
        if (mList != null) {
            mAdapter.setVideoList(mList);
        }
        mBinding.rvList.setAdapter(mAdapter);
        Log.i(TAG, "onCreateView: ");
        return mBinding.getRoot();
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: attached videos fragment to " + context.getClass().getName());
    }

    private void recoverList(@Nullable Bundle savedInstanceState) {
        if (mList != null) {
            // list was set before view was created
            return;
        }
        if (getArguments() != null && getArguments().containsKey(VIDEOS_LIST)) {
            mList = getArguments().getParcelable(VIDEOS_LIST);
        } else if (savedInstanceState != null && savedInstanceState.containsKey(VIDEOS_LIST)) {
            mList = savedInstanceState.getParcelable(VIDEOS_LIST);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mList != null) {
            outState.putParcelable(VIDEOS_LIST, mList);
        }
    }

    /**
     * executed right before
     */
    @Override
    public void onPreExecute() {

    }

    @Override
    public void onResponse(@Nullable VideosList response) {
        Log.d(TAG, "onResponse: got response " + response);
        mList = response;
        if (mAdapter != null) {
            mAdapter.setVideoList(response);
        }
    }

    /**
     * receives movie to load
     *
     * @param movie movie to load
     */
    @Override
    public void onLoadMovie(Movie movie) {
        Log.i(TAG, "onLoadMovie: loading videos for movie " + movie);
        new AsyncVideosTask(this).execute(movie.getId());
    }
}
