package com.rtikcirebonkota.thecataloguemovie.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class MovieHelper {

    private static String DATABASE_TABLE = MyTMDB.MovieColumns.TABLEName;
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private static MovieHelper INSTANCE;
    private SQLiteDatabase sqliteDatabase;
    public MovieHelper(Context context){
        this.context = context;
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }
    public void open() throws SQLException{
        MyTMDB myTMDB = new MyTMDB(context);
        sqliteDatabase = myTMDB.getWritableDatabase();
    }

    public void close(){
        sqliteDatabase.close();
    }

    public Cursor queryProvider() {
        return sqliteDatabase.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                MyTMDB.MovieColumns.idMovie+ " DESC",
                null
        );
    }
    public Cursor queryByIdProvider(String id) {
        return sqliteDatabase.query(DATABASE_TABLE, null
                , MyTMDB.MovieColumns.idMovie + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }
    public long insertProvider(ContentValues values) {
        return sqliteDatabase.insert(DATABASE_TABLE, null, values);
    }
    public int updateProvider(String id, ContentValues values) {
        return sqliteDatabase.update(DATABASE_TABLE, values,
                MyTMDB.MovieColumns.idMovie + " = ?", new String[]{id});
    }
    public int deleteProvider(String id) {
        return sqliteDatabase.delete(DATABASE_TABLE,
                MyTMDB.MovieColumns.idMovie + " = ?", new String[]{id});
    }
}