package com.example.emanu.pmu_projekat;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;

import com.example.emanu.pmu_projekat.Activities.OptionsActivity;
import com.example.emanu.pmu_projekat.Activities.StartedGameActivity;
import com.example.emanu.pmu_projekat.DB.MyDbHelper;
import com.example.emanu.pmu_projekat.DB.TableEntry;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by emanu on 3/7/2018.
 */

public class Model implements Serializable{
    private int SHAKE_TIMEOUT = 500;
    private int SPEED_THRESHOLD = 1400;
    private int DIFFTIME_THRESHOLD = 150;

    public static final int ALL_BEARED_OFF = 15;

    public enum State{
        INITIAL_ROLL1, INITIAL_ROLL2, WAITING_FOR_ROLL, WAITING_FOR_MOVE;
    }

    public static final String ROLL_THE_DICE = "roll the dice";
    public static final String IS_ON_THE_MOVE = "is on the move";

    private MyPaint black = new MyPaint();
    private MyPaint white = new MyPaint();
    protected transient MyImageView myImageView;
    protected transient StartedGameActivity startedGameActivity;
    private int imageViewHeight;
    private int imageViewWidth;

    private Player player1;
    protected Player player2;
    private Player playerOnTurn;

    private Checker currentChecker;

    private boolean isComputer;
    protected State gameStatus;
    private Dice die1;
    private Dice die2;
    private Dice die3;
    private Dice die4;
    private int initialValuePlayer1;
    private List<Integer> availableMoves;
    protected List<Triangle> availablePositions;
    private List<Checker> sourceTriangle;
    private int sourceTriangleIndex;
    private int destinationTriangleIndex;
    private int oldX;
    private int oldY;
    private boolean doubles;
    private List<Dice> dice = new ArrayList<>();
    private int usedDices;
    private Player opponent;

    private long lastTime = 0;
    private float lastX;
    private float lastY;
    private float lastZ;
    private long lastShakeTime;
    protected MyMediaPlayer mediaPlayerShaking;
    protected MyMediaPlayer mediaPlayerRolling;
    private boolean loaded;
    private boolean waitingForShake = true;

    public Model(String player1name, String player2name, boolean isPlayer1Black, boolean isComputer, StartedGameActivity startedGameActivity) {
        this.isComputer = isComputer;
        this.startedGameActivity = startedGameActivity;
        black.setColor(Color.BLACK);
        white.setColor(Color.WHITE);
        player1 = new Human(player1name, isPlayer1Black ? black : white);
        if(isComputer)
            player2 = new Computer(player2name, isPlayer1Black ? white : black, this);
        else
            player2 = new Human(player2name, isPlayer1Black ? white : black);
        playerOnTurn = player2;
        gameStatus = State.INITIAL_ROLL1;
        startedGameActivity.setConsoleText(player1.name + " " + ROLL_THE_DICE);

        dice.add(die3 = new Dice(true, true));
        dice.add(die4 = new Dice(false, true));
        dice.add(die1 = new Dice(true, false));
        dice.add(die2 = new Dice(false, false));

        mediaPlayerShaking = new MyMediaPlayer(MediaPlayer.create(StartedGameActivity.getAppContext(), R.raw.shaking_dice));
        mediaPlayerShaking.setLooping(true);
        mediaPlayerRolling = new MyMediaPlayer(MediaPlayer.create(StartedGameActivity.getAppContext(), R.raw.rolling_dice));

        setParameters();
    }

    public void setParameters(){
        SharedPreferences sharedPreferences = startedGameActivity.getSharedPreferences(OptionsActivity.PREFS, Context.MODE_PRIVATE);
        SPEED_THRESHOLD = sharedPreferences.getInt(OptionsActivity.CURRENT_SPEED, OptionsActivity.DEFAULT_SPEED_VALUE);
        DIFFTIME_THRESHOLD = sharedPreferences.getInt(OptionsActivity.CURRENT_REFRESH, OptionsActivity.DEFAULT_REFRESH_VALUE);
        SHAKE_TIMEOUT = sharedPreferences.getInt(OptionsActivity.CURRENT_TIMEOUT, OptionsActivity.DEFAULT_TIMEOUT_VALUE);
    }

