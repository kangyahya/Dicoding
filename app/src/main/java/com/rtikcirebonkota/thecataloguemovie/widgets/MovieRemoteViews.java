package com.rtikcirebonkota.thecataloguemovie.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.rtikcirebonkota.thecataloguemovie.R;
import com.rtikcirebonkota.thecataloguemovie.database.MyTMDB;
import com.rtikcirebonkota.thecataloguemovie.model.MovieResult;
import com.rtikcirebonkota.thecataloguemovie.utils.Constanta;

import java.util.concurrent.ExecutionException;

public class MovieRemoteViews implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private int mAppWidgetId;
    private Cursor cursor;

    public MovieRemoteViews(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    private MovieResult getFav(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new MovieResult(
                cursor.getLong(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.idMovie)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.titleMovie)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.posterMovie)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.backdropMovie)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.releaseMovie)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.averageMovie)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.overviewMovie)),
                cursor.getLong(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.countMovie)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.popularityMovie))
        );
    }

    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(
                MyTMDB.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(
                MyTMDB.CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        MovieResult movieFavorite = getFav(i);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.movie_widget_item);

        Log.d("Widgetku",movieFavorite.getTitle());

        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(Constanta.URL_BACKDROP_WIDGET+movieFavorite.getBackdropPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            rv.setImageViewBitmap(R.id.img_widget,bmp);
            rv.setTextViewText(R.id.tv_movie_title, movieFavorite.getTitle());

        }catch (InterruptedException | ExecutionException e){
            Log.d("Widget Load Error","error"+e);
        }
        Bundle extras = new Bundle();
        extras.putInt(MovieWidget.EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.img_widget, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return cursor.moveToPosition(i) ? cursor.getLong(0) : i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
