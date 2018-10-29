package com.comp2160.robot.comp2160_ridesharing;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar; /* **THIS IS IMPORTANT you need this import instead of
                                             android.widget.Toolbar, it will not function */
import android.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

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
        toolbar.setSubtitle("Find a Ride");         // *add this to string file
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // handling click events in the nav menu
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

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
        // drops a pin at specified location
        LatLng seattle = new LatLng(47.6062095, -122.3320708);
        mMap.addMarker(new MarkerOptions().position(seattle).title("Seattle"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seattle));

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
