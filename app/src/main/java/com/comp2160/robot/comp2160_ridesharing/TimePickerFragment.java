package com.comp2160.robot.comp2160_ridesharing;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;
import android.text.format.DateFormat;
import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    static int userHour;
    static int userMin;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // getting current time as a default
        final Calendar mCal = Calendar.getInstance();
        int hour = mCal.get(Calendar.HOUR_OF_DAY);
        int min = mCal.get(Calendar.MINUTE);

        // make new calender instance and return it with current time
        return new TimePickerDialog(getActivity(), this, hour, min, DateFormat.is24HourFormat(getActivity()));
    }

        @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        userHour = hourOfDay;
        userMin = minute;
    }
}
