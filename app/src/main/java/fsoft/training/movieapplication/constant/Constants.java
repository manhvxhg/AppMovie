package fsoft.training.movieapplication.constant;

/**
 * Created by TanNV13 on 10/18/2016
 */
public final class Constants {

    public static final String ACC = "";
    public static final String PASS = "";


    public static final String MOVIES = "Movies";
    public static final String FAVOURITE = "Favourite";
    public static final String SETTINGS = "Settings";
    public static final String ABOUT = "About";


    public static final String URL_LIST_MOVIES_POPULAR = "https://api.themoviedb.org/3/movie/popular?api_key=e7631ffcb8e766993e5ec0c1f4245f93&page=";
    public static final String URL_LIST_MOVIES_TOP_REATED = "https://api.themoviedb.org/3/movie/top_rated?api_key=e7631ffcb8e766993e5ec0c1f4245f93&page=";
    public static final String URL_LIST_MOVIES_UPCOMING = "https://api.themoviedb.org/3/movie/upcoming?api_key=e7631ffcb8e766993e5ec0c1f4245f93&page=";
    public static final String URL_LIST_MOVIES_NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing?api_key=e7631ffcb8e766993e5ec0c1f4245f93&page=";
    public static final String URL_BASE_IMAGE = "https://image.tmdb.org/t/p/w300";
    public static final String URL_BASE_IMAGE_CAST_CREW = "https://image.tmdb.org/t/p/w500";
    public static final String PRE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String LAST_URL_MOVIE_DETAIL = "?api_key=e7631ffcb8e766993e5ec0c1f4245f93";
    public static final String LAST_URL_CAST_CREW = "/credits?api_key=e7631ffcb8e766993e5ec0c1f4245f93";

    public static final String ERROR = "error";

    public static final String FILE_NAME_TYPE_LAYOUT = "type_layout";

    public static final String TYPE_LAYOUT_KEY = "typelayout";
    public static final String MOVIES_OBJECT_BUNDLE = "movie_object";
    public static final String MOVIES_ID_KEY = "item_selected_key";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Movie.db";
    public static final String DATABASE_NAME_FAVOURITE = "Favourite.db";
    public static final String KEY_ID = "_id";
    public static final String KEY_MOVIE_ID = "movieId";
    public static final String KEY_MOVIE_NAME= "movieName";
    public static final String KEY_REMINDER_TIME = "reminderTime";
    public static final String KEY_VOTE_AVERAGE= "voteAverage";
    public static final String KEY_POSTER_PATH = "poster";
    public static final String TABLE_NAME = "tblReminder";
    public static final String TAG_MOVIE = "ID_MOVIE";
    public static final String TAG_REMINDER = "ID_REMINDER";

    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_ADULT = "adult";
    public static final String KEY_IS_FAVOURITE = "isFavourite";
    public static final String KEY_RELEASE_DATE = "releaseDate";
    public static final String TABLE_NAME_FAVOURITE = "tblFavourite";

    public static final String ADD_SUCCESS = "Add Reminder Success";
    public static final String ADD_ERROR = "Add Reminder Error";

    public static final int REQUEST_CODE_EDIT_PROFILE = 6969;
    public static final int REQUEST_CODE_CAMERA = 6666;


    public static final int REQUEST_CODE_GALLERY = 8888;

    public static final String PROFILE_NAME  = "profile_database";

    public static final String GENDER_MALE = "Male";
    public static final String GENDER_FEMALE = "Female";
    public static final String BIRTHDAY_CHOOSE_ERROR = "BIRTHDAY MUST BEFORE CURRENT DATE";
    public static final String EDIT_PROFILE_BUNDLE_KEY_NAME = "name";
    public static final String EDIT_PROFILE_BUNDLE_KEY_BIRTHDAY = "birthday";
    public static final String EDIT_PROFILE_BUNDLE_KEY_EMAIL = "email";
    public static final String EDIT_PROFILE_BUNDLE_KEY_GENDER = "gender";
    public static final String EDIT_PROFILE_PREFERENCES_KEY_EMAIL = "email_profile";
    public static final String EDIT_PROFILE_PREFERENCES_KEY_NAME = "name_profile";
    public static final String EDIT_PROFILE_PREFERENCES_KEY_BIRTHDAY = "birthday";
    public static final String EDIT_PROFILE_PREFERENCES_KEY_GENDER = "gender";
    public static final String EDIT_PROFILE_PREFERENCES_KEY_PICTURE_PROFILE = "profile_picture";
    public static final String TITLE_ALL_REMINDERS = "All Reminder";
    public static final String DELETE_SUCCESS = "Delete Success";
    public static final String DELETE_ERROR = "Delete Error";
    public static final String FLAG_REMINDER = "check_reminder";
    public static final String MESSAGE_REMOVE_CONFIRM = "Do you want to delete this reminders ?";
    public static final String ADD_FAVOURITE_SUCCESS = "Success";
    public static final String ADD_FAVOURITE_ERROR = "Error";
    public static final String FLAG_ACTION_BROADCAST_COUNT_FAVOURITE = "favourite_count";
    public static final String URL_ABOUT = "https://www.themoviedb.org/about";
}
