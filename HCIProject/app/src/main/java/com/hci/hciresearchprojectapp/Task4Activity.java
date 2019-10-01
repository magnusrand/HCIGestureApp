package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Task4Activity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4);

        TextView task1Text = findViewById(R.id.task4Txt);
        task1Text.setText("I was in fire,\n" +
                "The room was dark and somber.\n" +
                "I sleep peacefully\n"); //Insert Task 4 text

        ProgressBar task1Timer = findViewById(R.id.task4Timer);
        task1Timer.setProgress(120);

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
}
