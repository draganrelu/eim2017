package ro.pub.cs.systems.eim.practicaltest02;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by relu on 25.05.2018.
 */

public class ClientAsyncTask extends AsyncTask<String, String, Void> {

    PracticalTest02MainActivity activity;
    EditText resultEditText;

    public ClientAsyncTask(PracticalTest02MainActivity activity) {
        this.activity = activity;
        this.resultEditText = activity.findViewById(R.id.result_edit_text);
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            String serverAddress = params[0];
            Integer serverPort = Integer.parseInt(params[1]);

            Log.d(Constants.TAG,"Async client opening socket on " + serverAddress + " " + serverPort);
            Socket socket = new Socket(serverAddress, serverPort);

            // send url to server
            PrintWriter writer = Utilities.getWriter(socket);
            EditText urlEditText = activity.findViewById(R.id.url_edit_text);
            writer.println(urlEditText.getText().toString());
            writer.flush();

            // receive response from server
            String line;
            BufferedReader reader = Utilities.getReader(socket);
            while ((line = reader.readLine()) != null) {
                Log.d(Constants.TAG, "[Client]Received line.");
                publishProgress(line);
            }

            socket.close();
        } catch (Exception exception) {
            Log.e(Constants.TAG, "An exception has occurred: " + exception.getMessage());
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        resultEditText.setText("");

    }

    @Override
    protected void onProgressUpdate(String... progress) {
        resultEditText.append(progress[0] + "\n");
    }

    @Override
    protected void onPostExecute(Void result) {}

}
