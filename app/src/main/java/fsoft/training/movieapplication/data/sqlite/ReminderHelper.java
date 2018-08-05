package fsoft.training.movieapplication.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fsoft.training.movieapplication.domain.model.dto.reminder.ReminderDto;

import static fsoft.training.movieapplication.constant.Constants.DATABASE_NAME;
import static fsoft.training.movieapplication.constant.Constants.DATABASE_VERSION;
import static fsoft.training.movieapplication.constant.Constants.KEY_ADULT;
import static fsoft.training.movieapplication.constant.Constants.KEY_ID;
import static fsoft.training.movieapplication.constant.Constants.KEY_MOVIE_ID;
import static fsoft.training.movieapplication.constant.Constants.KEY_MOVIE_NAME;
import static fsoft.training.movieapplication.constant.Constants.KEY_OVERVIEW;
import static fsoft.training.movieapplication.constant.Constants.KEY_POSTER_PATH;
import static fsoft.training.movieapplication.constant.Constants.KEY_RELEASE_DATE;
import static fsoft.training.movieapplication.constant.Constants.KEY_REMINDER_TIME;
import static fsoft.training.movieapplication.constant.Constants.KEY_VOTE_AVERAGE;
import static fsoft.training.movieapplication.constant.Constants.TABLE_NAME;
import static fsoft.training.movieapplication.constant.Constants.TABLE_NAME_FAVOURITE;

/**
 * Created by mac on 10/27/17.
 */

public class ReminderHelper extends SQLiteOpenHelper {

    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private List<ReminderDto> mReminderList = null;
    private static final String[] COLUMNS = {KEY_ID, KEY_MOVIE_ID, KEY_MOVIE_NAME, KEY_REMINDER_TIME, KEY_VOTE_AVERAGE, KEY_POSTER_PATH};

    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////
    public ReminderHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOK_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MOVIE_ID + " TEXT, " + KEY_MOVIE_NAME + " TEXT, " + KEY_REMINDER_TIME + " TEXT, " + KEY_VOTE_AVERAGE + " REAL, " + KEY_POSTER_PATH + " TEXT )";
        db.execSQL(CREATE_BOOK_TABLE);
        String CREATE_FAVOURITE_TABLE = "CREATE TABLE " + TABLE_NAME_FAVOURITE + " ( " + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MOVIE_ID + " TEXT, " + KEY_MOVIE_NAME + " TEXT, " + KEY_OVERVIEW + " TEXT, " + KEY_VOTE_AVERAGE + " REAL, " + KEY_POSTER_PATH + " TEXT " + KEY_RELEASE_DATE + " TEXT " + KEY_ADULT + " TEXT " + " )";
        Log.d("CREATE TABLE", CREATE_FAVOURITE_TABLE);
        db.execSQL(CREATE_FAVOURITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    /**
     * CRUD Operations add read update delete
     */

    /**
     * Add Reminder to database
     *
     * @param reminder
     * @return
     */
    public boolean addReminder(ReminderDto reminder) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MOVIE_ID, reminder.movieId);
        contentValues.put(KEY_MOVIE_NAME, reminder.movieName);
        contentValues.put(KEY_REMINDER_TIME, reminder.reminderTime);
        contentValues.put(KEY_VOTE_AVERAGE, reminder.voteCountAverage);
        contentValues.put(KEY_POSTER_PATH, reminder.posterPath);

        long rowInserted = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        boolean check;
        if (rowInserted != -1) {
            check = true;
        } else {
            check = false;
        }
        sqLiteDatabase.close();
        return check;
    }

    /**
     * get a Reminder object from database by reminderId
     *
     * @param id
     * @return
     */
    public ReminderDto getReminder(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, COLUMNS, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        ReminderDto reminder = new ReminderDto();
        reminder.reminderId = cursor.getString(0);
        reminder.movieId = cursor.getString(1);
        reminder.movieName = cursor.getString(2);
        reminder.reminderTime = cursor.getString(3);
        reminder.voteCountAverage = Float.parseFloat(cursor.getString(4));
        reminder.posterPath = cursor.getString(5);
        return reminder;
    }

    /**
     * get All reminder object from database
     *
     * @return
     */
    public List<ReminderDto> getAllReminders() {
        List<ReminderDto> reminderList = new ArrayList<ReminderDto>();
        // Select All Query
        String sql = "SELECT " + KEY_ID + "," + KEY_MOVIE_ID + "," + KEY_MOVIE_NAME + "," + KEY_REMINDER_TIME + "," + KEY_VOTE_AVERAGE + "," + KEY_POSTER_PATH + " FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ReminderDto reminder = new ReminderDto();
                reminder.reminderId = cursor.getString(0);
                reminder.movieId = cursor.getString(1);
                reminder.movieName = cursor.getString(2);
                reminder.reminderTime = cursor.getString(3);
                reminder.voteCountAverage = Float.parseFloat(cursor.getString(4));
                reminder.posterPath = cursor.getString(5);
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }

        // return contact list
        return reminderList;
    }

    /**
     * delete reminder from database by reminderId
     *
     * @param reminder
     * @return
     */
    public boolean deteleReminder(ReminderDto reminder) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int rowCount;
        rowCount = sqLiteDatabase.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(reminder.reminderId)});
        boolean isDelete;
        if (rowCount > 0) {
            isDelete = true;
        } else {
            isDelete = false;
        }
        sqLiteDatabase.close();
        return isDelete;
    }

    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////
}

