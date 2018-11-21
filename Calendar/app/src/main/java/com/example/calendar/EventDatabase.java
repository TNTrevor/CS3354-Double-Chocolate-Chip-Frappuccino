package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Date;

public class EventDatabase extends SQLiteOpenHelper implements Serializable {
    public static String DATABASE_NAME = "event_database";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_EVENTS = "events";
    private static final String KEY_EVENTID = "eventid";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " +
                                                        TABLE_EVENTS +
                                                        "(" +
                                                        KEY_ID +
                                                        " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        KEY_NAME +
                                                        " TEXT," +
                                                        KEY_EVENTID +
                                                        " INTEGER" +
                                                        ");";

    //Default Constructor
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

    public long addEvent(String name, String eventID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_EVENTID, eventID);

        long insert = db.insert(TABLE_EVENTS, null, cv);
        return insert;
    }

    public Hashtable<String, String> getEvent(String eventID) {
        //Create a hash table with each of the items from the database
        Hashtable<String, String> table = new Hashtable<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + KEY_EVENTID + " = '" + eventID + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //Check if we get a result
        if (c.moveToFirst()) {

            //Add all of the event data we need to the hash table
            String name = c.getString(c.getColumnIndex(KEY_NAME));
            System.out.println("NAME: " + name);
            table.put(KEY_NAME, name);
        }
        //Return the table
        return table;
    }
}
