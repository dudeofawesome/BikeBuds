package com.dudeofawesome.bikebuds;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;


public class BikeActivity extends ActionBarActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, LocationListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    public double longitute, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);

        LocationManager mlocManager=null;
        LocationListener mlocListener;
        mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new GPSS();
        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

        mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
             longitute = GPSS.longitude;
             latitude = GPSS.latitude;
            TextView textB = (TextView)  findViewById(R.id.textView);
            textB.setText("long: "+ longitute+"latitude"+latitude);
            Toast.makeText(this,"heyyy",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location loc) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}