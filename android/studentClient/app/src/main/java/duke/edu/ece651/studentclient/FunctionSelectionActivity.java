package duke.edu.ece651.studentclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import duke.edu.ece651.studentclient.service.SocketService;

public class FunctionSelectionActivity extends AppCompatActivity {
//    private SocketService socketService;
//
//    private boolean isBound = false;
//
//    private ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName className, IBinder service) {
//            SocketService.LocalBinder binder = (SocketService.LocalBinder) service;
//            socketService = binder.getService();
//            isBound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName arg0) {
//            isBound = false;
//        }
//    };
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent intent = new Intent(this, SocketService.class);
//        bindService(intent, connection, Context.BIND_AUTO_CREATE);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (isBound) {
//            unbindService(connection);
//            isBound = false;
//        }
//    }

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
        Intent intent = new Intent(this, ReportSelectionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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