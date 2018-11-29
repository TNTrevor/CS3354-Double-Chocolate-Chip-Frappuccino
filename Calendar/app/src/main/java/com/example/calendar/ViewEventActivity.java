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

public class ViewEventActivity extends Activity {
    EventDatabase eventdb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);
        eventdb = new EventDatabase(this);
        delButton = findViewById(R.id.btnDelete);
        saveEditButton = findViewById(R.id.btnSaveEdit);
        editSwitch = findViewById(R.id.swEdit);
        saveEditButton.setVisibility(View.INVISIBLE);


    }

    @Override
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
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        saveEditButton.setOnClickListener(new View.OnClickListener() {
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

    private void MakeAllTextFieldsEditable(boolean b) {
        txtDBTitle.setEnabled(b);
        txtDBDetails.setEnabled(b);
        txtDBTag.setEnabled(b);
        txtDBDate.setEnabled(b);
        txtDBTime.setEnabled(b);
        txtDBColor.setEnabled(b);
    }

    private String formatDate(String date, String separator) {
        String []dateArr = date.split(separator);
        return dateArr[1] + "/" + dateArr[2] + "/" + dateArr[0];
    }
}
