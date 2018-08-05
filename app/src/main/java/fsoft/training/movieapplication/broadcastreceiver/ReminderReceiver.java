package fsoft.training.movieapplication.broadcastreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import fsoft.training.movieapplication.R;
import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;
import fsoft.training.movieapplication.view.activity.BaseActivity;

/**
 * Created by ManhND16 on 11/1/2017
 */

public class ReminderReceiver extends BroadcastReceiver{
    private MovieDto mMovieDto;
    @Override
    public void onReceive(Context context, Intent intent) {
        mMovieDto = new MovieDto();
        Bundle bundle = intent.getExtras();
        mMovieDto = (MovieDto) bundle.getSerializable(Constants.MOVIES_OBJECT_BUNDLE);
        createNotification(context, mMovieDto);
    }

    private void createNotification(Context context, MovieDto movieDto) {
        final Calendar calendar = Calendar.getInstance();
        final int hours = calendar.get(Calendar.HOUR);
        final int minutes = calendar.get(Calendar.MINUTE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
        calendar.set(0,0,0,hours,minutes);
        String time = simpleDateFormat.format(calendar.getTime());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, movieDto.id, new Intent(context, BaseActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_custom);
        contentView.setTextViewText(R.id.notification_title, movieDto.title);
        contentView.setTextViewText(R.id.notification_content, "Year :"+movieDto.releaseDate + "  "+"Rate: "+movieDto.voteAverage+"/10.0");
        contentView.setTextViewText(R.id.notification_time, time);
        contentView.setImageViewResource(R.id.notification_logo, R.mipmap.ic_launcher_round);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder
                .setWhen(System.currentTimeMillis())
                .setContent(contentView)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .build();
        notification.tickerText = "Ticker Text";
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(movieDto.id, notification);
        Picasso
                .with(context)
                .load(Constants.URL_BASE_IMAGE + movieDto.posterPath)
                .into(contentView, R.id.notification_logo, movieDto.id, notification);
    }
    private String convertReleaseToYear(String releaseDate) {
        return null;
    }
}
