package com.example.emanu.pmu_projekat.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.emanu.pmu_projekat.DB.MyDbHelper;
import com.example.emanu.pmu_projekat.DB.TableEntry;
import com.example.emanu.pmu_projekat.R;

import java.io.File;

public class GameRecapActivity extends AppCompatActivity {

    private String[] fields = {TableEntry.COL_WINNER, TableEntry.COL_DATETIME};
    private int[] viewIds = {R.id.winnerTextView, R.id.dateTextView};
    private static Context context;
    private String winner;
    private String looser;
    private boolean deleteSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_recap);
        GameRecapActivity.context = getApplicationContext();
        hideStatusBar();

        Bundle extras = getIntent().getExtras();
        winner = extras.getString(StartedGameActivity.WINNER);
        looser = extras.getString(StartedGameActivity.LOOSER);
        deleteSave = extras.getBoolean(StatsActivity.DELETE_SAVE);
        Cursor cursor = getPreviousResults(winner, looser);

        TextView textView1 = findViewById(R.id.player1recap);
        textView1.setText(winner);
        TextView textView2 = findViewById(R.id.player1score);
        textView2.setText(getNumberOfVictories(winner, looser) + "");
        TextView textView3 = findViewById(R.id.player2score);
        textView3.setText(getNumberOfVictories(looser, winner) + "");
        TextView textView4 = findViewById(R.id.player2recap);
        textView4.setText(looser);

        ListView listView = findViewById(R.id.listViewRecap);
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.recap_list_item, cursor, fields, viewIds, 0);
        listView.setAdapter(cursorAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(deleteSave){
            File saveFile = new File(getFilesDir(), StartedGameActivity.SAVE_FILE);
            if(saveFile.exists()){
                saveFile.delete();
            }
        }

    }

    public Cursor getPreviousResults(String winner, String looser){
        MyDbHelper helper = new MyDbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = "(" + TableEntry.COL_WINNER + " = ? OR " + TableEntry.COL_WINNER + " = ?) AND (" + TableEntry.COL_SECOND_PLAYER + " = ? OR " + TableEntry.COL_SECOND_PLAYER + " = ?)";
        String[] selectionArgs = {winner, looser, winner, looser};
        Cursor cursor = db.query(TableEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        return cursor;
    }

    public int getNumberOfVictories(String player1, String player2){
        MyDbHelper helper = new MyDbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select count(*) from " + TableEntry.TABLE_NAME + " where " + TableEntry.COL_WINNER + " = '" + player1 + "' AND " + TableEntry.COL_SECOND_PLAYER + " = '" + player2 +"'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void onClickClear(View view) {
        new DeletingResults().execute();
    }

    public void onClickMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class DeletingResults extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            MyDbHelper helper = new MyDbHelper(GameRecapActivity.this);
            SQLiteDatabase db = helper.getWritableDatabase();
            String selection = "(" + TableEntry.COL_WINNER + " = ? OR " + TableEntry.COL_WINNER + " = ?) AND (" + TableEntry.COL_SECOND_PLAYER + " = ? OR " + TableEntry.COL_SECOND_PLAYER + " = ?)";
            String[] selectionArgs = {winner, looser, winner, looser};
            db.delete(TableEntry.TABLE_NAME, selection, selectionArgs);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = getIntent();
            intent.putExtra(StartedGameActivity.WINNER, winner);
            intent.putExtra(StartedGameActivity.LOOSER, looser);
            startActivity(intent);
        }
    }

    public static Context getAppContext() {
        return GameRecapActivity.context;
    }

}
