package duke.edu.ece651.studentclient;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientNetworkTask extends AsyncTask<Void, Void, String> {
    private String dstAddress;
    private int dstPort;
    private String response = "";

    public ClientNetworkTask(String addr, int port) {
        dstAddress = addr;
        dstPort = port;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Socket socket = null;
        try {
            socket = new Socket(dstAddress, dstPort);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Example: send a command to the server
            out.write("Hello Server\n");
            out.flush();

            // Read server response
            response = in.readLine();

            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // Here you can update your UI with the response
    }
}