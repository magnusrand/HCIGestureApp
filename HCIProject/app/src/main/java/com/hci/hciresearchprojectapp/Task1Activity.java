package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Task1Activity extends AppCompatActivity {
    private static final int REQ_Task1ToCalm = 112;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);

        TextView task1Text = findViewById(R.id.task1Txt);
        task1Text.setText("JANUARY\n" +
                "Delightful display\n" +
                "Snowdrops bow their pure white heads\n" +
                "To the sun's glory."); //Insert Task 1 text

        ProgressBar task1Timer = findViewById(R.id.task1Timer);
        task1Timer.setProgress(120);

        Button continueFromTask1Btn = findViewById(R.id.continueFromTask1Btn);
        continueFromTask1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueToCalmPhaseIntent = new Intent(Task1Activity.this, RelaxNResetActivity.class)
                        .putExtra("RequestCode", REQ_Task1ToCalm);
                startActivity(continueToCalmPhaseIntent);
            }
        });
    }
}
