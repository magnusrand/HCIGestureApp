package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class RelaxNResetActivity extends AppCompatActivity {

    private static final int REQ_TrainingToCalm = 111;
    private static final int REQ_Task1ToCalm = 112;
    private static final int REQ_Task2ToCalm = 113;
    private static final int REQ_Task3ToCalm = 113;
    Button nextPhaseBtn = findViewById(R.id.nextPhaseBtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax_nreset);

        ProgressBar RelaxTimer = findViewById(R.id.relaxTimer);
        RelaxTimer.setProgress(60);

        ImageView RelaxingImgs = findViewById(R.id.relaxingImg); //Maybe update imgs displayed if even needed.
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_TrainingToCalm) {
            nextPhaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent task1Intent = new Intent(RelaxNResetActivity.this, Task1Activity.class);
                    startActivity(task1Intent);
                }
            });
        } if(requestCode == REQ_Task1ToCalm) {
            nextPhaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent task2Intent = new Intent(RelaxNResetActivity.this, Task2Activity.class);
                    startActivity(task2Intent);
                }
            });
        } if(requestCode == REQ_Task2ToCalm) {
            nextPhaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent task3Intent = new Intent(RelaxNResetActivity.this, Task3Activity.class);
                    startActivity(task3Intent);
                }
            });
        } if(requestCode == REQ_Task3ToCalm) {
            nextPhaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent task4Intent = new Intent(RelaxNResetActivity.this, Task4Activity.class);
                    startActivity(task4Intent);
                }
            });
        }

    }
}
