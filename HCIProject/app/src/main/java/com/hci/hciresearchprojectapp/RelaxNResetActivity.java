package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.Console;
import java.util.concurrent.ExecutionException;

public class RelaxNResetActivity extends AppCompatActivity {

    private static final int REQ_TrainingToCalm = 111;
    private static final int REQ_Task1ToCalm = 112;
    private static final int REQ_Task2ToCalm = 113;
    private static final int REQ_Task3ToCalm = 114;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax_nreset);

        ProgressBar RelaxTimer = findViewById(R.id.relaxTimer);
        RelaxTimer.setMax(60);

        ImageView RelaxingImgs = findViewById(R.id.relaxingImg); //Maybe update imgs displayed if even needed.
        Button nextPhaseBtn = findViewById(R.id.nextPhaseBtn);
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
    }
