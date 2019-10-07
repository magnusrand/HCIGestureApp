package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TrainingActivity extends AppCompatActivity {
    private static final int REQ_TrainingToCalm = 111;
    TextView trainingTaskTimer;
    TextView trainingTxt;
    Button continueBtn;
    long endtime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = endtime - System.currentTimeMillis();
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

        final MultiAutoCompleteTextView trainingTxtInput = findViewById(R.id.TrainingTxtInput);
        trainingTxtInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //Do some handling with the txt input

        trainingTxt = findViewById(R.id.trainingTxt);
        continueBtn = findViewById(R.id.ContinueFromTrainingBtn);
        trainingTaskTimer = findViewById(R.id.trainingTaskTimer);
        Button startTimerBtn = findViewById(R.id.startTrainingTaskTimerBtn);
        startTimerBtn.setText("start");
        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                    Button btn = (Button)v;
                    trainingTxtInput.setVisibility(View.VISIBLE);
                    trainingTxt.setVisibility(View.VISIBLE);
                    endtime = System.currentTimeMillis() + 121000;
                    timerHandler.postDelayed(timerRunnable, 0);
                    btn.setVisibility(View.INVISIBLE);
                    timerHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            timerHandler.removeCallbacks(timerRunnable);
                            closeKeyboard();
                            trainingTxtInput.setVisibility(View.INVISIBLE);
                            continueBtn.setVisibility(View.VISIBLE);
                        }
                    }, 2000); //set correct time here
                }
        });

        trainingTxt.setMovementMethod(new ScrollingMovementMethod());
        trainingTxt.setText("my watch fell in the water\n\n" +
                "prevailing wind from the east\n\n" +
                "never too rich and never too thin\n\n" +
                "breathing is difficult\n\n" +
                "I can see the rings on Saturn\n\n" +
                "physics and chemistry are hard\n\n" +
                "my bank account is overdrawn\n\n" +
                "elections bring out the best\n\n" +
                "we are having spaghetti\n\n" +
                "time to go shopping\n\n" +
                "a problem with the engine\n\n" +
                "elephants are afraid of mice\n\n" +
                "my favorite place to visit\n\n" +
                "three two one zero blast off\n\n" +
                "my favorite subject is psychology\n\n" +
                "circumstances are unacceptable\n\n" +
                "watch out for low flying objects\n\n" +
                "if at first you do not succeed\n\n" +
                "please provide your date of birth\n\n" +
                "we run the risk of failure\n\n" +
                "prayer in schools offends some\n\n" +
                "he is just like everyone else\n\n" +
                "great disturbance in the force\n\n" +
                "love means many things\n\n" +
                "you must be getting old\n\n" +
                "the world is a stage\n\n" +
                "can I skate with sister today\n\n" +
                "neither a borrower nor a lender be\n\n" +
                "one heck of a question"); //Inserted 30 lines from phrases2.txt, to finish would require completing a sentence every 2 sec.

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnToStartIntent = new Intent(TrainingActivity.this, MainActivity.class);
                startActivity(returnToStartIntent);
                finish();
            }
        });
        showPopup();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
    
    // Show popup alerts
    private  void showPopup()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(TrainingActivity.this).create();
        alertDialog.setTitle("Notification");
        alertDialog.setMessage("Dismiss me with the taught gesture" +
                "");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }
}
