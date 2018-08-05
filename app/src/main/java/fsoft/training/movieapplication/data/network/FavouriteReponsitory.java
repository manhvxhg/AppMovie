package fsoft.training.movieapplication.data.network;

import android.content.Context;

import fsoft.training.movieapplication.application.utils.AppSingleton;
import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.data.sqlite.FavouriteHelper;
import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;
import fsoft.training.movieapplication.presentation.FavouritePresenter;

/**
 * Created by mac on 10/30/17.
 */

public class FavouriteReponsitory {
    private static final String ACTION_HOGEHOGE = "com.android.broadcast.FLAG_COUNT_FAVOURITE";
    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private FavouriteHelper mFavouriteHelper;
    private Context mContext;
    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////

    public FavouriteReponsitory(Context mContext) {
        this.mContext = mContext;
        this.mFavouriteHelper = new FavouriteHelper(mContext);
    }

    public void addFavourite(MovieDto movieDto, FavouritePresenter favouritePresenter) {
        boolean isSuccess = mFavouriteHelper.addFavourite(movieDto);
        if (isSuccess) {
            favouritePresenter.resultAdd(Constants.ADD_FAVOURITE_SUCCESS);
//            AppSingleton.sendDataCount(mContext, mFavouriteHelper);
        } else {
            favouritePresenter.resultAdd(Constants.ADD_FAVOURITE_ERROR);
        }

    }

    public void deleteFavourite(MovieDto movieDto, FavouritePresenter favouritePresenter) {
        boolean isSuccess = mFavouriteHelper.deteleFavourite(movieDto);
        if (isSuccess) {
            favouritePresenter.resultDelete(Constants.DELETE_SUCCESS);

        } else {
            favouritePresenter.resultDelete(Constants.DELETE_ERROR);
        }
    }

    public void checkIsFavourite(int movieId, FavouritePresenter favouritePresenter) {
        favouritePresenter.resultCheckIsFavourite(mFavouriteHelper.checkIsFavourite(movieId));
    }

    public void getAllFavourite(FavouritePresenter favouritePresenter) {
        favouritePresenter.resultListFavourite(mFavouriteHelper.getAllFavourite());
        AppSingleton.sendDataCount(mContext, mFavouriteHelper);
    }
    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////
}
