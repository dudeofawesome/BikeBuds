package com.dudeofawesome.bikebuds;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import android.location.LocationListener;

import java.text.BreakIterator;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;

public class BikeActivity extends ActionBarActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
    public static PositionUpdate location = new PositionUpdate(0, 0);
    Timer timer;
    private final int TIMER_INTERVAL_MS = 500;
    TimerTask timerTask;
    double[][] coordinates = new double[2][2];

    final Handler handler = new Handler();

    double totalDistance = 0;
    boolean firstTime = true;

    LocationManager mlocManager;
    LocationListener mlocListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);

//        InterfaceClient.connect();
        // TODO: start GPS module once Michelle has it done
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new GPSS();

        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
        mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);



        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                coordinates[i][j] = 0;
            }
        }

        timer = new Timer();

        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        doStuff();
                    }
                });
            }
        };

        timer.schedule(timerTask, 0, TIMER_INTERVAL_MS);


//        InterfaceClient.sendLocation(new PositionUpdate(0, 0));
        // TODO: end test
    }

    double lastSpeed = 0;
    double lastAverageSpeed = 0;

    public void doStuff(){
        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (firstTime) {
                coordinates[0][0] = GPSS.latitude;
                coordinates[0][1] = GPSS.longitude;
                firstTime = false;
            }

            // Setting the old coords to our init pos
            coordinates[1][0] = coordinates[0][0];
            coordinates[1][1] = coordinates[0][1];

            // Getting new final pos
            coordinates[0][0] = GPSS.latitude;
            coordinates[0][1] = GPSS.longitude;

            if (coordinates[0][0] != 0 && coordinates[1][0] != 0) {
                double distance = measureDistance(coordinates[0][0], coordinates[0][1],
                        coordinates[1][0], coordinates[1][1]);

                totalDistance += distance;

                double speed = (distance / (2 * TIMER_INTERVAL_MS) * 1000 * 60 * 60); //mph
                double averageSpeed = (lastSpeed + speed) / 2;
                lastSpeed = speed;

//                TextView textB = (TextView) findViewById(R.id.textView);
//                if (speed != 0) {
//                    textB.setText(String.format("Latitude: %s \n" +
//                                    "Longitude: %s \n" +
//                                    "Speed: %s \n" +
//                                    "Distance Traveled: %s",
//                            coordinates[0][0], coordinates[0][1], averageSpeed, totalDistance));
//                }
                RidePainter.speed = (float) (averageSpeed > 0.1 ? averageSpeed : 0);
                RidePainter.deltaSpeed = (float) (lastAverageSpeed - averageSpeed);
                RidePainter.totalDistance = (float) totalDistance;
            }
        }
    }

    private double measureDistance (double lat1, double lon1, double lat2, double lon2){  // generally used geo measurement function
        double R = 6378.137; // Radius of earth in KM

        double dLat = (lat2 - lat1) * Math.PI / 180;
        double dLon = (lon2 - lon1) * Math.PI / 180;

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) *
                        Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;

        return d * 0.621371; // miles
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
