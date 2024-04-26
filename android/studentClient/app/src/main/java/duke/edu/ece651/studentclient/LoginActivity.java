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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import duke.edu.ece651.studentclient.service.SocketService;

public class LoginActivity extends AppCompatActivity implements SocketService.SocketServiceCallback {

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

//    private void updateUI(String message) {
//
//        System.out.println(message+"\n");
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                statusTextView.append(message + "\n");
//            }
//        });
//    }

    private void submitCredentials(String userId, String password) {
        // Assume SocketService is bound and available
        String message = userId + "\n" + password;
        MainActivity.socketService.sendMessage(message);
        //MainActivity.socketService.sendMessage(password);
    }



    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            SocketService.LocalBinder binder = (SocketService.LocalBinder) service;
            socketService = binder.getService();
            socketService.registerCallback(LoginActivity.this);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // 服务断开连接处理
            isBound = false;
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
            socketService.unregisterCallback(this);
            unbindService(connection);
            isBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(connection);
            socketService.unregisterCallback(this);
            isBound = false;  // 更新标志状态
        }
    }

    @Override
    public void onMessageReceived(String message) {
        runOnUiThread(() -> {
            statusTextView.setText(message);
            if (message.startsWith("student login")) {
                String[] parts = message.split(" "); // Assuming the format is "student login username"
                if (parts.length > 2) {
                    showLoginSuccessDialog(parts[2]);
                }
            } else {
                showLoginFailureDialog();
            }
        });
    }

    private void showLoginSuccessDialog(String userName) {
        new AlertDialog.Builder(this)
                .setTitle("Login Success")
                .setMessage("You have successfully logged in.")
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    navigateToSelectionActivity(userName);
                })
                .show();
    }

    private void showLoginFailureDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Log in Failed")
                .setMessage("ID or password is incorrect.")
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    navigateBackToMainActivity();
                })
                .show();
    }

    private void navigateBackToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // 结束 LoginActivity，返回 MainActivity
    }

    private void navigateToSelectionActivity(String userName) {
        Intent intent = new Intent(this, SelectionActivity.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
        finish(); // 结束 LoginActivity
    }
}

