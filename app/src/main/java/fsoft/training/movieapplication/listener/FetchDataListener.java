package fsoft.training.movieapplication.listener;

/**
 * Created by ManhND16 on 9/26/2017.
 */

public interface FetchDataListener {
    void onFetchComplete(String data);

    void onFetchFailure(String msg);

    void onFetchStart();
}
