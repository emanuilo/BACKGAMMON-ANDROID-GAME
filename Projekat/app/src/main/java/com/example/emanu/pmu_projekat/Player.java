package com.example.emanu.pmu_projekat;

import android.graphics.Color;
import android.graphics.Paint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emanu on 3/12/2018.
 */

public class Player implements Serializable{
    protected String name;
    protected List<Checker> checkers = new ArrayList<>();
    protected List<List<Checker>> triangles = new ArrayList<List<Checker>>(24) ;
    protected List<Checker> eatenCheckers = new ArrayList<>();
    protected List<Checker> bearedOffCheckers = new ArrayList<>();
    protected MyPaint color;
    protected int height;
    protected int width;
    protected boolean isBearingOff;


    public Player(String name, MyPaint color) {
        this.name = name;
        this.color = color;
        for(int i = 0; i < 24; i++){
            triangles.add(i, new ArrayList<Checker>());
        }
    }

    public void eatOpponentsChecker(int triangleIndex){
        List<Checker> triangle = triangles.get(triangleIndex);
        if(triangle.size() == 1){
            Checker checkerToEat = triangle.get(0);
            triangle.remove(checkerToEat);
            eatenCheckers.add(checkerToEat);
            checkerToEat.moveToTheEdge(isWhite() ? 1 : 0, eatenCheckers.size() - 1, height, width);
        }
    }

    public boolean isWhite(){
        return color.getColor() == Color.WHITE;
    }

    public void initCheckers(){
        Checker checker;
        if(color.getColor() == Color.WHITE){
            List triangle1 = triangles.get(0); //get triangle 1
            checker = new Checker((int) (width * 0.133), (int) (height * 0.16), color, height, width);
            checkers.add(checker);
            triangle1.add(checker);
            checker = new Checker((int) (width * 0.133), (int) (height * 0.237), color, height, width);
            checkers.add(checker);
            triangle1.add(checker);


            List triangle12 = triangles.get(11); //get triangle 12
            checker = new Checker((int) (width * 0.947), (int) (height * 0.16), color, height, width);
            checkers.add(checker);
            triangle12.add(checker);
            checker = new Checker((int) (width * 0.947), (int) (height * 0.237), color, height, width);
            checkers.add(checker);
            triangle12.add(checker);
            checker = new Checker((int) (width * 0.947), (int) (height * 0.314), color, height, width);
            checkers.add(checker);
            triangle12.add(checker);
            checker = new Checker((int) (width * 0.947), (int) (height * 0.391), color, height, width);
            checkers.add(checker);
            triangle12.add(checker);
            checker = new Checker((int) (width * 0.947), (int) (height * 0.468), color, height, width);
            checkers.add(checker);
            triangle12.add(checker);


            List triangle19 = triangles.get(18); //get triangle 19
            checker = new Checker((int) (width * 0.46), (int) (height * 0.937), color, height, width);
            checkers.add(checker);
            triangle19.add(checker);
            checker = new Checker((int) (width * 0.46), (int) (height * 0.86), color, height, width);
            checkers.add(checker);
            triangle19.add(checker);
            checker = new Checker((int) (width * 0.46), (int) (height * 0.783), color, height, width);
            checkers.add(checker);
            triangle19.add(checker);
            checker = new Checker((int) (width * 0.46), (int) (height * 0.706), color, height, width);
            checkers.add(checker);
            triangle19.add(checker);
            checker = new Checker((int) (width * 0.46), (int) (height * 0.629), color, height, width);
            checkers.add(checker);
            triangle19.add(checker);


            List triangle17 = triangles.get(16); //get triangle 17
            checker = new Checker((int) (width * 0.684), (int) (height * 0.937), color, height, width);
            checkers.add(checker);
            triangle17.add(checker);
            checker = new Checker((int) (width * 0.684), (int) (height * 0.86), color, height, width);
            checkers.add(checker);
            triangle17.add(checker);
            checker = new Checker((int) (width * 0.684), (int) (height * 0.783), color, height, width);
            checkers.add(checker);
            triangle17.add(checker);
        }
        else{
            List triangle24 = triangles.get(23); //get triangle 24
            checker = new Checker((int) (width * 0.133), (int) (height * 0.937), color, height, width);
            checkers.add(checker);
            triangle24.add(checker);
            checker = new Checker((int) (width * 0.133), (int) (height * 0.86), color, height, width);
            checkers.add(checker);
            triangle24.add(checker);


            List triangle13 = triangles.get(12); //get triangle 13
            checker = new Checker((int) (width * 0.947), (int) (height * 0.937), color, height, width);
            checkers.add(checker);
            triangle13.add(checker);
            checker = new Checker((int) (width * 0.947), (int) (height * 0.86), color, height, width);
            checkers.add(checker);
            triangle13.add(checker);
            checker = new Checker((int) (width * 0.947), (int) (height * 0.783), color, height, width);
            checkers.add(checker);
            triangle13.add(checker);
            checker = new Checker((int) (width * 0.947), (int) (height * 0.706), color, height, width);
            checkers.add(checker);
            triangle13.add(checker);
            checker = new Checker((int) (width * 0.947), (int) (height * 0.629), color, height, width);
            checkers.add(checker);
            triangle13.add(checker);


            List triangle6 = triangles.get(5); //get triangle 6
            checker = new Checker((int) (width * 0.46), (int) (height * 0.16), color, height, width);
            checkers.add(checker);
            triangle6.add(checker);
            checker = new Checker((int) (width * 0.46), (int) (height * 0.237), color, height, width);
            checkers.add(checker);
            triangle6.add(checker);
            checker = new Checker((int) (width * 0.46), (int) (height * 0.314), color, height, width);
            checkers.add(checker);
            triangle6.add(checker);
            checker = new Checker((int) (width * 0.46), (int) (height * 0.391), color, height, width);
            checkers.add(checker);
            triangle6.add(checker);
            checker = new Checker((int) (width * 0.46), (int) (height * 0.468), color, height, width);
            checkers.add(checker);
            triangle6.add(checker);


            List triangle8 = triangles.get(7); //get triangle 8
            checker = new Checker((int) (width * 0.684), (int) (height * 0.16), color, height, width);
            checkers.add(checker);
            triangle8.add(checker);
            checker = new Checker((int) (width * 0.684), (int) (height * 0.237), color, height, width);
            checkers.add(checker);
            triangle8.add(checker);
            checker = new Checker((int) (width * 0.684), (int) (height * 0.314), color, height, width);
            checkers.add(checker);
            triangle8.add(checker);
        }
    }

    public boolean isBearingOff() {
        return isBearingOff;
    }

    public void setBearingOff(boolean bearingOff) {
        isBearingOff = bearingOff;
    }

    public void setHeightAndWidth(int h, int w) {
        this.height = h;
        this.width = w;
    }

    public MyPaint getColor() {
        return color;
    }

    public void setColor(MyPaint color) {
        this.color = color;
    }

    public List<Checker> getCheckers() {
        return checkers;
    }

    public List<List<Checker>> getTriangles() {
        return triangles;
    }

    public void onTheMove() {}

    public void rollTheDice() {}

    public ArrayList<Integer> getNonEmptyTrianglesIndexes(){return null;}
}
