package duke.edu.ece651.studentclient;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import duke.edu.ece651.studentclient.service.SocketService;

public class PreferenceActivity extends AppCompatActivity implements SocketService.SocketServiceCallback {
    private TextView textViewServerMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        textViewServerMessage = findViewById(R.id.textViewServerMessage);
        Button buttonYes = findViewById(R.id.buttonYes);
        Button buttonNo = findViewById(R.id.buttonNo);

        MainActivity.socketService.registerCallback(this);
        MainActivity.socketService.sendMessage("1");

        buttonYes.setOnClickListener(v -> {
            MainActivity.socketService.sendMessage("y");
            showAlert("You have successfully changed your preference!");
        });

        buttonNo.setOnClickListener(v -> {
            MainActivity.socketService.sendMessage("n");
            finish();
        });
    }

    @Override
    public void onMessageReceived(String message) {
        runOnUiThread(() -> textViewServerMessage.setText(message));
    }

    private void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> finish())
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.socketService.unregisterCallback(this);
    }
}

