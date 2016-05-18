package com.ahmeddonkl.GP;

/**
 * Created by Ahmed Donkl on 12/15/2015.
 */
public class Bus {
    private double mNumber;
    private String mStartTime;
    private String mIntervalTime;
    private Double mTripDuration;
    private String mStationsCoordinates;
    private String mStations;
    private String waiting;
    private String distance;
    private String duration;
    private double mPrice;
    private double mNumberOfBuses;

    private String souDestStations;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSouDestStations() {
        return souDestStations;
    }

    public void setSouDestStations(String souDestStations) {
        this.souDestStations = souDestStations;
    }

    public Bus(double mNumber , String mIntervalTime, double mNumberOfBuses, double mPrice, String mStartTime, String mStationsCoordinates, Double mTripDuration, String mStations) {
        this.mNumber = mNumber;
        this.mStartTime = mStartTime;
        this.mIntervalTime = mIntervalTime;
        this.mNumberOfBuses = mNumberOfBuses;
        this.mPrice = mPrice;
        this.mStartTime = mStartTime;
        this.mStationsCoordinates = mStationsCoordinates;
        this.mTripDuration = mTripDuration;
        this.mStations=mStations;
    }

    public void setmTripDuration(Double mTripDuration) {
        this.mTripDuration = mTripDuration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getWaiting() {
        return waiting;
    }

    public void setWaiting(String waiting) {
        this.waiting = waiting;
    }

    public String getmStationsCoordinates() {
        return mStationsCoordinates;
    }

    public void setmStationsCoordinates(String mStationsCoordinates) {
        this.mStationsCoordinates = mStationsCoordinates;
    }

    public Double getmNumber() {
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

    public Double getmTripDuration() {
        return mTripDuration;
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
