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

public class Task2Activity extends AppCompatActivity {
    private static final int REQ_Task2ToCalm = 113;
    TextView task2Timer;
    TextView task2Text;
    Button continueFromTask2Btn;
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

            task2Timer.setText(String.format("%d:%02d",minutes,seconds));
            timerHandler.postDelayed(this, 500);
        }
    };
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);

        final MultiAutoCompleteTextView task2TxtInput = findViewById(R.id.task2TxtInput);
        task2TxtInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //Do some handling with the txt input

        task2Text = findViewById(R.id.task2Txt);

        task2Text.setMovementMethod(new ScrollingMovementMethod());
        task2Text.setText("but the levee was dry\n\n" +
                "I took the rover from the shop\n\n" +
                "movie about a nutty professor\n\n" +
                "come and see our new car\n\n" +
                "coming up with killer sound bites\n\n" +
                "I am going to a music lesson\n\n" +
                "the opposing team is over there\n\n" +
                "soon we will return from the city\n\n" +
                "I am wearing a tie and a jacket\n\n" +
                "the quick brown fox jumped\n\n" +
                "all together in one big pile\n\n" +
                "wear a crown with many jewels\n\n" +
                "there will be some fog tonight\n\n" +
                "I am allergic to bees and peanuts\n\n" +
                "he is still on our team\n\n" +
                "the dow jones index has risen\n\n" +
                "my preferred treat is chocolate\n\n" +
                "the king sends you to the tower\n\n" +
                "we are subjects and must obey\n\n" +
                "mom made her a turtleneck\n\n" +
                "goldilocks and the three bears\n\n" +
                "we went grocery shopping\n\n" +
                "the assignment is due today\n\n" +
                "what you see is what you get\n\n" +
                "for your information only\n\n" +
                "a quarter of a century\n\n" +
                "the store will close at ten\n\n" +
                "head shoulders knees and toes\n\n" +
                "vanilla flavored ice cream"); //Inserted 30 lines from phrases2.txt, to finish would require completing a sentence every 2 sec.


        task2Timer = findViewById(R.id.task2Timer);
        Button startTimerBtn = findViewById(R.id.startTask2TimerBtn);
        startTimerBtn.setText("start");
        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                task2TxtInput.setVisibility(View.VISIBLE);
                task2Text.setVisibility(View.VISIBLE);

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
                        task2TxtInput.setVisibility(View.INVISIBLE);
                        continueFromTask2Btn.setVisibility(View.VISIBLE);
                        currentTimer.cancel();
                    }
                }, 20000); //set correct time here
            }
        });

        continueFromTask2Btn = findViewById(R.id.continueFromTask2Btn);
        continueFromTask2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueToCalmPhaseIntent = new Intent(Task2Activity.this, RelaxNResetActivity.class)
                        .putExtra("RequestCode", REQ_Task2ToCalm);
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
        AlertDialog alertDialog = new AlertDialog.Builder(Task2Activity.this).create();
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
