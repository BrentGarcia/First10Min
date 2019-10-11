package com.example.android.first10minutes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;

    Button startButton;

    CheckBox openBlindsCheckBox;

    TextView timeRemainingTextView;

    private boolean timerRunning;

    private static final long START_TIME_IN_MILLIS = 600000;

    private long timeLeftInMillis = START_TIME_IN_MILLIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openBlindsCheckBox = (CheckBox) findViewById(R.id.openBlindsCheckBox);
        timeRemainingTextView = (TextView) findViewById(R.id.timeRemainingTextView);
        startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startButton.setVisibility(View.INVISIBLE);
                timeRemainingTextView.setVisibility(View.VISIBLE);

                countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        timeLeftInMillis = millisUntilFinished;
                        //Translate Hours/Min/Seconds from millis
                        int s = (int)(timeLeftInMillis/1000) % 60;
                        int m = (int)(timeLeftInMillis/(1000*60)) % 60;

                        //Print Time Remaining

                        String timeLeftFormatted = String.format("%02d:%02d", m, s);
                        timeRemainingTextView.setText(timeLeftFormatted);
                    }

                    @Override
                    public void onFinish() {
                        timeRemainingTextView.setText("You done!");
                    }
                }.start();

                timerRunning = true;
            }
        });

    }
}
