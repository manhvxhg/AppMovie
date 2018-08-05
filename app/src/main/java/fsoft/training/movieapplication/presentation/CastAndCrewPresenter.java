package fsoft.training.movieapplication.presentation;

import java.util.ArrayList;
import java.util.List;

import fsoft.training.movieapplication.application.utils.GsonService;
import fsoft.training.movieapplication.data.network.CastAndCrewReponsitory;
import fsoft.training.movieapplication.domain.model.dto.moviedetail.CastDto;
import fsoft.training.movieapplication.domain.model.dto.moviedetail.CrewDto;
import fsoft.training.movieapplication.listener.CastAndCrewPresenterListener;
import fsoft.training.movieapplication.listener.CastAndCrewReponsitoryListener;

/**
 * Created by mac on 10/26/17.
 */

public class CastAndCrewPresenter implements CastAndCrewReponsitoryListener {
    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private CastAndCrewPresenterListener mCastAndCrewPresenterListener;
    private List<CastDto> mCastDtos;
    private List<CrewDto> mCrewDtos;
    private GsonService mGsonService;

    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////
    public CastAndCrewPresenter(CastAndCrewPresenterListener mCastAndCrewPresenterListener) {
        this.mCastAndCrewPresenterListener = mCastAndCrewPresenterListener;
    }

    /**
     * Request to reponsitory for get data CastCrew from API in MVP pattern
     *
     * @param movieId
     */
    public void requestToReponsitory(int movieId) {
        new CastAndCrewReponsitory().passDataCastCrew(movieId, this);
    }

    /**
     * Result from reponsitory
     *
     * @param resultCastAndCrew
     */
    @Override
    public void resultCastCrewReponsitory(String resultCastAndCrew) {
        mCastDtos = new ArrayList<>();
        mCrewDtos = new ArrayList<>();
        mGsonService = new GsonService();
        mCastDtos = mGsonService.convertToListCast(resultCastAndCrew);
        mCrewDtos = mGsonService.convertToListCrew(resultCastAndCrew);
        mCastAndCrewPresenterListener.resultCastCrewPresenter(mCastDtos, mCrewDtos);
    }

    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////


}
