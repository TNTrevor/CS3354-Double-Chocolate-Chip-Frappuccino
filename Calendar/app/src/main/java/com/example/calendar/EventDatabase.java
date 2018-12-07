package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * This is the Database which holds all of the user information on
 * event information. They can be able to add, edit, and delete this
 * info through the interface and those changes are made here.
 * In this class, variable and event data are defined to be used later
 * in code.
 */
public class EventDatabase extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "event_database";
    private static int DATABASE_VERSION = 9;
    private static final String TABLE_EVENTS = "events";
    private static final String KEY_EVENTID = "eventid";
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_EVENTDATE = "eventdate";
    private static final String KEY_EVENTTAG = "tag";
    private static final String KEY_EVENTCOLOR = "color";
    private static final String KEY_EVENTDETAILS = "details";
    private static final String KEY_TIME = "time";
    private static final String KEY_SORT = "sort";

    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " +   //allows user to add information toward a new event
                                                        TABLE_EVENTS +
                                                        "(" +
                                                        KEY_ID +
                                                        " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        KEY_TITLE +
                                                        " TEXT," +
                                                        KEY_EVENTID +
                                                        " TEXT," +
                                                        KEY_EVENTDATE +
                                                        " TEXT," +
                                                        KEY_EVENTTAG +
                                                        " TEXT," +
                                                        KEY_EVENTCOLOR +
                                                        " TEXT," +
                                                        KEY_EVENTDETAILS +
                                                        " TEXT," +
                                                        KEY_TIME +
                                                        " TEXT," +
                                                        KEY_SORT +
                                                        " INTEGER" +
                                                        ");";


    /**
     * This is the Constructor for the database
     *
     * @param context A reference from Context
     */
    public EventDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    /**
     * Connects SQLite local Database to Calendar App
     *
     * @param sqLiteDatabase Reference to SQLiteDatabase
     */
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_EVENTS);
    }

    @Override
    /**
     * Deletes previous data from the database and then recreates it
     *
     * @param sqLiteDatabase Reference to SQLiteDatabase
     * @param i shows how many times it has been reset
     * @param i1 database calculates 2nd value similar to i relating to table events
     */
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + "'" + TABLE_EVENTS + "'");
        onCreate(sqLiteDatabase);
    }

    /**
     * Allows user to store information on adding an event in a selected date.
     * Using the interface, the user can push this information to the database
     * and save it there whenever it needs to be shown.
     *
     * @param title This is the title of the added event
     * @param eventID This will be a unique id in order to determine what specific
     *                event to edit or delete once added.
     * @param eventDate This is the date for the event
     * @param tag This is a tag for if we decide to implement a filtering feature
     * @param details This will allow user to add more detail to an event
     * @param color This allows the user to choose a color to be more recognizable
     *              in both the list and calendar once added to database
     * @param time This allows user to insert a time for when the event is happening
     * @param sort This will help sort events if there are multiple on selected day
     * @return insert which sends it back to the database to save changes made in
     *         that particular date
     */
    public long addEvent(String title, String eventID, String eventDate, String tag, String details, String color, String time, int sort) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, title);
        cv.put(KEY_EVENTID, eventID);
        cv.put(KEY_EVENTDATE, eventDate);
        cv.put(KEY_EVENTTAG, tag);
        cv.put(KEY_EVENTDETAILS, details);
        cv.put(KEY_EVENTCOLOR, color);
        cv.put(KEY_TIME, time);
        cv.put(KEY_SORT, sort);


        long insert = db.insert(TABLE_EVENTS, null, cv);
        return insert;
    }

    /**
     * Returns Cursor with one row
     *
     * @param eventID Grabs unique id from an added event and retrieves data
     * @return c which gets requested information from database and shows it to user
     */
    public Cursor getEventForTime(String eventID) {
        //This will return a cursor with one row
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + KEY_EVENTID + " = '" + eventID + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Return the Cursor with all of the information
        return c;
    }

    /**
     * This is basically like getEventForTime where it returns cursor with one row from
     * the added date
     *
     * @param eventDate Grabs unique id from an added event and retrieves data
     * @return c which gets requested information from database and shows it to user
     */
    public Cursor getEventForDate(String eventDate) {
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + KEY_EVENTDATE + " = '" + eventDate + "' ORDER BY " + KEY_SORT + " ASC" + "," + KEY_EVENTID + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        return c;
    }

    /**
     * This will delete particular events through searching for the unique event id
     * that is requested to be deleted
     *
     * @param id This is the value that is searched in order to delete the event
     * @return the delete function which deletes the event from the database and interface
     */
    public boolean deleteEventById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_EVENTS, KEY_ID + "=" + id, null) > 0;
    }

    /**
     * Allows the user to edit currently added information and change the value that
     * were currently given or not added originally.
     *
     * @param id This is the unique id which was originally created and saved by
     *           add event
     * @param title This is the title of the added event
     * @param eventID This will be a unique id in order to determine what specific
     *                event to edit or delete once added.
     * @param eventDate This is the date for the event
     * @param tag This is a tag for if we decide to implement a filtering feature
     * @param details This will allow user to add more detail to an event
     * @param color This allows the user to choose a color to be more recognizable
     *              in both the list and calendar once added to database
     * @param time This allows user to insert a time for when the event is happening
     * @return Allows the database to update information given by the user from editing
     *         and therefore changes info given throughout the Calendar App
     */
    public boolean editEventById(int id, String title, String eventID, String eventDate, String tag, String details, String color, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, title);
        cv.put(KEY_EVENTID, eventID);
        cv.put(KEY_EVENTDATE, eventDate);
        cv.put(KEY_EVENTTAG, tag);
        cv.put(KEY_EVENTDETAILS, details);
        cv.put(KEY_EVENTCOLOR, color);
        cv.put(KEY_TIME, time);

        return db.update(TABLE_EVENTS, cv, "_id="+id, null) > 0;

    }
}
