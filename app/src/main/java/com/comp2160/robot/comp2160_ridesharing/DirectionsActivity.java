package com.comp2160.robot.comp2160_ridesharing;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class DirectionsActivity extends FragmentActivity implements OnMapReadyCallback {

    // static and final values
    public static final String TAG = "MAP_TAG";
    //public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCAL_PERMISSON_REQ_CODE = 4000;
    public static final float DEFAULT_ZOOM = 15;

    // check for location services permissionsDoing
    private boolean mLocationPermissionGranted;

    // maps vars
    private GoogleMap mMap;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets content view for map
        setContentView(R.layout.activity_directions);

        // get the location permissions from the user
        getLocationPermissions();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "map ready");
        mMap = googleMap;

        if (mLocationPermissionGranted) {
            getCurrentLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    // initalizes the map
    private void initMap(){
        Log.d(TAG, "init map");
        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(DirectionsActivity.this);
    }




    // method to get the current device location
    private void getCurrentLocation(){
        Log.d(TAG, "attempting ot get device location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionGranted){
                Task loc = mFusedLocationProviderClient.getLastLocation();
                loc.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "location found");
                            Location currentLocal = (Location) task.getResult();
                            // move cam to new location
                            moveCam(new LatLng(currentLocal.getLatitude(), currentLocal.getLongitude()),DEFAULT_ZOOM);

                        } else{
                            Log.d(TAG, "location null");
                            Toast.makeText(DirectionsActivity.this, "Unable to get current location", Toast.LENGTH_LONG);
                        }
                    }
                });
            }


        }catch (SecurityException e){
            Log.d(TAG, "security exception" + e.getMessage());
        }
    }



    // custom method to move camera around based on what is passed to it
    // i know there is a built in function to move the camera for a map object, but it just was not behaving properly
    // hence, this method
    private void moveCam(LatLng ltlg, float zoom){
        Log.d(TAG, "moving cam to" + ltlg.latitude + ltlg.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltlg, zoom));

    }



    // method to request location permissions from users
    private void getLocationPermissions(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        // string arr to hold permissions params
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCAL_PERMISSON_REQ_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCAL_PERMISSON_REQ_CODE);
        }
    }



    // custom method to deal with permssion requests
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionGranted = false;

        switch(requestCode){
            case LOCAL_PERMISSON_REQ_CODE:{
                // this checks if grant results has any values
                // if it has at least one, then some kind of permission was granted
                // then steps through and checks each item, ie: fine and coarse permissions
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted= false;
                            Log.d(TAG, "permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "permission granted");
                    mLocationPermissionGranted = true;
                    initMap();
                }
            }
        }
    }
}
