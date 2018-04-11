package com.example.emanu.pmu_projekat.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.emanu.pmu_projekat.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static final String LOAD_GAME = "load_game";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideStatusBar();
        File saveFile = new File(getFilesDir(), StartedGameActivity.SAVE_FILE);
        if(!saveFile.exists()){
            Button continueButton = findViewById(R.id.continue_button);
            continueButton.setEnabled(false);
            continueButton.setAlpha(0.8f);
        }

    }

    public void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void onClickNewGame(View view) {
        Intent intent = new Intent(this, GameCreationActivity.class);
        startActivity(intent);
    }

    public void onClickContinue(View view) {
        Intent intent = new Intent(this, StartedGameActivity.class);
        intent.putExtra(LOAD_GAME, true);
        startActivity(intent);
    }


    public void onClickStats(View view) {
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    public void onClickOptions(View view) {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }
}
