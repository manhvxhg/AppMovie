package fsoft.training.movieapplication.listener;

import java.util.List;

import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;

/**
 * Created by mac on 10/25/17.
 */

public interface MovieListPresenterListener {
    void resultFromPresenter(List<MovieDto> movieDtoList);
}
