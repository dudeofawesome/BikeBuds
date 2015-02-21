package com.dudeofawesome.bikebuds;

import android.app.IntentService;
import android.content.Intent;
import android.location.Geocoder;

import java.util.Locale;

/**
 * Created by Josh on 2/21/15.
 */
public class FetchAddressIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public FetchAddressIntentService() {
        super("FetchAddressIntentService");
    }

    public FetchAddressIntentService(String name) {
        super(name);
    }


    /*created the geocoder, which will convert the location object to someonething comprehendable*/
    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    }

    /*What will translate the location into an actual address*/
    public final class Constants {
        public static final int SUCCESS_RESULT = 0;
        public static final int FAILURE_RESULT = 1;
        public static final String PACKAGE_NAME =
                "com.google.android.gms.location.sample.locationaddress";
        public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
        public static final String RESULT_DATA_KEY = PACKAGE_NAME +
                ".RESULT_DATA_KEY";
        public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
                ".LOCATION_DATA_EXTRA";
    }
}
