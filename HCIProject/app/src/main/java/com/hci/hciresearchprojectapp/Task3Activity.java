package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

public class Task3Activity extends AppCompatActivity {
    private static final int REQ_Task3ToCalm = 114;
    TextView task3Timer;
    Button continueFromTask3Btn;
    long endtime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = endtime - System.currentTimeMillis();
            int seconds = (int) milliseconds/1000;
            int minutes = seconds / 60;
            seconds = seconds % 60;

            task3Timer.setText(String.format("%d:%02d",minutes,seconds));
            timerHandler.postDelayed(this, 500);
        }
    };
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);

        final MultiAutoCompleteTextView task3TxtInput = findViewById(R.id.task3TxtInput);
        task3TxtInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //Do some handling with the txt input

        TextView task3Text = findViewById(R.id.task3Txt);
        task3Text.setText("The chill, worming in\n" +
                "Shock, pleasure, bursting within\n" +
                "Summer tongue awakes\n"); //Insert Task 3 text

        task3Text.setMovementMethod(new ScrollingMovementMethod());
        task3Timer = findViewById(R.id.task3Timer);
        Button startTimerBtn = findViewById(R.id.startTask3TimerBtn);
        startTimerBtn.setText("start");
        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                task3TxtInput.setVisibility(View.VISIBLE);
                endtime = System.currentTimeMillis() + 121000;
                timerHandler.postDelayed(timerRunnable, 0);
                btn.setVisibility(View.INVISIBLE);
                timerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timerHandler.removeCallbacks(timerRunnable);
                        closeKeyboard();
                        task3TxtInput.setVisibility(View.INVISIBLE);
                        continueFromTask3Btn.setVisibility(View.VISIBLE);
                    }
                }, 11000); //set correct time here
            }
        });

        continueFromTask3Btn = findViewById(R.id.continueFromTask3Btn);
        continueFromTask3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueToCalmPhaseIntent = new Intent(Task3Activity.this, RelaxNResetActivity.class)
                                        .putExtra("RequestCode", REQ_Task3ToCalm);
                startActivity(continueToCalmPhaseIntent);
            }
        });

        // Todo uncomment below to make timer start
        //createRandomTimerInSeconds(5,10).start();
    }

    public CountDownTimer createRandomTimerInSeconds(final int minSeconds, final int maxSeconds) {
        final int seconds = minSeconds + (int) (Math.random() * maxSeconds);
        return new CountDownTimer(seconds*1000,1000) {
            int displayCounter = seconds;
            @Override
            public void onTick(long millisUntilFinished) {
                timerTickUpdateEvent(displayCounter);
                displayCounter--;
            }
            @Override
            public void onFinish() {
                // TODO create notification method here
            }
        };
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
