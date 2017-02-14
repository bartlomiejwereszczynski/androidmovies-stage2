package com.example.werek.themoviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.werek.themoviedb.model.Movie;
import com.squareup.picasso.Picasso;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = DetailsActivity.class.getName();
    protected Movie mMovie;
    @BindView(R.id.iv_poster)
    ImageView mPoster;
    @BindView(R.id.iv_backdrop)
    ImageView mBackdrop;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_synopsis)
    TextView mSynopsis;
    @BindView(R.id.tv_release_date)
    TextView mReleaseDate;
    @BindView(R.id.tv_user_rating)
    TextView mRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.MOVIE_EXTRA)) {
            Log.d(TAG, "got movie in incoming intent");
            mMovie = intent.getParcelableExtra(MainActivity.MOVIE_EXTRA);
        }

        if (mMovie != null) {
            Log.d(TAG, "Movie details: " + mMovie.toString());
            loadMovie(mMovie);
        }
    }

    private void loadMovie(Movie movie) {
        mTitle.setText(movie.getOriginalTitle());
        mSynopsis.setText(movie.getOverview());
        mReleaseDate.setText(movie.getReleaseDate());
        mRating.setText(String.valueOf(movie.getVoteAverage()));
        URL backdrop = movie.getBackdropUrl();
        URL poster = movie.getPosterUrl();
        if (poster != null) {
            Picasso.with(this)
                    .load(poster.toString())
                    .error(R.drawable.ic_chain_broken)
                    .placeholder(R.drawable.ic_image)
                    .into(mPoster);
        }
        if (backdrop != null) {
            Picasso.with(this)
                    .load(backdrop.toString())
                    .error(R.drawable.ic_chain_broken)
                    .placeholder(R.drawable.ic_image)
                    .into(mBackdrop);
        }
        setTitle(movie.getTitle());
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
