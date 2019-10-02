package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Task4Activity extends AppCompatActivity {
    TextView task4Timer;
    long startTime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = System.currentTimeMillis() - startTime;
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

        MultiAutoCompleteTextView task4TxtInput = findViewById(R.id.task4TxtInput);
        task4TxtInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //Do some handling with the txt input

        TextView task1Text = findViewById(R.id.task4Txt);
        task1Text.setText("I was in fire,\n" +
                "The room was dark and somber.\n" +
                "I sleep peacefully\n"); //Insert Task 4 text

        task4Timer = findViewById(R.id.task4Timer);
        Button startTimerBtn = findViewById(R.id.startTask4TimerBtn);
        startTimerBtn.setText("start");
        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                if(btn.getText().equals("stop")){
                    timerHandler.removeCallbacks(timerRunnable);
                    btn.setText("start");
                } else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    btn.setText("stop");
                }
            }
        });

        Button continueFromTask1Btn = findViewById(R.id.continueFromTask4Btn);
        continueFromTask1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent endTestIntent = new Intent(Task4Activity.this, MainActivity.class);
                startActivity(endTestIntent);
                finish();
            }
        });
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
