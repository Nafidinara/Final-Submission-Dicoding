package com.nafidinara.threesubmission.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;

import com.nafidinara.threesubmission.model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.BACKDROP_PATH;
import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.ID;
import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.ORIGINAL_LANGUANGE;
import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.OVERVIEW;
import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.POSTER_PATH;
import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.RELEASE_DATE;
import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.TABLE_MOVIE_NAME;
import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.TITLE;
import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.VOTE_AVERAGE;
import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.VOTE_COUNT;
import static com.nafidinara.threesubmission.db.TvDbContract.TvColumns.TABLE_TV_NAME;

public class TvHelper {
    private static final String DB_TABLE = TABLE_TV_NAME;
    private static TvDbHelper tvDbHelper;
    private static TvHelper INST;
    private static SQLiteDatabase db;

    public TvHelper (Context context){
        tvDbHelper = new TvDbHelper(context);
    }

    public static TvHelper getInst(Context context){
        if (INST == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INST == null) {
                    INST= new TvHelper(context);
                }
            }
        }
        return INST;
    }
    public void open() throws SQLException{
        db = tvDbHelper.getWritableDatabase();
    }
    public void close() {
        tvDbHelper.close();

        if (db.isOpen())
            db.close();
    }

    public ArrayList<TvShow> getAllMovies(){
        ArrayList<TvShow> arrayList = new ArrayList<>();
        Cursor cursor = db.query(DB_TABLE,null,
                null,
                null,
                null,
                null,
                TITLE+ " ASC",
                null);
        cursor.moveToFirst();
        TvShow tvShow;
        if (cursor.getCount()>0){
            do{
                tvShow = new TvShow();
                tvShow.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                tvShow.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvShow.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                tvShow.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                tvShow.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                tvShow.setVoteCount(cursor.getInt(cursor.getColumnIndexOrThrow(VOTE_COUNT)));
                tvShow.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANGUANGE)));
                tvShow.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                tvShow.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)));
                arrayList.add(tvShow);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        } cursor.close();
        return arrayList;
    }

    public long tvInsert (TvShow tvShow){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,tvShow.getId());
        contentValues.put(TITLE,tvShow.getTitle());
        contentValues.put(OVERVIEW,tvShow.getOverview());
        contentValues.put(RELEASE_DATE, tvShow.getReleaseDate());
        contentValues.put(VOTE_AVERAGE, tvShow.getVoteAverage());
        contentValues.put(VOTE_COUNT, tvShow.getVoteCount());
        contentValues.put(ORIGINAL_LANGUANGE, tvShow.getOriginalLanguage());
        contentValues.put(POSTER_PATH, tvShow.getPosterPath());
        contentValues.put(BACKDROP_PATH, tvShow.getBackdropPath());

        return db.insert(DB_TABLE,null,contentValues);
    }

    public int tvDelete(String title) {
        return db.delete(TABLE_TV_NAME, TITLE + " = '" + title + "'", null);
    }

    public Cursor cursorTvGet(){
        return db.query(TABLE_TV_NAME,null,null,null,null,null,MediaStore.Audio.Playlists.Members.TITLE+" ASC");
    }
    public Cursor cursorTvGetId(String id) {
        return db.query(DB_TABLE, null
                , MediaStore.Audio.Playlists.Members.TITLE + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }
    public int tvDeleteProvider(String id) {
        return db.delete(TABLE_TV_NAME, ID+ "=?",new String[]{id});
    }
    public int TvupdateProvider(String id, ContentValues values) {
        return db.update(DB_TABLE, values, MediaStore.Audio.Playlists.Members.TITLE + " =?", new String[]{id});
    }
    public long tvInsertProvider(ContentValues values) {
        return db.insert(DB_TABLE, null, values);
    }
}
