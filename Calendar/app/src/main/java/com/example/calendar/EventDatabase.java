package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

public class EventDatabase extends SQLiteOpenHelper implements Serializable {
    public static String DATABASE_NAME = "event_database";
    private static int DATABASE_VERSION = 2;
    private static final String TABLE_EVENTS = "events";
    private static final String KEY_EVENTID = "eventid";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_EVENTDATE = "eventdate";
    private static final String KEY_EVENTTAG = "tag";
    private static final String KEY_EVENTCOLOR = "color";
    private static final String KEY_EVENTDETAILS = "details";

    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " +
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

    public long addEvent(String name, String eventID, String eventDate, String tag, String details, String color) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, name);
        cv.put(KEY_EVENTID, eventID);
        cv.put(KEY_EVENTDATE, eventDate);
        cv.put(KEY_EVENTTAG, tag);
        cv.put(KEY_EVENTDETAILS, details);
        cv.put(KEY_EVENTCOLOR, color);


        long insert = db.insert(TABLE_EVENTS, null, cv);
        return insert;
    }

    public Cursor getEventForDate(String eventID) {
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + KEY_EVENTID + " = '" + eventID + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Return the Cursor with all of the information
        return c;
    }

    public Cursor getEventForTime(String eventDate) {
        //This will return a cursor with one row
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + KEY_EVENTDATE + " = '" + eventDate + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        return c;
    }
}