    public int selectChecker(int x, int y){
        List<List<Checker>> triangles = playerOnTurn.getTriangles();

        if(playerOnTurn.eatenCheckers.size() == 0){
            for(int i = 0; i < 24; i++){
                if(triangles.get(i).size() > 0){
                    int topCheckerIndex = triangles.get(i).size();
                    Checker topChecker = triangles.get(i).get(topCheckerIndex - 1);
                    if(distance(x, topChecker.getX(), y, topChecker.getY()) <= Checker.RADIUS * imageViewHeight){
                        currentChecker = topChecker;
                        sourceTriangle = triangles.get(i);
                        sourceTriangleIndex = i;
                        oldX = topChecker.getX();
                        oldY = topChecker.getY();
                        return i;
                    }
                }
            }
        }
        else{
            Checker topChecker = playerOnTurn.eatenCheckers.get(playerOnTurn.eatenCheckers.size() - 1);
            if(distance(x, topChecker.getX(), y, topChecker.getY()) <= Checker.RADIUS * imageViewHeight){
                currentChecker = topChecker;
                sourceTriangle = playerOnTurn.eatenCheckers;
                if(playerOnTurn.isWhite())
                    sourceTriangleIndex = -1;
                else
                    sourceTriangleIndex = 24;
                oldX = topChecker.getX();
                oldY = topChecker.getY();
                return sourceTriangleIndex;
            }
        }
        return -1;
    }

    public void findSpots(int sourceIndex){

        if(playerOnTurn != player1)
            opponent = player1;
        else
            opponent = player2;

        availablePositions = new ArrayList<>();
        boolean bearOffFlag = false;
        for (int i = 0; i < availableMoves.size(); i++){
            if(playerOnTurn.getColor().getColor() == Color.WHITE){
                if(playerOnTurn.isBearingOff && availableMoves.get(i) + sourceIndex > 24 && !bearOffFlag){
                    boolean lastNonEmpty = true;
                    for(int j = sourceIndex - 1; j > 17; j--){
                        if(playerOnTurn.triangles.get(j).size() > 0){
                            lastNonEmpty = false;
                            break;
                        }
                    }
                    if(lastNonEmpty && sourceIndex > 18){
                        Triangle newPosition = new Triangle(24, imageViewHeight, imageViewWidth);
                        newPosition.setBearingOff(0);
                        availablePositions.add(newPosition);
                        bearOffFlag = true;
                    }
                }
                else if(playerOnTurn.isBearingOff && availableMoves.get(i) + sourceIndex == 24 && !bearOffFlag){
                    Triangle newPosition = new Triangle(24, imageViewHeight, imageViewWidth);
                    newPosition.setBearingOff(0);
                    availablePositions.add(newPosition);
                    bearOffFlag = true;
                }
                else if((availableMoves.get(i) + sourceIndex) < 24 && opponent.getTriangles().get(availableMoves.get(i) + sourceIndex).size() < 2){
                    Triangle newPosition = new Triangle(availableMoves.get(i) + sourceIndex, imageViewHeight, imageViewWidth);
                    availablePositions.add(newPosition);
                }
            }
            else{
                if(playerOnTurn.isBearingOff && sourceIndex - availableMoves.get(i) < -1 && !bearOffFlag){
                    boolean lastNonEmpty = true;
                    for(int j = sourceIndex + 1; j < 6; j++){
                        if(playerOnTurn.triangles.get(j).size() > 0){
                            lastNonEmpty = false;
                            break;
                        }
                    }
                    if(lastNonEmpty && sourceIndex < 5){
                        Triangle newPosition = new Triangle(-1, imageViewHeight, imageViewWidth);
                        newPosition.setBearingOff(1);
                        availablePositions.add(newPosition);
                        bearOffFlag = true;
                    }
                }
                else if(playerOnTurn.isBearingOff && sourceIndex - availableMoves.get(i) == -1 && !bearOffFlag){
                    Triangle newPosition = new Triangle(-1, imageViewHeight, imageViewWidth);
                    newPosition.setBearingOff(1);
                    availablePositions.add(newPosition);
                    bearOffFlag = true;
                }
                else if((sourceIndex - availableMoves.get(i)) >= 0 && opponent.getTriangles().get(sourceIndex - availableMoves.get(i)).size() < 2){
                    Triangle newPosition = new Triangle(sourceIndex - availableMoves.get(i), imageViewHeight, imageViewWidth);
                    availablePositions.add(newPosition);
                }
            }
        }
    }

