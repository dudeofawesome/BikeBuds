package com.dudeofawesome.bikebuds;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by dongkyun on 2/21/15.
 */
public class RotationClass extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;

    // TOTAL_OLD dictates how many previous orientations to store.
    private final int TOTAL_ORIENTATION = 3;
    private float[][] orientationRecords = new float[TOTAL_ORIENTATION][3];

    final float rad2deg = (float)(180.0f/Math.PI);

    public RotationClass() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        float[] r = new float[9];

        mSensorManager.getOrientation(r, orientationRecords[0]);

        // So no null values
        for (float[] orientation: orientationRecords) {
            orientation = orientationRecords[0];
        }
    }

    public void updateOrientation ()
    {
        // Pushing every record back one
        for (int i = orientationRecords.length - 1; i > 0; i--) {
            orientationRecords[i] = orientationRecords[i - 1];
        }

        float[] r = new float[9];
        mSensorManager.getOrientation(r, orientationRecords[0]);
    }

    public boolean checkTurn (int angleLowerBound) {
        float turnRadius = Math.abs(orientationRecords[0][0] - orientationRecords[TOTAL_ORIENTATION - 1][0]) +
                           Math.abs(orientationRecords[0][1] - orientationRecords[TOTAL_ORIENTATION - 1][1]);

        turnRadius *= rad2deg;

        return turnRadius > angleLowerBound;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
    }
}
