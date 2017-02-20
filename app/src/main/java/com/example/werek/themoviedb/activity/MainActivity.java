package com.example.werek.themoviedb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.werek.themoviedb.AppApplication;
import com.example.werek.themoviedb.BuildConfig;
import com.example.werek.themoviedb.R;
import com.example.werek.themoviedb.adapter.MovieAdapter;
import com.example.werek.themoviedb.adapter.MovieAdapterPaginated;
import com.example.werek.themoviedb.fragment.MovieDetailsFragment;
import com.example.werek.themoviedb.model.Movie;
import com.example.werek.themoviedb.model.MoviesList;
import com.example.werek.themoviedb.task.AsyncMovieTask;
import com.example.werek.themoviedb.util.EndlessRecyclerViewScrollListener;
import com.example.werek.themoviedb.util.Preferences;

import butterknife.BindBool;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieDetailsListener, AsyncMovieTask.MovieLoaderListener {
    public static final String TAG = MainActivity.class.getName();
    public static final String MOVIE_EXTRA = BuildConfig.APPLICATION_ID + "movieItem";
    public static final String MOVIES_LIST = BuildConfig.APPLICATION_ID + "moviesList";
    private MovieAdapter mMovieAdapter;
    @BindView(R.id.rv_poster_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_error)
    TextView mError;
    @BindView(R.id.pb_loading)
    ProgressBar mLoading;
    @Nullable
    @BindView(R.id.main_list)
    LinearLayout mTwoPaneLayout;
    @BindInt(R.integer.grid_columns)
    int mGridColumns;
    @BindBool(R.bool.use_fragment)
    boolean mUseFragment;
    private EndlessRecyclerViewScrollListener mEndlessScroll;
    private MovieDetailsFragment mDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setupGrid();

        restoreState(savedInstanceState);
    }

    void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIES_LIST)) {
            MoviesList list = savedInstanceState.getParcelable(MOVIES_LIST);
            mMovieAdapter.setMovieList(list);
            setListTitle(list.getType());
            showResults();
        } else {
            loadMovies(null);
        }
    }

    void loadMovies(@Nullable String sorting) {
        if (BuildConfig.MOVIE_DB_API_KEY.equals("missingKeyFile")) {
            showError(R.string.error_key);
            return;
        }
        if (sorting == null) {
            sorting = Preferences.getSorting(this);
        }
        new AsyncMovieTask(this).execute(sorting);
    }

    void showError(int stringResource) {
        if (mUseFragment) {
            mTwoPaneLayout.setVisibility(View.INVISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
        mLoading.setVisibility(View.INVISIBLE);
        mError.setText(stringResource);
        mError.setVisibility(View.VISIBLE);
    }

    void showLoading() {
        if (mUseFragment) {
            mTwoPaneLayout.setVisibility(View.INVISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
        mError.setVisibility(View.INVISIBLE);
        mLoading.setVisibility(View.VISIBLE);
    }

    void showResults() {
        if (mUseFragment) {
            mTwoPaneLayout.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        mError.setVisibility(View.INVISIBLE);
        mLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIES_LIST, mMovieAdapter.getMovieList());
    }

    void setupGrid() {
        if (mUseFragment) {
            mDetailsFragment = (MovieDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.movie_details_fragment);
        }
        mMovieAdapter = new MovieAdapterPaginated(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, mGridColumns);
        if (mMovieAdapter instanceof EndlessRecyclerViewScrollListener.LoadMore) {
            mEndlessScroll = new EndlessRecyclerViewScrollListener(layoutManager, (EndlessRecyclerViewScrollListener.LoadMore) mMovieAdapter);
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);
        mRecyclerView.addOnScrollListener(mEndlessScroll);

    }

    @Override
    public void onMovieDetails(Movie movie) {
        if (mUseFragment) {
            mDetailsFragment.loadMovie(movie);
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(MOVIE_EXTRA, movie);
            startActivity(intent);
        }
    }

    void setListTitle(String type) {
        switch (type) {
            case Preferences.POPULAR:
                setTitle(getString(R.string.app_name) + " - " + getString(R.string.sort_popular));
                break;
            case Preferences.TOP_RATED:
                setTitle(getString(R.string.app_name) + " - " + getString(R.string.sort_top_rated));
                break;
            default:
                setTitle(R.string.app_name);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_popular:
                Preferences.setSorting(this, Preferences.POPULAR);
                loadMovies(Preferences.POPULAR);
                return true;
            case R.id.action_sort_top_rated:
                Preferences.setSorting(this, Preferences.TOP_RATED);
                loadMovies(Preferences.TOP_RATED);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPreExecute() {
        showLoading();
        mMovieAdapter.setMovieList(null);
        mEndlessScroll.resetState();
    }

    @Override
    public void onMovieListReady(@Nullable MoviesList moviesList) {
        if (moviesList != null) {
            Log.d(TAG, "got response with " + moviesList.getResults().size() + " movies");
            mMovieAdapter.setMovieList(moviesList);
            setListTitle(moviesList.getType());
            if (mUseFragment) {
                // load first movie at start
                mDetailsFragment.loadMovie(moviesList.getResults().get(0));
            }
            showResults();
        } else {
            Log.d(TAG, "got empty result response");
            int message = AppApplication.app.isInternetAvailable() ? R.string.error_no_results : R.string.error_no_connection;
            showError(message);
        }
    }
}
