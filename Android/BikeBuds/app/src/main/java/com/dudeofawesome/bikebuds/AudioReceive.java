package com.dudeofawesome.bikebuds;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by dongkyun on 2/21/15.
 */
public class AudioReceive extends Activity {
    private Button receiveButton,stopButton;

    public static DatagramSocket socket;
    private AudioTrack speaker;

    //Audio Configuration.
    private int sampleRate = 44100;      //How much will be ideal?
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    private boolean status = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//        receiveButton = (Button) findViewById (R.id.receive_button);
//        stopButton = (Button) findViewById (R.id.stop_button);
//        findViewById(R.id.receive_label);

        receiveButton.setOnClickListener(receiveListener);
        stopButton.setOnClickListener(stopListener);

    }


    private final View.OnClickListener stopListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            status = false;
            speaker.release();
            Log.d("VR", "Speaker released");

        }

    };


    private final View.OnClickListener receiveListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            status = true;
            startReceiving();

        }

    };

    public void startReceiving() {

        Thread receiveThread = new Thread (new Runnable() {

            @Override
            public void run() {

                try {

                    DatagramSocket socket = new DatagramSocket(50005);
                    Log.d("VR", "Socket Created");


                    byte[] buffer = new byte[256];


                    //minimum buffer size. need to be careful. might cause problems. try setting manually if any problems faced
                    int minBufSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);

                    speaker = new AudioTrack(AudioManager.STREAM_MUSIC,sampleRate,channelConfig,audioFormat,minBufSize,AudioTrack.MODE_STREAM);

                    speaker.play();

                    while(status == true) {
                        try {


                            DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
                            socket.receive(packet);
                            Log.d("VR", "Packet Received");

                            //reading content from packet
                            buffer=packet.getData();
                            Log.d("VR", "Packet data read into buffer");

                            //sending data to the Audiotrack obj i.e. speaker
                            speaker.write(buffer, 0, minBufSize);
                            Log.d("VR", "Writing buffer content to speaker");

                        } catch(IOException e) {
                            Log.e("VR","IOException");
                        }
                    }


                } catch (SocketException e) {
                    Log.e("VR", "SocketException");
                }


            }

        });
        receiveThread.start();
    }

}
