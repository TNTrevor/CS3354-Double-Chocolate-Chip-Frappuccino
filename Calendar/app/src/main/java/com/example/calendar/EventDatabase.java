package com.example.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDatabase extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "event_database";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_EVENTS = "events";
    private static final String KEY_EVENTID = "eventid";
    private static final String KEY_NAME = "name";

    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " +
                                                        TABLE_EVENTS +
                                                        "(" +
                                                        KEY_EVENTID +
                                                        " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        KEY_NAME +
                                                        " TEXT" +
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
}
