package com.comp2160.robot.comp2160_ridesharing;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.sql.Driver;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DriverStartActivity extends AppCompatActivity {
    // tags
    public static final String mTAG = "COMP2160";
    // cloud vars
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // main component variables
    private DrawerLayout mDrawerLayout;

    // trip param variables
    private Time depatTime;
    private Date departDate;
    private String startLocation;
    private String endLocation;

    // car data variables
    private String carModel;
    private int numOfSeats;



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

    public void showTimePickerFragment(View v){
        DialogFragment newFrag = new TimePickerFragment();
        newFrag.show(getSupportFragmentManager(), "timePicker");
        int driverHour = TimePickerFragment.userHour;
        int driverMin = TimePickerFragment.userMin;
        showTime(driverHour, driverMin);
        //String testHour = Integer.toString(driverHour);
        //String testMin = Integer.toString(driverMin);
        //String test = ("Chosen Time:" + testHour + testMin);
        //Toast.makeText(this, test, Toast.LENGTH_LONG).show();

    }

    // TODO
    // problem, the toast fires ON the click, not after the time has been set
    // so right now, it is always zero
    // need to find a way to actually get and display the time
    // my theory, is to put this in the onTimeSet method in the TimePickerFragment
    // then it may just fire after the time is picked, instead of on click
    public void showTime(int mHour, int mMin){
        String testHour = Integer.toString(mHour);
        String testMin = Integer.toString(mMin);
        String test = ("Chosen Time:" + testHour + testMin);
        Toast.makeText(this, test, Toast.LENGTH_LONG).show();
    }

    // updates data from user entries and assigns to global vars for easy access
    public void getData(){
        TextView startLocal = (TextView) (findViewById(R.id.startingLocation));
        TextView endLocal = (TextView) findViewById(R.id.destLocation);
        startLocation = startLocal.getText().toString();
        endLocation = endLocal.getText().toString();
    }


    // method to get all data from trip to upload to firestore db
    public void sendData(View v){
        // calls getData method to update the trip info before submitting
        getData();

        // gets the collection of available trips for cloud
        Map<String, Object> avail_trips = new HashMap<>();

        // gets user entry data and submits to available trips database
        avail_trips.put("depart_local", startLocation);
        avail_trips.put("dest_local", endLocation);

        // listeners for success and fail
        db.collection("avail_trips")
                .add(avail_trips)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(mTAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(mTAG, "Error adding document", e);
                    }
                });

    }


}
