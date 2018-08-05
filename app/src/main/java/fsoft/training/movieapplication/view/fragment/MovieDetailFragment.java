package fsoft.training.movieapplication.view.fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import fsoft.training.movieapplication.R;
import fsoft.training.movieapplication.application.utils.AppSingleton;
import fsoft.training.movieapplication.broadcastreceiver.ReminderReceiver;
import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;
import fsoft.training.movieapplication.domain.model.dto.moviedetail.CastDto;
import fsoft.training.movieapplication.domain.model.dto.moviedetail.CrewDto;
import fsoft.training.movieapplication.domain.model.dto.reminder.ReminderDto;
import fsoft.training.movieapplication.listener.CastAndCrewPresenterListener;
import fsoft.training.movieapplication.listener.ReminderPresenterListener;
import fsoft.training.movieapplication.presentation.CastAndCrewPresenter;
import fsoft.training.movieapplication.presentation.ReminderPresenter;
import fsoft.training.movieapplication.view.activity.BaseActivity;
import fsoft.training.movieapplication.view.adapter.CastAndCrewAdapter;

import static android.R.attr.defaultValue;
import static fsoft.training.movieapplication.R.id.overview_detail_txt;
import static fsoft.training.movieapplication.R.id.poster_detail_img;

/**
 * Created by mac on 10/26/17.
 */

