package com.example.cooltimes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button timerBtn;
    private TextView clocktextView;
    private SeekBar seekBar;
    private Boolean isTimerOn =  true;
    private CountDownTimer timer;

    int min = 0;
    int seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerBtn = (Button) findViewById(R.id.timerBtn);
        clocktextView = findViewById(R.id.clocktextView);

        isTimerOn = false;

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(60);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                long progressInMillis = i*1000;
                updateTimer(progressInMillis);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        timerBtn.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (!isTimerOn){
                    timerBtn.setText("STOP");
                    seekBar.setEnabled(false);
                    isTimerOn=true;
                    timer = new CountDownTimer(seekBar.getProgress() * 1000,1000) {
                        @Override
                        public void onTick(long l) {
                            updateTimer(l);
                            if (min<1 && seconds<5){
                                turnOnAnimation();
                            }
                        }
                        @Override
                        public void onFinish() {
                            MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.bell_sound);
                            player.start();
                            resetTiemer();;
                        }
                    };
                    timer.start();
                } else {
                    resetTiemer();
                }

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateTimer(long millisUntillFinish){
        min = (int) millisUntillFinish/1000/60;
        seconds  = (int) millisUntillFinish/1000 - (min * 60);

        String minuteString = "";
        String secondsString = "";

        if (min<10){
            minuteString = "0" + min;
        } else {
            minuteString = String.valueOf(min);
        }

        if (seconds<10){
            secondsString = "0" + seconds;
        } else {
            secondsString = String.valueOf(seconds);
        }
        clocktextView.setText(minuteString + ":" + secondsString);
    }

    @SuppressLint("SetTextI18n")
    private void resetTiemer(){
        timer.cancel();
        timerBtn.setText("START");
        clocktextView.setText("00:60");
        clocktextView.setTextColor(getResources().getColor(R.color.black));
        seekBar.setEnabled(true);
        seekBar.setProgress(60);
        isTimerOn = false;
        clocktextView.clearAnimation();
    }

    private void turnOnAnimation(){
        clocktextView.setTextColor(getResources().getColor(R.color.red));
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(100);
        animation.setStartOffset(50);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        clocktextView.startAnimation(animation);
    }

}