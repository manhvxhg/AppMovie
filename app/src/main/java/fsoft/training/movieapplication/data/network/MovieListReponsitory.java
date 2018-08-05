package fsoft.training.movieapplication.data.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import fsoft.training.movieapplication.application.utils.BaseAsyncTask;
import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.presentation.MovieListPresenter;

import static android.content.ContentValues.TAG;

/**
 * Created by mac on 10/25/17.
 */

public class MovieListReponsitory implements SharedPreferences.OnSharedPreferenceChangeListener{
    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private int mFilterList;
    private int mRateSetting;
    private SharedPreferences mSharedPreferencesSetting;
    private Context mContext;
    private MovieListPresenter mMovieListPresenter;
    private int mPageNumber;
    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    public void requestApi(Context context, int pageNumber, MovieListPresenter movieListPresenter) {
        Log.d(TAG, "requestApi: " + pageNumber);
        this.mContext = context;
        this.mMovieListPresenter = movieListPresenter;
        this.mPageNumber = pageNumber;
        getSetting();
        mSharedPreferencesSetting.registerOnSharedPreferenceChangeListener(this);
        try {
            switch (mFilterList) {
                case 1 :
                    movieListPresenter.resultFromReponsitory(new ReadMovieFromURL().execute(Constants.URL_LIST_MOVIES_POPULAR + pageNumber).get());
                    break;
                case 2 :
                    movieListPresenter.resultFromReponsitory(new ReadMovieFromURL().execute(Constants.URL_LIST_MOVIES_TOP_REATED + pageNumber).get());
                    break;
                case 3 :
                    movieListPresenter.resultFromReponsitory(new ReadMovieFromURL().execute(Constants.URL_LIST_MOVIES_UPCOMING + pageNumber).get());
                    break;
                case 4 :
                    movieListPresenter.resultFromReponsitory(new ReadMovieFromURL().execute(Constants.URL_LIST_MOVIES_NOW_PLAYING + pageNumber).get());
                    break;
                default:
                    break;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void getSetting() {
        mSharedPreferencesSetting = PreferenceManager.getDefaultSharedPreferences(mContext);
        mFilterList = Integer.parseInt(mSharedPreferencesSetting.getString("category_key","1"));
        mRateSetting = mSharedPreferencesSetting.getInt("rate_key",0);
        Log.d(TAG, "getSetting: "+mSharedPreferencesSetting.getInt("rate_key",0));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        getSetting();
        try {
            switch (mFilterList) {
                case 1 :
                    mMovieListPresenter.resultFromReponsitory(new ReadMovieFromURL().execute(Constants.URL_LIST_MOVIES_POPULAR + mPageNumber).get());
                    break;
                case 2 :
                    mMovieListPresenter.resultFromReponsitory(new ReadMovieFromURL().execute(Constants.URL_LIST_MOVIES_TOP_REATED + mPageNumber).get());
                    break;
                case 3 :
                    mMovieListPresenter.resultFromReponsitory(new ReadMovieFromURL().execute(Constants.URL_LIST_MOVIES_UPCOMING + mPageNumber).get());
                    break;
                case 4 :
                    mMovieListPresenter.resultFromReponsitory(new ReadMovieFromURL().execute(Constants.URL_LIST_MOVIES_NOW_PLAYING + mPageNumber).get());
                    break;
                default:
                    break;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////

    public class ReadMovieFromURL extends BaseAsyncTask<String, String, String> {
        StringBuilder builder = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {

            return readJsonFromApi(strings[0]);
        }

        /**
         * Read json from api return to string
         *
         * @param string
         * @return
         */
        private String readJsonFromApi(String string) {
            try {
                URL url = new URL(string);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }
                return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }
}
