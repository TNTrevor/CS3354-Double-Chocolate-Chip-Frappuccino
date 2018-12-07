package com.example.calendar;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 *This is an adapter for the Cursor which allows the database to
 * be managed properly and send the correct info to the Calendar
 * App.
 * @see MainActivity
 * @see EventDatabase
 */
class CustomCursorAdapter extends CursorAdapter {
    public CustomCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    /**
     * This correlates to the listview shown after changes are
     * made throughout adding an event
     *
     * @param context Reference to Context to where it reaches the database
     * @param cursor Reference to Cursor which has information from the
     *               database
     * @param parent Reference to ViewGroup which changes the view for the
     *               user and has specific information for each event saved
      */
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listview_row, parent, false);
    }

    @Override
    /**
     * Adjusts the view for when adding the information for events
     * @param view Reference to View
     * @param context Reference to Context to where it reaches the database
     * @param cursor Reference to Cursor which has information from the
     *               database
     */
    public void bindView(View view, Context context, Cursor cursor) {
        TextView time = (TextView)view.findViewById(R.id.time);
        TextView title = (TextView)view.findViewById(R.id.title);

        time.setText(cursor.getString(cursor.getColumnIndex("time")));
        title.setText(cursor.getString(cursor.getColumnIndex("title")));
    }
}
