package duke.edu.ece651.studentclient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        String userName = getIntent().getStringExtra("userName");
        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewWelcome.setText("Welcome, " + userName);

        Button buttonManipulate = findViewById(R.id.buttonManipulate);
        buttonManipulate.setOnClickListener(v -> {
            sendMessageToServer("1");
            navigateBackToCourseSelectionActivity();
        });

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            sendMessageToServer("2");
            showLogoutSuccessDialog();
        });
    }

    private void navigateBackToCourseSelectionActivity() {
        Intent intent = new Intent(this, CourseSelectionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //finish(); // 结束 SelectionActivity，返回 MainActivity
    }

    private void sendMessageToServer(String message) {
        MainActivity.socketService.sendMessage(message);
    }

    private void showLogoutSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logged Out")
                .setMessage("You have successfully logged out!")
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
        finish(); // 结束 SelectionActivity，返回 MainActivity
    }
}
