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

public class TrainingActivity extends AppCompatActivity {
    private static final int REQ_TrainingToCalm = 111;
    TextView trainingTaskTimer;
    long startTime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = System.currentTimeMillis() - startTime;
            int seconds = (int) milliseconds/1000;
            int minutes = seconds / 60;
            seconds = seconds % 60;

            trainingTaskTimer.setText(String.format("%d:%02d",minutes,seconds));
            timerHandler.postDelayed(this, 500);
        }
    };
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        MultiAutoCompleteTextView trainingTxtInput = findViewById(R.id.TrainingTxtInput);
        trainingTxtInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //Do some handling with the txt input

        TextView trainingTxt = findViewById(R.id.trainingTxt);
        Button continueBtn = findViewById(R.id.ContinueFromTrainingBtn);
        trainingTaskTimer = findViewById(R.id.trainingTaskTimer);
        Button startTimerBtn = findViewById(R.id.startTrainingTaskTimerBtn);
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

        trainingTxt.setText("Adebe D. A.\n" +
                "I come from the land of\n" +
                "\n" +
                "Where You From?\n" +
                "\n" +
                "My people dispossessed of their stories\n" +
                "\n" +
                "and who have died again and again\n" +
                "\n" +
                "in a minstrelsy of afterlives, wakes,\n" +
                "\n" +
                "the dead who walk, waiting and\n" +
                "\n" +
                "furrowed, like ivy crawling up"); //Insert training text here

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueToCalmPhaseIntent = new Intent(TrainingActivity.this, RelaxNResetActivity.class)
                        .putExtra("RequestCode", REQ_TrainingToCalm);
                startActivity(continueToCalmPhaseIntent);
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
