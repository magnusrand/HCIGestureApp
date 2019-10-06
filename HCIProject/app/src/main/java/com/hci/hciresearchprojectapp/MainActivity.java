package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MainActivity extends AppCompatActivity {

    private SensorManager sm;
    private GestureDetection gDetector = new GestureDetection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener , sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        Button btn1 = findViewById(R.id.startTrainingBtn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent TrainingTaskIntent = new Intent(MainActivity.this, TrainingActivity.class);
                startActivity(TrainingTaskIntent);
                finish();
            }
        });
    }

    // Create the gesture listener
    private final SensorEventListener sensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            gDetector.startGestureDetection(sensorEvent, isNotificationVisible);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
