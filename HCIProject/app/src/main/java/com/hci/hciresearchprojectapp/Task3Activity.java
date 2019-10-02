package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Task3Activity extends AppCompatActivity {
    private static final int REQ_Task3ToCalm = 114;
    TextView task3Timer;
    long startTime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = System.currentTimeMillis() - startTime;
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

        MultiAutoCompleteTextView task3TxtInput = findViewById(R.id.task3TxtInput);
        task3TxtInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //Do some handling with the txt input

        TextView task1Text = findViewById(R.id.task3Txt);
        task1Text.setText("The chill, worming in\n" +
                "Shock, pleasure, bursting within\n" +
                "Summer tongue awakes\n"); //Insert Task 3 text

        task3Timer = findViewById(R.id.task3Timer);
        Button startTimerBtn = findViewById(R.id.startTask3TimerBtn);
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

        Button continueFromTask3Btn = findViewById(R.id.continueFromTask3Btn);
        continueFromTask3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueToCalmPhaseIntent = new Intent(Task3Activity.this, RelaxNResetActivity.class)
                                        .putExtra("RequestCode", REQ_Task3ToCalm);
                startActivity(continueToCalmPhaseIntent);
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
