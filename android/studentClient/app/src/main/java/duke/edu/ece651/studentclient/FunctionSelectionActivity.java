package duke.edu.ece651.studentclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FunctionSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_selection);
    }

    public void onChangeNotificationClicked(View view) {
        //MainActivity.socketService.sendMessage("1");
        Intent intent = new Intent(this, PreferenceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void onGetReportClicked(View view) {
        MainActivity.socketService.sendMessage("2");
    }

    public void onExitCourseClicked(View view) {
        MainActivity.socketService.sendMessage("3");
        //navigateBackToSelectionActivity();
        finish();
    }

    private void navigateBackToSelectionActivity() {
        Intent intent = new Intent(this, SelectionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}