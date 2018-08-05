package fsoft.training.movieapplication.data.sqlite;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;
import fsoft.training.movieapplication.domain.model.dto.reminder.ReminderDto;
import fsoft.training.movieapplication.presentation.ReminderListPresenter;
import fsoft.training.movieapplication.presentation.ReminderPresenter;

/**
 * Created by mac on 10/27/17.
 */

public class ReminderReponsitory {
    private ReminderHelper mReminderHelper;
    private ReminderDto mReminderDto;
    private List<ReminderDto> reminderDtoList;

    public void addReminder(String date, String time, Context context, MovieDto movieDto, ReminderPresenter reminderPresenter) {
        mReminderHelper = new ReminderHelper(context);
        mReminderDto = new ReminderDto();
        mReminderDto.movieId = String.valueOf(movieDto.id);
        mReminderDto.movieName = movieDto.title;
        mReminderDto.reminderTime = date + "," + time;
        mReminderDto.voteCountAverage = movieDto.voteAverage;
        mReminderDto.posterPath = movieDto.posterPath;
        boolean check = mReminderHelper.addReminder(mReminderDto);
        if (check) {
            reminderPresenter.addSuccess(Constants.ADD_SUCCESS);
        } else {
            reminderPresenter.addError(Constants.ADD_ERROR);
        }
    }
    public void removeReminder(ReminderDto reminderDto, Context context, ReminderListPresenter reminderListPresenter) {
        mReminderHelper = new ReminderHelper(context);
        boolean isDelete;
        isDelete = mReminderHelper.deteleReminder(reminderDto);
        if (isDelete) {
            reminderListPresenter.resultRemoveReminder(Constants.DELETE_SUCCESS);
        } else {
            reminderListPresenter.resultRemoveReminder(Constants.DELETE_ERROR);
        }
    }
    public void getAllReminder(Context context, ReminderListPresenter reminderListPresenter){
        mReminderHelper = new ReminderHelper(context);
        reminderDtoList = new ArrayList<>();
        reminderDtoList = mReminderHelper.getAllReminders();
        reminderListPresenter.resultListReminder(reminderDtoList);
    }
}
