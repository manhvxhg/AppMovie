package fsoft.training.movieapplication.application.utils;

import android.os.AsyncTask;

/**
 * Created by mac on 9/25/17.
 */

public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private Callback<Params, Progress, Result> callback;
    private Result result;

    /**
     * Register a callback for this task.
     *
     * @param callback The callback to register
     */
    public void setCallback(Callback<Params, Progress, Result> callback) {
        this.callback = callback;
        if (callback != null) {
            callback.onAttachedToTask(getStatus(), result);
        }
    }

    /**
     * If the task is finished there should be a result in there. If the task is running the result is {@code null}.
     *
     * @return The result
     */
    public Result getResult() {
        return result;
    }

    @Override
    protected void onCancelled() {
        if (callback != null) {
            callback.onCancelled();
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        this.result = result;
        if (callback != null) {
            callback.onPostExecute(result);
        }
    }

    @Override
    protected void onPreExecute() {
        if (callback != null) {
            callback.onPreExecute();
        }
    }

    @Override
    protected void onProgressUpdate(Progress... values) {
        if (callback != null) {
            callback.onProgressUpdate(values);
        }
    }

    /**
     * Callback for an {@link AsyncTask}. Basically allows to proxy all the UI calls made by an {@link AsyncTask} in a
     * callback.
     *
     * @param <Params>
     * @param <Progress>
     * @param <Result>
     * @author Medes-IMPS
     */
    public interface Callback<Params, Progress, Result> {

        /**
         * Called when a callback is attached to the task. Useful when a manager is taking care of registering and
         * unregistering the callback for you.
         *
         * @param status The status of the task
         * @param result The result if any
         */
        void onAttachedToTask(Status status, Result result);

        /**
         * Called when the task is cancelled.
         */
        void onCancelled();

        /**
         * Called when the execution of task is finished and gives the resul back.
         *
         * @param result The result of the task
         */
        void onPostExecute(Result result);

        /**
         * Called before running the task.
         */
        void onPreExecute();

        /**
         * Called each time the task wants to make contribution to the UI thread.
         *
         * @param values The progress values
         */
        void onProgressUpdate(Progress... values);

    }

    public static class BaseCallback<Params, Progress, Result> implements Callback<Params, Progress, Result> {

        @Override
        public void onAttachedToTask(Status status, Result result) {
        }

        @Override
        public void onCancelled() {
        }

        @Override
        public void onPostExecute(Result result) {
        }

        @Override
        public void onPreExecute() {
        }

        @Override
        public void onProgressUpdate(Progress... values) {
        }

    }

}