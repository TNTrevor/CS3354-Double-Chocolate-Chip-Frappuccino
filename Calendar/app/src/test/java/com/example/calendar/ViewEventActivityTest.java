package com.example.calendar;

import org.junit.Test;

import static org.junit.Assert.*;

public class ViewEventActivityTest {

    @Test
    public void onCreate() {
    }

    @Test
    public void onStart() {
    }

    @Test
    public void formatDate() {
        String input = "11@02@1995";
        String output;
        String expected = "11/02/1995";

        ViewEventActivity view = new ViewEventActivity();
        output = view.formatDate(input, "@");

        assertEquals(expected,output);
    }
}