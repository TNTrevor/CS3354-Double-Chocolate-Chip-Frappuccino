package com.example.calendar;

import org.junit.Test;

import static org.junit.Assert.*;

public class ViewEventActivityTestWrong {

    @Test
    public void formatDate() {
        String input = "11@02@1995";
        String output;
        String expected = "11021995";

        ViewEventActivity view = new ViewEventActivity();
        output = view.formatDate(input, "@");

        assertFalse(expected.equals(output));
    }
}