package com.dudeofawesome.bikebuds;

import android.graphics.Color;
import android.graphics.Point;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URISyntaxException;

/**
 * Created by dudeofawesome on 2/21/15.
 */
public class InterfaceClient {
    private static Socket socket = null;
    public static String IP_ADDRESS = "192.168.2.10";

    public static void connect () {
        try {
            socket = IO.socket(IP_ADDRESS);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            }
        }).on("receive locations", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final JSONArray members = (JSONArray) args[0];
                RidePainter.partyMembers.clear();
                for (int i = 0; i < members.length(); i++) {
                    try {
                        PositionUpdate member = (PositionUpdate) members.get(i);
                        float[] color = {i * 20, 1, 0.5f};
                        int[] relPos = {member.x, member.y};
                        RidePainter.partyMembers.add(new PartyMember(new Point(relPos[0], relPos[1]), Color.HSVToColor(color)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void sendLocation (PositionUpdate pos) {
        socket.emit("update location", pos);
    }

    public static void disconnect () {
        socket.disconnect();
    }
}
