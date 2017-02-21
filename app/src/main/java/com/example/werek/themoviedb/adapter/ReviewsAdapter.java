package com.example.werek.themoviedb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.werek.themoviedb.R;
import com.example.werek.themoviedb.model.Review;
import com.example.werek.themoviedb.model.ReviewList;

import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>{
    ReviewList mReviewList;
    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.loadReview(mReviewList.results.get(position));
    }

    @Override
    public int getItemCount() {
        return mReviewList != null ? mReviewList.results.size() : 0;
    }

    public ReviewList getReviewList() {
        return mReviewList;
    }

    public void setReviewList(ReviewList mReviewList) {
        this.mReviewList = mReviewList;
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void loadReview(Review review) {

        }
    }
}
