package com.comp2160.robot.comp2160_ridesharing;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class FindRideActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

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
}
