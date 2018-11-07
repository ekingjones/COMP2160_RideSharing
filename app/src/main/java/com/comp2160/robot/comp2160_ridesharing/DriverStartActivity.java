package com.comp2160.robot.comp2160_ridesharing;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.widget.TimePicker;

import java.sql.Driver;
import java.util.Calendar;

public class DriverStartActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_start);

        // define layout for mDrawerLayout from activity_driver_start layout
        mDrawerLayout = findViewById(R.id.drawer_layout);

        // used to set custom toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Start a Ride");         // TODO add this to string file
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // handling click events in the nav menu
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        int id = menuItem.getItemId();
                        switch (id) {
                            case(R.id.nav_1):
                                Intent i1 = new Intent(DriverStartActivity.this, FindRideActivity.class);
                                startActivity(i1);
                                break;
                            case(R.id.nav_2):
                                mDrawerLayout.closeDrawers();
                                break;
                            case(R.id.nav_3):
                                //Intent i3 = new Intent(DriverStartActivity.this, trip_activity.class);
                                //startActivity(i3);
                                break;
                            case(R.id.nav_4):
                                //Intent i4 = new Intent(DriverStartActivity.this, settings_activity.class);
                                //startActivity(i4);
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    // TODO fix this mess to add time picker, and add date picker as well
    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }*/


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // this method will do something with the time the user picks
        // probably set the text of the label that they touch
    }
}
