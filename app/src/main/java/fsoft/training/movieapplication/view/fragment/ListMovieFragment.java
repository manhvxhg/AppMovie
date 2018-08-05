package fsoft.training.movieapplication.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fsoft.training.movieapplication.R;
import fsoft.training.movieapplication.application.utils.AppSingleton;
import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;
import fsoft.training.movieapplication.listener.MovieListPresenterListener;
import fsoft.training.movieapplication.listener.OnItemClickListener;
import fsoft.training.movieapplication.presentation.CastAndCrewPresenter;
import fsoft.training.movieapplication.presentation.MovieListPresenter;
import fsoft.training.movieapplication.view.activity.BaseActivity;
import fsoft.training.movieapplication.view.adapter.MoviesAdapter;

/**
 * Created by mac on 10/24/17.
 */

public class ListMovieFragment extends Fragment implements MovieListPresenterListener
        , SharedPreferences.OnSharedPreferenceChangeListener, SwipeRefreshLayout.OnRefreshListener {
    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private MovieListPresenter mMovieListPresenter;
    private int mPagerNumber = 1;
    private MoviesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private boolean mTypeLayout;
    private SharedPreferences mSharedPreferences;
    private List<MovieDto> mMovieDtos;
    private CastAndCrewPresenter mCastAndCrewPresenter;
    private final String TAG = getClass().getSimpleName();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SharedPreferences mSharedPreferencesSetting;
    private int mFilterList;
    private int mRateSetting;
    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        mMovieListPresenter = new MovieListPresenter(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        mAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                fragmentJump(mMovieDtos.get(position));

//                Log.d("ListMovieFragment", "onClick: "+position);
            }
        });
//        mSharedPreferencesSetting.registerOnSharedPreferenceChangeListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_list_movie, container, false);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.list_movie_recycler);
        if (AppSingleton.checkConnection(getContext())) {
            mMovieListPresenter.requestToReponsitory(getContext(), mPagerNumber);
        } else {
            Toast.makeText(getContext(), "No internet!", Toast.LENGTH_SHORT).show();
        }
        mSwipeRefreshLayout = rootview.findViewById(R.id.movie_list_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSharedPreferencesSetting = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return rootview;
    }

    @Override
    public void onPause() {
        super.onPause();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
//        mSharedPreferencesSetting.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Call to api in reponsitory
     */
    public void requestMovieListApiReponsitory(int pageNumber) {

    }

    @Override
    public void resultFromPresenter(final List<MovieDto> movieDtoList) {
        if (movieDtoList != null) {
            this.mMovieDtos = movieDtoList;
            restoringData();
            if (movieDtoList !=  null) {
                mAdapter = new MoviesAdapter(getContext(), movieDtoList, mRecyclerView, mTypeLayout);
                mRecyclerView.setAdapter(mAdapter);
            }

        } else {
            Toast.makeText(getContext(), "No internet access!", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser & isAdded()) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        restoringData();
        getSetting();
        List<MovieDto> dtoList = new ArrayList<>();
        for (MovieDto movieDto : mMovieDtos) {
            if (movieDto.voteAverage > (double)mRateSetting) {
                dtoList.add(movieDto);
            }
        }
        mAdapter = new MoviesAdapter(getContext(), dtoList, mRecyclerView, mTypeLayout);
        refreshFragment();
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onRefresh() {
        mMovieListPresenter.requestToReponsitory(getContext(), mPagerNumber);
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                fragmentJump(mMovieDtos.get(position));
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Refresher fragment when click changelayout
     */
    private void refreshFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(ListMovieFragment.this);
        fragmentTransaction.attach(ListMovieFragment.this);
        fragmentTransaction.commit();
    }

    /**
     * Restore data from sharedPreferences
     */
    private void restoringData() {
        mSharedPreferences = getActivity().getSharedPreferences(Constants.FILE_NAME_TYPE_LAYOUT, Context.MODE_PRIVATE);
        mTypeLayout = mSharedPreferences.getBoolean(Constants.TYPE_LAYOUT_KEY, true);
    }

    /**
     * Jump to movieDetail screen
     *
     * @param mItemSelected
     */
    private void fragmentJump(MovieDto mItemSelected) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt(Constants.MOVIES_ID_KEY, mItemSelected.id);
        mBundle.putSerializable(Constants.MOVIES_OBJECT_BUNDLE, mItemSelected);
        movieDetailFragment.setArguments(mBundle);
        AppSingleton.titleActionBar = mItemSelected.title;
        switchContent(R.id.movies_fragment, movieDetailFragment, mItemSelected.title);
    }

    /**
     * switch content to movie detail screen
     *
     * @param id
     * @param fragment
     * @param title
     */
    public void switchContent(int id, Fragment fragment, String title) {
        if (getContext() == null)
            return;
        if (getContext() instanceof BaseActivity) {
            BaseActivity listMovieActivity = (BaseActivity) getContext();
            listMovieActivity.switchContent(id, fragment, title);
        }

    }

    private void getSetting() {
        mSharedPreferencesSetting = PreferenceManager.getDefaultSharedPreferences(getContext());
        mFilterList = Integer.parseInt(mSharedPreferencesSetting.getString("category_key", "1"));
        mRateSetting = mSharedPreferencesSetting.getInt("rate_key", 0);
        Log.d(TAG, "getSetting: " + mSharedPreferencesSetting.getInt("rate_key", 0));
    }

    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////


}
