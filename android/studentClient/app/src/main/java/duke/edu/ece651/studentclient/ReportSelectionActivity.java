package duke.edu.ece651.studentclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ReportSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_selection);
    }

    public void onSummaryClicked(View view) {
        //MainActivity.socketService.sendMessage("s");
        navigateToReportDisplay();
    }

    public void onDetailedClicked(View view) {
        //MainActivity.socketService.sendMessage("d");
        // Functionality to be determined
        Intent intent = new Intent(this, FormatSelectionActivity.class);
        //intent.putExtra("reportType", type);
        startActivity(intent);
        finish();
    }

    public void onBackClicked(View view) {
        MainActivity.socketService.sendMessage("Back");
        finish();
    }

    private void navigateToReportDisplay() {
        Intent intent = new Intent(this, ReportDisplayActivity.class);
        //intent.putExtra("reportType", type);
        startActivity(intent);
        finish();
    }
}

