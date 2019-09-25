package com.rtikcirebonkota.thecataloguemovie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

public class MyTMDB extends SQLiteOpenHelper {
    public static final String AUTHORITY = "com.rtikcirebonkota.thecataloguemovie";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(MyTMDB.MovieColumns.TABLEName)
            .build();
    public static final Uri CONTENT_TV = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(MyTMDB.TvColumns.TABLEName)
            .build();
    public static abstract class MovieColumns implements BaseColumns{
        public static final String TABLEName = "table_movie";
        public static final String idMovie = "id_movie";
        public static final String titleMovie = "title_movie";
        public static final String posterMovie = "poster_movie";
        public static final String backdropMovie = "backdrop_movie";
        public static  final String overviewMovie = "overview_movie";
        public static  final String releaseMovie = "release_movie";
        public static  final String averageMovie = "average_movie";
        public static  final String countMovie = "count_movie";
        public static  final String popularityMovie = "popularity_movie";


    }
    public static abstract class TvColumns implements BaseColumns{
        public static final String TABLEName = "table_tv";
        public static final String IDTv = "id_tv";
        public static final String TitleTv = "name_tv";
        public static final String PosterTv = "poster_tv";
        public static final String BackdropTv = "backdrop_tv";
        public static  final String OverviewTv = "overview_tv";
        public static  final String FirstAiring = "first_airing";
        public static  final String AverageTv = "average_tv";
        public static  final String CountTv = "count_tv";
        public static  final String PopularityTv = "popularity_tv";

    }
    private static final String DBName = "db_the_movie";
    private static final int DBVersion = 1;
    private static final String SQL_CREATE_TABLE_MOVIE =
            String.format("CREATE TABLE %s(%s TEXT PRIMARY KEY, %s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL,%s TEXT NOT NULL,%s REAL NOT NULL,%s REAL NOT NULL,%s REAL NOT NULL)", MovieColumns.TABLEName, MovieColumns.idMovie, MovieColumns.titleMovie, MovieColumns.posterMovie, MovieColumns.backdropMovie, MovieColumns.overviewMovie, MovieColumns.releaseMovie, MovieColumns.averageMovie, MovieColumns.countMovie, MovieColumns.popularityMovie);

    private static final String SQL_CREATE_TABLE_TV =
            String.format("CREATE TABLE %s(%s TEXT PRIMARY KEY, %s TEXT NOT NULL ,%s TEXT NOT NULL ,%s TEXT NOT NULL ,%s TEXT NOT NULL ,%s TEXT NOT NULL,%s REAL NOT NULL,%s REAL NOT NULL,%s REAL NOT NULL)", TvColumns.TABLEName, TvColumns.IDTv, TvColumns.TitleTv, TvColumns.PosterTv, TvColumns.BackdropTv, TvColumns.OverviewTv, TvColumns.FirstAiring, TvColumns.AverageTv, TvColumns.CountTv, TvColumns.PopularityTv);

    private static final String SQL_DELETE_TABLE_MOVIE = String.format("DROP TABLE IF EXISTS %s", MovieColumns.TABLEName);
    private static final String SQL_DELETE_TABLE_TV = String.format("DROP TABLE IF EXISTS %s", TvColumns.TABLEName);

    public MyTMDB(Context context){
        super(context,DBName,null,DBVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE_TV);
        onCreate(sqLiteDatabase);
    }
}
