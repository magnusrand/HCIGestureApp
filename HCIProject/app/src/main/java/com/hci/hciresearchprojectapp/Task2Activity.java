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

public class Task2Activity extends AppCompatActivity {

    private static final int REQ_Task2ToCalm = 113;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);

        TextView task1Text = findViewById(R.id.task2Txt);
        task1Text.setText("Like crunchy cornflakes\n" +
                "Gold leaves rustle underfoot\n" +
                "Beauty in decay.\n"); //Insert Task 2 text

        ProgressBar task1Timer = findViewById(R.id.task2Timer);
        task1Timer.setProgress(120);

        Button continueFromTask1Btn = findViewById(R.id.continueFromTask2Btn);
        continueFromTask1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueToCalmPhaseIntent = new Intent(Task2Activity.this, RelaxNResetActivity.class)
                        .putExtra("RequestCode", REQ_Task2ToCalm);
                startActivity(continueToCalmPhaseIntent);
            }
        });
    }
}
