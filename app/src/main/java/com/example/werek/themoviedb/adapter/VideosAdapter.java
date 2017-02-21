package com.example.werek.themoviedb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.werek.themoviedb.R;
import com.example.werek.themoviedb.model.Video;
import com.example.werek.themoviedb.model.VideosList;

import butterknife.ButterKnife;

/**
 * Created by werek on 21.02.2017.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder>{
    VideosList mVideoList;
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.loadVideo(mVideoList.results.get(position));
    }

    @Override
    public int getItemCount() {
        return mVideoList != null ? mVideoList.results.size() : 0;
    }

    public VideosList getVideoList() {
        return mVideoList;
    }

    public void setVideoList(VideosList mVideoList) {
        this.mVideoList = mVideoList;
        notifyDataSetChanged();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void loadVideo(Video review) {

        }
    }
}
