package com.example.emanu.pmu_projekat;

import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by emanu on 3/13/2018.
 */

public class Computer extends Player{
    private Model model;

    public Computer(String name, MyPaint color, Model model) {
        super(name, color);
        this.model = model;
    }

    public void onTheMove(){
        ArrayList<Integer> nonEmptyTriangles = new ArrayList<>(); //searching for triangles with checkers
        for(int i = 0; i < 23; i++){
            if(triangles.get(i).size() > 0)
                nonEmptyTriangles.add(i);
        }

        int source = 0;
        List<Triangle> availablePositions = null;
        Collections.shuffle(nonEmptyTriangles);     //randomizing triangle to choose
        boolean found = false;
        for(int i = 0; i < nonEmptyTriangles.size(); i++){
            source = nonEmptyTriangles.get(i);
            model.findSpots(source);
            availablePositions = model.getAvailablePositions();
            if(availablePositions.size() > 0){                     //finding source checker with any available moves
                found = true;
                break;
            }
        }

        if(!found){
            //todo ako nema available poteza
            return;
        }

        int randAvailPosition = (int)(Math.random()*availablePositions.size());             //random choosing the available move
        int destination = availablePositions.get(randAvailPosition).getTriangleIndex();

        int currentCheckerIndex = triangles.get(source).size() - 1;
        Checker currentChecker = triangles.get(source).get(currentCheckerIndex);
        int blah = model.selectChecker(currentChecker.getX(), currentChecker.getY());
//        model.setCurrentChecker(currentChecker);

        make_a_move(destination);

    }

    public void rollTheDice(){
        model.mediaPlayerShaking.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                model.mediaPlayerShaking.pause();
                model.mediaPlayerRolling.start();
                model.roll();
                model.myImageView.invalidate();
            }
        }, 2000);
    }

    public void make_a_move(int triangleIndex){
        int x = 0;
        int y = 0;
        switch (triangleIndex){
            case 0: case 1: case 2: case 3: case 4: case 5:
                x = (int) (width * (Checker.TRIANGLE0 + triangleIndex * Checker.TRIANGLE_OFFSET));
                y = (int) (height * (Checker.POS1UP + 1 * Checker.POSITION_OFFSET));
                break;
            case 6: case 7: case 8: case 9: case 10: case 11:
                x = (int) (width * (Checker.TRIANGLE6 + (triangleIndex - 6) * Checker.TRIANGLE_OFFSET));
                y = (int) (height * (Checker.POS1UP + 1 * Checker.POSITION_OFFSET));
                break;
            case 12: case 13: case 14: case 15: case 16: case 17:
                x = (int) (width * (Checker.TRIANGLE6 + (17 - triangleIndex) * Checker.TRIANGLE_OFFSET));
                y = (int) (height * (Checker.POS1DOWN - 1 * Checker.POSITION_OFFSET));
                break;
            case 18: case 19: case 20: case 21: case 22: case 23:
                x = (int) (width * (Checker.TRIANGLE0 + (23 - triangleIndex) * Checker.TRIANGLE_OFFSET));
                y = (int) (height * (Checker.POS1DOWN - 1 * Checker.POSITION_OFFSET));
                break;
        }

        model.onActionUp(x, y);
    }

}
