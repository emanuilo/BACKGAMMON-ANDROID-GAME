package com.example.emanu.pmu_projekat.DB;

import android.provider.BaseColumns;

/**
 * Created by emanu on 4/2/2018.
 */

public class TableEntry implements BaseColumns {
    public static final String TABLE_NAME = "results";
    public static final String COL_WINNER = "winner";
    public static final String COL_SECOND_PLAYER = "second_player";
    public static final String COL_DATETIME = "finish_datetime";
}
