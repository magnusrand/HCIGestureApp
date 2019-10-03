package com.hci.hciresearchprojectapp;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Timer extends AppCompatActivity {

    public CountDownTimer createTimerTextviewInSeconds(final int seconds, final TextView textView) {
        return new CountDownTimer(seconds*1000,1000) {
            int displayCounter = seconds;
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(String.valueOf(displayCounter));
                timerTickUpdateEvent(displayCounter);
                displayCounter--;
            }
            @Override
            public void onFinish() {
                textViewTimerDoneEvent(textView);
            }
        };
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
                randomTimerDoneEvent(minSeconds, maxSeconds);
            }
        };
    }

    public void textViewTimerDoneEvent(TextView textView){
        createTimerTextviewInSeconds(5, textView).start();
    }

    public void randomTimerDoneEvent(int minSeconds, int maxSeconds) {
        Log.d("Done", "Done");
        createRandomTimerInSeconds(minSeconds, maxSeconds).start();
    }

    public void timerTickUpdateEvent(int counterValue) {
        // TODO run function that needs counterValue here
        Log.d(String.valueOf(counterValue), String.valueOf(counterValue)); // Logs value, remove on implementation
    }
}



