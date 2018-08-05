package fsoft.training.movieapplication.presentation;

import android.content.Context;

import java.util.List;

import fsoft.training.movieapplication.data.sqlite.ReminderReponsitory;
import fsoft.training.movieapplication.domain.model.dto.reminder.ReminderDto;
import fsoft.training.movieapplication.listener.ReminderListPresenterListener;
import fsoft.training.movieapplication.listener.ReminderListReponsitoryListener;

/**
 * Created by mac on 10/27/17.
 */

public class ReminderListPresenter implements ReminderListReponsitoryListener {
    private ReminderListPresenterListener mReminderListPresenterListener;

    public ReminderListPresenter(ReminderListPresenterListener mReminderListPresenterListener) {
        this.mReminderListPresenterListener = mReminderListPresenterListener;
    }

    public void requestListReminderToResponsitory(Context context) {
        new ReminderReponsitory().getAllReminder(context, this);
    }

    public void requestDeleteReminderToResponsitory(ReminderDto reminderDto, Context context) {
        new ReminderReponsitory().removeReminder(reminderDto,context,this);
    }

    @Override
    public void resultListReminder(List<ReminderDto> reminderDtoList) {
        mReminderListPresenterListener.resultListFromPresenter(reminderDtoList);
    }

    @Override
    public void resultRemoveReminder(String result) {
        mReminderListPresenterListener.resultRemoveReminderFromPresenter(result);
    }
}
