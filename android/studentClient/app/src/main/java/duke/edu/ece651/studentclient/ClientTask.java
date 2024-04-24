package duke.edu.ece651.studentclient;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

public class ClientTask extends AsyncTask<Void, String, Boolean> {
    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;
    private String host;
    private int port;
    private Consumer<String> onResponseReceived;

    public ClientTask(String host, int port, Consumer<String> onResponseReceived) {
        this.host = host;
        this.port = port;
        this.onResponseReceived = onResponseReceived;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            socket = new Socket(host, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            publishProgress("Connected to server");
            Scanner scanner = new Scanner(System.in);

            // Example of sending a message to the server
            //sendMessage("Hello from client!");

            // Example of receiving a message from the server
            String fromServer;
            boolean flag = true;
            while (flag) {
                while ((fromServer = input.readLine()) != null && !fromServer.isEmpty()) {
                    publishProgress(fromServer);
                    if ("endConnection".equalsIgnoreCase(fromServer)) {
                        return true; // End loop on "endConnection" command from server
                    }
                }
                System.out.print("Enter response: ");
                String userResponse = scanner.nextLine();
                output.write(userResponse + "\n");
                output.flush();

                if ("endConnection".equalsIgnoreCase(userResponse)) {
                    flag = false;
                    return true;
                }

            }
            return true;
        } catch (Exception e) {
            publishProgress("Error: " + e.getMessage());
            return false;
        } finally {
            try {
                if (socket != null) socket.close();
                if (input != null) input.close();
                if (output != null) output.close();
            } catch (IOException e) {
                publishProgress("Error closing resources: " + e.getMessage());
            }
        }
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        onResponseReceived.accept(progress[0]);
    }

    private void sendMessage(String message) throws IOException {
        if (output != null) {
            output.write(message + "\n");
            output.flush();
        }
    }
}
