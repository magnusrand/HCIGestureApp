package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.hci.hciresearchprojectapp.Timer.timerTickUpdateEvent;

public class Task4Activity extends AppCompatActivity {
    public static Boolean isTrainingSelected = false,
            isTapSelected = false,
            isFlickSelected = false,
            isTiltSelected = false,
            isShakeSelected = false;
    TextView task4Timer;
    TextView task4Text;
    Button continueFromTask4Btn;
    long endtime = 0;

    CountDownTimer currentTimer;

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = endtime - System.currentTimeMillis();
            int seconds = (int) milliseconds/1000;
            int minutes = seconds / 60;
            seconds = seconds % 60;

            task4Timer.setText(String.format("%d:%02d",minutes,seconds));
            timerHandler.postDelayed(this, 500);
        }
    };
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4);

        if(getIntent().getExtras() != null) {
        isTrainingSelected = getIntent().getExtras().getBoolean("TrainingSelected");
        isFlickSelected = getIntent().getExtras().getBoolean("FlickSelected");
        isShakeSelected = getIntent().getExtras().getBoolean("ShakeSelected");
        isTapSelected = getIntent().getExtras().getBoolean("TapSelected");
        isTiltSelected = getIntent().getExtras().getBoolean("TiltSelected");
            if (isFlickSelected) {
                //Todo: Flick handling
            }

            if (isShakeSelected) {
                //Todo: Shake handling
            }

            if (isTapSelected) {
                //Todo: Tap handling
            }

            if (isTiltSelected) {
                //Todo: Tilt handling
            }
        }

        final MultiAutoCompleteTextView task4TxtInput = findViewById(R.id.task4TxtInput);
        task4TxtInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //Do some handling with the txt input

        task4Text = findViewById(R.id.task4Txt);

        task4Text.setMovementMethod(new ScrollingMovementMethod());
        task4Text.setText("healthy food is good for you\n\n" +
                "hands on experience with a job\n\n" +
                "this watch is too expensive\n\n" +
                "the postal service is very slow\n\n" +
                "communicate through email\n\n" +
                "the capital of our nation\n\n" +
                "travel at the speed of light\n\n" +
                "I do not fully agree with you\n\n" +
                "gas bills are sent monthly\n\n" +
                "earth quakes are predictable\n\n" +
                "life is but a dream\n\n" +
                "take it to the recycling depot\n\n" +
                "sent this by registered mail\n\n" +
                "fall is my favorite season\n\n" +
                "a fox is a very smart animal\n\n" +
                "the kids are very excited\n\n" +
                "parking lot is full of trucks\n\n" +
                "my bike has a flat tire\n\n" +
                "do not walk too quickly\n\n" +
                "a duck quacks to ask for food\n\n" +
                "limited warranty of two years\n\n" +
                "the four seasons will come\n\n" +
                "the sun rises in the east\n\n" +
                "it is very windy today\n\n" +
                "do not worry about this\n\n" +
                "dashing through the snow\n\n" +
                "want to join us for lunch\n\n" +
                "stay away from strangers\n\n" +
                "accompanied by an adult\n\n" +
                "see you later alligator\n\n" +
                "make my day you sucker"); //Inserted 30 lines from phrases2.txt, to finish would require completing a sentence every 2 sec.

        task4Timer = findViewById(R.id.task4Timer);
        Button startTimerBtn = findViewById(R.id.startTask4TimerBtn);
        startTimerBtn.setText("start");
        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                task4TxtInput.setVisibility(View.VISIBLE);
                task4Text.setVisibility(View.VISIBLE);
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
                        task4TxtInput.setVisibility(View.INVISIBLE);
                        continueFromTask4Btn.setVisibility(View.VISIBLE);
                        currentTimer.cancel();
                    }
                }, 2000); //set correct time here
            }
        });

        continueFromTask4Btn = findViewById(R.id.continueFromTask4Btn);
        continueFromTask4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent endTestIntent = new Intent(Task4Activity.this, MainActivity.class);
                startActivity(endTestIntent);
                finish();
            }
        });
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

    // Show popup alerts
    private  void showPopup()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Task4Activity.this).create();
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

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button btn = (Button)findViewById(R.id.startTask1TimerBtn);
        if(btn != null) {
            btn.setText("start");
        }
    }
}
