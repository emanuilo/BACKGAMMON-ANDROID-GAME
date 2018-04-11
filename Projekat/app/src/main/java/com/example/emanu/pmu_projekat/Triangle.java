package com.example.emanu.pmu_projekat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by emanu on 3/20/2018.
 */

public class Triangle extends Figure implements Serializable{
    public static final int BEAR_OFF_RADIUS = 15;
    public static final double BEAR_OFF_X1 = 0.01;
    public static final double BEAR_OFF_X2 = 0.07;
    public static final double BEAR_OFF_Y1 = 0.591;
    public static final double BEAR_OFF_Y2 = 0.987;
    public static final double BEAR_OFF_OFFSET = 0.4785;

    public static final double OFFSET = 0.066;
    public static final double OFFSET2 = 0.092;
    public static final double POS_X1_UP = 0.10;
    public static final double POS_Y1_UP = 0.127;

    public static final double POS_X2_UP = 0.165;
    public static final double POS_Y2_UP = 0.127;

    public static final double POS_X3_UP = 0.1315;
    public static final double POS_Y3_UP = 0.485;

    public static final double POS_X1_DOWN = 0.10;
    public static final double POS_Y1_DOWN = 0.97;

    public static final double POS_X2_DOWN = 0.165;
    public static final double POS_Y2_DOWN = 0.97;

    public static final double POS_X3_DOWN = 0.1315;
    public static final double POS_Y3_DOWN = 0.61;

    private Paint color;
    private int height;
    private int width;
    private int triangleIndex;

    private int x1;
    private int x2;
    private int x3;
    private int y1;
    private int y2;
    private int y3;

    private boolean bearingOff;

    public Triangle(int triangleIndex, int height, int width) {
        this.height = height;
        this.width = width;
        this.triangleIndex = triangleIndex;
        color = new Paint();
        color.setColor(Color.MAGENTA);
        color.setAlpha(110);
        setCoordinates();
    }

    public void setCoordinates(){
        switch (triangleIndex){
            case 0: case 1: case 2: case 3: case 4: case 5:
                x1 = (int)(width * (POS_X1_UP + triangleIndex * OFFSET));
                y1 = (int)(height * POS_Y1_UP);
                x2 = (int)(width * (POS_X2_UP + triangleIndex * OFFSET));
                y2 = (int)(height * POS_Y2_UP);
                x3 = (int)(width * (POS_X3_UP + triangleIndex * OFFSET));
                y3 = (int)(height * POS_Y3_UP);
                break;
            case 6: case 7: case 8: case 9: case 10: case 11:
                x1 = (int)(width * (OFFSET2 + POS_X1_UP + triangleIndex * OFFSET));
                y1 = (int)(height * POS_Y1_UP);
                x2 = (int)(width * (OFFSET2 + POS_X2_UP + triangleIndex * OFFSET));
                y2 = (int)(height * POS_Y2_UP);
                x3 = (int)(width * (OFFSET2 + POS_X3_UP + triangleIndex * OFFSET));
                y3 = (int)(height * POS_Y3_UP);
                break;
            case 12: case 13: case 14: case 15: case 16: case 17:
                x1 = (int)(width * (POS_X1_DOWN + (23 - triangleIndex) * OFFSET + OFFSET2));
                y1 = (int)(height * POS_Y1_DOWN);
                x2 = (int)(width * (POS_X2_DOWN + (23 - triangleIndex) * OFFSET + OFFSET2));
                y2 = (int)(height * POS_Y2_DOWN);
                x3 = (int)(width * (POS_X3_DOWN + (23 - triangleIndex) * OFFSET + OFFSET2));
                y3 = (int)(height * POS_Y3_DOWN);
                break;
            case 18: case 19: case 20: case 21: case 22: case 23:
                x1 = (int)(width * (POS_X1_DOWN + (23 - triangleIndex) * OFFSET));
                y1 = (int)(height * POS_Y1_DOWN);
                x2 = (int)(width * (POS_X2_DOWN + (23 - triangleIndex) * OFFSET));
                y2 = (int)(height * POS_Y2_DOWN);
                x3 = (int)(width * (POS_X3_DOWN + (23 - triangleIndex) * OFFSET));
                y3 = (int)(height * POS_Y3_DOWN);
                break;
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Path trianglePath = new Path();
        if(triangleIndex == -1 || triangleIndex == 24){
            canvas.drawRoundRect(x1, y1, x2, y2, BEAR_OFF_RADIUS, BEAR_OFF_RADIUS, color);
        }
        else{
            trianglePath.moveTo(x1, y1);
            trianglePath.lineTo(x2, y2);
            trianglePath.lineTo(x3, y3);
            trianglePath.lineTo(x1, y1);
        }
            canvas.drawPath(trianglePath, color);


    }

    public void setBearingOff(int black){
        bearingOff = true;
        x1 = (int) (width * BEAR_OFF_X1);
        x2 = (int) (width * BEAR_OFF_X2);
        y1 = (int) (height * (BEAR_OFF_Y1 - black * BEAR_OFF_OFFSET));
        y2 = (int) (height * (BEAR_OFF_Y2 - black * BEAR_OFF_OFFSET));
    }

    public boolean isBearingOff() {
        return bearingOff;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getX3() {
        return x3;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public int getY3() {
        return y3;
    }

    public int getTriangleIndex() {
        return triangleIndex;
    }
}
