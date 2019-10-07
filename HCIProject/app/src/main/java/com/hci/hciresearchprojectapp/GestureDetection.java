package com.hci.hciresearchprojectapp;

import android.hardware.SensorEvent;
import android.util.Log;
import android.view.GestureDetector;

import static android.content.ContentValues.TAG;

public class GestureDetection {

    private static Boolean isNotificationDisplayed = false;
    private float oldX = 0.0f, oldY = 0.0f, oldZ = 0.0f;
    private int i = 0;
    private final float delta = 1.0f;

    private static final float ALPHA = 0.8f;
    private float[] gravity = new float[3];
    private float[] linear_acceleration = new float[3];

    public void setNotificationFlag(Boolean flag){
        isNotificationDisplayed = flag;
    }

    public String startGestureDetection(SensorEvent sensorEvent){

        String gesture = "";
        gravity[0] = ALPHA * gravity[0] + (1 - ALPHA) * sensorEvent.values[0];
        gravity[1] = ALPHA * gravity[1] + (1 - ALPHA) * sensorEvent.values[1];
        gravity[2] = ALPHA * gravity[2] + (1 - ALPHA) * sensorEvent.values[2];

        float linearX = sensorEvent.values[0] - gravity[0];
        float linearY = sensorEvent.values[1] - gravity[1];
        float linearZ = sensorEvent.values[2] - gravity[2];


        float deltaX = Math.abs(linearX - oldX);
        float deltaY = Math.abs(linearY - oldY);
        float deltaZ = Math.abs(linearZ - oldZ);

        if(i > 0)
        {
            if (deltaX > deltaY) {
                if(deltaX > delta){
                    // Write code here for when either left or right tilt gesture is detected
                    if (linearX < 0) {
                        // Write code here for when phone is tilted right
                        Log.i(TAG, "onSensorChanged: You tilt the device right\n\n\n\n\n\n\n\n");
                    }
                    if (linearX > 0) {
                        // Write code here for when phone is tilted left
                        Log.i(TAG, "onSensorChanged: You tilt the device left\n\n\n\n\n\n\n\n");
                    }
                    if(isNotificationDisplayed)
                        gesture = "RL";
                }
            } else if(deltaY > delta){
                // Write code here for when either up or down tilt gesture is detected
                if (linearY < 0) {
                    // Write code here for when phone is tilted up
                    Log.i(TAG, " tilting forward : You tilt the device up\n\n\n\n\n\n\n\n");
                }
                if (linearY > 0) {
                    // Write code here for when phone is tilted down
                    Log.i(TAG, " tilting forward : You tilt the device downs\n\n\n\n\n\n\n\n");
                }
                if(isNotificationDisplayed)
                    gesture = "UD";
            }
            else if (linearX > (-2) && linearX < (2) && linearY > (-2) && linearY < (2)) {
//                    Log.i(TAG, "onSensorChanged: Not tilt device");
                gesture = "NONE";
            }
            else
                gesture = "SHAKE";
        }

        oldX = linearX;
        oldY = linearY;

        i++;


        try {
            Thread.sleep(150);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gesture;
    }
}
