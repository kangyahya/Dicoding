package com.rtikcirebonkota.thecataloguemovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.rtikcirebonkota.thecataloguemovie.database.MovieHelper;
import com.rtikcirebonkota.thecataloguemovie.database.MyTMDB;
import com.rtikcirebonkota.thecataloguemovie.database.TvHelper;

import org.jetbrains.annotations.NotNull;

import static com.rtikcirebonkota.thecataloguemovie.database.MyTMDB.AUTHORITY;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 100;
    private static final int MOVIE_ID = 101;
    private static final int TV = 102;
    private static final int TV_ID = 103;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY,
                MyTMDB.MovieColumns.TABLEName, MOVIE);
        sUriMatcher.addURI(AUTHORITY,
                MyTMDB.MovieColumns.TABLEName+ "/#",
                MOVIE_ID);
        sUriMatcher.addURI(AUTHORITY,
                MyTMDB.TvColumns.TABLEName, TV);
        sUriMatcher.addURI(AUTHORITY,
                MyTMDB.TvColumns.TABLEName+ "/#",
                TV_ID);
    }

    private MovieHelper movieHelper;
    private TvHelper tvHelper;
    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        tvHelper = new TvHelper(getContext());
        movieHelper.open();
        tvHelper.open();

        return false;
    }

    @Override
    public Cursor query(@NotNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch(match){
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case TV:
                cursor = tvHelper.queryProvider();
                break;
            case TV_ID:
                cursor = tvHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }

    @Override
    public String getType(@NotNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NotNull Uri uri, ContentValues contentValues) {
        String content = "";
        long added ;
        if (sUriMatcher.match(uri) == MOVIE) {
            added = movieHelper.insertProvider(contentValues);
            content = "CONTENT_URI";

        }else if (sUriMatcher.match(uri) == TV) {
            added = tvHelper.insertProvider(contentValues);
            content = "CONTENT_TV";
        } else {
            added = 0;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(content + "/" + added);
    }

    @Override
    public int delete(@NotNull Uri uri, String s, String[] strings) {
        int Deleted;

        int match = sUriMatcher.match(uri);
        if (match == MOVIE_ID) {
            Deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
        }else if (match == TV_ID) {
            Deleted = tvHelper.deleteProvider(uri.getLastPathSegment());
        } else {
            Deleted = 0;
        }

        if (Deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Deleted;
    }

    @Override
    public int update(@NotNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int Updated;
        if (sUriMatcher.match(uri) == MOVIE_ID) {
            Updated = movieHelper.updateProvider(uri.getLastPathSegment(), contentValues);
        }else if (sUriMatcher.match(uri) == TV_ID) {
            Updated = tvHelper.updateProvider(uri.getLastPathSegment(), contentValues);
        } else {
            Updated = 0;
        }

        if (Updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Updated;
    }
}
