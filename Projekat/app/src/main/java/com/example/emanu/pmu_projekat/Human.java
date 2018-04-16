package com.example.emanu.pmu_projekat;

import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by emanu on 3/13/2018.
 */

public class Human extends Player{

    public Human(String name, MyPaint color) {
        super(name, color);
    }

    public ArrayList<Integer> getNonEmptyTrianglesIndexes(){
        ArrayList<Integer> nonEmptyTrianglesIndexes = new ArrayList<>(); //searching for triangles with checkers
        for(int i = 0; i < 24; i++){
            if(triangles.get(i).size() > 0)
                nonEmptyTrianglesIndexes.add(i);
        }
        return nonEmptyTrianglesIndexes;
    }
}
