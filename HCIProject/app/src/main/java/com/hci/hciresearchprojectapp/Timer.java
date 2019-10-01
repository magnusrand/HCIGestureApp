package com.hci.hciresearchprojectapp;

import android.os.CountDownTimer;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Timer extends AppCompatActivity {

    public CountDownTimer createTimerTextViewInSeconds(final int seconds, final TextView textView) {
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
                timerDoneEvent(textView);
            }
        };
    }

    public void timerDoneEvent(TextView textView){
        createTimerTextViewInSeconds(5, textView).start();
    }

    public void timerTickUpdateEvent(int counterValue) {
        // TODO run function that needs counterValue here
    }
}



