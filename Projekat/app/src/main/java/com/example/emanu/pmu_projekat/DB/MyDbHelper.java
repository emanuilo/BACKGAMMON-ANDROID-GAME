package com.example.emanu.pmu_projekat.DB;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * Created by emanu on 4/2/2018.
 */

public class MyDbHelper extends SQLiteOpenHelper implements Serializable{
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + TableEntry.TABLE_NAME + " ( " + TableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TableEntry.COL_WINNER + " TEXT, "
            + TableEntry.COL_SECOND_PLAYER + " TEXT, "
            + TableEntry.COL_DATETIME + " TEXT" + " );";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TableEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Results.db";

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
