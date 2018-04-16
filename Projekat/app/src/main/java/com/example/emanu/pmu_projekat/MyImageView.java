package com.example.emanu.pmu_projekat;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by emanu on 3/11/2018.
 */

public class MyImageView extends android.support.v7.widget.AppCompatImageView{
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

    private Model model;
    private int height1;
    private int width1;

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height1 = h;
        width1 = w;
        model.setHeightAndWidth(h, w);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = canvas.getHeight();
        int width = canvas.getWidth();

        List<Checker> player1Checkers = model.getPlayer1Checkers();
        List<Checker> player2Checkers = model.getPlayer2Checkers();
        Dice dice1 = model.getDie1();
        Dice dice2 = model.getDie2();
        Dice dice3 = model.getDie3();
        Dice dice4 = model.getDie4();
        List<Triangle> availablePositions = model.getAvailablePositions();

        for (Checker checker : player1Checkers) {
            checker.draw(canvas);
        }

        for (Checker checker : player2Checkers) {
            checker.draw(canvas);
        }

        dice1.draw(canvas);
        dice2.draw(canvas);
        dice3.draw(canvas);
        dice4.draw(canvas);

        if(availablePositions != null){
            for (Triangle triangle : availablePositions) {
                triangle.draw(canvas);
            }
        }

    }

    public void setModel(Model model) {
        this.model = model;
    }

}
