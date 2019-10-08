package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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
    private static final int REQ_Task1ToCalm = 112;
    private static final int REQ_Task2ToCalm = 113;
    private static final int REQ_Task3ToCalm = 114;
    public static Boolean isTrainingSelected = false,
            isTapSelected = false,
            isFlickSelected = false,
            isTiltSelected = false,
            isShakeSelected = false;
    TextView relaxTimer;
    Button nextPhaseBtn;
    ImageView relaxingImgView;
    long endtime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = endtime - System.currentTimeMillis();
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

        if(getIntent().getExtras() != null) {
            isTrainingSelected = getIntent().getExtras().getBoolean("TrainingSelected");
            isFlickSelected = getIntent().getExtras().getBoolean("FlickSelected");
            isShakeSelected = getIntent().getExtras().getBoolean("ShakeSelected");
            isTapSelected = getIntent().getExtras().getBoolean("TapSelected");
            isTiltSelected = getIntent().getExtras().getBoolean("TiltSelected");

        relaxTimer = findViewById(R.id.relaxTimer);
        Button startTimerBtn = findViewById(R.id.startRelaxTimerBtn);
        startTimerBtn.setText("start");
        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                relaxingImgView = findViewById(R.id.relaxingImg);
                relaxingImgView.setVisibility(View.VISIBLE);
                endtime = System.currentTimeMillis() + 61000;
                int rawMusic = R.raw.tranquility;
                final MediaPlayer voice = MediaPlayer.create(RelaxNResetActivity.this, rawMusic);
                voice.start();
                timerHandler.postDelayed(timerRunnable, 0);
                btn.setVisibility(View.INVISIBLE);
                timerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timerHandler.removeCallbacks(timerRunnable);
                        relaxingImgView.setVisibility(View.INVISIBLE);
                        nextPhaseBtn.setVisibility(View.VISIBLE);
                        voice.stop();
                    }
                }, 2000); //set correct time here
            }
        });

        relaxingImgView = findViewById(R.id.relaxingImg); //Maybe update imgs displayed if even needed.
        nextPhaseBtn = findViewById(R.id.nextPhaseBtn);

        int requestCode = getIntent().getExtras().getInt("RequestCode");
        if (requestCode == REQ_Task1ToCalm) {
            nextPhaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent task2Intent = new Intent(RelaxNResetActivity.this, Task2Activity.class)
                            .putExtra("TrainingSelected", isTrainingSelected)
                            .putExtra("FlickSelected", isFlickSelected)
                            .putExtra("TapSelected", isTapSelected)
                            .putExtra("TiltSelected", isTiltSelected)
                            .putExtra("ShakeSelected", isShakeSelected);
                    startActivity(task2Intent);
                    finish();
                }
            });
        }
        if (requestCode == REQ_Task2ToCalm) {
            nextPhaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mainActivityIntent = new Intent(RelaxNResetActivity.this, MainActivity.class);
         /*           Intent task3Intent = new Intent(RelaxNResetActivity.this, Task3Activity.class)
                            .putExtra("TrainingSelected", isTrainingSelected)
                            .putExtra("FlickSelected", isFlickSelected)
                            .putExtra("TapSelected", isTapSelected)
                            .putExtra("TiltSelected", isTiltSelected)
                            .putExtra("ShakeSelected", isShakeSelected);
                    startActivity(task3Intent);*/
                    startActivity(mainActivityIntent);
                    finish();
                }
            });
        }
        if (requestCode == REQ_Task3ToCalm) {
            nextPhaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent task4Intent = new Intent(RelaxNResetActivity.this, Task4Activity.class)
                            .putExtra("TrainingSelected", isTrainingSelected)
                            .putExtra("FlickSelected", isFlickSelected)
                            .putExtra("TapSelected", isTapSelected)
                            .putExtra("TiltSelected", isTiltSelected)
                            .putExtra("ShakeSelected", isShakeSelected);
                    startActivity(task4Intent);
                    finish();
                }
            });
        }
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
