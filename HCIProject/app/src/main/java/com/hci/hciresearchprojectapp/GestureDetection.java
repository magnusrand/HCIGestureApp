package com.hci.hciresearchprojectapp;

import android.hardware.SensorEvent;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class GestureDetection {

    private static Boolean isNotificationDisplayed = false;
    private float oldX = 0.0f, oldY = 0.0f, oldZ = 0.0f;
    private int i = 0;
    private final float tiltDelta = 2.0f;
    private final float shakeDetla = 8.5f;

    private static final float ALPHA = 0.8f;
    private float[] gravity = new float[3];
    private float[] linear_acceleration = new float[3];
    private int sensorCounter = 0;

    public GestureDetection(){
        gravity[0] = 0;
        gravity[1] = 0;
        gravity[2] = 0;
    }

    public void setNotificationFlag(Boolean flag){
        isNotificationDisplayed = flag;
    }

    public String startGestureDetection(SensorEvent sensorEvent){
        String gesture = "";
        if(isNotificationDisplayed) {

            float linearX = 0.0f, linearY = 0.0f, linearZ = 0.0f, deltaX = 0.0f, deltaY = 0.0f,deltaZ = 0.0f;

            gravity[0] = ALPHA * gravity[0] + (1 - ALPHA) * sensorEvent.values[0];
            gravity[1] = ALPHA * gravity[0] + (1 - ALPHA) * sensorEvent.values[1];
            gravity[2] = ALPHA * gravity[0] + (1 - ALPHA) * sensorEvent.values[2];

            linearX = sensorEvent.values[0] - gravity[0];
            linearY = sensorEvent.values[1] - gravity[1];
            linearZ = sensorEvent.values[2] - gravity[2];

            deltaX = Math.abs(linearX - oldX);
            deltaY = Math.abs(linearY - oldY);
            deltaZ = Math.abs(linearZ - oldZ);

            oldX = linearX;
            oldY = linearY;
            oldZ = linearZ;

            if (deltaX > deltaY) {
                if(deltaX > tiltDelta){
                    gesture = "RL";
                }
            } else if(deltaY > tiltDelta){
                gesture = "UD";
            }
//            else if (linearX > (-2) && linearX < (2) && linearY > (-2) && linearY < (2)) {
//                gesture = "NONE";
//            }
            if(deltaZ > shakeDetla || deltaX > shakeDetla || deltaY > shakeDetla) {
                gesture = "SHAKE";
            }

            oldX = linearX;
            oldY = linearY;

            try {
                Thread.sleep(150);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "gesture is " + gesture);
        return gesture;
    }
}

