package com.example.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * This file allows the user to view the events of the
 * Calendar app. This goes through the database in order
 * to grab the information so the user can change, save,
 * edit, or delete a selected event
 */
public class ViewEventActivity extends Activity {
    EventDatabase eventdb;

    @Override
    /**
     * This creates the main layout of the view event/edit window
     * and also prepare the database to read the already inputted
     * information given through add event.
     *
     * @param savedInstanceState This allows the layout to become saved
     *                           and show the user what they need to insert
     *                           for viewing an activity
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);
        eventdb = new EventDatabase(this);                  //creates a reference to the View for future use
        delButton = findViewById(R.id.btnDelete);
        saveEditButton = findViewById(R.id.btnSaveEdit);
        editSwitch = findViewById(R.id.swEdit);
        saveEditButton.setVisibility(View.INVISIBLE);


    }

    @Override
    /**
     * Happens after onCreate where the user starts to go into the view event window
     */
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        eventdbDateTimeKey = intent.getStringExtra("eventTimeID");
        System.out.println("EEKFMEKWFM: " + eventdbDateTimeKey);
        Cursor eventTimeCursor = eventdb.getEventForTime(eventdbDateTimeKey);
        eventTimeCursor.moveToFirst();

        txtDBTitle = findViewById(R.id.txtDBTitle);
        txtDBDetails = findViewById(R.id.txtDBDetails);
        txtDBTag = findViewById(R.id.txtDBTag);
        txtDBDate = findViewById(R.id.txtDBDate);
        txtDBTime = findViewById(R.id.txtDBStart);
        txtDBColor = findViewById(R.id.txtDBColor);

        eventID = eventTimeCursor.getInt(0);
        title = eventTimeCursor.getString(1);
        details = eventTimeCursor.getString(6);
        tag = eventTimeCursor.getString(4);
        date = formatDate(eventTimeCursor.getString(3), "-");
        time = eventTimeCursor.getString(7);
        color = eventTimeCursor.getString(5);

        txtDBTitle.setText(title);
        txtDBDetails.setText(details);
        txtDBTag.setText(tag);
        txtDBDate.setText(date);
        txtDBTime.setText(time);
        txtDBColor.setText(color);


        editSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * This is the mainframe to the buttons which allow the user to
             * save or delete any changes to the event selected.
             *
             * @param buttonView This enables or disables the buttons visibility on
             *                   whether the user wants to make changes to the
             *                   event
             * @param isChecked This checker allows for the user to make changes
             *                  to an event once that button or slider is checked
             */
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    delButton.setVisibility(View.INVISIBLE);
                    saveEditButton.setVisibility(View.VISIBLE);
                    MakeAllTextFieldsEditable(true);
                }
                else {
                    delButton.setVisibility(View.VISIBLE);
                    saveEditButton.setVisibility(View.INVISIBLE);
                    MakeAllTextFieldsEditable(false);
                }
            }
        });


        delButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This allows the user to delete an event when they select
             * the button/slider option in the edit window
             *
             * @param v References to View where the delete option is available
             *          to the user
             */
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    /**
                     * Once the user clicks the confirmation for the deletion,
                     * this code allows the database to remove the selected info
                     * for the event
                     *
                     * @param dialog Confirms to the user that information is
                     *               deleted from the database
                     * @param which Compares values for verification
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (eventdb.deleteEventById(eventID)) { //Delete the row in the database
                            Toast toast = Toast.makeText(getApplicationContext(), "Deletion Successful", Toast.LENGTH_SHORT);
                            toast.show();
                            finish();
                        }
                        else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Deletion Failed", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    /**
                     * This executes if the user exits the deletion statement
                     * where it will dismiss statement whether it clicks or declines
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        saveEditButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This searches the database for the edited values and
             * saves the new values to the database
             *
             * @param v Reference to View as it shows the values of
             *          the edited values
             */
            public void onClick(View v) {

                EditText etTitle = findViewById(R.id.txtDBTitle);
                EditText etTag = findViewById(R.id.txtDBTag);
                EditText etDate = findViewById(R.id.txtDBDate);
                EditText etTime = findViewById(R.id.txtDBStart);
                EditText etDetails = findViewById(R.id.txtDBDetails);
                EditText etColor = findViewById(R.id.txtDBColor);

                final String date = etDate.getText().toString();
                String []dateArr = date.split("/");
                String typedMonth = dateArr[0];
                String typedDay = dateArr[1];
                String typedYear = dateArr[2];

                final String useDate = typedYear + "-" + typedMonth + "-" + typedDay;
                final String timeEventID = useDate + "-" + etTime.getText().toString();
                final String title = etTitle.getText().toString();
                final String tag = etTag.getText().toString();
                final String details = etDetails.getText().toString();
                final String color = etColor.getText().toString();
                final String time = etTime.getText().toString();

                System.out.println("Swig swag whats in the bag");
                System.out.println(useDate);
                System.out.println(timeEventID);
                System.out.println(title);
                System.out.println(tag);
                System.out.println(details);
                System.out.println(color);
                System.out.println(time);

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to edit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    /**
                     * This checks if the edit has been successful and to see if
                     * the database has successfully added the edits in its system
                     *
                     * @param dialog This will dismiss the dialog that comes with succession
                     * @param which Compares values for verification
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (eventdb.editEventById(eventID, title, timeEventID, useDate, tag, details, color, time)) { //Delete the row in the database
                            Toast toast = Toast.makeText(getApplicationContext(), "Edit Successful", Toast.LENGTH_SHORT);
                            toast.show();
                            finish();
                        }
                        else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Edit Failed", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    /**
                     * This activates when the edit has been made and the
                     * button has been already pressed
                     *
                     * @param dialog This will dismiss the dialog that comes with succession
                     * @param which Compares values for verification
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }
    private String eventdbDateTimeKey;
    private int eventID;
    private Button saveEditButton;
    private Button delButton;
    private Switch editSwitch;
    private EditText txtDBTitle;
    private EditText txtDBDetails;
    private EditText txtDBTag;
    private EditText txtDBDate;
    private EditText txtDBTime;
    private EditText txtDBColor;
    private String title;
    private String details;
    private String tag;
    private String date;
    private String time;
    private String color;

    /**
     * This will allow all of the fields to be editable
     *
     * @param b checks if these values can be editable
     *          by true or false
     */
    private void MakeAllTextFieldsEditable(boolean b) {
        txtDBTitle.setEnabled(b);
        txtDBDetails.setEnabled(b);
        txtDBTag.setEnabled(b);
        txtDBDate.setEnabled(b);
        txtDBTime.setEnabled(b);
        txtDBColor.setEnabled(b);
    }

    /**
     * This allows the date to be formatted to look more
     * coherent
     *
     * @param date Grabs the date information from the values given
     * @param separator Basically seperates the date values to make
     *                  it more coherent
     * @return The final result for the formatted date
     */
    public String formatDate(String date, String separator) {
        String []dateArr = date.split(separator);
        return dateArr[1] + "/" + dateArr[2] + "/" + dateArr[0];
    }
}
