package com.ahmeddonkl.GP;

/**
 * Created by Ahmed Donkl on 12/15/2015.
 */
public class Bus {
    private double mNumber;
    private String mStartTime;
    private String mIntervalTime;
    private String mTripDuration;
    private String mStations;
    private double mPrice;
    private double mNumberOfBuses;

    public Bus(double mNumber , String mIntervalTime, double mNumberOfBuses, double mPrice, String mStartTime,String mStations,String mTripDuration) {
        this.mNumber = mNumber;

        this.mStartTime = mStartTime;
        this.mIntervalTime = mIntervalTime;
        this.mNumberOfBuses = mNumberOfBuses;
        this.mPrice = mPrice;
        this.mStartTime = mStartTime;
        this.mStations = mStations;
        this.mTripDuration = mTripDuration;
    }



    public double getmNumber() {
        return mNumber;
    }

    public void setmNumber(double mNumber) {
        this.mNumber = mNumber;
    }

    public String getmStartTime() { return mStartTime;}

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getmIntervalTime() {
        return mIntervalTime;
    }

    public void setmIntervalTime(String mIntervalTime) {
        this.mIntervalTime = mIntervalTime;
    }

    public String getmTripDuration() {
        return mTripDuration;
    }

    public void setmmTripDuratione(String mTripDuration) {
        this.mTripDuration = mTripDuration;
    }

    public String getmStations() {
        return mStations;
    }

    public void setmStations(String mStations) {
        this.mStations = mStations;
    }

    public double getmNumberOfBuses() {
        return mNumberOfBuses;
    }

    public void setmNumberOfBuses(double mNumberOfBuses) {
        this.mNumberOfBuses = mNumberOfBuses;
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }
}
