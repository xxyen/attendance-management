package duke.edu.ece651.studentclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

import duke.edu.ece651.studentclient.service.SocketService;

public class CourseSelectionActivity extends AppCompatActivity implements SocketService.SocketServiceCallback {
    private LinearLayout layoutCourseList;
    private List<String> courseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selection);
        layoutCourseList = findViewById(R.id.layoutCourseList);
        MainActivity.socketService.registerCallback(this);
        //MainActivity.socketService.sendMessage("1");  // 请求课程列表
    }

    @Override
    public void onMessageReceived(String message) {
        runOnUiThread(() -> {
            if ("start of section list".equals(message)) {
                courseList.clear();
            } else if ("end of section list".equals(message)) {
                displayCourses();
            } else {
                courseList.add(message);
            }
        });
    }

    private void displayCourses() {
        layoutCourseList.removeAllViews();
        for (int i = 0; i < courseList.size(); i++) {
            Button button = new Button(this);
            final int index = i + 1;
            button.setText(index + ". " + courseList.get(i));
            button.setOnClickListener(v -> sendCourseSelection(index));
            layoutCourseList.addView(button);
        }
    }

    private void sendCourseSelection(int index) {
        MainActivity.socketService.sendMessage(String.valueOf(index));
        Intent intent = new Intent(this, FunctionSelectionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onBackClicked(View view) {
        MainActivity.socketService.sendMessage("Back");
//        Intent intent = new Intent(this, SelectionActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.socketService.unregisterCallback(this);
    }
}

