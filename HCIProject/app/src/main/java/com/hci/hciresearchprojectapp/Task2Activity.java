package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import static com.hci.hciresearchprojectapp.Timer.timerTickUpdateEvent;

public class Task2Activity extends AppCompatActivity {
    private static final int REQ_Task2ToCalm = 113;
    public static Boolean isTrainingSelected = false,
            isTapSelected = false,
            isFlickSelected = false,
            isTiltSelected = false,
            isShakeSelected = false;
    public int textId = 0;
    TextView task2Timer;
    TextView task2Text;
    Button continueFromTask2Btn;
    long endtime = 0;

    CountDownTimer currentTimer;

    // members for gesture detection
    private SensorManager sm;
    private Sensor accelerometer;
    private GestureDetection gDetector = new GestureDetection();
    private int assignedGesture = 0;
    private Boolean isNotificationDisplayed = false;

    private AlertDialog alertDialog;

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = endtime - System.currentTimeMillis();
            int seconds = (int) milliseconds/1000;
            int minutes = seconds / 60;
            seconds = seconds % 60;

            task2Timer.setText(String.format("%d:%02d",minutes,seconds));
            timerHandler.postDelayed(this, 500);
        }
    };
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);

        if(getIntent().getExtras() != null) {
        isTrainingSelected = getIntent().getExtras().getBoolean("TrainingSelected");
        isFlickSelected = getIntent().getExtras().getBoolean("FlickSelected");
        isShakeSelected = getIntent().getExtras().getBoolean("ShakeSelected");
        isTapSelected = getIntent().getExtras().getBoolean("TapSelected");
        isTiltSelected = getIntent().getExtras().getBoolean("TiltSelected");
            if (isTapSelected)
                assignedGesture = 0;
            if (isFlickSelected)
                assignedGesture = 1;
            if (isTiltSelected)
                assignedGesture = 2;
            if (isShakeSelected)
                assignedGesture = 3;
        }
        final Random rand = new Random();
        textId = rand.nextInt((3-1)+1)+1;
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        final MultiAutoCompleteTextView task2TxtInput = findViewById(R.id.task2TxtInput);
        task2TxtInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //Do some handling with the txt input

        task2Text = findViewById(R.id.task2Txt);

        task2Text.setMovementMethod(new ScrollingMovementMethod());
        switch (textId){
            case 1:
                task2Text.setText(getString(R.string.text4));
                break;
            case 2:
                task2Text.setText(getString(R.string.text5));
                break;
            case 3:
                task2Text.setText(getString(R.string.text6));
                break;
            default:
                task2Text.setText("");
        }
        task2Timer = findViewById(R.id.task2Timer);
        Button startTimerBtn = findViewById(R.id.startTask2TimerBtn);
        startTimerBtn.setText("start");
        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                task2TxtInput.setVisibility(View.VISIBLE);
                task2Text.setVisibility(View.VISIBLE);

                endtime = System.currentTimeMillis() + 121000;

                currentTimer = createRandomTimerInSeconds(5,10);
                currentTimer.start();

                timerHandler.postDelayed(timerRunnable, 0);
                btn.setVisibility(View.INVISIBLE);
                timerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timerHandler.removeCallbacks(timerRunnable);
                        closeKeyboard();
                        task2TxtInput.setVisibility(View.INVISIBLE);
                        continueFromTask2Btn.setVisibility(View.VISIBLE);
                        currentTimer.cancel();
                    }
                }, 20000); //set correct time here
            }
        });

        continueFromTask2Btn = findViewById(R.id.continueFromTask2Btn);
        continueFromTask2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextCompare txt = new TextCompare();
                double score;
                score = txt.getLevenshteinDistance(task2Text.getText().toString(), task2TxtInput.getText().toString());
                System.out.println("Your score is: " + score);

                setContentView(R.layout.scorelayout);
                final Button btnDismiss = findViewById(R.id.buttonDismiss);
                TextView finalScr = findViewById(R.id.FinalScore);
                ProgressBar scoreBar = findViewById(R.id.progressBar);
                scoreBar.setMax(100);
                scoreBar.setProgress((int) score);
                String fscore = String.valueOf((int) score);
                finalScr.setText(fscore + "%");
                btnDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent continueToCalmPhaseIntent = new Intent(Task2Activity.this, RelaxNResetActivity.class)
                                .putExtra("RequestCode", REQ_Task2ToCalm)
                                .putExtra("TrainingSelected", isTrainingSelected)
                                .putExtra("FlickSelected", isFlickSelected)
                                .putExtra("TapSelected", isTapSelected)
                                .putExtra("TiltSelected", isTiltSelected)
                                .putExtra("ShakeSelected", isShakeSelected);
                        startActivity(continueToCalmPhaseIntent);
                        finish();
                    }
            });
    }});}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(sensorListener);
    }


    // Create the gesture listener
    private SensorEventListener sensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(isNotificationDisplayed){
                if(gDetector.startGestureDetection(sensorEvent, assignedGesture))
                    alertDialog.dismiss();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    public CountDownTimer createRandomTimerInSeconds(final int minSeconds, final int maxSeconds) {
        final int seconds = minSeconds + (int) (Math.random() * (maxSeconds - minSeconds) + 1);
        return new CountDownTimer(seconds*1000,1000) {
            int displayCounter = seconds;
            @Override
            public void onTick(long millisUntilFinished) {
                timerTickUpdateEvent(displayCounter);
                displayCounter--;
            }
            @Override
            public void onFinish() {
                showPopup();
            }
        };
    }

    // Show popup alerts
    private  void showPopup()
    {
        alertDialog = new AlertDialog.Builder(Task2Activity.this).create();
        alertDialog.setTitle("Notification");
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Dismiss me with the taught gesture" +
                "");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        currentTimer = createRandomTimerInSeconds(5,10);
                        currentTimer.start();

                    }
                });
        alertDialog.show();
        isNotificationDisplayed = true;
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    /*@Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button btn = (Button)findViewById(R.id.startTask1TimerBtn);
        if(btn != null) {
            btn.setText("start");
        }
    }*/
}
