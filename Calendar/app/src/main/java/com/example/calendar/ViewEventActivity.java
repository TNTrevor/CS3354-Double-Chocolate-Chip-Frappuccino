package com.example.calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class ViewEventActivity extends Activity {
    EventDatabase eventdb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);
        eventdb = new EventDatabase(this);

        Intent intent = getIntent();
        selectedDay = intent.getIntExtra("day", 01);
        selectedYear = intent.getIntExtra("year", 2000);
        selectedMonth = intent.getIntExtra("month", 01);

        eventdbDateKey = selectedYear + "-" + selectedMonth + "-" + selectedDay;
        Cursor eventCursor = eventdb.getEventForDate(eventdbDateKey);
        //eventCursor.moveToFirst();

        CustomCursorAdapter sca = new CustomCursorAdapter(this, eventCursor);
        ListView lv = findViewById(R.id.listview);
        lv.setAdapter(sca);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private int selectedDay;
    private int selectedYear;
    private int selectedMonth;
    private String eventdbDateKey;
}
