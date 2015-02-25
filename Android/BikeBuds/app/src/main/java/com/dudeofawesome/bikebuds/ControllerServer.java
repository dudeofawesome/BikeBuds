package com.dudeofawesome.bikebuds;

import android.app.Activity;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by dudeofawesome on 2/21/15.
 */
public class ControllerServer {

    public static boolean start (final Activity that, final TextView connectionStatus, final Button startRide) {
        // start server on pi
        that.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connectionStatus.setText("Hosting Server");
                connectionStatus.setTextColor(Color.GREEN);
                startRide.setText("Start Group Ride");
            }
        });
        return true;
    }

    public static boolean stop () {
        // stop server on pi
        return true;
    }


}
