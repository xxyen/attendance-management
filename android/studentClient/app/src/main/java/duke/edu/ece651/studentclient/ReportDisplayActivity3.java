package duke.edu.ece651.studentclient;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import duke.edu.ece651.studentclient.service.SocketService;

public class ReportDisplayActivity3 extends AppCompatActivity implements SocketService.SocketServiceCallback {

    private TextView textViewReportContentXml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_display3);
        textViewReportContentXml = findViewById(R.id.textViewReportContentXml);

        // 注册SocketService回调
        MainActivity.socketService.registerCallback(this);

        // 向服务器发送请求XML报告的消息
        MainActivity.socketService.sendMessage("xml");
    }

    @Override
    public void onMessageReceived(String message) {
        runOnUiThread(() -> {
            if (message.equals("startOfFile")) {
                textViewReportContentXml.setText("");  // 清空文本框
            } else if (message.equals("endOfFile")) {
                // 文件接收完毕，无需操作
            } else {
                textViewReportContentXml.append(message + "\n");
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
