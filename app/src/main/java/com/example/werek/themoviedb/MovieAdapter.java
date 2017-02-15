package com.example.werek.themoviedb;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.werek.themoviedb.model.Movie;
import com.example.werek.themoviedb.model.MoviesList;
import com.example.werek.themoviedb.task.AsyncMovieTask;
import com.example.werek.themoviedb.util.EndlessRecyclerViewScrollListener;
import com.example.werek.themoviedb.util.MovieDbApi;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by werek on 22.01.2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>
        implements EndlessRecyclerViewScrollListener.LoadMore, AsyncMovieTask.MovieLoaderListener {
    private static final String TAG = MovieAdapter.class.getName();
    private MoviesList mMovieList;
    private MovieDetailsListener mMovieDetails;



    interface MovieDetailsListener {
        void onMovieDetails(Movie movie);
    }

    public MovieAdapter(@Nullable MovieDetailsListener movieDetails) {
        mMovieDetails = movieDetails;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View posterItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poster_item, parent, false);
        return new MovieViewHolder(posterItem);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.loadMovie(mMovieList.getResults().get(position));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mMovieList == null ? 0 : mMovieList.getResults().size();
    }

    /**
     * used to load movie list into adapter
     *
     * @param movies list of movies to display
     */
    public void setMovieList(@Nullable MoviesList movies) {
        mMovieList = movies;
        notifyDataSetChanged();
    }

    public MovieAdapter addMovie(Movie movie) {
        int newPosition = mMovieList.getResults().size();
        mMovieList.getResults().add(movie);
        notifyItemInserted(newPosition);
        return this;
    }

    public MovieAdapter appendMoviesList(MoviesList moviesList) {
        mMovieList.setPage(moviesList.getPage());
        for (Movie movie : moviesList.getResults()) {
            addMovie(movie);
        }
        return this;
    }

    public MoviesList getMovieList() {
        return mMovieList;
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        Log.d(TAG, "onLoadMore: page("+page+"), totalItemCount("+totalItemsCount+")");
        String sorting = mMovieList != null ? mMovieList.getType() : null;
        int nextPage = mMovieList != null ? mMovieList.getPage() + 1 : 1;
        Log.d(TAG, "onLoadMore: sorting("+sorting+"), nextPage("+nextPage+")");
        new AsyncMovieTask(this,page).execute(sorting);
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void loadedMovies(@Nullable MoviesList moviesList) {
        if (moviesList != null) {
            appendMoviesList(moviesList);
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final String TAG = MovieViewHolder.class.getName();
        @BindView(R.id.iv_movie_poster)
        ImageView mPosterImage;

        /**
         * @param itemView parent view object for this particular viewholder
         */
        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        /**
         * called to load movie details into MovieViewHolder
         *
         * @param movie movie form which to take information
         */
        void loadMovie(Movie movie) {
            Log.d(TAG, "Loading movie poster for: " + movie.getTitle());
            String posterUrl = movie.getPosterUrl(MovieDbApi.POSTER_WIDTH_342).toString();
            Log.d(TAG, "poster url: " + posterUrl);
            Picasso.with(mPosterImage.getContext())
                    .load(posterUrl)
                    .error(R.drawable.ic_chain_broken)
                    .placeholder(R.drawable.ic_image)
                    .into(mPosterImage);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (mMovieDetails != null) {
                Log.d(TAG, "sending movie to listener");
                mMovieDetails.onMovieDetails(mMovieList.getResults().get(getAdapterPosition()));
            }
        }
    }
}
