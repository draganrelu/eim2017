package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by relu on 25.05.2018.
 */

public class CommunicationThread extends Thread {
    private PracticalTest02MainActivity activity;
    private Socket socket;
    private ServerThread serverThread;

    public CommunicationThread(PracticalTest02MainActivity activity, ServerThread serverThread, Socket socket) {
        this.activity = activity;
        this.socket = socket;
        this.serverThread = serverThread;
    }

    private boolean isUrlBad(String url) {
        return url.contains(Constants.BAD_KEY);
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, "[Server]Comm thread started.");

        try {
            // receive url from client
            BufferedReader reader = Utilities.getReader(socket);
            PrintWriter writer = Utilities.getWriter(socket);
            String line;
            line = reader.readLine();
            if (line == null) {
                Log.e(Constants.TAG, "[Server]Failed to receive url from client.");
            } else {
                Log.d(Constants.TAG, "[Server]Received from client: " + line);
                if (!isUrlBad(line)) {
                    // do http request
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(line);
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    String content = httpClient.execute(httpGet, responseHandler);
                    Log.d(Constants.TAG, "[Server]Content len: " + content.length());

                    // send content to client
                    writer.print(content);
                    writer.flush();
                } else {
                    // write message
                    writer.write("URL blocked by firewall.");
                    writer.flush();
                }
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
