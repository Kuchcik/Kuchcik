package com.example.okno_wyszukiwanie_kat_nazwy.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.okno_wyszukiwanie_kat_nazwy.R;

import java.io.File;
import java.util.Locale;

public class Timer2 extends AppCompatActivity {
    private EditText EditTextInput;
    private TextView Timertext;
    private Button TimerSet;
    private Button TimerButton;
    private Button TimerReset;
    private CountDownTimer Timer;
    private  long StartTimeInMillis;
    private long timeLeftInMiliseconds;
    private long EndTime;
    private boolean timerRunning;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer2);
        Timertext = findViewById(R.id.timer_text);
        TimerButton = findViewById(R.id.timer_button);
        TimerReset = findViewById(R.id.timer_reset);
        EditTextInput = findViewById(R.id.editTextTime);
        TimerSet = findViewById(R.id.setTimeButton);
        mediaPlayer = MediaPlayer.create(this, R.raw.audio);

        TimerSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = EditTextInput.getText().toString();
                if(input.length() == 0){
                    Toast.makeText(Timer2.this,"Field can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisInput = Long.parseLong(input)*60000;
                if(millisInput == 0){
                    Toast.makeText(Timer2.this,"Please enter a positive number",Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                EditTextInput.setText("");
            }
        });

        TimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning){
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        TimerReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        updateCountDownText();
    }
    private void setTime(long milliseconds){
        StartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }
    private void startTimer(){
        EndTime = System.currentTimeMillis() + timeLeftInMiliseconds;

        Timer = new CountDownTimer(timeLeftInMiliseconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMiliseconds = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mediaPlayer.start();

                //Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                //final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                timerRunning = false;

                updateWatchInterface();
                //r.play();
            }
        }.start();
        timerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer(){
        Timer.cancel();
        timerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer(){
        timeLeftInMiliseconds = StartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
        mediaPlayer.stop();
    }

    private void updateCountDownText(){
        int hours = (int) (timeLeftInMiliseconds / 1000) / 3600;
        int minutes = (int) ((timeLeftInMiliseconds / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMiliseconds / 1000) % 60;

        String timeLeftFormatted;
        if(hours > 0){
            timeLeftFormatted = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        Timertext.setText(timeLeftFormatted);
    }

    private void updateWatchInterface(){
        if(timerRunning){
            EditTextInput.setVisibility(View.INVISIBLE);
            TimerSet.setVisibility(View.INVISIBLE);
            TimerReset.setVisibility(View.INVISIBLE);
            TimerButton.setText("Pause");
        } else {
            EditTextInput.setVisibility(View.VISIBLE);
            TimerSet.setVisibility(View.VISIBLE);
            TimerButton.setText("Start");

            if(timeLeftInMiliseconds < 1000){
                TimerButton.setVisibility(View.INVISIBLE);
            } else {
                TimerButton.setVisibility(View.VISIBLE);
            }

            if(timeLeftInMiliseconds < StartTimeInMillis){
                TimerReset.setVisibility(View.VISIBLE);
            } else {
                TimerReset.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft",timeLeftInMiliseconds);
        outState.putBoolean("timerRunning",timerRunning);
        outState.putLong("endTime",EndTime);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        timeLeftInMiliseconds = savedInstanceState.getLong("millisLeft");
        timerRunning = savedInstanceState.getBoolean("timerRunning");
        updateCountDownText();
        updateWatchInterface();
        if(timerRunning){
            EndTime=savedInstanceState.getLong("endTime");
            timeLeftInMiliseconds = EndTime - System.currentTimeMillis();
            startTimer();
        }
    }

    private void closeKeyboard(){
        View view= this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis",StartTimeInMillis);
        editor.putLong("millisLeft",timeLeftInMiliseconds);
        editor.putBoolean("timerRunning",timerRunning);
        editor.putLong("endTime",EndTime);

        editor.apply();

        if(Timer != null) {
            Timer.cancel();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
        StartTimeInMillis = prefs.getLong("startTimeInMillis",600000);
        timeLeftInMiliseconds = prefs.getLong("milliLeft", StartTimeInMillis);
        timerRunning = prefs.getBoolean("timerRunning",false);
        updateCountDownText();
        updateWatchInterface();
        if(timerRunning){
            EndTime = prefs.getLong("endTime",0);
            timeLeftInMiliseconds = EndTime - System.currentTimeMillis();

            if(timeLeftInMiliseconds < 0){
                timeLeftInMiliseconds = 0;
                timerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }
}
