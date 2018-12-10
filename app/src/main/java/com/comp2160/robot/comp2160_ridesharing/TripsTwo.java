package com.comp2160.robot.comp2160_ridesharing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class TripsTwo extends AppCompatActivity {

    // firebase vars
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "TRIP_TAG";

    // trip params
    String start;
    String end;
    int seats;

    // list for storing trips
    //private List<TripModel> tripsList;

    RecyclerView recyclerView = findViewById(R.id.mrecycler_view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        //tripsList = new ArrayList<>();

        // init recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // getting user data from intent
        start = getIntent().getStringExtra("START");
        end = getIntent().getStringExtra("END");
        seats = getIntent().getIntExtra("SEATS", 0);
        Log.d(TAG, "data retrived: " + start + end + seats);
        getData();

    }

    public void getData(){
        // this snippet makes firebase behave properly, dont know why
        // DONT TOUCH
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);



        // looks up trips according to user input
        db.collection("avail_trips")
                .whereEqualTo("start_local", start)
                .whereEqualTo("end_local", end)
                .whereGreaterThanOrEqualTo("number_of_seats", seats)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //TripModel trip = document.toObject(TripModel.class);


                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void displayData(){

    }

}
