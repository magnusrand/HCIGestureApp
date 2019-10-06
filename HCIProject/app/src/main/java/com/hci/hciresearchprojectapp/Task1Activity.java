package com.hci.hciresearchprojectapp;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.hci.hciresearchprojectapp.Timer.timerTickUpdateEvent;

public class Task1Activity extends AppCompatActivity {
    private static final int REQ_Task1ToCalm = 112;
    TextView task1Timer;
    TextView task1Text;
    Button continueFromTask1Btn;
    long endtime = 0;

    String tempTextTest = "a";

    Handler timerHandler = new Handler();
    Runnable timerRunnable= new Runnable() {
        @Override
        public void run() {
            long milliseconds = endtime - System.currentTimeMillis();
            int seconds = (int) milliseconds/1000;
            int minutes = seconds / 60;
            seconds = seconds % 60;

            task1Timer.setText(String.format("%d:%02d",minutes,seconds));
            timerHandler.postDelayed(this, 500);
        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);

        final MultiAutoCompleteTextView task1TxtInput = findViewById(R.id.task1TxtInput);
        task1TxtInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //Do some handling with the txt input

        task1Text = findViewById(R.id.task1Txt);

        task1Text.setMovementMethod(new ScrollingMovementMethod());
        task1Text.setText("question that must be answered\n\n" +
                "beware the ides of March\n\n" +
                "double double toil and trouble\n\n" +
                "the power of denial\n\n" +
                "I agree with you\n\n" +
                "do not say anything\n\n" +
                "play it again Sam\n\n" +
                "the force is with you\n\n" +
                "you are not a jedi yet\n\n" +
                "an offer you cannot refuse\n\n" +
                "are you talking to me\n\n" +
                "yes you are very smart\n\n" +
                "all work and no play\n\n" +
                "hair gel is very greasy\n\n" +
                "Valium in the economy size\n\n" +
                "the facts get in the way\n\n" +
                "the dreamers of dreams\n\n" +
                "did you have a good time\n\n" +
                "space is a high priority\n\n" +
                "you are a wonderful example\n\n" +
                "do not squander your time\n\n" +
                "do not drink too much\n\n" +
                "take a coffee break\n\n" +
                "popularity is desired by all\n\n" +
                "the music is better than it sounds\n\n" +
                "starlight and dewdrop\n\n" +
                "the living is easy\n\n" +
                "fish are jumping\n\n" +
                "the cotton is high\n\n" +
                "drove my chevy to the levee"); //Inserted 30 lines from phrases2.txt, to finish would require completing a sentence every 2 sec.

        task1Timer = findViewById(R.id.task1Timer);
        Button startTimerBtn = findViewById(R.id.startTask1TimerBtn);
        startTimerBtn.setText("start");
        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                task1TxtInput.setVisibility(View.VISIBLE);
                task1Text.setVisibility(View.VISIBLE);
                endtime = System.currentTimeMillis() + 121000;
                timerHandler.postDelayed(timerRunnable, 0);
                btn.setVisibility(View.INVISIBLE);
                timerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timerHandler.removeCallbacks(timerRunnable);
                        closeKeyboard();
                        task1TxtInput.setVisibility(View.INVISIBLE);
                        continueFromTask1Btn.setVisibility(View.VISIBLE);
                    }
                }, 2000); //set correct time here
            }
        });

        continueFromTask1Btn = findViewById(R.id.continueFromTask1Btn);
        continueFromTask1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueToCalmPhaseIntent = new Intent(Task1Activity.this, RelaxNResetActivity.class)
                        .putExtra("RequestCode", REQ_Task1ToCalm);
                startActivity(continueToCalmPhaseIntent);
            }
        });

        //createRandomTimerInSeconds(5,10).start();
    }

    public CountDownTimer createRandomTimerInSeconds(final int minSeconds, final int maxSeconds) {
        final int seconds = minSeconds + (int) (Math.random() * maxSeconds);
        return new CountDownTimer(seconds*1000,1000) {
            int displayCounter = seconds;
            @Override
            public void onTick(long millisUntilFinished) {
                timerTickUpdateEvent(displayCounter);
                displayCounter--;
            }
            @Override
            public void onFinish() {
                // TODO create notification method here
            }
        };
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
