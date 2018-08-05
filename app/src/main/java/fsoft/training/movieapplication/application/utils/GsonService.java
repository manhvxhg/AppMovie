package fsoft.training.movieapplication.application.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;
import fsoft.training.movieapplication.domain.model.dto.listmovie.ResultJsonDto;
import fsoft.training.movieapplication.domain.model.dto.moviedetail.CastDto;
import fsoft.training.movieapplication.domain.model.dto.moviedetail.CrewDto;
import fsoft.training.movieapplication.domain.model.dto.moviedetail.ListCastAndCrewDto;
import fsoft.training.movieapplication.domain.model.dto.moviedetail.MovieDetailDto;

/**
 * Created by ManhND16 on 9/27/2017.
 */

public class GsonService {
    private List<CastDto> mCastList;
    private List<CrewDto> mCrewList;
    private List<MovieDto> mMovieList;
    private MovieDetailDto mMovieDetail;
    public List<CastDto> convertToListCast(String json) {
        mCastList = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ListCastAndCrewDto listCastAndCrew = gson.fromJson(json, ListCastAndCrewDto.class);
        mCastList = listCastAndCrew.castDto;
        return mCastList;
    }
    public List<CrewDto> convertToListCrew(String json) {
        mCrewList = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ListCastAndCrewDto listCastAndCrew = gson.fromJson(json, ListCastAndCrewDto.class);
        mCrewList = listCastAndCrew.crewDto;
        return mCrewList;
    }
    public List<MovieDto> convertToListMovie(String json) {
        mMovieList = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ResultJsonDto resultJson = gson.fromJson(json, ResultJsonDto.class);
        mMovieList = resultJson.results;
        return mMovieList;
    }
    public MovieDetailDto convertToMovieObject(String json) {
        mMovieDetail = new MovieDetailDto();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        mMovieDetail = gson.fromJson(json, MovieDetailDto.class);
        return mMovieDetail;
    }
}
