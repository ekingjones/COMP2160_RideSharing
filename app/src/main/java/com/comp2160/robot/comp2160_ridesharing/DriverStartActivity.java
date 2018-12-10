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
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
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
    public static final String FILE_TAG = "FIRESTORE_DB_WRITES";

    // cloud vars
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // main component variables
    private DrawerLayout mDrawerLayout;

    // trip param variables
    private Time depatTime;
    private Date departDate;
    private String startLocation;
    private String endLocation;
    private String optionsText;
    private boolean acCheck = false;
    private boolean wheelchairCheck = false;
    private boolean heatCheck = false;
    private boolean otherCheck = false;

    // car data variables
    private int carYear;
    private String carMake;
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
        Integer driverHour = TimePickerFragment.userHour;
        Integer driverMin = TimePickerFragment.userMin;
        if(driverHour == null || driverMin == null){

        }
        else{
            showTime(driverHour, driverMin);
        }

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

    // method to open maps layout
    public void planOnMap(View v){
        Intent mapsIntent = new Intent(DriverStartActivity.this, DirectionsActivity.class);
        startActivity(mapsIntent);
    }

    // method to check which ammenities check boxes are checked
    public void onCheckboxClicked(View v){
        // bool to confirm if a box is checked
        boolean checked = ((CheckBox) v).isChecked();

        // check which box was ticked
        switch(v.getId()){
            case R.id.acCheckBox:
                if(checked)
                    acCheck = true;
                else
                    acCheck = false;
                break;
            case R.id.wheelchairCheckBox:
                if(checked)
                    wheelchairCheck = true;
                else
                    wheelchairCheck = false;
                break;
            case R.id.heaterCheckBox:
                if(checked)
                    heatCheck = true;
                else
                    heatCheck = false;
                break;
            case R.id.otherCheckBox:
                if(checked)
                    otherCheck = true;
                else
                    otherCheck = false;
        }
    }


    // updates data from user entries and assigns to global vars for easy access
    public void getData(){
        // get all the required data from text fields
        TextView startLocal = (TextView) (findViewById(R.id.startingLocation));
        TextView endLocal = (TextView) findViewById(R.id.destLocation);
        TextView carBrand = (TextView) findViewById(R.id.makeEditText);
        TextView carMod = (TextView) findViewById(R.id.modelEditText);
        TextView carAge = (TextView) findViewById(R.id.yearEditText);
        TextView seatNum = (TextView) findViewById(R.id.seatsEditText);
        startLocation = startLocal.getText().toString();
        endLocation = endLocal.getText().toString();
        carMake = carBrand.getText().toString();
        carModel = carMod.getText().toString();
        carYear = Integer.parseInt(carAge.getText().toString());
        numOfSeats = Integer.parseInt(seatNum.getText().toString());

        // dealing with optionals
        if(otherCheck = true) {
            TextView options = (TextView) findViewById(R.id.otherEditText);
            optionsText = options.getText().toString();
        }

    }


    // method to get all data from trip to upload to firestore db
    public void sendData(View v){
        // calls getData method to update the trip info before submitting
        getData();

        // writes to a collection of avalible trips
        Map<String, Object> avail_trips = new HashMap<>();

        // gets user entry data and submits to available trips database
        avail_trips.put("start_local", startLocation);
        avail_trips.put("end_local", endLocation);
        avail_trips.put("car_make", carMake);
        avail_trips.put("car_model", carModel);
        avail_trips.put("car_year", carYear);
        avail_trips.put("number_of_seats", numOfSeats);
        avail_trips.put("ac_check", acCheck);
        avail_trips.put("heat_check", heatCheck);

        // listeners for success and fail
        db.collection("avail_trips")
                // take note, that the line below adds to the collection with a randomly generated doc id
                // if this is ever successful, there may be name collision, but for now, no need to worry
                .add(avail_trips)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(FILE_TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(FILE_TAG, "Error adding document", e);
                    }
                });

    }


}
