package duke.edu.ece651.studentclient.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SocketService extends Service {
    private final IBinder binder = new LocalBinder();
    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;
    private Consumer<String> onResponseReceived;

    private List<Consumer<String>> callbacks = new ArrayList<>();

    private String host = "vcm-37924.vm.duke.edu";
    private int port = 12345;

    public class LocalBinder extends Binder {
        public SocketService getService() {
            return SocketService.this;
        }
    }

    public interface SocketServiceCallback {
        void onMessageReceived(String message);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

//    public void setCallback(Consumer<String> onResponseReceived) {
//        this.onResponseReceived = onResponseReceived;
//    }
//
//    private SocketServiceCallback callback;
//
//    public void setCallback2(SocketServiceCallback callback) {
//        this.callback = callback;
//    }

    public void registerCallback(Consumer<String> onResponseReceive) {
        if (!callbacks.contains(onResponseReceive)) {
            callbacks.add(onResponseReceive);
        }
    }

    public void unregisterCallback(Consumer<String> onResponseReceive) {
        callbacks.remove(onResponseReceive);
    }

    private void notifyCallbacks(String message) {
        for (Consumer<String> onResponseReceive : callbacks) {
            onResponseReceive.accept(message);
        }
    }

    public void startConnection() {
        new Thread(() -> {
            try {
                socket = new Socket(host, port);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                //onResponseReceived.accept("Connected to server");
                notifyCallbacks("Connected to server");

                String fromServer;
                boolean flag = true;
                while (flag) {
                    while ((fromServer = input.readLine()) != null && !fromServer.isEmpty()) {
                       //onResponseReceived.accept(fromServer);
//                        if (callback != null) {
//                            callback.onMessageReceived(fromServer);
//                        }
                        notifyCallbacks(fromServer);
                        if ("endConnection".equalsIgnoreCase(fromServer)) {
                            flag = false;
                            break; // Exit loop on "endConnection" command from server
                        }
                    }
                }
            } catch (IOException e) {
                //onResponseReceived.accept("Error: " + e.getMessage());
//                if (callback != null) {
//                    callback.onMessageReceived("Error: " + e.getMessage());
//                }
                notifyCallbacks("Error: " + e.getMessage());
            } finally {
                closeResources();
            }
        }).start();
    }

    public void sendMessage(String message) {
        new Thread(() -> {
            try {
                if (output != null) {
                    output.write(message + "\n");
                    output.flush();
                }
            } catch (IOException e) {
                //onResponseReceived.accept("Error sending message: " + e.getClass().toString() + "  " + e.getMessage());
                notifyCallbacks("Error sending message: " + e.getClass().toString() + "  " + e.getMessage());
            }
        }).start();
    }

    private void closeResources() {
        //onResponseReceived.accept("Close service");
        notifyCallbacks("Close service");
        try {
            if (socket != null) socket.close();
            if (input != null) input.close();
            if (output != null) output.close();
        } catch (IOException e) {
            //onResponseReceived.accept("Error closing resources: " + e.getMessage());
            notifyCallbacks("Error sending message: " + e.getClass().toString() + "  " + e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        //onResponseReceived.accept("Destroy service");
        super.onDestroy();
        closeResources();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true; // Return true to allow rebinds
    }

}
