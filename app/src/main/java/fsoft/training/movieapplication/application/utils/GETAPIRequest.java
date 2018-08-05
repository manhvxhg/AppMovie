package fsoft.training.movieapplication.application.utils;

import android.content.Context;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import fsoft.training.movieapplication.listener.FetchDataListener;

/**
 * Created by ManhND16 on 9/26/2017.
 */

public class GETAPIRequest {
        public void request(final Context context, final FetchDataListener listener, final String ApiURL) throws JSONException {
            if (listener != null) {
                //call onFetchComplete of the listener
                //to show progress dialog
                //-indicates calling started
                listener.onFetchStart();
            }
            //add extension api url received from caller
            //and make full api
            StringRequest postRequest = new StringRequest(Request.Method.GET, ApiURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (listener != null) {
                                listener.onFetchComplete(response);
                            }else {
                                listener.onFetchFailure(response);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NoConnectionError) {
                        listener.onFetchFailure("Network Connectivity Problem");
                    } else if (error.networkResponse != null && error.networkResponse.data != null) {
                        VolleyError volley_error = new VolleyError(new String(error.networkResponse.data));
                        String errorMessage      = "";
                        String errorJson = new String(volley_error.getMessage().toString());

                        if (errorMessage.isEmpty()) {
                            errorMessage = volley_error.getMessage();
                        }
                        if (listener != null) listener.onFetchFailure(errorMessage);
                    } else {
                        listener.onFetchFailure("Something went wrong. Please try again later");
                    }

                }
            });

            RequestQueueService.getInstance(context).addToRequestQueue(postRequest.setShouldCache(false));
        }

}
