package com.comp2160.robot.comp2160_ridesharing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FindRideActivity extends AppCompatActivity {
    private static final String mTAG = "GET_TAG";
    // firebase vars
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // main component vars
    private DrawerLayout mDrawerLayout;

    // trip queries vars
    private String startLocation;
    private String endLocation;
    private int seatsNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);

        // define layout for mDrawerLayout from activity_onboard layout
        mDrawerLayout = findViewById(R.id.drawer_layout);

        // used to set custom toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Where to?");         // TODO *add this to string file
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // handling click events in the nav menu
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // switch to handle navigation between activities
                        int id = menuItem.getItemId();
                        switch (id) {
                            case(R.id.nav_1):
                                // nav_1 is this current activity, so instead of reloading the activity
                                // with an intent, simply close the drawer and take the user back to the screen
                                mDrawerLayout.closeDrawers();
                                break;
                            case(R.id.nav_2):
                                // nav_2 is the DriverStartActivity, so when this item is selected
                                // the DriverStart activity is started
                                Intent i2 = new Intent(FindRideActivity.this, DriverStartActivity.class);
                                startActivity(i2);
                                break;
                            case(R.id.nav_3):
                                // TODO add when  trips layout is complete
                                //Intent i3 = new Intent(FindRideActivity.this, trip_layout);
                                //startActivity(i3);
                                break;
                            case(R.id.nav_4):
                                // TODO add when setting layout is complete
                                //Intent i4 = new Intent(FindRideActivity.this, settings_layout);
                                //startActivity(i4);
                                break;
                            case(R.id.nav_5):
                                Intent i5 = new Intent(FindRideActivity.this, TestActivity.class);
                                startActivity(i5);

                        }
                        // closes drawer after item is selected
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    // this opens the menu when the icon in the toolbar is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    // method to get data from user
    public void getData(){
        try {
            TextView pickupLocal = (TextView) findViewById(R.id.pickupLocation);
            TextView destLocal = (TextView) findViewById(R.id.destLocation);
            TextView seats = (TextView) findViewById(R.id.numOfSeats);
            startLocation = pickupLocal.getText().toString();
            endLocation = destLocal.getText().toString();
            seatsNum = Integer.parseInt(seats.getText().toString());
        }catch (Exception e){

        }
    }


    // method to get current location
    public void getLocation(View v){
        Intent getLocat = new Intent(FindRideActivity.this, DirectionsActivity.class);
        startActivity(getLocat);
    }

    // method to search for data
    public void tripLookup(View v) {

        // updates and gets data from vars
        getData();

        // intent to send to trips activity
        Intent findTrips = new Intent(FindRideActivity.this, TripsTwo.class);
        findTrips.putExtra("START", startLocation);
        findTrips.putExtra("END", endLocation);
        findTrips.putExtra("SEATS", seatsNum);
        startActivity(findTrips);

    }
}
