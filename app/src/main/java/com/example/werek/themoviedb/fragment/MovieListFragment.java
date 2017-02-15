package com.example.werek.themoviedb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.werek.themoviedb.BuildConfig;
import com.example.werek.themoviedb.MovieAdapter;
import com.example.werek.themoviedb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListFragment extends Fragment {
    public static final String MOVIE_EXTRA = BuildConfig.APPLICATION_ID + "movieItem";
    public static final String MOVIES_LIST = BuildConfig.APPLICATION_ID + "moviesList";
    private MovieAdapter mMovieAdapter;
    @BindView(R.id.rv_poster_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_error)
    TextView mError;
    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_list, container, false);
        ButterKnife.bind(this, view);
        return view;
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
}
