package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

public class EventDatabase extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "event_database";
    private static int DATABASE_VERSION = 4;
    private static final String TABLE_EVENTS = "events";
    private static final String KEY_EVENTID = "eventid";
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_EVENTDATE = "eventdate";
    private static final String KEY_EVENTTAG = "tag";
    private static final String KEY_EVENTCOLOR = "color";
    private static final String KEY_EVENTDETAILS = "details";
    private static final String KEY_TIME = "time";

    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " +   //allows user to add information toward a new event
                                                        TABLE_EVENTS +
                                                        "(" +
                                                        KEY_ID +
                                                        " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        KEY_TITLE +
                                                        " TEXT," +
                                                        KEY_EVENTID +
                                                        " INTEGER," +
                                                        KEY_EVENTDATE +
                                                        " TEXT," +
                                                        KEY_EVENTTAG +
                                                        " TEXT," +
                                                        KEY_EVENTCOLOR +
                                                        " TEXT," +
                                                        KEY_EVENTDETAILS +
                                                        " TEXT," +
                                                        KEY_TIME +
                                                        " TEXT" +
                                                        ");";


    //Constructor
    public EventDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_EVENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + "'" + TABLE_EVENTS + "'");
        onCreate(sqLiteDatabase);
    }

    public long addEvent(String title, String eventID, String eventDate, String tag, String details, String color, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, title);
        cv.put(KEY_EVENTID, eventID);
        cv.put(KEY_EVENTDATE, eventDate);
        cv.put(KEY_EVENTTAG, tag);
        cv.put(KEY_EVENTDETAILS, details);
        cv.put(KEY_EVENTCOLOR, color);
        cv.put(KEY_TIME, time);


        long insert = db.insert(TABLE_EVENTS, null, cv);
        return insert;
    }

    public Cursor getEventForTime(String eventID) {
        //This will return a cursor with one row
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + KEY_EVENTID + " = '" + eventID + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Return the Cursor with all of the information
        return c;
    }

    public Cursor getEventForDate(String eventDate) {
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + KEY_EVENTDATE + " = '" + eventDate + "' ORDER BY " + KEY_EVENTID + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        return c;
    }
}
