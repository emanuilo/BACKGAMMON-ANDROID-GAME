package com.example.emanu.pmu_projekat.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.emanu.pmu_projekat.DB.MyDbHelper;
import com.example.emanu.pmu_projekat.DB.TableEntry;
import com.example.emanu.pmu_projekat.R;

public class StatsActivity extends AppCompatActivity {

    public static final String DELETE_SAVE = "DELETE_SAVE";
    private String[] fields = {TableEntry.COL_WINNER, "rez1", "rez2", TableEntry.COL_SECOND_PLAYER};
    private int[] viewIds = {R.id.statsPlayer1name, R.id.statsPlayer1score, R.id.statsPlayer2score, R.id.statsPlayer2name};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        hideStatusBar();

        ListView statsListView = findViewById(R.id.statsListView);

        Cursor cursor = getStats();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.stats_list_item, cursor, fields, viewIds, 0);
        statsListView.setAdapter(cursorAdapter);

        statsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String player1 = ((TextView)view.findViewById(R.id.statsPlayer1name)).getText().toString();
                String player2 = ((TextView)view.findViewById(R.id.statsPlayer2name)).getText().toString();

                Intent intent = new Intent(StatsActivity.this, GameRecapActivity.class);
                intent.putExtra(StartedGameActivity.WINNER, player1);
                intent.putExtra(StartedGameActivity.LOOSER, player2);
                intent.putExtra(DELETE_SAVE, false);
                startActivity(intent);
            }
        });
    }

    public Cursor getStats(){
        MyDbHelper helper = new MyDbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select r._id, r.winner, r.second_player, (select count(*) from results r1 where r1.winner = r.winner and r1.second_player = r.second_player) as rez1, (select count(*) from results r2 where r2.winner = r.second_player and r2.second_player = r.winner) as rez2 from results r where r.winner = (select r3.winner from results r3 where (r3.winner = r.winner and r3.second_player = r.second_player) or (r3.winner = r.second_player and r3.second_player = r.winner) order by r3.winner) group by r.winner, r.second_player";
        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }

    public void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void onClickBackStats(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickClearStats(View view) {
        new DeletingAllResults().execute();
    }

    private class DeletingAllResults extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            MyDbHelper helper = new MyDbHelper(StatsActivity.this);
            SQLiteDatabase db = helper.getWritableDatabase();
            db.delete(TableEntry.TABLE_NAME, null, null);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = getIntent();
            startActivity(intent);
        }
    }
}
