package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

import static android.content.ContentValues.TAG;

public class SettingsActivity extends AppCompatActivity {

    private RadioButton trainingBtn, tapBtn, flickBtn, tiltBtn, shakeBtn;
    public static Boolean isTrainingSelected = false,
            isTapSelected = false,
            isFlickSelected = false,
            isTiltSelected = false,
            isShakeSelected = false;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        trainingBtn = findViewById(R.id.trainingOn);
        tapBtn = findViewById(R.id.tapOn);
        flickBtn = findViewById(R.id.flickOn);
        tiltBtn = findViewById(R.id.tiltOn);
        shakeBtn = findViewById(R.id.shakeOn);
        saveBtn = findViewById(R.id.configSave);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
                Intent mainIntent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }

    public void saveSettings()
    {
        isTrainingSelected = trainingBtn.isChecked();
        isFlickSelected = flickBtn.isChecked();
        isTapSelected = tapBtn.isChecked();
        isTiltSelected = tiltBtn.isChecked();
        isShakeSelected = shakeBtn.isChecked();

        Log.i(TAG, "onClick: " + isTrainingSelected + "\n" +
                isTapSelected+ "\n" +
                isFlickSelected+ "\n" +
                isTiltSelected+ "\n" +
                isShakeSelected);
    }
}
