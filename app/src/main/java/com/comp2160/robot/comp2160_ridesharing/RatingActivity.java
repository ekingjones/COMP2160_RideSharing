package com.comp2160.robot.comp2160_ridesharing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingActivity extends AppCompatActivity {
RatingBar rating;
TextView ratingvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        rating = (RatingBar)findViewById(R.id.rating);
        ratingvalue=(TextView)findViewById(R.id.ratingvalue);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingvalue.setText("Rating Value:"+ rating);
            }
        });

    }
}
