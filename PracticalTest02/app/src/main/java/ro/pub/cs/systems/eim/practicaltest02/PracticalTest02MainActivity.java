package ro.pub.cs.systems.eim.practicaltest02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest02MainActivity extends AppCompatActivity {

    Button goButton;
    Button startButton;
    EditText portEditText;

    ServerThread serverThread = null;
    PracticalTest02MainActivity selfRef = null;

    private void setReferences() {
        goButton = findViewById(R.id.go_button);
        startButton = findViewById(R.id.start_button);
        portEditText = findViewById(R.id.port_edit_text);
        selfRef = this;
    }

    private void setListeners() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Constants.TAG, "Start button clicked.");
                if (serverThread != null) {
                    serverThread.stopServer();
                }
                serverThread = new ServerThread(selfRef);
                serverThread.startServer();
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Constants.TAG, "Go button clicked.");
                ClientAsyncTask clientAsyncTask = new ClientAsyncTask(selfRef);
                clientAsyncTask.execute(Constants.SERVER_ADDRESS, portEditText.getText().toString());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        setReferences();
        setListeners();
    }
}
