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

/**
 * Constructs the main structure of the app, connecting the the layouts to database
 * and also showing the main calendar and view of the interface. MainActivity also
 * refreshes the view of whatever is changed throughout the other methods.
 */


public class MainActivity extends AppCompatActivity {

    CalendarView cale;
    EventDatabase eventdb;
    @Override

    /**
     * Creates a new database and beginning view of the Calendar app
     *
     * @param savedInstanceState Allows program to save data being changed by the user
     *                           whenever they are changing the view or information of
     *                           the program, in conjuction to changing layout when
     *                           updated info is added to eventdb
     */
    protected void onCreate(Bundle savedInstanceState) {
        eventdb = new EventDatabase(this); //Creates a new database in order to add to calendar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Shows the layout for the main view of the Calendar View
        cale = (CalendarView) findViewById(R.id.cale);

        cale.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            /**
             * Allows user to select a particular day to start adding event information
             * and to update the date selected to show if there is any previous information
             * added to that chosen date
             *
             * @param view Reference to CalendarView
             * @param year Collects data on the selected year that the user wants to
             *             edit or view in calendar when clicking on calendar
             * @param month Collects data on the selected month that the user wants to
             *              edit or view in calendar when clicking on calendar
             * @param dayOfMonth Collects data on the selected day that the user wants to
             *                   edit or view in calendar when clicking on calendar
             */
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

        /**
         * Creates the add button which allows user to add, update, or even delete events
         *
         * @param View.OnClickListener() References to View since it will carry out to
         *                               changing the view of the interface
         */
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

            /**
             * Changes the view after clicking on selected date and lets user see
             * a new window which allows them to alter or add a new event
             *
             * @param parent Reference to AdapterVoew
             * @param view  Referemce to View and also does the gruntwork of the interface
             *              appearing for the user
             * @param position Gets the position of where to click for the add icon
             * @param id Gets time of when the date was clicked from the calendar
             */
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
    /**
     * Happens after onCreate where the user starts to go into the Calendar App
     */
    public void onStart() {
        super.onStart();
        refreshListView();
    }

    @Override
    /**
     * Keeps data when user gets back to the Calendar after making changes
     */
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

    /**
     * Once the user has added or changed information in the interface, the calendar
     * should refresh to show those changes using this method
     */
    public void refreshListView() {
        Cursor eventCursor = eventdb.getEventForDate(eventdbDateKey);
        CustomCursorAdapter cca = new CustomCursorAdapter(getApplicationContext(), eventCursor);
        ListView lv = findViewById(R.id.lstvw_event);
        lv.setAdapter(cca);
    }

}
