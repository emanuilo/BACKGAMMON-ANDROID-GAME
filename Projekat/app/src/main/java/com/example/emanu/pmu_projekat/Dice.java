package com.example.emanu.pmu_projekat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

/**
 * Created by emanu on 3/13/2018.
 */

public class Dice extends Figure{
    private int value;
    private boolean show;
    private Paint color = new Paint();
    private Paint black = new Paint();
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private int r = 7;
    private int r1;
    private int r2;
    private int height;
    private int width;
    private boolean firstDice;
    private boolean doubles;

    public Dice(boolean firstDice, boolean doubles){
        color.setColor(Color.LTGRAY);
        black.setColor(Color.BLACK);
        this.firstDice = firstDice;
        this.doubles = doubles;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if(!show) return;
        canvas.drawRoundRect(x1, y1, x2,y2, r1, r2, color);

        switch (value){
            case 1:
                canvas.drawCircle(x1 + (x2-x1) / 2, y1 + (y2 - y1) / 2, r, black);
                break;
            case 2:
                canvas.drawCircle(x1 * 1.01f, y2 * 0.85f, r, black);
                canvas.drawCircle(x2 * 0.99f, y1 * 1.4f, r, black);
                break;
            case 3:
                canvas.drawCircle(x1 * 1.01f, y2 * 0.85f, r, black);
                canvas.drawCircle(x1 + (x2-x1) / 2, y1 + (y2 - y1) / 2, r, black);
                canvas.drawCircle(x2 * 0.99f, y1 * 1.4f, r, black);
                break;
            case 4:
                canvas.drawCircle(x1 * 1.01f, y2 * 0.85f, r, black);
                canvas.drawCircle(x2 * 0.99f, y1 * 1.4f, r, black);
                canvas.drawCircle(x1 * 1.01f, y1 * 1.4f, r, black);
                canvas.drawCircle(x2 * 0.99f, y2 * 0.85f, r, black);
                break;
            case 5:
                canvas.drawCircle(x1 * 1.01f, y2 * 0.85f, r, black);
                canvas.drawCircle(x1 + (x2-x1) / 2, y1 + (y2 - y1) / 2, r, black);
                canvas.drawCircle(x2 * 0.99f, y1 * 1.4f, r, black);
                canvas.drawCircle(x1 * 1.01f, y1 * 1.4f, r, black);
                canvas.drawCircle(x2 * 0.99f, y2 * 0.85f, r, black);
                break;
            case 6:
                canvas.drawCircle(x1 + width * 0.011f, y2 * 0.86f, r, black);
                canvas.drawCircle(x1 + width * 0.022f, y2 * 0.86f, r, black);
                canvas.drawCircle(x1 + width * 0.011f, y1 + (y2 - y1) / 2, r, black);
                canvas.drawCircle(x1 + width * 0.022f, y1 + (y2 - y1) / 2, r, black);
                canvas.drawCircle(x1 + width * 0.011f, y1 * 1.35f, r, black);
                canvas.drawCircle(x1 + width * 0.022f, y1 * 1.35f, r, black);
                break;
        }
    }

    public void setHeightAndWidth(int height, int width) {
        this.height = height;
        this.width = width;

        if(firstDice){
            if(!doubles){
                x1 = (int) (width * 0.9);
                x2 = (int) (width * 0.934);
                y1 = (int) (height * 0.03);
                y2 = (int) (height * 0.08);
                r1 = 10;
                r2 = 10;
            }
            else{
                x1 = (int) (width * 0.82);
                x2 = (int) (width * 0.854);
                y1 = (int) (height * 0.03);
                y2 = (int) (height * 0.08);
                r1 = 10;
                r2 = 10;
            }
        }
        else{
            if(!doubles){
                x1 = (int) (width * 0.94);
                x2 = (int) (width * 0.974);
                y1 = (int) (height * 0.03);
                y2 = (int) (height * 0.08);
                r1 = 10;
                r2 = 10;
            }
            else{
                x1 = (int) (width * 0.86);
                x2 = (int) (width * 0.894);
                y1 = (int) (height * 0.03);
                y2 = (int) (height * 0.08);
                r1 = 10;
                r2 = 10;
            }
        }
    }

    public void roll(){
        value = (int) (Math.random()*6 + 1);
    }

    public void incOpacity(){
        color.setAlpha(110);
        black.setAlpha(110);
    }

    public void resetOpacity(){
        color.setAlpha(255);
        black.setAlpha(255);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getR1() {
        return r1;
    }

    public void setR1(int r1) {
        this.r1 = r1;
    }

    public int getR2() {
        return r2;
    }

    public void setR2(int r2) {
        this.r2 = r2;
    }


    public void setDoubles(boolean doubles) {
        this.doubles = doubles;
    }
}
