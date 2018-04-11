package com.example.emanu.pmu_projekat.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.example.emanu.pmu_projekat.Model;
import com.example.emanu.pmu_projekat.R;

public class OptionsActivity extends AppCompatActivity {
    public static final String PREFS = "prefs";

    private static final int SPEED_DIFF = 100;
    private static final int MIN_SPEED = 800;

    private static final int REFRESH_DIFF = 20;
    private static final int MIN_REFRESH = 50;

    private static final int TIMEOUT_DIFF = 100;
    private static final int MIN_TIMEOUT = 100;

    public static final int DEFAULT_SPEED_VALUE = 1200;
    public static final String CURRENT_SPEED = "current_speed";

    public static final int DEFAULT_REFRESH_VALUE = 150;
    public static final String CURRENT_REFRESH = "current_refresh";

    public static final int DEFAULT_TIMEOUT_VALUE = 500;
    public static final String CURRENT_TIMEOUT = "current_timeout";

    private SeekBar speedSeekBar;
    private SeekBar refreshSeekBar;
    private SeekBar timeoutSeekBar;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        hideStatusBar();
        OptionsActivity.context = getApplicationContext();

        speedSeekBar = findViewById(R.id.speedSeekBar);
        speedSeekBar.setProgress((getCurrentSpeed() - MIN_SPEED) / SPEED_DIFF);
        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setCurrentSpeed(i * SPEED_DIFF + MIN_SPEED);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        refreshSeekBar = findViewById(R.id.refreshSeekBar);
        refreshSeekBar.setProgress((getCurrentRefresh() - MIN_REFRESH) / REFRESH_DIFF);
        refreshSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setCurrentRefresh(i * REFRESH_DIFF + MIN_REFRESH);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        timeoutSeekBar = findViewById(R.id.timeoutSeekBar);
        timeoutSeekBar.setProgress((getCurrentTimeout() - MIN_TIMEOUT) / TIMEOUT_DIFF);
        timeoutSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setCurrentTimeout(i * TIMEOUT_DIFF + MIN_TIMEOUT);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public int getCurrentSpeed(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(CURRENT_SPEED, DEFAULT_SPEED_VALUE);
    }

    public void setCurrentSpeed(int currentSpeed){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURRENT_SPEED, currentSpeed);
        editor.apply();
    }

    public int getCurrentRefresh(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(CURRENT_REFRESH, DEFAULT_REFRESH_VALUE);
    }

    public void setCurrentRefresh(int currentRefresh){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURRENT_REFRESH, currentRefresh);
        editor.apply();
    }

    public int getCurrentTimeout(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(CURRENT_TIMEOUT, DEFAULT_TIMEOUT_VALUE);
    }

    public void setCurrentTimeout(int currentTimeout){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURRENT_TIMEOUT, currentTimeout);
        editor.apply();
    }

    public void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void onClickBackOptions(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickResetOptions(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURRENT_SPEED, DEFAULT_SPEED_VALUE);
        editor.putInt(CURRENT_REFRESH, DEFAULT_REFRESH_VALUE);
        editor.putInt(CURRENT_TIMEOUT, DEFAULT_TIMEOUT_VALUE);
        editor.apply();

        speedSeekBar.setProgress((getCurrentSpeed() - MIN_SPEED) / SPEED_DIFF);
        refreshSeekBar.setProgress((getCurrentRefresh() - MIN_REFRESH) / REFRESH_DIFF);
        timeoutSeekBar.setProgress((getCurrentTimeout() - MIN_TIMEOUT) / TIMEOUT_DIFF);
    }

    public static Context getAppContext() {
        return OptionsActivity.context;
    }
}
