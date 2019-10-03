package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Console;
import java.util.concurrent.ExecutionException;

public class RelaxNResetActivity extends AppCompatActivity {

    private static final int REQ_TrainingToCalm = 111;
    private static final int REQ_Task1ToCalm = 112;
    private static final int REQ_Task2ToCalm = 113;
    private static final int REQ_Task3ToCalm = 114;
    TextView relaxTimer;
    Button nextPhaseBtn;
    ImageView relaxingImgView;
    long startTime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = System.currentTimeMillis() - startTime;
            int seconds = (int) milliseconds/1000;
            int minutes = seconds / 60;
            seconds = seconds % 60;

            relaxTimer.setText(String.format("%d:%02d",minutes,seconds));
            timerHandler.postDelayed(this, 500);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax_nreset);

        relaxTimer = findViewById(R.id.relaxTimer);
        Button startTimerBtn = findViewById(R.id.startRelaxTimerBtn);
        startTimerBtn.setText("start");
        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                relaxingImgView = findViewById(R.id.relaxingImg);
                relaxingImgView.setVisibility(View.VISIBLE);
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);
                btn.setVisibility(View.INVISIBLE);
                timerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timerHandler.removeCallbacks(timerRunnable);
                        relaxingImgView.setVisibility(View.INVISIBLE);
                        nextPhaseBtn.setVisibility(View.VISIBLE);
                    }
                }, 11000); //set correct time here
            }
        });

        relaxingImgView = findViewById(R.id.relaxingImg); //Maybe update imgs displayed if even needed.
        nextPhaseBtn = findViewById(R.id.nextPhaseBtn);
        int requestCode = getIntent().getExtras().getInt("RequestCode");
        if(requestCode == REQ_TrainingToCalm) {
            nextPhaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent task1Intent = new Intent(RelaxNResetActivity.this, Task1Activity.class);
                    startActivity(task1Intent);
                    finish();
                }
            });
        }
        if (requestCode == REQ_Task1ToCalm) {
            nextPhaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent task2Intent = new Intent(RelaxNResetActivity.this, Task2Activity.class);
                    startActivity(task2Intent);
                    finish();
                }
            });
        }
        if (requestCode == REQ_Task2ToCalm) {
            nextPhaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent task3Intent = new Intent(RelaxNResetActivity.this, Task3Activity.class);
                    startActivity(task3Intent);
                    finish();
                }
            });
        }
        if (requestCode == REQ_Task3ToCalm) {
            nextPhaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent task4Intent = new Intent(RelaxNResetActivity.this, Task4Activity.class);
                    startActivity(task4Intent);
                    finish();
                }
            });
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
