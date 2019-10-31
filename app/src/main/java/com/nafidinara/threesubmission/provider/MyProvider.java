package com.nafidinara.threesubmission.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nafidinara.threesubmission.db.MovieHelper;
import com.nafidinara.threesubmission.db.TvHelper;

import java.util.Objects;

import static com.nafidinara.threesubmission.db.MovieDbContract.AUTH;
import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.MOVIE_URI;
import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.TABLE_MOVIE_NAME;
import static com.nafidinara.threesubmission.db.TvDbContract.TvColumns.TABLE_TV_NAME;
import static com.nafidinara.threesubmission.db.TvDbContract.TvColumns.TV_URI;

public class MyProvider extends ContentProvider {
    MovieHelper movieHelper;
    TvHelper tvHelper;
    static final int MOVIE=10;
    static final int MOVIE_ID=11;
    static final int TV=12;
    static final int TV_ID=13;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTH,TABLE_MOVIE_NAME,MOVIE);
        URI_MATCHER.addURI(AUTH,TABLE_MOVIE_NAME + "/#",MOVIE_ID);
        URI_MATCHER.addURI(AUTH,TABLE_TV_NAME,TV);
        URI_MATCHER.addURI(AUTH,TABLE_TV_NAME + "/#",TV_ID);

    }

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        tvHelper = new TvHelper(getContext());
        tvHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,@Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (URI_MATCHER.match(uri)){
            case MOVIE:
                cursor = movieHelper.cursorMovieGet();
                break;
            case MOVIE_ID:
                cursor = movieHelper.cursorMovieGetId(uri.getLastPathSegment());
                break;
            case TV:
                cursor = tvHelper.cursorTvGet();
                break;
            case TV_ID:
                cursor = tvHelper.cursorTvGetId(uri.getLastPathSegment());
                break;
            default:
                cursor=null;
                break;
        }
        if (cursor != null){
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        Uri contentUri = null;

        switch (URI_MATCHER.match(uri)) {
            case MOVIE:
                added = movieHelper.movieInsertProvider(values);
                if (added > 0) {
                    contentUri = ContentUris.withAppendedId(MOVIE_URI, added);
                }
                break;
            case TV:
                added = tvHelper.tvInsertProvider(values);
                if (added > 0) {
                    contentUri = ContentUris.withAppendedId(TV_URI, added);
                }
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return contentUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,@Nullable String[] selectionArgs) {
        int deleted;
        switch (URI_MATCHER.match(uri)){
            case MOVIE_ID:
                deleted = movieHelper.movieDeleteProvider(uri.getLastPathSegment());
                break;
            case TV_ID:
                deleted = tvHelper.tvDeleteProvider(uri.getLastPathSegment());
                break;
                default:
                    deleted = 0;
                    break;

        }
        if (deleted > 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);

        }
        return deleted;
    }


    @Override
    public int update(@NonNull Uri uri,@Nullable ContentValues values,@Nullable String selection,
                     @Nullable String[] selectionArgs) {
        int updated;
        switch (URI_MATCHER.match(uri)) {
            case MOVIE_ID:
                updated = movieHelper.movieUpdateProvider(uri.getLastPathSegment(), values);
                break;
            case TV_ID:
                updated = tvHelper.TvupdateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
