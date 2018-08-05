package fsoft.training.movieapplication.listener;

import java.util.List;

import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;

/**
 * Created by mac on 10/30/17.
 */

public interface FavouriteReponsitoryListener {
    void resultAdd(String result);
    void resultCheckIsFavourite(boolean isFavourite);
    void resultDelete(String result);
    void resultListFavourite(List<MovieDto> movieDtoList);
}
