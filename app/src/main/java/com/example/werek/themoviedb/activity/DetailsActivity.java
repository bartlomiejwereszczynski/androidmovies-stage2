package com.example.werek.themoviedb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.werek.themoviedb.R;
import com.example.werek.themoviedb.fragment.MovieFragment;
import com.example.werek.themoviedb.model.Movie;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = DetailsActivity.class.getName();
    protected Movie mMovie;
    private MovieFragment mDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.MOVIE_EXTRA)) {
            Log.d(TAG, "got movie in incoming intent");
            mMovie = intent.getParcelableExtra(MainActivity.MOVIE_EXTRA);
            Log.d(TAG, "Movie details: " + mMovie.toString());
            MovieFragment movieFragment = new MovieFragment();
            Bundle arguments = new Bundle();
            arguments.putParcelable(MainActivity.MOVIE_EXTRA,mMovie);
            movieFragment.setArguments(arguments);
            Log.d(TAG, "onCreate: replacing fragment");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_placeholder, movieFragment)
                    .commit();
        } else {
            throw new NullPointerException("Need to have movie object in intent");
        }
    }

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
