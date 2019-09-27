package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Task4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4);

        TextView task1Text = findViewById(R.id.task4Txt);
        task1Text.setText("The chill, worming in\n" +
                "Shock, pleasure, bursting within\n" +
                "Summer tongue awakes\n"); //Insert Task 4 text

        ProgressBar task1Timer = findViewById(R.id.task4Timer);
        task1Timer.setProgress(120);

        Button continueFromTask1Btn = findViewById(R.id.continueFromTask4Btn);
        continueFromTask1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent endTestIntent = new Intent(Task4Activity.this, MainActivity.class);
                startActivity(endTestIntent);
            }
        });
    }
}
