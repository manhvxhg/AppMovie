package fsoft.training.movieapplication.application.utils;

import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;

/**
 * Created by ManhND16 on 9/27/2017.
 */

public class ReadJsonFromApi extends BaseAsyncTask<String,Void,String>{
    StringBuilder builder = null;
    List<MovieDto> movies;
    ProgressBar progressBar;

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
}
