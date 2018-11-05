package com.comp2160.robot.comp2160_ridesharing;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar; /* **THIS IS IMPORTANT you need this import instead of
                                             android.widget.Toolbar, it will not function */
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.sax2.Driver;

import java.io.IOException;
import java.util.List;

public class OnboardActivity extends AppCompatActivity implements OnMapReadyCallback {
    // global variables
    private DrawerLayout mDrawerLayout;
    private MapView mapView;
    private GoogleMap mMap;


    // final vars and keys
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        // define layout for mDrawerLayout from activity_onboard layout
        mDrawerLayout = findViewById(R.id.drawer_layout);

        // used to set custom toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Find a Ride");         // TODO *add this to string file
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
                                Intent i2 = new Intent(OnboardActivity.this, DriverStartActivity.class);
                                startActivity(i2);
                                break;
                            case(R.id.nav_3):
                                // TODO add when  trips layout is complete
                                //Intent i3 = new Intent(OnboardActivity.this, trip_layout);
                                //startActivity(i3);
                                break;
                            case(R.id.nav_4):
                                // TODO add when setting layout is complete
                                //Intent i4 = new Intent(OnboardActivity.this, settings_layout);
                                //startActivity(i4);
                                break;

                        }
                        // closes drawer after item is selected
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        // MAPS IMPLEMENTATION

        // this will start the mapView bundle as null
        // see the onSavedInstanceState method fo bundling
        Bundle mapViewBundle = null;
        if (savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        // define map view from layout
        mapView = findViewById(R.id.mapView);
        // define what happens onCreate
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

    }

    // method to get destination from user and display it on map
    public void getUserDestination(View view){
        EditText destQuery = (EditText)findViewById(R.id.destRequest);
        String location = destQuery.getText().toString();
        List<Address> addressList = null;

        if(location != null || !location.equals("")){
            Geocoder geocoder = new Geocoder(this);
            try{
                addressList = geocoder.getFromLocationName(location, 1);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(location));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }

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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // ui settings for display7
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        // drops a pin at specified location
        //LatLng seattle = new LatLng(47.6062095, -122.3320708);
        //mMap.addMarker(new MarkerOptions().position(seattle).title("Seattle"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(seattle));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if(mapViewBundle == null){
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
