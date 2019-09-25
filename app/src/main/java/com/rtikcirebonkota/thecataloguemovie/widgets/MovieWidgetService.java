package com.rtikcirebonkota.thecataloguemovie.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MovieWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MovieRemoteViews(this.getApplicationContext(), intent);
    }
    public void onCreate() {
        super.onCreate();
    }
}
