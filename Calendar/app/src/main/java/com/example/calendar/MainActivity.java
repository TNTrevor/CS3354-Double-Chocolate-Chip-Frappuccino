package com.example.calendar;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.*;

public class MainActivity extends AppCompatActivity {

    CalendarView cale;
    EventDatabase eventdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        eventdb = new EventDatabase(this); //Creates a new database in order to add to calendar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Shows the layout for the main view of the Calendar View
        cale = (CalendarView) findViewById(R.id.cale);

        cale.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) { // Depending on the Selected day, The user can be able to add an event the database on that chosen date using the given information
                inputDay = dayOfMonth;
                inputMonth = month;
                inputYear = year;
                eventdbDateKey = inputYear + "-" + (inputMonth+1) + "-" + inputDay;
                Cursor eventCursor = eventdb.getEventForDate(eventdbDateKey);
                CustomCursorAdapter cca = new CustomCursorAdapter(getApplicationContext(), eventCursor);
                ListView lv = findViewById(R.id.lstvw_event);
                lv.setAdapter(cca);
            }
        });


        final Button plusButton = findViewById(R.id.addevent_btn); //Allows user to press the plus button in order to add more events to the database
        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddEventActivity.class);
                intent.putExtra("day", inputDay);
                intent.putExtra("month", inputMonth+1);
                intent.putExtra("year", inputYear);
                startActivity(intent);
            }
        });

        ListView eventList = findViewById(R.id.lstvw_event);
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvTime = (TextView)view.findViewById(R.id.time);
                String time = tvTime.getText().toString();
                Intent intent = new Intent(view.getContext(), ViewEventActivity.class);
                String eventTimeID = eventdbDateKey + "-" + time;
                intent.putExtra("eventTimeID", eventTimeID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshListView();
    }

    @Override
    public void onResume(){
        super.onResume();

        //When resuming, re-fill the listview so that anything that was changed will show
        refreshListView();
    }
    private int inputDay;
    private int inputMonth;
    private int inputYear;
    private String eventdbDateKey;


    CustomCursorAdapter RetrieveCustomCursorAdapterForCursor(Cursor c) {
        return new CustomCursorAdapter(this, c);
    }

    public void refreshListView() {
        Cursor eventCursor = eventdb.getEventForDate(eventdbDateKey);
        CustomCursorAdapter cca = new CustomCursorAdapter(getApplicationContext(), eventCursor);
        ListView lv = findViewById(R.id.lstvw_event);
        lv.setAdapter(cca);
    }

}
