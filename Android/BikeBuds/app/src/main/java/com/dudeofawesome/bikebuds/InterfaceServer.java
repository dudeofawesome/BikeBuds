package com.dudeofawesome.bikebuds;

import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by dudeofawesome on 2/21/15.
 */
public class InterfaceServer {

    public static boolean start (final TextView connectionStatus, final Button startRide) {
        // start server on pi
        connectionStatus.setText("Hosting Server");
        connectionStatus.setTextColor(Color.GREEN);
        startRide.setText("Start Group Ride");
        return true;
    }

    public static boolean stop () {
        // stop server on pi
        return false;
    }


}
