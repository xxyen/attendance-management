package duke.edu.ece651.studentclient;


import duke.edu.ece651.studentclient.service.SocketService;
import duke.edu.ece651.studentclient.ui.theme.*;

import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;
import java.net.*;

public class MainActivity extends AppCompatActivity implements SocketService.SocketServiceCallback {

    private TextView textViewResponse;
    public static SocketService socketService;
    private boolean isBound = false;

    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            SocketService.LocalBinder binder = (SocketService.LocalBinder) service;
            socketService = binder.getService();
            //socketService.setCallback(this::updateUI);
            socketService.registerCallback(MainActivity.this);
            isBound = true;
            socketService.startConnection();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }

//        private void updateUI(String message) {
//
//            System.out.println(message+"\n");
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    textViewResponse.append(message + "\n");
//                }
//            });
//        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, SocketService.class);
        startService(intent);  // Start service to keep it running even if unbound temporarily
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }

    // You can call this method to send messages
    public void sendMessageToServer(String message) {
        if (isBound) {
            socketService.sendMessage(message);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.buttonLogin);
        textViewResponse = findViewById(R.id.textview_response);

        loginButton.setOnClickListener(view -> {
            sendMessageToServer("1");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onMessageReceived(String message) {
        runOnUiThread(() -> {
            textViewResponse.setText(message);
        });
    }

//    private void updateUI(final String response) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                textViewResponse.append(response + "\n");
//            }
//        });
//    }
}
