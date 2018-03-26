package com.example.emanu.pmu_projekat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

/**
 * Created by emanu on 3/12/2018.
 */

public class Checker extends Figure {
    public static final int RADIUS = 40;
    public static final double TRIANGLE_OFFSET = 0.066;
    public static final double POSITION_OFFSET = 0.077;
    public static final double TRIANGLE0 = 0.133;
    public static final double TRIANGLE6 = 0.618;
    public static final double POS1UP = 0.16;
    public static final double POS1DOWN = 0.937;
    public static final double POS1ON_EDGE_X = 0.54;
    public static final double POS1ON_EDGE_Y = 0.16;
    public static final double EDGE_OFFSET = 6 * 0.077;


    private Paint grey = new Paint();
    private Paint color;
    private int x, y;
    private int x2, y2;
    private String status;
    private int height;
    private int width;
    private boolean bearedOff;


    public Checker(int x, int y, Paint color, int height, int width) {
        this.color = color;
        this.x = x;
        this.y = y;
        grey.setColor(Color.GRAY);
        this.height = height;
        this.width = width;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if(bearedOff){
            canvas.drawRoundRect(x - 4, y - 4, x2 + 4,y2 + 4, RADIUS + 4, RADIUS + 4, grey);
            canvas.drawRoundRect(x, y, x2,y2, RADIUS, RADIUS, color);
        }
        else{
            canvas.drawCircle(x, y, RADIUS + 4, grey);
            canvas.drawCircle(x, y, RADIUS, color);
        }
    }

    public void setOnPosition(int height, int width, int triangleIndex, int position){
        switch (triangleIndex){
            case 0: case 1: case 2: case 3: case 4: case 5:
                x = (int) (width * (TRIANGLE0 + triangleIndex * TRIANGLE_OFFSET));
                y = (int) (height * (POS1UP + position * POSITION_OFFSET));
                break;
            case 6: case 7: case 8: case 9: case 10: case 11:
                x = (int) (width * (TRIANGLE6 + (triangleIndex - 6) * TRIANGLE_OFFSET));
                y = (int) (height * (POS1UP + position * POSITION_OFFSET));
                break;
            case 12: case 13: case 14: case 15: case 16: case 17:
                x = (int) (width * (TRIANGLE6 + (17 - triangleIndex) * TRIANGLE_OFFSET));
                y = (int) (height * (POS1DOWN - position * POSITION_OFFSET));
                break;
            case 18: case 19: case 20: case 21: case 22: case 23:
                x = (int) (width * (TRIANGLE0 + (23 - triangleIndex) * TRIANGLE_OFFSET));
                y = (int) (height * (POS1DOWN - position * POSITION_OFFSET));
                break;
        }
    }

    public void moveToTheEdge(int whiteChecker, int index, int height, int width){
        x = (int) (width * (POS1ON_EDGE_X));
        y = (int) (height * (POS1ON_EDGE_Y + index * POSITION_OFFSET + whiteChecker * EDGE_OFFSET));
    }

    public void bearOff(int blackChecker, int index) {
        this.bearedOff = true;
        x = (int) (width * 0.02);
        x2 = (int) (width * 0.06);
        y = (int) (height * (0.953 - index * 0.025 - blackChecker * 0.48));
        y2 = (int) (height * (0.972 - index * 0.025 - blackChecker * 0.48));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
