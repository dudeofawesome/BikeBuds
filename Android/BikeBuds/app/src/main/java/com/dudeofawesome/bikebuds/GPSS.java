package com.dudeofawesome.bikebuds;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GPSS implements LocationListener {

    public static double latitude;
    public static double longitude;
    public static double initTime;

    @Override
    public void onLocationChanged(Location loc)
    {
        loc.getLatitude();
        loc.getLongitude();
        initTime = System.nanoTime();
        latitude=loc.getLatitude();
        longitude=loc.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        //print "Currently GPS is Disabled";  
    }
    @Override
    public void onProviderEnabled(String provider)
    {
        //print "GPS got Enabled";  
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }
}  