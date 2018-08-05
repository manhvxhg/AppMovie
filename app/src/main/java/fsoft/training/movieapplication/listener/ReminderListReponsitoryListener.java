package fsoft.training.movieapplication.listener;

import java.util.List;

import fsoft.training.movieapplication.domain.model.dto.reminder.ReminderDto;

/**
 * Created by mac on 10/27/17.
 */

public interface ReminderListReponsitoryListener {
    void resultListReminder(List<ReminderDto> reminderDtoList);
    void resultRemoveReminder(String result);
}
