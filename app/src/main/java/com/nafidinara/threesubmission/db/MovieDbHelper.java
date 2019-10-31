package com.nafidinara.threesubmission.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns;

import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.TABLE_MOVIE_NAME;

public class MovieDbHelper extends SQLiteOpenHelper {
    private static final String MOVIE_DB_NAME = "moviedb";
    private static final int MOVIE_DB_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format(
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
            TABLE_MOVIE_NAME,
            MovieColumns.ID,
            MovieColumns.TITLE,
            MovieColumns.OVERVIEW,
            MovieColumns.RELEASE_DATE,
            MovieColumns.VOTE_AVERAGE,
            MovieColumns.VOTE_COUNT,
            MovieColumns.ORIGINAL_LANGUANGE,
            MovieColumns.POSTER_PATH,
            MovieColumns.BACKDROP_PATH
    );

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_MOVIE_NAME);
        onCreate(db);
    }
    public MovieDbHelper (Context context){
        super(context, MOVIE_DB_NAME,null,MOVIE_DB_VERSION);
    }
}
