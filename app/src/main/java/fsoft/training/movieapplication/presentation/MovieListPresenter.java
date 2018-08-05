package fsoft.training.movieapplication.presentation;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.data.network.MovieListReponsitory;
import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;
import fsoft.training.movieapplication.domain.model.dto.listmovie.ResultJsonDto;
import fsoft.training.movieapplication.listener.MovieListPresenterListener;
import fsoft.training.movieapplication.listener.MovieListReponsitoryListener;

/**
 * Created by mac on 10/25/17.
 */

public class MovieListPresenter implements MovieListReponsitoryListener {
    private MovieListPresenterListener mMovieListPresenterListener;

    public MovieListPresenter(MovieListPresenterListener mMovieListPresenterListener) {
        this.mMovieListPresenterListener = mMovieListPresenterListener;
    }

    @Override
    public void resultFromReponsitory(String result) {
        if (Constants.ERROR.equals(result)) {
            mMovieListPresenterListener.resultFromPresenter(null);
        } else {
            List<MovieDto> movies = new ArrayList<>();
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            ResultJsonDto resultJson = gson.fromJson(result, ResultJsonDto.class);
            movies = resultJson.results;
            mMovieListPresenterListener.resultFromPresenter(movies);
        }

    }

    public void requestToReponsitory(Context context, int pageNumber) {
        new MovieListReponsitory().requestApi(context, pageNumber, this);
    }
}
