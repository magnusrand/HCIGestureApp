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

public class TrainingActivity extends AppCompatActivity {

    private static final int REQ_TrainingToCalm = 111;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        TextView trainingTxt = findViewById(R.id.trainingTxt);
        Button continueBtn = findViewById(R.id.ContinueFromTrainingBtn);
        ProgressBar timer = findViewById(R.id.Timer);

        timer.setMax(120);

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
}
