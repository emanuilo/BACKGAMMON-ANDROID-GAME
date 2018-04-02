package com.example.emanu.pmu_projekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

public class GameCreationActivity extends AppCompatActivity {
    public static final String PLAYER1NAME = "player1name";
    public static final String PLAYER2NAME = "player2name";
    public static final String IS_PLAYER1_BLACK = "player1color";
    public static final String COMPUTER = "computer";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_creation);

        hideStatusBar();

        RadioButton radioBlackPlayer1 = findViewById(R.id.radioBlackPlayer1);
        radioBlackPlayer1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                RadioButton player2white = findViewById(R.id.radioWhitePlayer2);
                RadioButton player2black = findViewById(R.id.radioBlackPlayer2);

                if(b == true){
                    player2white.setChecked(true);
                    player2black.setChecked(false);
                }
                else{
                    player2white.setChecked(false);
                    player2black.setChecked(true);
                }
            }
        });

        RadioButton radioBlackPlayer2 = findViewById(R.id.radioBlackPlayer2);
        radioBlackPlayer2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                RadioButton player1white = findViewById(R.id.radioWhitePlayer1);
                RadioButton player1black = findViewById(R.id.radioBlackPlayer1);

                if(b == true){
                    player1white.setChecked(true);
                    player1black.setChecked(false);
                }
                else{
                    player1white.setChecked(false);
                    player1black.setChecked(true);
                }
            }
        });
    }

    public void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void onClickStart(View view) {
        EditText editTextPlayer1 = findViewById(R.id.editTextPlayer1);
        String player1Name = editTextPlayer1.getText().toString();
        EditText editTextPlayer2 = findViewById(R.id.editTextPlayer2);
        String player2Name = editTextPlayer2.getText().toString();
        boolean isPlayer1Black = ((RadioButton)findViewById(R.id.radioBlackPlayer1)).isChecked() ? true : false;
        boolean isComputer = ((CheckBox)findViewById(R.id.checkBoxComputer)).isChecked() ? true : false;

        Intent intent = new Intent(this, StartedGameActivity.class);
        intent.putExtra(MainActivity.LOAD_GAME, false);
        intent.putExtra(PLAYER1NAME, player1Name);
        intent.putExtra(PLAYER2NAME, player2Name);
        intent.putExtra(IS_PLAYER1_BLACK, isPlayer1Black);
        intent.putExtra(COMPUTER, isComputer);
        startActivity(intent);
    }
}
