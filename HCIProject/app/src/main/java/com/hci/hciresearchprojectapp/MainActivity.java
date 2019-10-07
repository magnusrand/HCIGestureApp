package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MainActivity extends AppCompatActivity {

    private SensorManager sm;
    private Sensor accelerometer;
    private GestureDetection gDetector = new GestureDetection();
    public static Boolean isTrainingSelected = false,
            isTapSelected = false,
            isFlickSelected = false,
            isTiltSelected = false,
            isShakeSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Button btn1 = findViewById(R.id.startTrainingBtn);
        Button settingsButton = findViewById(R.id.settings);

       if(getIntent().getExtras() != null) {
            isTrainingSelected = getIntent().getExtras().getBoolean("TrainingSelected");
            isFlickSelected = getIntent().getExtras().getBoolean("FlickSelected");
            isShakeSelected = getIntent().getExtras().getBoolean("ShakeSelected");
            isTapSelected = getIntent().getExtras().getBoolean("TapSelected");
            isTiltSelected = getIntent().getExtras().getBoolean("TiltSelected");
        }

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                finish();
            }
        });

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isTrainingSelected) {
                    Intent TrainingTaskIntent = new Intent(MainActivity.this, TrainingActivity.class);
                    startActivity(TrainingTaskIntent);
                    finish();
                } else {
                    Intent Task1Intent = new Intent(MainActivity.this, Task1Activity.class)
                            .putExtra("TrainingSelected", isTrainingSelected)
                            .putExtra("FlickSelected", isFlickSelected)
                            .putExtra("TapSelected", isTapSelected)
                            .putExtra("TiltSelected", isTiltSelected)
                            .putExtra("ShakeSelected", isShakeSelected);
                    startActivity(Task1Intent);
                    finish();
                    }
                }
            });
        //sm.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Create the gesture listener
    private SensorEventListener sensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            gDetector.startGestureDetection(sensorEvent);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
