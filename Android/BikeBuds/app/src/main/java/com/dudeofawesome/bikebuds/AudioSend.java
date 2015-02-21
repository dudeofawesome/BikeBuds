package com.dudeofawesome.bikebuds;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class AudioSend extends Activity {

    private EditText target;
    private TextView streamingLabel;
    private Button startButton,stopButton;

    public byte[] buffer;
    public static DatagramSocket socket;
    private int port=50005;         //which port??
    AudioRecord recorder;

    //Audio Configuration.
    private int sampleRate = 44100;      //How much will be ideal?
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    private boolean status = true;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("a","asdf");
        //startStreaming();
//      target = (EditText) findViewById (R.id.target_IP);
//        streamingLabel = (TextView) findViewById(R.id.streaming_label);
//        startButton = (Button) findViewById (R.id.start_button);
//        stopButton = (Button) findViewById (R.id.stop_button);

//        streamingLabel.setText("Press Start! to begin");
//
//        startButton.setOnClickListener (startListener);
//        stopButton.setOnClickListener (stopListener);
    }

//    private final OnClickListener stopListener = new OnClickListener() {
//
//        @Override
//        public void onClick(View arg0) {
//            status = false;
//            recorder.release();
//            Log.d("VS","Recorder released");
//        }
//
//    };
//
//    private final OnClickListener startListener = new OnClickListener() {
//
//        @Override
//        public void onClick(View arg0) {
//            status = true;
//            startStreaming();
//        }
//
//    };

    public void startStreaming() {


        Thread streamThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {


                    int minBufSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
                    DatagramSocket socket = new DatagramSocket();
                    Log.d("VS", "Socket Created");

                    byte[] buffer = new byte[minBufSize];

                    Log.d("VS","Buffer created of size " + minBufSize);
                    DatagramPacket packet;

                    final InetAddress destination = InetAddress.getByName(target.getText().toString());
                    Log.d("VS", "Address retrieved");


                    recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,sampleRate,channelConfig,audioFormat,minBufSize);
                    Log.d("VS", "Recorder initialized");

                    recorder.startRecording();


                    while (status) {


                        //reading data from MIC into buffer
                        minBufSize = recorder.read(buffer, 0, buffer.length);

                        //putting buffer in the packet
                        packet = new DatagramPacket (buffer,buffer.length,destination,port);

                        socket.send(packet);


                    }



                } catch(UnknownHostException e) {
                    Log.e("VS", "UnknownHostException");
                } catch (IOException e) {
                    Log.e("VS", "IOException");
                }


            }

        });
        streamThread.start();
    }
}
