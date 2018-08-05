package fsoft.training.movieapplication.presentation;

import android.content.Context;

import java.util.List;

import fsoft.training.movieapplication.data.network.FavouriteReponsitory;
import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;
import fsoft.training.movieapplication.listener.FavouritePresenterListener;
import fsoft.training.movieapplication.listener.FavouriteReponsitoryListener;

/**
 * Created by mac on 10/30/17.
 */

public class FavouritePresenter implements FavouriteReponsitoryListener {
    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private FavouritePresenterListener mFavouritePresenterListener;

    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Contructor
     * @param mFavouritePresenterListener
     */
    public FavouritePresenter(FavouritePresenterListener mFavouritePresenterListener) {
        this.mFavouritePresenterListener = mFavouritePresenterListener;
    }

    /**
     * Request addFavourite to database to Reponsitory in MVP pattern
     * @param movieDto
     * @param context
     */
    public void requestAddFavourite(MovieDto movieDto, Context context) {
        new FavouriteReponsitory(context).addFavourite(movieDto, this);
    }
    /**
     * Request delete favourite to database to Reponsitory in MVP pattern
     * @param movieDto
     * @param context
     */
    public void requestDeleteFavourite(MovieDto movieDto, Context context) {
        new FavouriteReponsitory(context).deleteFavourite(movieDto, this);
    }
    /**
     * Request check exist favourite in database to Reponsitory in MVP pattern
     * @param movieDto
     * @param context
     */
    public void requestCheckIsFavourite(int movieId, Context context) {
        new FavouriteReponsitory(context).checkIsFavourite(movieId, this);

    }

    /**
     * request get list favourite in database to Reponsitory
     * @param context
     */
    public void requestListFavourite(Context context) {
        new FavouriteReponsitory(context).getAllFavourite(this);
    }

    @Override
    public void resultAdd(String result) {
        mFavouritePresenterListener.resultAdd(result);
    }

    @Override
    public void resultCheckIsFavourite(boolean isFavourite) {
        mFavouritePresenterListener.resultCheckIsFavourite(isFavourite);
    }

    @Override
    public void resultDelete(String result) {
        mFavouritePresenterListener.resultDelete(result);
    }

    @Override
    public void resultListFavourite(List<MovieDto> movieDtoList) {
        mFavouritePresenterListener.resultListFavourite(movieDtoList);
    }

    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////


}
