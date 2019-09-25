package com.rtikcirebonkota.thecataloguemovie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TvHelper {

    private static String DATABASE_TABLE = MyTMDB.TvColumns.TABLEName;
    private Context context;
    private SQLiteDatabase sqliteDatabase;
    public TvHelper(Context context){
        this.context = context;
    }
    public void open() throws SQLException {
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
                MyTMDB.TvColumns.IDTv+ " DESC"
        );
    }
    public Cursor queryByIdProvider(String id) {
        return sqliteDatabase.query(DATABASE_TABLE, null
                , MyTMDB.TvColumns.IDTv + " = ?"
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
                MyTMDB.TvColumns.IDTv + " = ?", new String[]{id});
    }
    public int deleteProvider(String id) {
        return sqliteDatabase.delete(DATABASE_TABLE,
                MyTMDB.TvColumns.IDTv + " = ?", new String[]{id});
    }
}
