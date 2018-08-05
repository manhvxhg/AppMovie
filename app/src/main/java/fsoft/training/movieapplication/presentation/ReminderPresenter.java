package fsoft.training.movieapplication.presentation;

import android.content.Context;

import fsoft.training.movieapplication.data.sqlite.ReminderReponsitory;
import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;
import fsoft.training.movieapplication.listener.ReminderPresenterListener;
import fsoft.training.movieapplication.listener.ReminderReponsitoryListener;

/**
 * Created by mac on 10/27/17.
 */

public class ReminderPresenter implements ReminderReponsitoryListener {
    private ReminderPresenterListener mReminderPresenterListener;

    public ReminderPresenter(ReminderPresenterListener mReminderPresenterListener) {
        this.mReminderPresenterListener = mReminderPresenterListener;
    }

    public void requestAddReminder(Context context, String date, String time, MovieDto mMovieDto) {
        new ReminderReponsitory().addReminder(date, time, context, mMovieDto, this);
    }

    @Override
    public void addSuccess(String success) {
        mReminderPresenterListener.addSuccess(success);
    }

    @Override
    public void addError(String error) {
        mReminderPresenterListener.addError(error);
    }
}