public class MovieDetailFragment extends Fragment implements CastAndCrewPresenterListener, View.OnClickListener, ReminderPresenterListener {
    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private ImageView mImageFavourite;
    private MovieDto mMovieDto;
    private TextView mTvRating;
    private int mMovieDetailId;
    private TextView mTvReleaseDateDetail;
    private TextView mTvRatingDetail;
    private ImageView mImgPosterDetail;
    private TextView mTvOverviewDetail;
    private ImageView mImgAdult;
    private Button mReminderBtn;
    private CastAndCrewPresenter mCastAndCrewPresenter;
    private ReminderPresenter mReminderPresenter;
    private List<CastDto> mCastDtos;
    private List<CrewDto> mCrewDtos;
    private RecyclerView mRecyclerView;
    private CastAndCrewAdapter mAdapter;
    private Menu mMenu;
    private String mDate;
    private String mTime;
    Calendar calendar = Calendar.getInstance();
    Calendar current = Calendar.getInstance();
    int day = calendar.get(Calendar.DATE);
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);
    int hours = current.get(Calendar.HOUR);
    int minutes = current.get(Calendar.MINUTE);
    int seconds = current.get(Calendar.SECOND);
    private ReminderDto mReminderDto;

    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        setRetainInstance(true);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMovieDetailId = bundle.getInt(Constants.MOVIES_ID_KEY, defaultValue);
            mMovieDto = (MovieDto) bundle.getSerializable(Constants.MOVIES_OBJECT_BUNDLE);
        }
        mCastAndCrewPresenter = new CastAndCrewPresenter(this);
        mCastAndCrewPresenter.requestToReponsitory(mMovieDetailId);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("MovieFragment", "setUserVisibleHint: ");
        if (!isVisibleToUser) {
            ((BaseActivity) getActivity())
                    .setActionBarTitle(AppSingleton.titleActionBar);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((BaseActivity) getActivity())
                .setActionBarTitle(AppSingleton.titleActionBar);
        Log.d("MovieFragment", "OnResume MovieDetailFragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        ((BaseActivity) getActivity())
                .setActionBarTitle(Constants.MOVIES);

        Log.d("MovieFragment", "onStop Movie Detail");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MovieFragment", "onDestroy Movie Detail");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        mTvReleaseDateDetail = (TextView) rootView.findViewById(R.id.release_date_detail_txt);
        mTvRating = (TextView) rootView.findViewById(R.id.rating_detail_txt);
        mTvOverviewDetail = (TextView) rootView.findViewById(overview_detail_txt);
        mImgPosterDetail = (ImageView) rootView.findViewById(poster_detail_img);
        mImgAdult = (ImageView) rootView.findViewById(R.id.adult_detail_txt);
        mReminderBtn = (Button) rootView.findViewById(R.id.reminder_detail_btn);
        mTvReleaseDateDetail.setText(mMovieDto.releaseDate);
        mTvRating.setText(mMovieDto.voteAverage + "/10.0");
        mTvOverviewDetail.setText(mMovieDto.overview);
        mImageFavourite = rootView.findViewById(R.id.favourite_icon_img);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_cast_crew);

        ((SendDataToActivity) activity).onPassData(mMovieDto.title);
        mTvOverviewDetail.setMovementMethod(new ScrollingMovementMethod());
        Picasso.with(getContext())
                .load(Constants.URL_BASE_IMAGE + mMovieDto.posterPath)
                .error(R.mipmap.ic_home)      // optional
                .resize(110, 120)
                .into(mImgPosterDetail);
        if (!mMovieDto.adult) {
            mImgAdult.setVisibility(View.GONE);
        }
        if (mCastDtos != null && mCrewDtos != null) {
            mAdapter = new CastAndCrewAdapter(mCastDtos, mCrewDtos, getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Toast.makeText(getContext(), "No Internet!", Toast.LENGTH_SHORT).show();
        }
        mReminderBtn.setOnClickListener(this);
        mReminderPresenter = new ReminderPresenter(this);
        return rootView;
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        this.mMenu = menu;
        menu.findItem(R.id.change_view_actionbar).setVisible(false);
    }

    @Override
    public void resultCastCrewPresenter(List<CastDto> castDtos, List<CrewDto> crewDtos) {
        this.mCastDtos = castDtos;
        this.mCrewDtos = crewDtos;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reminder_detail_btn:
                chooseDay();
                break;
            default:
                break;
        }
    }

    @Override
    public void addSuccess(String success) {
        Toast.makeText(getContext(), success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addError(String error) {

    }


    public interface SendDataToActivity {
        void onPassData(String data);
    }

    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////
    private void chooseDay() {
        calendar = Calendar.getInstance();
        current.set(year, month, day);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext()
                , android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                if (datePicker.isShown()) {
                    calendar.set(i, i1, i2);
                    if (current.before(calendar)) {
                        mDate = simpleDateFormat.format(calendar.getTime());
//                        chooseTime();
                        openTimePickerDialog(false);
                    } else {
                        Toast.makeText(getContext(), "DATE MUST AFTER CURRENT DATE", Toast.LENGTH_SHORT).show();

                    }
                }


            }
        }, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        calendar.set(year, month, day);
        SimpleDateFormat simpleDateFormatTitle = new SimpleDateFormat("EEE, MMM dd, yyyy");
        datePickerDialog.setTitle(simpleDateFormatTitle.format(calendar.getTime()));
        datePickerDialog.show();
    }

    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.setTitle("SET TIME");
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();
            calSet.set(Calendar.YEAR, year);
            calSet.set(Calendar.MONTH, month);
            calSet.set(Calendar.DAY_OF_MONTH, day);
            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);
            mTime = simpleDateFormat.format(calSet.getTime());
            if (calSet.compareTo(calNow) <= 0) {
                calSet.add(Calendar.DATE, 1);
            }

            mReminderPresenter.requestAddReminder(getContext(), mDate, mTime, mMovieDto);
            setAlarm(calSet);
        }
    };

    private void setAlarm(Calendar targetCal) {
        Intent alertIntent = new Intent(getContext(), ReminderReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.MOVIES_OBJECT_BUNDLE,mMovieDto);
        alertIntent.putExtras(bundle);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis()
                , PendingIntent.getBroadcast(getContext(), mMovieDto.id, alertIntent, PendingIntent.FLAG_ONE_SHOT));
    }

    private void chooseTime() {
        final Calendar calendar = Calendar.getInstance();
        final int hours = calendar.get(Calendar.HOUR);
        final int minutes = calendar.get(Calendar.MINUTE);
        final Calendar current = Calendar.getInstance();
        current.set(0, 0, 0, hours, minutes);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity()
                , android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
                if (timePicker.isShown()) {
                    calendar.set(0, 0, 0, i, i1);
                    mTime = simpleDateFormat.format(calendar.getTime());
                    if (current.before(calendar)) {

                        mReminderPresenter.requestAddReminder(getContext(), mDate, mTime, mMovieDto);
                    } else {
                        Toast.makeText(activity, "Time must be greater than current time", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, hours, minutes, false);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.setTitle("SET TIME");
        timePickerDialog.show();
    }
    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////
}
