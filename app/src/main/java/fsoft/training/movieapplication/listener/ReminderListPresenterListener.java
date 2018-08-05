package fsoft.training.movieapplication.listener;

import java.util.List;

import fsoft.training.movieapplication.domain.model.dto.reminder.ReminderDto;

/**
 * Created by mac on 10/27/17.
 */

public interface ReminderListPresenterListener {
    void resultListFromPresenter(List<ReminderDto> reminderDtoList);
    void resultRemoveReminderFromPresenter(String result);
}
