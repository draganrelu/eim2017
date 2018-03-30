package ro.pub.cs.systems.eim.practicaltest01var03;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {

    Button navigateButton = null;
    Button displayInformationButton = null;
    EditText informationEditText = null;
    CheckBox studentCheckbox = null;
    CheckBox groupCheckbox = null;
    EditText studentEditText = null;
    EditText groupEditText = null;
    PracticalTest01Var03MainActivity thisReference = null;
    Intent serviceIntent = null;
    ServiceBroadcastReceiver broadcastReceiver = null;
    IntentFilter serviceIntentFilter = null;

    void setReferences() {
        navigateButton = findViewById(R.id.navigate_button);
        displayInformationButton = findViewById(R.id.display_information_button);
        informationEditText = findViewById(R.id.information_edit_text);
        studentCheckbox = findViewById(R.id.student_checkbox);
        groupCheckbox = findViewById(R.id.group_checkbox);
        studentEditText = findViewById(R.id.student_edit_text);
        groupEditText = findViewById(R.id.group_edit_text);
        thisReference = this;
    }


    void setListeners() {
        displayInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Constants.TAG, "display button clicked.");
                String information = new String();
                String studentName = studentEditText.getText().toString();
                String group = groupEditText.getText().toString();

                if (studentCheckbox.isChecked()) {
                    if (studentName.isEmpty()) {
                        Toast.makeText(getApplication(), "No student name", Toast.LENGTH_LONG).show();
                    } else {
                        information += studentName;
                    }
                }

                if (groupCheckbox.isChecked()) {
                    if (group.isEmpty()) {
                        Toast.makeText(getApplication(), "No group", Toast.LENGTH_LONG).show();
                    } else {
                        information += " " + group;
                    }
                }

                informationEditText.setText(information);

                if (!studentName.isEmpty() && !group.isEmpty()) {
                    serviceIntent = new Intent(thisReference, PracticalTest01Var03Service.class);

                    serviceIntent.putExtra(Constants.STUDENT_EDIT_TEXT, studentName);
                    serviceIntent.putExtra(Constants.GROUP_EDIT_TEXT, group);

                    startService(serviceIntent);
                }
            }
        });

        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Constants.TAG, "navigate button clicked");
                Intent intent = new Intent(thisReference, PracticalTest01Var03SecondaryActivity.class);
                intent.putExtra(Constants.STUDENT_EDIT_TEXT, studentEditText.getText().toString());
                intent.putExtra(Constants.GROUP_EDIT_TEXT, groupEditText.getText().toString());
                startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_INTENT_ID);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(Constants.TAG, "onSaveInstanceState()");

        savedInstanceState.putString(Constants.STUDENT_EDIT_TEXT, studentEditText.getText().toString());
        savedInstanceState.putString(Constants.GROUP_EDIT_TEXT, groupEditText.getText().toString());
        savedInstanceState.putString(Constants.INFORMATION_EDIT_TEXT, informationEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(Constants.TAG, "onRestoreInstanceState()");

        studentEditText.setText(savedInstanceState.getString(Constants.STUDENT_EDIT_TEXT));
        groupEditText.setText(savedInstanceState.getString(Constants.GROUP_EDIT_TEXT));
        informationEditText.setText(savedInstanceState.getString(Constants.INFORMATION_EDIT_TEXT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        setReferences();
        setListeners();
        registerBroadcastReceiver();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case Constants.SECONDARY_ACTIVITY_INTENT_ID:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getApplication(),
                            "RESULT OK",
                            Toast.LENGTH_LONG).show();
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(getApplication(), "RESULT CANCELED", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplication(), "UNKNOWN RESULT", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG, "Main activity onDestroy()");

        stopService(serviceIntent);
        unregisterReceiver(broadcastReceiver);
    }

    private void registerBroadcastReceiver() {
        broadcastReceiver = new ServiceBroadcastReceiver();

        serviceIntentFilter = new IntentFilter();
        serviceIntentFilter.addAction(Constants.ACTION_STRING);

        registerReceiver(broadcastReceiver, serviceIntentFilter);
    }
}
