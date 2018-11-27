package com.example.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.Button;
import android.view.*;

import java.io.Serializable;

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
                day = dayOfMonth;
                inputMonth = month;
                inputYear = year;
                Intent intent = new Intent(view.getContext(), ViewEventActivity.class);
                intent.putExtra("day", day);
                intent.putExtra("month", inputMonth+1);
                intent.putExtra("year", inputYear);
                startActivity(intent);

            }
        });


        final Button plusButton = findViewById(R.id.addevent_btn); //Allows user to press the plus button in order to add more events to the database
        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddEventActivity.class);
                intent.putExtra("day", day);
                intent.putExtra("month", inputMonth+1);
                intent.putExtra("year", inputYear);
                startActivity(intent);
            }
        });
    }
    private int day;
    private int inputMonth;
    private int inputYear;

}
