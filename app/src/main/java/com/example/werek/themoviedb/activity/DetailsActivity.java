package com.example.werek.themoviedb.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.werek.themoviedb.R;
import com.example.werek.themoviedb.model.Movie;
import com.example.werek.themoviedb.model.contentprovider.MovieContract;
import com.example.werek.themoviedb.model.FavouriteManager;
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
    private MenuItem mFavouriteMenu;

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

        if (mMovie == null) {
            throw new NullPointerException("Need to have movie object in intent");
        }
        Log.d(TAG, "Movie details: " + mMovie.toString());
        loadMovie(mMovie);
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

    private void switchFavourite(String favState) {
        if (mFavouriteMenu == null) {
            Log.d(TAG, "switchFavourite: menu option is empty");
            return;
        }
        switch (favState) {
            case Movie.FAV_YES:
                mFavouriteMenu.setIcon(android.R.drawable.star_big_on);
                mFavouriteMenu.setTitle(R.string.text_favourite_yes);
                break;
            case Movie.FAV_UNKNOWN:
                new AsyncTask<Movie, Void, String>() {
                    @Override
                    protected String doInBackground(Movie... params) {
                        Movie movie = params[0];
                        Uri movieUri = MovieContract.FavouriteEntry.buildEntryUri(movie.getId());
                        Cursor cursor = DetailsActivity.this.getContentResolver().query(movieUri, null, null, null, null);
                        String result = cursor.getCount() > 0 ? Movie.FAV_YES : Movie.FAV_NO;
                        cursor.close();
                        return result;
                    }

                    @Override
                    protected void onPostExecute(String isFavourite) {
                        mMovie.isFavourite = isFavourite;
                        if (!isFavourite.equals(Movie.FAV_UNKNOWN)) {
                            Log.d(TAG, "onPostExecute: movie favourite status is " + isFavourite);
                            switchFavourite(isFavourite);
                        }
                    }
                }.execute(mMovie);
            case Movie.FAV_NO:
            default:
                mFavouriteMenu.setIcon(android.R.drawable.star_big_off);
                mFavouriteMenu.setTitle(R.string.text_favourite_no);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        mFavouriteMenu = menu.findItem(R.id.action_favourite);
        // as soon as menu is created check what to set
        switchFavourite(mMovie.isFavourite);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_favourite:
                FavouriteManager fm = new FavouriteManager(this);
                if (mMovie.isFavourite.equals(Movie.FAV_YES)) {
                    mMovie.isFavourite = Movie.FAV_NO;
                    fm.removeFavourite(mMovie);
                } else {
                    mMovie.isFavourite = Movie.FAV_YES;
                    fm.storeFavourite(mMovie);
                }
                switchFavourite(mMovie.isFavourite);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
