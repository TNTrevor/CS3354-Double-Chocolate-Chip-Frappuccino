package com.example.calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEventActivity extends Activity {

    EditText Title;
    EventDatabase eventdb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        eventdb = MainActivity.eventdb;

        final Button submitButton = findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Check each required field for input. Don't store the info
                EditText etTitle = findViewById(R.id.txtTitle);
                EditText etTag = findViewById(R.id.txtTag);
                EditText etDate = findViewById(R.id.txtDate);
                EditText etTime = findViewById(R.id.txtStart);
                EditText etDetails = findViewById(R.id.txtDetails);
                EditText etColor = findViewById(R.id.txtColor);

                System.out.println("Clicked!");
                //Check for each important field
                if (isEmpty(etTitle) || isEmpty(etDate) || isEmpty(etTime)) {
                    System.out.println("Empty textfield");
                }
                else {
                    //Create the correct event date format
                    String date = etDate.toString().replace("/", "-");
                    String timeEventID = date + etTime.toString();
                    DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
                    long Time = -1;
                    try {
                        Time = formatter.parse(etTime.toString()).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println("TIME: " + Time);
                    //long l = eventdb.addEvent(etTitle.toString(), date, timeEventID, etTag.toString(), etDetails.toString(), etColor.toString());
                    //System.out.println("ROWS INSERTED: " + l);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        int selectedDay = intent.getIntExtra("day", 01);
        int selectedYear = intent.getIntExtra("year", 2000);
        int selectedMonth = intent.getIntExtra("month", 01);
        String autoFillDate = (selectedMonth + "/" + selectedDay + "/" + selectedYear);
        EditText dateText = findViewById(R.id.txtDate);
        dateText.setText(autoFillDate);
    }

    private boolean isEmpty(EditText et) {
        return et.getText().toString().trim().length() == 0;
    }

}
