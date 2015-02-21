package com.dudeofawesome.bikebuds;

import android.graphics.Color;
import android.graphics.Point;

/**
 * Created by dudeofawesome on 2/21/15.
 */
public class PartyMember {
    public Point position;
    public int color = Color.rgb(200, 200, 200);

    public PartyMember () {

    }

    public PartyMember (Point position) {
        this.position = position;
    }

    public PartyMember (Point position, int color) {
        this.position = position;
        this.color = color;
    }
}
