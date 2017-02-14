package com.example.werek.themoviedb;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.werek.themoviedb.model.Movie;
import com.example.werek.themoviedb.model.MoviesList;
import com.example.werek.themoviedb.util.MovieDbApi;
import com.example.werek.themoviedb.util.Preferences;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieDetailsListener {
    public static final String MOVIE_EXTRA = BuildConfig.APPLICATION_ID + "movieItem";
    public static final String MOVIES_LIST = BuildConfig.APPLICATION_ID + "moviesList";
    private MovieAdapter mMovieAdapter;
    @BindView(R.id.rv_poster_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_error)
    TextView mError;
    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setupGrid();

        restoreState(savedInstanceState);
    }

    void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIES_LIST) ) {
            MoviesList list =  savedInstanceState.getParcelable(MOVIES_LIST);
            mMovieAdapter.setMovieList(list);
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
        new LoadMoviesTask().execute(sorting);
    }

    void showError(int stringResource) {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoading.setVisibility(View.INVISIBLE);
        mError.setText(stringResource);
        mError.setVisibility(View.VISIBLE);
    }

    void showLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.INVISIBLE);
        mLoading.setVisibility(View.VISIBLE);
    }

    void showResults() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mError.setVisibility(View.INVISIBLE);
        mLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIES_LIST, mMovieAdapter.getMovieList());
    }

    void setupGrid() {
        mMovieAdapter = new MovieAdapter(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    @Override
    public void onMovieDetails(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MOVIE_EXTRA, movie);
        startActivity(intent);
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     * <p>
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     * <p>
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     * <p>
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     * <p>
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    boolean isInternetAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    class LoadMoviesTask extends AsyncTask<String, Void, MoviesList> {
        private final String TAG = LoadMoviesTask.class.getName();

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            showLoading();
            mMovieAdapter.setMovieList(null);
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected MoviesList doInBackground(String... params) {
            String sorting = params[0];
            MovieDbApi api = new MovieDbApi();
            api.setApiKey(BuildConfig.MOVIE_DB_API_KEY);
            String language = Locale.getDefault().toString();
            Log.d(TAG, "language used for TMDB: " + language);
            api.setLanguage(language);
            MoviesList movies;
            switch (sorting) {
                case Preferences.TOP_RATED:
                    movies = api.topRated();
                    break;
                case Preferences.POPULAR:
                default:
                    movies = api.popular();
                    break;
            }
            return movies;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param moviesList The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(MoviesList moviesList) {
            if (moviesList != null) {
                Log.d(TAG, "got response with " + moviesList.getResults().size() + " movies");
                mMovieAdapter.setMovieList(moviesList);
                showResults();
            } else {
                Log.d(TAG, "got empty result response");
                int message = isInternetAvailable() ? R.string.error_no_results : R.string.error_no_connection;
                showError(message);
            }
        }
    }
}
