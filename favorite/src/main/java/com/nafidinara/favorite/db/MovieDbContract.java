package com.nafidinara.favorite.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieDbContract {
    public static final String AUTH="com.nafidinara.threesubmission";
    public static final String SCHEME="content";
    public static final class MovieColumns implements BaseColumns{
        public static final  String TABLE_MOVIE_NAME = "movie";

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE= "release_date";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String VOTE_COUNT = "vote_count";
        public static final String ORIGINAL_LANGUANGE = "original_language";
        public static final String POSTER_PATH = "poster_path";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final Uri MOVIE_URI= new Uri.Builder().scheme(SCHEME).authority(AUTH).appendPath(TABLE_MOVIE_NAME).build();

    }
    public static String getStringMovie(Cursor cursor, String column){
        return cursor.getString(cursor.getColumnIndex(column));
    }
    public static int getIntMovie(Cursor cursor, String column){
        return cursor.getInt(cursor.getColumnIndex(column));
    }
}
