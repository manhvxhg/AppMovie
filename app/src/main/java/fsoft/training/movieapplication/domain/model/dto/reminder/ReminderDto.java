package fsoft.training.movieapplication.domain.model.dto.reminder;

/**
 * Created by mac on 9/28/17.
 */

public class ReminderDto {
    public String reminderId;
    public String movieId;
    public String movieName;
    public String reminderTime;
    public String posterPath;
    public double voteCountAverage;

    public ReminderDto() {
    }

    public ReminderDto(String reminderId, String movieId, String movieName, String reminderTime, double voteCountAvarage, String posterPath) {
        this.reminderId = reminderId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.reminderTime = reminderTime;
        this.voteCountAverage = voteCountAvarage;
        this.posterPath = posterPath;
    }

    @Override
    public String toString() {
        return "ReminderDto Id :"+this.reminderId+",MovieDto Id :"+this.movieId+",MovieName :"+this.movieName+",ReminderTime :"+this.reminderTime+",Rating :"+this.voteCountAverage;
    }
}
