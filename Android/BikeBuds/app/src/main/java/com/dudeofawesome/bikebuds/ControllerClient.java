package com.dudeofawesome.bikebuds;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.widget.Button;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URISyntaxException;

/**
 * Created by dudeofawesome on 2/21/15.
 */
public class ControllerClient {
    private static Socket socket = null;
    public static String IP_ADDRESS = "10.0.0.105"; //"192.168.2.10";
    public static int PORT = 24537;
    private static PositionUpdate lastSentLoc;

    public static void connect (final Activity that, final TextView connectionStatus, final Button startRide) {
        System.out.println("Trying to connect");
        try {
            socket = IO.socket("http://" + IP_ADDRESS + ":" + PORT);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("CONNECTED!!!! :D");
                that.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connectionStatus.setText("Connected to server");
                        connectionStatus.setTextColor(Color.GREEN);
                        startRide.setText("Start Group Ride");
                    }
                });
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Failed to connect");
                that.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connectionStatus.setText("Failed to connected to server");
                        connectionStatus.setTextColor(Color.RED);
                        startRide.setText("Start Solo Ride");
                    }
                });
            }
        }).on("receive locations", new Emitter.Listener() {
	        @Override
	        public void call (Object... args) {
		        final JSONArray members = (JSONArray) args[0];
		        RidePainter.partyMembers.clear();
		        for (int i = 0; i < members.length(); i++) {
			        try {
				        PositionUpdate member = (PositionUpdate) members.get(i);
				        float[] color = {i * 20, 1, 0.5f};
				        double[] relPos = {member.x, member.y};
				        RidePainter.partyMembers.add(new PartyMember(new PositionUpdate(relPos[0], relPos[1]), Color.HSVToColor(color)));
			        } catch (JSONException e) {
				        e.printStackTrace();
			        }
		        }
	        }
        });
        socket.connect();
    }

    public static void sendLocation (PositionUpdate pos) {
        if (socket != null  && lastSentLoc != pos && socket.connected())
            socket.emit("update location", pos);
        lastSentLoc = pos;
    }

    public static void disconnect () {
        if (socket != null && socket.connected())
            socket.disconnect();
    }
}