    public void onActionDown(int x, int y) {
       if(gameStatus == State.WAITING_FOR_MOVE){
            int sourceTriangleIndex = selectChecker(x, y);
            if(currentChecker == null) return;

            findSpots(sourceTriangleIndex);
        }
    }

    public void onActionMove(int x, int y) {
        if(currentChecker != null){
            currentChecker.setX(x);
            currentChecker.setY(y);
        }
    }

    public void onActionUp(int x, int y){
        if(currentChecker != null){

            for (int i = 0; i < availablePositions.size(); i++) {
                Triangle triangle = availablePositions.get(i);
                int x1 = triangle.getX1();
                int y1 = triangle.getY1();
                int x2 = triangle.getX2();
                int y2 = triangle.getY2();
                int x3 = triangle.getX3();
                int y3 = triangle.getY3();
                if(triangle.isBearingOff() && isInAvailableSpot(x, y, x1, y1, x2, y2)){
                    sourceTriangle.remove(currentChecker); //remove from old triangle
                    currentChecker.bearOff(playerOnTurn.isWhite() ? 0 : 1, playerOnTurn.bearedOffCheckers.size());
                    playerOnTurn.bearedOffCheckers.add(currentChecker);
                    destinationTriangleIndex = triangle.getTriangleIndex();
                    if(playerOnTurn.bearedOffCheckers.size() == ALL_BEARED_OFF)
                        finishTheGame();
                    updateAvailableMoves();
                    break;
                }
                else if(!triangle.isBearingOff() && isInAvailableSpot(x, y, x1, y1, x2, y2, x3, y3, triangle.getTriangleIndex() > 11)){
                    opponent.eatOpponentsChecker(triangle.getTriangleIndex());
                    sourceTriangle.remove(currentChecker); //remove from old triangle
                    int position = playerOnTurn.getTriangles().get(triangle.getTriangleIndex()).size(); //position index in new triangle
                    playerOnTurn.getTriangles().get(triangle.getTriangleIndex()).add(currentChecker); //add checker to new triangle
                    currentChecker.setOnPosition(imageViewHeight, imageViewWidth, triangle.getTriangleIndex(), position); //change x,y coords
                    destinationTriangleIndex = triangle.getTriangleIndex();
                    allInHomeBoard(playerOnTurn.isWhite() ? 0 : 1);
                    updateAvailableMoves();
                    break;
                }
                else{
                    currentChecker.setX(oldX);
                    currentChecker.setY(oldY);
                }
            }
            if(availablePositions.size() == 0){
                currentChecker.setX(oldX);
                currentChecker.setY(oldY);
            }

            availablePositions = null;
            currentChecker = null;
        }
    }

    public void finishTheGame() {
        new SavingResult().execute();
    }

    private class SavingResult extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            MyDbHelper helper = new MyDbHelper(startedGameActivity.getApplicationContext());
            SQLiteDatabase db = helper.getWritableDatabase();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Calendar calendar = Calendar.getInstance();

            ContentValues values = new ContentValues();
            values.put(TableEntry.COL_WINNER, playerOnTurn.name);
            values.put(TableEntry.COL_SECOND_PLAYER, getOtherPlayer().name);
            values.put(TableEntry.COL_DATETIME, simpleDateFormat.format(calendar.getTimeInMillis()));

