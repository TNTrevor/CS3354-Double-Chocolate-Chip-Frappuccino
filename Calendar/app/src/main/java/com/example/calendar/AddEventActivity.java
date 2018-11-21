package com.example.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import java.io.Serializable;

public class AddEventActivity extends Activity implements Serializable {

    EditText Title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.add_event);

        Intent intent = getIntent();
        int selectedDay = intent.getIntExtra("day", 01);
        int selectedYear = intent.getIntExtra("year", 2000);
        int selectedMonth = intent.getIntExtra("month", 01);

        String autoFillDate = (selectedMonth+ "/"+ selectedDay + "/" + selectedYear);
        EditText dateText = findViewById(R.id.editText8);
        dateText.setText(autoFillDate);
    }
}
