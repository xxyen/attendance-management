package duke.edu.ece651.studentclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import duke.edu.ece651.studentclient.service.SocketService;

public class ReportDisplayActivity2 extends AppCompatActivity implements SocketService.SocketServiceCallback {

    private TextView textViewReportContent;
    private StringBuilder reportBuilder = new StringBuilder();
    private boolean isReceivingFile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_display2);
        textViewReportContent = findViewById(R.id.textViewReportContent);

        // 注册SocketService回调
        MainActivity.socketService.registerCallback(this);

        // 向服务器发送请求JSON报告的消息
        MainActivity.socketService.sendMessage("json");
    }

    @Override
    public void onMessageReceived(String message) {
        runOnUiThread(() -> {
            if (message.equals("startOfFile")) {
                isReceivingFile = true;
                reportBuilder = new StringBuilder();
            } else if (message.equals("endOfFile")) {
                isReceivingFile = false;
                textViewReportContent.setText(reportBuilder.toString());
            } else if (isReceivingFile) {
                reportBuilder.append(message).append("\n");
            }
        });
    }

    public void onBackClicked(View view) {
        // 解除SocketService的回调注册
        MainActivity.socketService.unregisterCallback(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 确保在Activity销毁时取消注册回调
        MainActivity.socketService.unregisterCallback(this);
    }
}

