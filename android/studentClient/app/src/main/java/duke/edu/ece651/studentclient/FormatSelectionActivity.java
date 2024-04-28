package duke.edu.ece651.studentclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FormatSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_format_selection);
        MainActivity.socketService.sendMessage("d");
    }

    public void onJsonClicked(View view) {
        //MainActivity.socketService.sendMessage("json");
        Intent intent = new Intent(this, ReportDisplayActivity2.class);
        startActivity(intent);
        finish();
    }

    public void onXmlClicked(View view) {
        //MainActivity.socketService.sendMessage("xml");
        // If there is an XML display activity, you would start it here.
        Intent intent = new Intent(this, ReportDisplayActivity3.class);
        startActivity(intent);
        finish();
    }

    public void onBackClicked(View view) {
        MainActivity.socketService.sendMessage("Back");
        finish();
    }
}
