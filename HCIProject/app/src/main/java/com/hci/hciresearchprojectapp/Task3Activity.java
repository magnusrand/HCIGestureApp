package com.hci.hciresearchprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.hci.hciresearchprojectapp.Timer.timerTickUpdateEvent;

public class Task3Activity extends AppCompatActivity {
    private static final int REQ_Task3ToCalm = 114;
    TextView task3Timer;
    TextView task3Text;
    Button continueFromTask3Btn;
    long endtime = 0;

    CountDownTimer currentTimer;

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = endtime - System.currentTimeMillis();
            int seconds = (int) milliseconds/1000;
            int minutes = seconds / 60;
            seconds = seconds % 60;

            task3Timer.setText(String.format("%d:%02d",minutes,seconds));
            timerHandler.postDelayed(this, 500);
        }
    };
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);

        final MultiAutoCompleteTextView task3TxtInput = findViewById(R.id.task3TxtInput);
        task3TxtInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //Do some handling with the txt input

        task3Text = findViewById(R.id.task3Txt);
        task3Text.setText("frequently asked questions\n\n" +
                "round robin scheduling\n\n" +
                "information super highway\n\n" +
                "my favorite web browser\n\n" +
                "the laser printer is jammed\n\n" +
                "all good boys deserve fudge\n\n" +
                "the second largest country\n\n" +
                "call for more details\n\n" +
                "just in time for the party\n\n" +
                "have a good weekend\n\n" +
                "video camera with a zoom lens\n\n" +
                "what a monkey sees a monkey will do\n\n" +
                "that is very unfortunate\n\n" +
                "the back yard of our house\n\n" +
                "this is a very good idea\n\n" +
                "reading week is just about here\n\n" +
                "our fax number has changed\n\n" +
                "thank you for your help\n\n" +
                "no exchange without a bill\n\n" +
                "the early bird gets the worm\n\n" +
                "buckle up for safety\n\n" +
                "this is too much to handle\n\n" +
                "protect your environment\n\n" +
                "world population is growing\n\n" +
                "the library is closed today\n\n" +
                "Mary had a little lamb\n\n" +
                "teaching services will help\n\n" +
                "we accept personal checks\n\n" +
                "this is a non profit organization\n\n" +
                "user friendly interface"); //Inserted 30 lines from phrases2.txt, to finish would require completing a sentence every 2 sec.

        task3Text.setMovementMethod(new ScrollingMovementMethod());
        task3Timer = findViewById(R.id.task3Timer);
        Button startTimerBtn = findViewById(R.id.startTask3TimerBtn);
        startTimerBtn.setText("start");
        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                task3TxtInput.setVisibility(View.VISIBLE);
                task3Text.setVisibility(View.VISIBLE);
                endtime = System.currentTimeMillis() + 121000;

                currentTimer = createRandomTimerInSeconds(5,10);
                currentTimer.start();

                timerHandler.postDelayed(timerRunnable, 0);
                btn.setVisibility(View.INVISIBLE);
                timerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timerHandler.removeCallbacks(timerRunnable);
                        closeKeyboard();
                        task3TxtInput.setVisibility(View.INVISIBLE);
                        continueFromTask3Btn.setVisibility(View.VISIBLE);
                        currentTimer.cancel();
                    }
                }, 2000); //set correct time here
            }
        });

        continueFromTask3Btn = findViewById(R.id.continueFromTask3Btn);
        continueFromTask3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueToCalmPhaseIntent = new Intent(Task3Activity.this, RelaxNResetActivity.class)
                                        .putExtra("RequestCode", REQ_Task3ToCalm);
                startActivity(continueToCalmPhaseIntent);
            }
        });
    }

    public CountDownTimer createRandomTimerInSeconds(final int minSeconds, final int maxSeconds) {
        final int seconds = minSeconds + (int) (Math.random() * (maxSeconds - minSeconds) + 1);
        return new CountDownTimer(seconds*1000,1000) {
            int displayCounter = seconds;
            @Override
            public void onTick(long millisUntilFinished) {
                timerTickUpdateEvent(displayCounter);
                displayCounter--;
            }
            @Override
            public void onFinish() {
                showPopup();
            }
        };
    }

    // Show popup alerts
    private  void showPopup()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Task3Activity.this).create();
        alertDialog.setTitle("Notification");
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Dismiss me with the taught gesture" +
                "");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        currentTimer = createRandomTimerInSeconds(5,10);
                        currentTimer.start();
                    }
                });
        alertDialog.show();

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
}
