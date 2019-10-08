package com.hci.hciresearchprojectapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

import static com.hci.hciresearchprojectapp.Timer.timerTickUpdateEvent;
import static android.content.ContentValues.TAG;

public class Task1Activity extends AppCompatActivity {
    private static final int REQ_Task1ToCalm = 112;
    private Boolean isNotificationDisplayed = false;
    private int assignedGesture = 0;
    public static Boolean isTapSelected = false,
            isFlickSelected = false,
            isTiltSelected = false,
            isShakeSelected = false;
    public int textId = 0;
    TextView task1Timer;
    TextView task1Text;
    Button continueFromTask1Btn;
    long endtime = 0;

    CountDownTimer currentTimer;

    // members for gesture detection
    private SensorManager sm;
    private Sensor accelerometer;
    private GestureDetection gDetector = new GestureDetection();

    private AlertDialog alertDialog;

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = endtime - System.currentTimeMillis();
            int seconds = (int) milliseconds/1000;
            int minutes = seconds / 60;
            seconds = seconds % 60;

            task1Timer.setText(String.format("%d:%02d",minutes,seconds));
            timerHandler.postDelayed(this, 500);
        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);

        if (getIntent().getExtras() != null) {
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
        Random rand = new Random();
        textId = rand.nextInt((3 - 1) + 1) + 1;
        Log.i(TAG, "onCreate: assigned gesture is " + assignedGesture);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        final MultiAutoCompleteTextView task1TxtInput = findViewById(R.id.task1TxtInput);
        task1TxtInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //Do some handling with the txt input

        task1Text = findViewById(R.id.task1Txt);

        task1Text.setMovementMethod(new ScrollingMovementMethod());
        switch (textId) {
            case 1:
                task1Text.setText(getString(R.string.text1));
                break;
            case 2:
                task1Text.setText(getString(R.string.text2));
                break;
            case 3:
                task1Text.setText(getString(R.string.text3));
                break;
            default:
                task1Text.setText("");
        }


        task1Timer = findViewById(R.id.task1Timer);
        Button startTimerBtn = findViewById(R.id.startTask1TimerBtn);
        startTimerBtn.setText("start");
        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                task1TxtInput.setVisibility(View.VISIBLE);
                task1Text.setVisibility(View.VISIBLE);
                endtime = System.currentTimeMillis() + 121000;

                currentTimer = createRandomTimerInSeconds(5, 10);
                currentTimer.start();

                timerHandler.postDelayed(timerRunnable, 0);
                btn.setVisibility(View.INVISIBLE);
                timerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timerHandler.removeCallbacks(timerRunnable);
                        closeKeyboard();
                        task1TxtInput.setVisibility(View.INVISIBLE);
                        continueFromTask1Btn.setVisibility(View.VISIBLE);
                        currentTimer.cancel();
                    }
                }, 20000);//set correct time here
            }
        });

        continueFromTask1Btn = findViewById(R.id.continueFromTask1Btn);
        continueFromTask1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextCompare txt = new TextCompare();
                double score;
                score = txt.getLevenshteinDistance(task1Text.getText().toString(), task1TxtInput.getText().toString());
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
                        Intent continueToCalmPhaseIntent = new Intent(Task1Activity.this, RelaxNResetActivity.class)
                                .putExtra("RequestCode", REQ_Task1ToCalm)
                                .putExtra("FlickSelected", isFlickSelected)
                                .putExtra("TapSelected", isTapSelected)
                                .putExtra("TiltSelected", isTiltSelected)
                                .putExtra("ShakeSelected", isShakeSelected);
                        startActivity(continueToCalmPhaseIntent);
                        finish();
                    }
                });
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(sensorListener);
    }

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

    public void dismissAlert(){
        alertDialog.dismiss();
        currentTimer = createRandomTimerInSeconds(5,10);
        currentTimer.start();
    }

    // Show popup alerts
    private  void showPopup()
    {
        alertDialog = new AlertDialog.Builder(Task1Activity.this).create();
        alertDialog.setTitle("Notification");
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Dismiss me with the taught gesture" +
                "");
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                currentTimer = createRandomTimerInSeconds(5,10);
                currentTimer.start();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        currentTimer = createRandomTimerInSeconds(5,10);
//                        currentTimer.start();
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

/*    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button btn = (Button)findViewById(R.id.startTask1TimerBtn);
        if(btn != null) {
            btn.setText("start");
        }
    }*/
}
