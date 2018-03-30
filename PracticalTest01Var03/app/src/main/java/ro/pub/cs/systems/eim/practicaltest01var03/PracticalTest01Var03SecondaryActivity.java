package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {

    EditText studentEditText = null;
    EditText groupEditText = null;
    Button okButton = null;
    Button cancelButton = null;

    private void setReferences() {
        studentEditText = findViewById(R.id.student_secondary_edit_text);
        groupEditText = findViewById(R.id.group_secondary_edit_text);
        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);
    }

    private void setListeners() {
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Constants.TAG, "okButton clicked.");
                Intent intentToParent = new Intent();
                setResult(RESULT_OK, intentToParent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Constants.TAG, "cancelButton clicked");
                Intent intentToParent = new Intent();
                setResult(RESULT_CANCELED, intentToParent);
                finish();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        setReferences();
        setListeners();

        // intent from parent
        Intent intentFromParent = getIntent();
        Bundle data = intentFromParent.getExtras();

        studentEditText.setText(data.getString(Constants.STUDENT_EDIT_TEXT));
        groupEditText.setText(data.getString(Constants.GROUP_EDIT_TEXT));
    }
}
