package com.example.werek.themoviedb.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.werek.themoviedb.R;
import com.example.werek.themoviedb.model.Video;
import com.example.werek.themoviedb.model.VideosList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by werek on 21.02.2017.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {
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

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_name)
        TextView mName;
        @BindView(R.id.tv_size)
        TextView mSize;
        Context mContext;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        void loadVideo(Video video) {
            mName.setText(video.name);
            mSize.setText(String.valueOf(video.size) + "p");
        }

        @Override
        public void onClick(View v) {
            mVideoList.results.get(getAdapterPosition()).openVideo(mContext);
        }
    }
}
