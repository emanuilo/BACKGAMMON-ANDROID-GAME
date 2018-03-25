package com.example.emanu.pmu_projekat;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class StartedGameActivity extends AppCompatActivity implements SensorEventListener{
    private static Context context;

    private MyImageView myImageView;
    private Model model;
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_game);
        hideStatusBar();
        Bundle extras = getIntent().getExtras();
        StartedGameActivity.context = getApplicationContext();

        myImageView = findViewById(R.id.myImageView);
        model = new Model(extras.getString(GameCreationActivity.PLAYER1NAME), extras.getString(GameCreationActivity.PLAYER2NAME), extras.getBoolean(GameCreationActivity.IS_PLAYER1_BLACK), extras.getBoolean(GameCreationActivity.COMPUTER));
        myImageView.setModel(model);
        model.setMyImageView(myImageView);
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
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
