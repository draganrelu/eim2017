package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;
import android.widget.EditText;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by relu on 25.05.2018.
 */

public class ServerThread extends Thread {

    private PracticalTest02MainActivity activity;
    private boolean isRunning;
    private ServerSocket serverSocket;

    public ServerThread(PracticalTest02MainActivity activity) {
        this.activity = activity;
        this.isRunning = false;

    }

    public void startServer() {
        isRunning = true;
        start();
    }

    public void stopServer() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (Exception e) {
            Log.d(Constants.TAG, "Exception ocurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, "Server running.");

        try {
            EditText portEditText = activity.findViewById(R.id.port_edit_text);
            Integer port = Integer.parseInt(portEditText.getText().toString());
            Log.d(Constants.TAG, "Server socket opened on port " + port);
            serverSocket = new ServerSocket(port);

            while (isRunning) {
                Socket socket = serverSocket.accept();
                Log.v(Constants.TAG, "Connection opened with " + socket.getInetAddress() + ":" + socket.getLocalPort());
                CommunicationThread communicationThread = new CommunicationThread(activity, this, socket);
                communicationThread.start();
            }
        } catch (Exception e) {
            Log.d(Constants.TAG, "Exception ocurred.");
            e.printStackTrace();
        }

        Log.d(Constants.TAG, "Server stopped.");
    }
}
