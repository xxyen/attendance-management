package duke.edu.ece651.studentclient;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import duke.edu.ece651.studentclient.service.SocketService;

public class ReportDisplayActivity extends AppCompatActivity implements SocketService.SocketServiceCallback {

    private TextView textViewReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_display);
        textViewReport = findViewById(R.id.textViewReport);
        MainActivity.socketService.sendMessage("s");

        // 注册回调以接收服务器消息
        MainActivity.socketService.registerCallback(this);
    }

    @Override
    public void onMessageReceived(String message) {
        runOnUiThread(() -> {
            textViewReport.setText(message);  // 设置文本框内容为服务器发来的消息
        });
    }

    public void onBackClicked(View view) {
        //MainActivity.socketService.sendMessage("Back");
        finish();  // 结束当前活动，返回到 FunctionSelectionActivity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.socketService.unregisterCallback(this);  // 在活动销毁时取消注册回调
    }
}
