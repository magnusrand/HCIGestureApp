package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Task1Activity extends AppCompatActivity {
    private static final int REQ_Task1ToCalm = 112;
    TextView task1Timer;
    Button continueFromTask1Btn;
    long startTime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = System.currentTimeMillis() - startTime;
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

        final MultiAutoCompleteTextView task1TxtInput = findViewById(R.id.task1TxtInput);
        task1TxtInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //Do some handling with the txt input

        final TextView task1Text = findViewById(R.id.task1Txt);
        task1Text.setText("JANUARY\n" +
                "Delightful display\n" +
                "Snowdrops bow their pure white heads\n" +
                "To the sun's glory."); //Insert Task 1 text

        task1Timer = findViewById(R.id.task1Timer);
        Button startTimerBtn = findViewById(R.id.startTask1TimerBtn);
        startTimerBtn.setText("start");
        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                task1TxtInput.setVisibility(View.VISIBLE);
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);
                btn.setVisibility(View.INVISIBLE);
                timerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timerHandler.removeCallbacks(timerRunnable);
                        closeKeyboard();
                        task1TxtInput.setVisibility(View.INVISIBLE);
                        continueFromTask1Btn.setVisibility(View.VISIBLE);
                    }
                }, 11000); //set correct time here
            }
        });

        continueFromTask1Btn = findViewById(R.id.continueFromTask1Btn);
        continueFromTask1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueToCalmPhaseIntent = new Intent(Task1Activity.this, RelaxNResetActivity.class)
                        .putExtra("RequestCode", REQ_Task1ToCalm);
                startActivity(continueToCalmPhaseIntent);
            }
        });
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
