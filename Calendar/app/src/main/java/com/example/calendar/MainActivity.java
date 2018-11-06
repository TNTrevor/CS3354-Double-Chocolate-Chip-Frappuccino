package com.example.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.Button;
import android.view.*;

public class MainActivity extends AppCompatActivity {



    CalendarView cale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cale = (CalendarView) findViewById(R.id.cale);
        cale.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(),month+ "/"+ dayOfMonth + "/" + year, Toast.LENGTH_SHORT).show();
                System.out.println("YR: " + year + "MN: " + month);
                day = dayOfMonth;
                inputMonth = month;
                inputYear = year;
            }
        });


        final Button plusButton = findViewById(R.id.addevent_btn);
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