            db.insert(TableEntry.TABLE_NAME, null, values);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            startedGameActivity.gameRecapActivity(playerOnTurn.name, getOtherPlayer().name);
        }
    }

    public void allInHomeBoard(int black){
        for(int i = 0 + black * 6; i < 18 + black * 6; i++){
            if(playerOnTurn.triangles.get(i).size() > 0){
                playerOnTurn.isBearingOff = false;
                return;
            }
        }

        playerOnTurn.isBearingOff = true;
    }

    public void updateAvailableMoves(){
        int positionDifference = Math.abs(destinationTriangleIndex - sourceTriangleIndex);

        int moveToDelete = -1;
        for(int i = 0; i < availableMoves.size(); i++){
            if(availableMoves.get(i) == positionDifference){
                moveToDelete = i;
                break;
            }
        }

        if(moveToDelete == -1){  //bearing off
            int minValue = 7;
            for (int i = 0; i < availableMoves.size(); i++) {
                if(availableMoves.get(i) < minValue){
                    minValue = availableMoves.get(i);
                    moveToDelete = i;
                }
            }
        }

        if(doubles){
            int max = availableMoves.size();
            for(int i = 0; i <= moveToDelete; i++){ //depending on what move to delete, result is the same number of used dice
                dice.get(usedDices++).incOpacity();
            }
            while((moveToDelete + 1) > 0){
                availableMoves.remove(max - 1);
                max--;
                moveToDelete--;
            }
        }
        else{
            if(availableMoves.size() == 3){
                switch (moveToDelete){
                    case 0:
                        availableMoves.remove(2);
                        availableMoves.remove(0);
                        die1.incOpacity();
                    break;
                    case 1:
                        availableMoves.remove(2);
                        availableMoves.remove(1);
                        die2.incOpacity();
                        break;
                    case 2:
                        availableMoves.remove(2);
                        availableMoves.remove(1);
                        availableMoves.remove(0);
                        die1.incOpacity();
                        die2.incOpacity();
                        break;
                }
            }
            else{ //size == 1
                availableMoves.remove(0);
                die1.incOpacity();
                die2.incOpacity();
            }
        }

        if(availableMoves.size() == 0 || !anyAvailablePosition()){
            switchPlayerOnTurn();
            gameStatus = State.WAITING_FOR_ROLL;
            startedGameActivity.setConsoleText(playerOnTurn.name + " " + ROLL_THE_DICE);
            playerOnTurn.rollTheDice();
        }
        else{
            if(playerOnTurn == player2){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playerOnTurn.onTheMove(); //if playerOnTurn is not a computer, will do nothin
                    }
                }, 2100);
            }
        }

    }

    public boolean anyAvailablePosition(){
        if(playerOnTurn.eatenCheckers.size() > 0){
            Checker topChecker = playerOnTurn.eatenCheckers.get(playerOnTurn.eatenCheckers.size() - 1);
            int checkerSource = selectChecker(topChecker.getX(), topChecker.getY());
            findSpots(checkerSource);
            if(availablePositions.size() == 0){
                return false;
            }
            return true;
        }
        else{
            ArrayList<Integer> nonEmptyTrianglesIndexes = playerOnTurn.getNonEmptyTrianglesIndexes();
            for(int i = 0; i < nonEmptyTrianglesIndexes.size(); i++){
                int source = nonEmptyTrianglesIndexes.get(i);
                findSpots(source);
                if(availablePositions.size() > 0){   //finding source checker with any available moves
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isInAvailableSpot(int px, int py, int x1, int y1, int x2, int y2){
        return px >= x1 && px <= x2 && py >= y1 && py <= y2;
    }

    public boolean isInAvailableSpot(int px, int py, int x1, int y1, int x2, int y2, int x3, int y3, boolean down){
//        double w1 = (x1*(y3 - y1) + (py - y1)*(x3 - x1) - px*(y3 - y1))/((y2 - y1)*(x3 - x1) - (x2 - x1)*(y3 - y1));
//        double w2 = (py - y1 - w1*(y2 - y1))/(y3 - y1);
//
//        return w1 >= 0 && w1 >= 0 && (w1 + w2) <= 1;

//        int as_x = px-x1;
//        int as_y = py-y1;
//
//        boolean s_ab = (x2-x1)*as_y-(y2-y1)*as_x > 0;
//
//        if((x3-x1)*as_y-(y3-y1)*as_x > 0 == s_ab) return false;
//
//        if((x3-x2)*(py-y2)-(y3-y2)*(px-x2) > 0 != s_ab) return false;
//
//        return true;

        if(!down)
            return px > x1 && px < x2 && py > y1 && py < (y3 + 30);
        else
            return px > x1 && px < x2 && py < y1 && py > (y3 - 30);



    }

    private double distance(int x1, int x2, int y1, int y2){
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void createAvailableMoves(){
        availableMoves = new ArrayList<>();
        usedDices = 0;
        for(int i = 0; i < 4; i++){
            dice.get(i).resetOpacity();
        }
        if(die1.getValue() == die2.getValue()){ //when player gets doubles, that counts as 4x
            availableMoves.add(die1.getValue());
            availableMoves.add(die1.getValue() * 2);
            availableMoves.add(die2.getValue() * 3);
            availableMoves.add(die2.getValue() * 4);
            doubles = true;
            die3.setValue(die1.getValue());
            die3.setShow(true);
            die4.setValue(die1.getValue());
            die4.setShow(true);
        }
        else{
            availableMoves.add(die1.getValue());
            availableMoves.add(die2.getValue());
            availableMoves.add(die1.getValue() + die2.getValue());
        }
    }

    public void roll(){
        Handler handler = new Handler();

        switch (gameStatus){
            case INITIAL_ROLL1:
                die1.roll();
                die1.setShow(true);
                die2.setShow(false);

                initialValuePlayer1 = die1.getValue();
                startedGameActivity.setConsoleText(player2.name + " " + ROLL_THE_DICE);
                gameStatus = State.INITIAL_ROLL2;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        player2.rollTheDice();  //if player2 is not a computer, will do nothin
                    }
                }, 1000);
                break;
            case INITIAL_ROLL2:
                die2.roll();
                die2.setShow(true);
                if(initialValuePlayer1 < die2.getValue()){
                    playerOnTurn = player2;
                    startedGameActivity.setConsoleText(player2.name + " " + IS_ON_THE_MOVE);
                    gameStatus = State.WAITING_FOR_MOVE;
                    createAvailableMoves();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            player2.onTheMove(); //if player2 is not a computer, will do nothin
                        }
                    }, 2100);
                }
                else if (initialValuePlayer1 > die2.getValue()){
                    playerOnTurn = player1;
                    startedGameActivity.setConsoleText(player1.name + " " + IS_ON_THE_MOVE);
                    gameStatus = State.WAITING_FOR_MOVE;
                    createAvailableMoves();
                }
                else{ //equals
                    switchPlayerOnTurn();
                    gameStatus = State.INITIAL_ROLL1;
                    startedGameActivity.setConsoleText(player1.name + " " + ROLL_THE_DICE);
                }
                break;
            case WAITING_FOR_ROLL:
                die1.roll();
                die2.roll();
                die1.setShow(true);
                die2.setShow(true);
                die3.setShow(false);
                die4.setShow(false);
                doubles = false;
                createAvailableMoves();
                gameStatus = State.WAITING_FOR_MOVE;
                startedGameActivity.setConsoleText(playerOnTurn.name + " " + IS_ON_THE_MOVE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playerOnTurn.onTheMove(); //if playerOnTurn is not a computer, will do nothin
                    }
                }, 2100);
                break;
        }
    }

    public Player getOtherPlayer(){
        if(playerOnTurn == player1)
            return player2;
        else
            return player1;
    }

    public void switchPlayerOnTurn(){
        if(playerOnTurn == player1){
            playerOnTurn = player2;
        }
        else
            playerOnTurn = player1;
    }

    public void onNewValues(float values[], long time){
        long currentTime = android.os.SystemClock.uptimeMillis();

        if(gameStatus == State.WAITING_FOR_MOVE) return;

        if(currentTime - lastTime > DIFFTIME_THRESHOLD){
            long diffTime = currentTime - lastTime;
            lastTime = currentTime;
            float speed = Math.abs(values[0] + values[1] + values[2] - lastX - lastY - lastZ) / diffTime * 10000;

            if(speed > SPEED_THRESHOLD){
                if(!mediaPlayerShaking.isPlaying())
                    mediaPlayerShaking.start();
                waitingForShake = true;
                lastShakeTime = currentTime;
            }

            lastX = values[0];
            lastY = values[1];
            lastZ = values[2];
        }

        if(currentTime - lastShakeTime > SHAKE_TIMEOUT && waitingForShake){ //prestanak muckanja
            waitingForShake = false;
            if(mediaPlayerShaking.isPlaying()){
                mediaPlayerShaking.pause();
                mediaPlayerRolling.start();
                roll();
                myImageView.invalidate();
                return;

            }
        }
    }

    public void setLoaded(){
        loaded = true;
    }

    public void createMediaPlayers(){
        mediaPlayerShaking = new MyMediaPlayer(MediaPlayer.create(StartedGameActivity.getAppContext(), R.raw.shaking_dice));
        mediaPlayerShaking.setLooping(true);
        mediaPlayerRolling = new MyMediaPlayer(MediaPlayer.create(StartedGameActivity.getAppContext(), R.raw.rolling_dice));
    }

    public void setStartedGameActivity(StartedGameActivity startedGameActivity){
        this.startedGameActivity = startedGameActivity;
    }

    public void setHeightAndWidth(int h, int w) {
        this.imageViewHeight = h;
        this.imageViewWidth = w;
        player1.setHeightAndWidth(h, w);
        player2.setHeightAndWidth(h, w);
        die1.setHeightAndWidth(h, w);
        die2.setHeightAndWidth(h, w);
        die3.setHeightAndWidth(h, w);
        die4.setHeightAndWidth(h, w);
        if(!loaded){
            player1.initCheckers();
            player2.initCheckers();
        }

    }

    public List<Checker> getPlayer1Checkers(){
        return player1.getCheckers();
    }

    public List<Checker> getPlayer2Checkers(){
        return player2.getCheckers();
    }

    public MyImageView getMyImageView() {
        return myImageView;
    }

    public void setMyImageView(MyImageView myImageView) {
        this.myImageView = myImageView;
    }

    public Checker getCurrentChecker() {
        return currentChecker;
    }

    public void setCurrentChecker(Checker currentChecker) {
        this.currentChecker = currentChecker;
    }

    public Dice getDie1() {
        return die1;
    }

    public Dice getDie2() {
        return die2;
    }

    public Dice getDie3() {
        return die3;
    }

    public Dice getDie4() {
        return die4;
    }

    public List<Triangle> getAvailablePositions() {
        return availablePositions;
    }

    public int getSHAKE_TIMEOUT() {
        return SHAKE_TIMEOUT;
    }

    public void setSHAKE_TIMEOUT(int SHAKE_TIMEOUT) {
        this.SHAKE_TIMEOUT = SHAKE_TIMEOUT;
    }

    public int getSPEED_THRESHOLD() {
        return SPEED_THRESHOLD;
    }

    public void setSPEED_THRESHOLD(int SPEED_THRESHOLD) {
        this.SPEED_THRESHOLD = SPEED_THRESHOLD;
    }

    public int getDIFFTIME_THRESHOLD() {
        return DIFFTIME_THRESHOLD;
    }

    public void setDIFFTIME_THRESHOLD(int DIFFTIME_THRESHOLD) {
        this.DIFFTIME_THRESHOLD = DIFFTIME_THRESHOLD;
    }
}
