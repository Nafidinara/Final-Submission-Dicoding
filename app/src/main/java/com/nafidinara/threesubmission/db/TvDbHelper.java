package com.nafidinara.threesubmission.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nafidinara.threesubmission.db.TvDbContract.TvColumns;
import static com.nafidinara.threesubmission.db.TvDbContract.TvColumns.TABLE_TV_NAME;

public class TvDbHelper extends SQLiteOpenHelper {
    private static final String TV_DB_NAME = "tvdb";
    private static final int TV_DB_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TV = String.format(
            "CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_TV_NAME,
            TvColumns.ID,
            TvColumns.TITLE,
            TvColumns.OVERVIEW,
            TvColumns.RELEASE_DATE,
            TvColumns.VOTE_AVERAGE,
            TvColumns.VOTE_COUNT,
            TvColumns.ORIGINAL_LANGUAGE,
            TvColumns.POSTER_PATH,
            TvColumns.BACKDROP_PATH
    );

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TV);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_TV_NAME);
        onCreate(db);
    }
    public TvDbHelper (Context context){
        super(context, TV_DB_NAME,null,TV_DB_VERSION);
    }
}
