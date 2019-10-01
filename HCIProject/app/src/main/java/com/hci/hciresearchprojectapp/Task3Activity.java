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

public class Task3Activity extends AppCompatActivity {
    private static final int REQ_Task3ToCalm = 114;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);
        TextView task1Text = findViewById(R.id.task3Txt);
        task1Text.setText("The chill, worming in\n" +
                "Shock, pleasure, bursting within\n" +
                "Summer tongue awakes\n"); //Insert Task 3 text

        ProgressBar task1Timer = findViewById(R.id.task3Timer);
        task1Timer.setProgress(120);

        Button continueFromTask1Btn = findViewById(R.id.continueFromTask3Btn);
        continueFromTask1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueToCalmPhaseIntent = new Intent(Task3Activity.this, RelaxNResetActivity.class)
                                        .putExtra("RequestCode", REQ_Task3ToCalm);
                startActivity(continueToCalmPhaseIntent);
            }
        });
    }
}
