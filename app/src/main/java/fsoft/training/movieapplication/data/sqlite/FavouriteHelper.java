package fsoft.training.movieapplication.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fsoft.training.movieapplication.domain.model.dto.listmovie.MovieDto;

import static fsoft.training.movieapplication.constant.Constants.DATABASE_NAME_FAVOURITE;
import static fsoft.training.movieapplication.constant.Constants.DATABASE_VERSION;
import static fsoft.training.movieapplication.constant.Constants.KEY_ADULT;
import static fsoft.training.movieapplication.constant.Constants.KEY_ID;
import static fsoft.training.movieapplication.constant.Constants.KEY_MOVIE_ID;
import static fsoft.training.movieapplication.constant.Constants.KEY_MOVIE_NAME;
import static fsoft.training.movieapplication.constant.Constants.KEY_OVERVIEW;
import static fsoft.training.movieapplication.constant.Constants.KEY_POSTER_PATH;
import static fsoft.training.movieapplication.constant.Constants.KEY_RELEASE_DATE;
import static fsoft.training.movieapplication.constant.Constants.KEY_VOTE_AVERAGE;
import static fsoft.training.movieapplication.constant.Constants.TABLE_NAME_FAVOURITE;

/**
 * Created by mac on 10/1/17.
 */

public class FavouriteHelper extends SQLiteOpenHelper {
    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private List<MovieDto> mFaMovieDtos = null;
    private static final String[] COLUMNS = {KEY_ID, KEY_MOVIE_ID, KEY_MOVIE_NAME, KEY_OVERVIEW, KEY_VOTE_AVERAGE, KEY_POSTER_PATH, KEY_RELEASE_DATE, KEY_ADULT};

    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////
    public FavouriteHelper(Context context) {
        super(context, DATABASE_NAME_FAVOURITE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVOURITE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FAVOURITE + " ( " + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MOVIE_ID + " TEXT, " + KEY_MOVIE_NAME + " TEXT, " + KEY_OVERVIEW + " TEXT, " + KEY_VOTE_AVERAGE + " REAL, " + KEY_POSTER_PATH + " TEXT, " + KEY_RELEASE_DATE + " TEXT, " + KEY_ADULT + " TEXT " + " )";
        Log.d("CREATE TABLE", CREATE_FAVOURITE_TABLE);
        db.execSQL(CREATE_FAVOURITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FAVOURITE);
        this.onCreate(db);
    }

    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    /**
     * ADD Favourite To database
     */
    public boolean addFavourite(MovieDto movie) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MOVIE_ID, movie.id);
        contentValues.put(KEY_MOVIE_NAME, movie.title);
        contentValues.put(KEY_OVERVIEW, movie.overview);
        contentValues.put(KEY_VOTE_AVERAGE, movie.voteAverage);
        contentValues.put(KEY_POSTER_PATH, movie.posterPath);
        contentValues.put(KEY_RELEASE_DATE, movie.releaseDate);
        contentValues.put(KEY_ADULT, String.valueOf(movie.adult));
        boolean isAddSuccess;
        long rowCount = sqLiteDatabase.insert(TABLE_NAME_FAVOURITE, null, contentValues);
        if (rowCount > 0) {
            isAddSuccess = true;
        } else {
            isAddSuccess = false;
        }
        sqLiteDatabase.close();
        return isAddSuccess;
    }

    /**
     * get favourite object by favouriteid
     *
     * @param id
     * @return
     */
    public MovieDto getFavouriteById(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_FAVOURITE, COLUMNS, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        MovieDto movie = new MovieDto();
        movie.favouriteId = cursor.getString(0);
        movie.id = Integer.parseInt(cursor.getString(1));
        movie.title = cursor.getString(2);
        movie.overview = cursor.getString(3);
        movie.voteAverage = Double.parseDouble(cursor.getString(4));
        movie.posterPath = cursor.getString(5);
        movie.releaseDate = cursor.getString(6);
        movie.adult = Boolean.parseBoolean(cursor.getString(7));

        return movie;
    }

    /**
     * Check exist favourite object by id
     *
     * @param id
     * @return
     */
    public boolean checkIsFavourite(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_FAVOURITE, COLUMNS, KEY_MOVIE_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor.getCount() > 0)
            return true;
        return false;
    }

    /**
     * get all favourite from database
     *
     * @return
     */
    public List<MovieDto> getAllFavourite() {
        List<MovieDto> favouriteList = new ArrayList<MovieDto>();
        // Select All Query
        String sql = "SELECT " + KEY_ID + "," + KEY_MOVIE_ID + "," + KEY_MOVIE_NAME + "," + KEY_OVERVIEW + "," + KEY_VOTE_AVERAGE + "," + KEY_POSTER_PATH + "," + KEY_RELEASE_DATE + "," + KEY_ADULT + " FROM " + TABLE_NAME_FAVOURITE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MovieDto movie = new MovieDto();
                movie.favouriteId = cursor.getString(0);
                movie.id = Integer.parseInt(cursor.getString(1));
                movie.title = cursor.getString(2);
                movie.overview = cursor.getString(3);
                movie.voteAverage = Double.parseDouble(cursor.getString(4));
                movie.posterPath = cursor.getString(5);
                movie.releaseDate = cursor.getString(6);
                movie.adult = Boolean.parseBoolean(cursor.getString(7));
                favouriteList.add(movie);
            } while (cursor.moveToNext());
        }

        // return contact list
        return favouriteList;
    }

    /**
     * delete favourite from database
     *
     * @param favourite
     * @return
     */
    public boolean deteleFavourite(MovieDto favourite) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        boolean isDelete;
        int rowCount;
        rowCount = sqLiteDatabase.delete(TABLE_NAME_FAVOURITE, KEY_MOVIE_ID + " = ?", new String[]{String.valueOf(favourite.id)});
        if (rowCount > 0) {
            isDelete = true;
        } else {
            isDelete = false;
        }
        sqLiteDatabase.close();
        return isDelete;
    }
    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////

}
