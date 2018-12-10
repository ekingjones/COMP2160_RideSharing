package com.comp2160.robot.comp2160_ridesharing;

// this class will be used to import trips from the firestore database when
// users request a trip
public class TripModel {
    private boolean ac_check;
    private String car_make;
    private String car_model;
    private int car_year;
    private String end_local;
    private boolean heat_check;
    private int number_of_seats;
    private String start_local;

    // default constructor
    public TripModel(){
        ac_check = false;
        car_make = "";
        car_model = "";
        car_year = 0;
        end_local = "";
        heat_check = false;
        number_of_seats = 0;
        start_local = "";
    }

    // constructor with parameters
    public TripModel(boolean mAC, String mCarMake, String mCarModel, int mCarYear, String mEndLocal,
                     boolean mHC, int mSeats, String mStartLocal){
        ac_check = mAC;
        car_make = mCarMake;
        car_model = mCarModel;
        car_year = mCarYear;
        end_local = mEndLocal;
        heat_check = mHC;
        number_of_seats = mSeats;
        start_local = mStartLocal;


    }





    }
