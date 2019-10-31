package com.nafidinara.favorite.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class TvDbContract {
    public static final String AUTH="com.nafidinara.threesubmission";
    public static final String SCHEME="content";
    public static final class TvColumns implements BaseColumns{
        public static final String TABLE_TV_NAME = "tv";

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String VOTE_COUNT = "vote_count";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String POSTER_PATH = "poster_path";
        public static final String BACKDROP_PATH = "backdrop_path";

        public static final Uri TV_URI= new Uri.Builder().scheme(SCHEME).authority(AUTH).appendPath(TABLE_TV_NAME).build();
    }

    public static String getStringTv(Cursor cursor, String column){
        return cursor.getString(cursor.getColumnIndex(column));
    }
    public static int getIntTv(Cursor cursor, String column){
        return cursor.getInt(cursor.getColumnIndex(column));
    }
}
