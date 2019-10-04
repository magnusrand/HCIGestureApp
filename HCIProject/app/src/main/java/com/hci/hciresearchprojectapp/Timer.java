package com.hci.hciresearchprojectapp;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Timer extends AppCompatActivity {

    public static CountDownTimer createTimerTextviewInSeconds(final int seconds, final TextView textView) {
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

    public static CountDownTimer createRandomTimerInSeconds(final int minSeconds, final int maxSeconds, final int ActivityNo) {
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
                switch (ActivityNo){
                    case 1:

                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:

                }
                randomTimerDoneEvent(minSeconds, maxSeconds);
            }
        };
    }

    public static void textViewTimerDoneEvent(TextView textView){
        createTimerTextviewInSeconds(5, textView).start();
    }

    public static String randomTimerDoneEvent(int minSeconds, int maxSeconds) {
        //TODO This event function should pop upa notification
        Log.d("Done", "Done");
        return "hi";
        //createRandomTimerInSeconds(minSeconds, maxSeconds).start();
    }

    public static void timerTickUpdateEvent(int counterValue) {
        // TODO run function that needs counterValue here
        Log.d(String.valueOf(counterValue), String.valueOf(counterValue)); // Logs value, remove on implementation
    }
}



