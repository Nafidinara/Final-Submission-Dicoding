package com.nafidinara.threesubmission.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;

import com.nafidinara.threesubmission.model.Movie;

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

public class MovieHelper {

    private static final String DB_TABLE = TABLE_MOVIE_NAME;
    private static MovieDbHelper movieDbHelper;
    private static MovieHelper INST;
    private static SQLiteDatabase db;

    public MovieHelper (Context context){
        movieDbHelper = new MovieDbHelper(context);
    }

    public static MovieHelper getInst(Context context){
        if (INST == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INST == null) {
                    INST= new MovieHelper(context);
                }
            }
        }
        return INST;
    }

    public void open() throws SQLException{
        db = movieDbHelper.getWritableDatabase();
//        db = movieDbHelper.getReadableDatabase();
    }

    public void close() {
        movieDbHelper.close();

        if (db.isOpen())
            db.close();
    }

    public ArrayList<Movie> getAllMovies(){
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = db.query(DB_TABLE,null,
                null,
                null,
                null,
                null,
                TITLE+ " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount()>0){
            do{
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                movie.setVoteCount(cursor.getInt(cursor.getColumnIndexOrThrow(VOTE_COUNT)));
                movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANGUANGE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        } cursor.close();
        return arrayList;
    }

    public long movieInsert (Movie movie){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,movie.getId());
        contentValues.put(TITLE,movie.getTitle());
        contentValues.put(OVERVIEW,movie.getOverview());
        contentValues.put(RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(VOTE_COUNT, movie.getVoteCount());
        contentValues.put(ORIGINAL_LANGUANGE, movie.getOriginalLanguage());
        contentValues.put(POSTER_PATH, movie.getPosterPath());
        contentValues.put(BACKDROP_PATH, movie.getBackdropPath());

        return db.insert(DB_TABLE,null,contentValues);
    }

    public int movieDelete(String title) {
        return db.delete(TABLE_MOVIE_NAME, TITLE + " = '" + title + "'", null);
    }
    public Cursor cursorMovieGet() {
        return db.query(DB_TABLE, null, null, null, null, null, MediaStore.Audio.Playlists.Members.TITLE+" ASC");
    }
    public Cursor cursorMovieGetId(String id) {
        return db.query(DB_TABLE, null
                , MediaStore.Audio.Playlists.Members.TITLE + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }
    public int movieDeleteProvider(String id) {
        return db.delete(TABLE_MOVIE_NAME, ID+ "=?",new String[]{id});
    }
    public int movieUpdateProvider(String id, ContentValues values) {
        return db.update(DB_TABLE, values, MediaStore.Audio.Playlists.Members.TITLE + " =?", new String[]{id});
    }
    public long movieInsertProvider(ContentValues values) {
        return db.insert(DB_TABLE, null, values);
    }
}
