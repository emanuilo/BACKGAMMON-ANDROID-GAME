package com.example.emanu.pmu_projekat.Activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.emanu.pmu_projekat.Model;
import com.example.emanu.pmu_projekat.MyImageView;
import com.example.emanu.pmu_projekat.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StartedGameActivity extends AppCompatActivity implements SensorEventListener{
    public static final String SAVE_FILE = "save_file";
    public static final String WINNER = "winner";
    public static final String LOOSER = "looser";

    private static Context context;
    private MyImageView myImageView;
    private Model model;
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView consoleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_game);
        StartedGameActivity.context = getApplicationContext();
        hideStatusBar();
        Bundle extras = getIntent().getExtras();
        consoleTextView = findViewById(R.id.consoleTextView);
        boolean loadGame = extras.getBoolean(MainActivity.LOAD_GAME);
        if(loadGame)
            loadModel();
        else
            model = new Model(extras.getString(GameCreationActivity.PLAYER1NAME), extras.getString(GameCreationActivity.PLAYER2NAME), extras.getBoolean(GameCreationActivity.IS_PLAYER1_BLACK), extras.getBoolean(GameCreationActivity.COMPUTER), this);

        myImageView = findViewById(R.id.myImageView);
        myImageView.setModel(model);
        model.setMyImageView(myImageView);
        model.setStartedGameActivity(this);
        myImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                handleTouch(motionEvent);
                return true;
            }
        });
        myImageView.invalidate();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_GAME);
    }

    public void handleTouch(MotionEvent motionEvent){
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++){
            int x = (int) motionEvent.getX(i);
            int y = (int) motionEvent.getY(i);
            int action = motionEvent.getActionMasked();

            switch (action){
                case MotionEvent.ACTION_DOWN:
                    model.onActionDown(x, y);
                    myImageView.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    model.onActionUp(x, y);
                    myImageView.invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    model.onActionMove(x, y);
                    myImageView.invalidate();
                    break;
            }
        }
    }

    public void gameRecapActivity(String winner, String looser){
        Intent intent = new Intent(this, GameRecapActivity.class);
        intent.putExtra(StatsActivity.DELETE_SAVE, true);
        intent.putExtra(WINNER, winner);
        intent.putExtra(LOOSER, looser);
        startActivity(intent);
    }

    public boolean loadModel(){
        try(FileInputStream fileInputStream = context.openFileInput(SAVE_FILE);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            model = (Model) objectInputStream.readObject();
            model.createMediaPlayers();
            model.setLoaded();
            return true;
        } catch (java.io.IOException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(model.getMediaPlayerRolling().isPlaying())
            model.getMediaPlayerRolling().pause();

        if(model.getMediaPlayerShaking().isPlaying())
            model.getMediaPlayerShaking().pause();

        try(FileOutputStream fileOutputStream = context.openFileOutput(SAVE_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(model);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void setConsoleText(String text){
        consoleTextView.setText(text);
    }

    public static Context getAppContext() {
        return StartedGameActivity.context;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        model.onNewValues(sensorEvent.values, sensorEvent.timestamp / 1000000);
    }

    public void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void onClickRoll(View view) {
        model.roll();
        myImageView.invalidate();
        model.finishTheGame();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
