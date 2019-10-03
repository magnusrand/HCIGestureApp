package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Task2Activity extends AppCompatActivity {
    private static final int REQ_Task2ToCalm = 113;
    TextView task2Timer;
    Button continueFromTask2Btn;
    long startTime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = System.currentTimeMillis() - startTime;
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

        final MultiAutoCompleteTextView task2TxtInput = findViewById(R.id.task2TxtInput);
        task2TxtInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //Do some handling with the txt input

        TextView task1Text = findViewById(R.id.task2Txt);
        task1Text.setText("Like crunchy cornflakes\n" +
                "Gold leaves rustle underfoot\n" +
                "Beauty in decay.\n"); //Insert Task 2 text

        task2Timer = findViewById(R.id.task2Timer);
        Button startTimerBtn = findViewById(R.id.startTask2TimerBtn);
        startTimerBtn.setText("start");
        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;

                task2TxtInput.setVisibility(View.VISIBLE);
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);
                btn.setVisibility(View.INVISIBLE);
                timerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timerHandler.removeCallbacks(timerRunnable);
                        closeKeyboard();
                        task2TxtInput.setVisibility(View.INVISIBLE);
                        continueFromTask2Btn.setVisibility(View.VISIBLE);
                    }
                }, 11000); //set correct time here
            }
        });

        continueFromTask2Btn = findViewById(R.id.continueFromTask2Btn);
        continueFromTask2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueToCalmPhaseIntent = new Intent(Task2Activity.this, RelaxNResetActivity.class)
                        .putExtra("RequestCode", REQ_Task2ToCalm);
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
