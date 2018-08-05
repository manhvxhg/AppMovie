package fsoft.training.movieapplication.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fsoft.training.movieapplication.R;
import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;
import fsoft.training.movieapplication.listener.FavouritePresenterListener;
import fsoft.training.movieapplication.presentation.FavouritePresenter;
import fsoft.training.movieapplication.view.adapter.FavouriteAdapter;

/**
 * Created by mac on 10/24/17.
 */

public class FavouriteMovieFragment extends Fragment implements FavouritePresenterListener
        , SearchView.OnQueryTextListener {
    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private FavouritePresenter mFavouritePresenter;
    private List<MovieDto> mMovieDtoList;
    private FavouriteAdapter mAdapter;
    private RecyclerView mRecyclerView;

    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavouritePresenter = new FavouritePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
        setList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_favourite_movie, container, false);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.list_movie_favourite_recycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        setList();
        return rootview;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.change_view_actionbar).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(true);
    }

    /**
     * LISTENER RESULT ADD FROM REPONSITORY
     *
     * @param success
     */
    @Override
    public void resultAdd(String success) {

    }

    /**
     * LISTENER RESULT CHECK FAVOURITE
     *
     * @param isFavourite
     */
    @Override
    public void resultCheckIsFavourite(boolean isFavourite) {

    }

    /**
     * LISTENER RESULT DELETE
     *
     * @param result
     */
    @Override
    public void resultDelete(String result) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mMovieDtoList != null) {
            mFavouritePresenter.requestListFavourite(getContext());
        }
        if (isVisibleToUser) {
            mAdapter.setmMovieDtos(mMovieDtoList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void resultListFavourite(List<MovieDto> movieDtoList) {
        this.mMovieDtoList = movieDtoList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<MovieDto> filteredModelList = filter(mMovieDtoList, newText);
        mAdapter.setFilter(filteredModelList);
        return true;
    }

    /**
     * Set data to list favourite
     */
    public void setList() {
        mFavouritePresenter.requestListFavourite(getContext());
        mAdapter = new FavouriteAdapter(getContext(), mMovieDtoList);
        Intent intent = new Intent();
        intent.putExtra("count_favourite", mMovieDtoList.size());
        getContext().sendBroadcast(intent);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    /**
     * filter for search in Favourite screen
     * @param movieList
     * @param newText
     * @return
     */
    private List<MovieDto> filter(List<MovieDto> movieList, String newText) {
        newText = newText.toLowerCase();
        final List<MovieDto> filteredModelList = new ArrayList<>();
        for (MovieDto movie : movieList) {
            final String text = movie.title.toLowerCase();
            if (text.contains(newText)) {
                filteredModelList.add(movie);
            }
        }
        return filteredModelList;
    }
    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////


}
