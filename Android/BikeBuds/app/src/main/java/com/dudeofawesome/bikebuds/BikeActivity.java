package com.dudeofawesome.bikebuds;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import java.io.FileOutputStream;
import java.util.Calendar;

public class BikeActivity extends ActionBarActivity {
    Calendar c = Calendar.getInstance();
    String filename = "bike_ride_" + c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DATE) + "-" + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE);
    FileOutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);

        ControllerLocation.init(this);
        ControllerClient.startRide();

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ControllerClient.sendLocation(new PositionUpdate(0, 0));
    }

    @Override
    protected void onDestroy () {
        try {
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
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
