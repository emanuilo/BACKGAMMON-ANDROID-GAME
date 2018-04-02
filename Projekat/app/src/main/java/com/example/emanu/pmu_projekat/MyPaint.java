package com.example.emanu.pmu_projekat;

import android.graphics.Paint;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by emanu on 3/27/2018.
 */

public class MyPaint extends Paint implements Serializable{

    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        setColor(objectInputStream.readInt());
    }

    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(getColor());
    }
}
