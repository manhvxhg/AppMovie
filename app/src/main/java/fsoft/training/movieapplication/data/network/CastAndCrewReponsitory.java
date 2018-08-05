package fsoft.training.movieapplication.data.network;

import java.util.concurrent.ExecutionException;

import fsoft.training.movieapplication.application.utils.ReadJsonFromApi;
import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.presentation.CastAndCrewPresenter;

/**
 * Created by mac on 10/26/17.
 */

public class CastAndCrewReponsitory {
    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private String mUrlCastCrew;
    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////
    public void passDataCastCrew(int movieId, CastAndCrewPresenter castAndCrewPresenter) {
        mUrlCastCrew = Constants.PRE_URL + movieId + Constants.LAST_URL_CAST_CREW;
        try {
            String resultJson = new ReadJsonFromApi().execute(mUrlCastCrew).get();
            castAndCrewPresenter.resultCastCrewReponsitory(resultJson);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////
}
