package com.example.emanu.pmu_projekat.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.example.emanu.pmu_projekat.Model;
import com.example.emanu.pmu_projekat.R;

public class OptionsActivity extends AppCompatActivity {
    private static final int SPEED_DIFF = 100;
    private static final int MIN_SPEED = 800;

    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        hideStatusBar();

        model = Model.getInstance();

        SeekBar speedSeekBar = findViewById(R.id.speedSeekBar);
        int i = (model.getSPEED_THRESHOLD() - MIN_SPEED) / SPEED_DIFF;
        speedSeekBar.setProgress((model.getSPEED_THRESHOLD() - MIN_SPEED) / SPEED_DIFF);
        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                model.setSPEED_THRESHOLD(i * SPEED_DIFF + MIN_SPEED);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar refreshSeekBar = findViewById(R.id.refreshSeekBar);
        refreshSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar timeoutSeekBar = findViewById(R.id.timeoutSeekBar);
        timeoutSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
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
}
