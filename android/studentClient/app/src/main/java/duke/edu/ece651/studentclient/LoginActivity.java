package duke.edu.ece651.studentclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import duke.edu.ece651.studentclient.service.SocketService;

public class LoginActivity extends AppCompatActivity{

    private EditText editTextUserId;
    private EditText editTextPassword;
    private Button buttonSubmit;
    private TextView statusTextView;
    public SocketService socketService;
    private boolean isBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserId = findViewById(R.id.editTextUserId);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        statusTextView = findViewById(R.id.statusTextView);


        buttonSubmit.setOnClickListener(view -> {
            String userId = editTextUserId.getText().toString();
            String password = editTextPassword.getText().toString();
            submitCredentials(userId, password);
        });

        // 绑定服务并设置回调
//        Intent intent = new Intent(this, SocketService.class);
//        bindService(intent, connection, Context.BIND_AUTO_CREATE);

//        MainActivity.socketService.registerCallback(this::updateUI);

    }

    private void updateUI(String message) {

        System.out.println(message+"\n");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statusTextView.append(message + "\n");
            }
        });
    }

    private void submitCredentials(String userId, String password) {
        // Assume SocketService is bound and available
        //String message = "Login:" + userId + ":" + password;
        MainActivity.socketService.sendMessage(userId);
        MainActivity.socketService.sendMessage(password);
    }



    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            SocketService.LocalBinder binder = (SocketService.LocalBinder) service;
            socketService = binder.getService();
            socketService.registerCallback(this::updateUI);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // 服务断开连接处理
            isBound = false;
        }

        private void updateUI(String message) {

            System.out.println(message+"\n");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    statusTextView.append(message + "\n");
                }
            });
        }
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
            socketService.unregisterCallback(this::updateUI);
            unbindService(connection);
            isBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}

