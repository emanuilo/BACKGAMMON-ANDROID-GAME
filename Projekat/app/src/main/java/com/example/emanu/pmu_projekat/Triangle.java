package com.example.emanu.pmu_projekat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;

/**
 * Created by emanu on 3/20/2018.
 */

public class Triangle extends Figure {
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

    public Triangle(int triangleIndex, int height, int width) {
        this.height = height;
        this.width = width;
        this.triangleIndex = triangleIndex;
        color = new Paint();
        color.setColor(Color.MAGENTA);
        color.setAlpha(110);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Path trianglePath = new Path();
        switch (triangleIndex){
            case 0: case 1: case 2: case 3: case 4: case 5:
                x1 = (int)(width * (POS_X1_UP + triangleIndex * OFFSET));
                y1 = (int)(height * POS_Y1_UP);
                x2 = (int)(width * (POS_X2_UP + triangleIndex * OFFSET));
                y2 = (int)(height * POS_Y2_UP);
                x3 = (int)(width * (POS_X3_UP + triangleIndex * OFFSET));
                y3 = (int)(height * POS_Y3_UP);
                trianglePath.moveTo((int)(width * (POS_X1_UP + triangleIndex * OFFSET)), (int)(height * POS_Y1_UP));
                trianglePath.lineTo((int)(width * (POS_X2_UP + triangleIndex * OFFSET)), (int)(height * POS_Y2_UP));
                trianglePath.lineTo((int)(width * (POS_X3_UP + triangleIndex * OFFSET)), (int)(height * POS_Y3_UP));
                trianglePath.lineTo((int)(width * (POS_X1_UP + triangleIndex * OFFSET)), (int)(height * POS_Y1_UP));
                break;
            case 6: case 7: case 8: case 9: case 10: case 11:
                x1 = (int)(width * (OFFSET2 + POS_X1_UP + triangleIndex * OFFSET));
                y1 = (int)(height * POS_Y1_UP);
                x2 = (int)(width * (OFFSET2 + POS_X2_UP + triangleIndex * OFFSET));
                y2 = (int)(height * POS_Y2_UP);
                x3 = (int)(width * (OFFSET2 + POS_X3_UP + triangleIndex * OFFSET));
                y3 = (int)(height * POS_Y3_UP);
                trianglePath.moveTo((int)(width * (OFFSET2 + POS_X1_UP + triangleIndex * OFFSET)), (int)(height * POS_Y1_UP));
                trianglePath.lineTo((int)(width * (OFFSET2 + POS_X2_UP + triangleIndex * OFFSET)), (int)(height * POS_Y2_UP));
                trianglePath.lineTo((int)(width * (OFFSET2 + POS_X3_UP + triangleIndex * OFFSET)), (int)(height * POS_Y3_UP));
                trianglePath.lineTo((int)(width * (OFFSET2 + POS_X1_UP + triangleIndex * OFFSET)), (int)(height * POS_Y1_UP));
                break;
            case 12: case 13: case 14: case 15: case 16: case 17:
                x1 = (int)(width * (POS_X1_DOWN + (23 - triangleIndex) * OFFSET + OFFSET2));
                y1 = (int)(height * POS_Y1_DOWN);
                x2 = (int)(width * (POS_X2_DOWN + (23 - triangleIndex) * OFFSET + OFFSET2));
                y2 = (int)(height * POS_Y2_DOWN);
                x3 = (int)(width * (POS_X3_DOWN + (23 - triangleIndex) * OFFSET + OFFSET2));
                y3 = (int)(height * POS_Y3_DOWN);
                trianglePath.moveTo((int)(width * (POS_X1_DOWN + (23 - triangleIndex) * OFFSET + OFFSET2)), (int)(height * POS_Y1_DOWN));
                trianglePath.lineTo((int)(width * (POS_X2_DOWN + (23 - triangleIndex) * OFFSET + OFFSET2)), (int)(height * POS_Y2_DOWN));
                trianglePath.lineTo((int)(width * (POS_X3_DOWN + (23 - triangleIndex) * OFFSET + OFFSET2)), (int)(height * POS_Y3_DOWN));
                trianglePath.lineTo((int)(width * (POS_X1_DOWN + (23 - triangleIndex) * OFFSET + OFFSET2)), (int)(height * POS_Y1_DOWN));
                break;
            case 18: case 19: case 20: case 21: case 22: case 23:
                x1 = (int)(width * (POS_X1_DOWN + (23 - triangleIndex) * OFFSET));
                y1 = (int)(height * POS_Y1_DOWN);
                x2 = (int)(width * (POS_X2_DOWN + (23 - triangleIndex) * OFFSET));
                y2 = (int)(height * POS_Y2_DOWN);
                x3 = (int)(width * (POS_X3_DOWN + (23 - triangleIndex) * OFFSET));
                y3 = (int)(height * POS_Y3_DOWN);
                trianglePath.moveTo((int)(width * (POS_X1_DOWN + (23 - triangleIndex) * OFFSET)), (int)(height * POS_Y1_DOWN));
                trianglePath.lineTo((int)(width * (POS_X2_DOWN + (23 - triangleIndex) * OFFSET)), (int)(height * POS_Y2_DOWN));
                trianglePath.lineTo((int)(width * (POS_X3_DOWN + (23 - triangleIndex) * OFFSET)), (int)(height * POS_Y3_DOWN));
                trianglePath.lineTo((int)(width * (POS_X1_DOWN + (23 - triangleIndex) * OFFSET)), (int)(height * POS_Y1_DOWN));
                break;
        }


        canvas.drawPath(trianglePath, color);
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
