package com.example.android.first10minutes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;

    Button startButton;
    Button musicButton;

    CheckBox openBlindsCheckBox;
    CheckBox brushTeethCheckBox;
    CheckBox pushUpsCheckBox;
    CheckBox pullUpsCheckBox;
    CheckBox drinkWaterCheckBox;
    CheckBox clothingPreppedCheckBox;
    CheckBox makeBedCheckBox;
    CheckBox beginMeditationCheckBox;

    TextView timeRemainingTextView;
    TextView currentStreakTextView;

    private boolean complete;
    private boolean timerRunning;

    private String currentStreakText;
    private boolean completeYesNo;
    private int currentStreak;
    private int loadStreak;

    private static final long START_TIME_IN_MILLIS = 600000;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CURRENT_STREAK_TEXT = "Current Streak: ";
    public static final String COMPLETE = "complete";
    public static final String CURRENT_STREAK = "streak";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openBlindsCheckBox = (CheckBox) findViewById(R.id.openBlindsCheckBox);
        brushTeethCheckBox = (CheckBox) findViewById(R.id.brushTeethCheckBox);
        pushUpsCheckBox = (CheckBox) findViewById(R.id.pushUpsCheckBox);
        pullUpsCheckBox = (CheckBox) findViewById(R.id.pullUpsCheckBox);
        drinkWaterCheckBox = (CheckBox) findViewById(R.id.drinkWaterCheckBox);
        clothingPreppedCheckBox = (CheckBox) findViewById(R.id.clothingPreppedCheckBox);
        makeBedCheckBox = (CheckBox) findViewById(R.id.makeBedCheckBox);
        beginMeditationCheckBox = (CheckBox) findViewById(R.id.beginMeditationCheckBox);


        currentStreakTextView =  (TextView) findViewById(R.id.currentStreakTextView);
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

                        //Check if all tasks complete
                        checkIfComplete();

                        if (complete){
                            timeRemainingTextView.setText("Tasks Complete!");
                            saveData();
                            cancel();
                        }
                    }

                    @Override
                    public void onFinish() {
                        complete = false;
                        timeRemainingTextView.setText("Ran out of time!");
                        saveData();
                    }
                }.start();

                timerRunning = true;
            }
        });

        loadData();
        updateViews();
    }

    private void checkIfComplete() {
        if (openBlindsCheckBox.isChecked() && brushTeethCheckBox.isChecked()
        && pushUpsCheckBox.isChecked() && pullUpsCheckBox.isChecked()
        && drinkWaterCheckBox.isChecked() && clothingPreppedCheckBox.isChecked()
        && makeBedCheckBox.isChecked() && beginMeditationCheckBox.isChecked()){
            complete = true;
        }
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        currentStreak++;

        if (!complete){
            currentStreak = 0;
        }

        editor.putInt(CURRENT_STREAK, currentStreak);
        editor.putString(CURRENT_STREAK_TEXT, "Current Streak: " + currentStreak);

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

//        text = sharedPreferences.getString(TEXT, "");
//        completeYesNo = sharedPreferences.getBoolean(COMPLETE, false);
        currentStreakText = sharedPreferences.getString(CURRENT_STREAK_TEXT, "");
        loadStreak = sharedPreferences.getInt(CURRENT_STREAK, 0);

    }

    public void updateViews() {
//        timeRemainingTextView.setText(text);
//        complete = completeYesNo;
        currentStreakTextView.setText(currentStreakText);
        currentStreak = loadStreak;

    }
}
