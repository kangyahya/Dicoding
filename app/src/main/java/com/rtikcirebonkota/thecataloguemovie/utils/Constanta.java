package com.rtikcirebonkota.thecataloguemovie.utils;
import android.annotation.SuppressLint;

import com.rtikcirebonkota.thecataloguemovie.BuildConfig;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constanta {
    public static String API_KEY = BuildConfig.TMDB_API_KEY;
    public final static String URL_POSTER = "http://image.tmdb.org/t/p/w185";
    public final static String URL_BACKDROP = "http://image.tmdb.org/t/p/w780";
    public final static String URL_BACKDROP_WIDGET = "http://image.tmdb.org/t/p/w500";
    public final static String DETAIL_MOVIE = "detail_movie";
    public final static String DETAIL_TV = "detail_tv";
    private final static String DATE_FORMAT_DAY = "EEEE, dd - MM - yyyy";
    public static final String INTENT_SEARCH = "intent_search";
    public static final String INTENT_TAG = "tag";
    public static final String INTENT_DETAIL = "detail";
    public final static int NOTIFICATION_ID = 1001;
    public final static String NOTIFICATION_CHANNEL_ID = "11001";
    public final static String NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_NAME";
    public final static int REQUEST_CODE_DAILY = 13;


    private static String format(String date) {
        String result = "";

        @SuppressLint("SimpleDateFormat") DateFormat old = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date oldDate = old.parse(date);
            @SuppressLint("SimpleDateFormat") DateFormat newFormat = new SimpleDateFormat(Constanta.DATE_FORMAT_DAY);
            result = newFormat.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String getDateDay(String date) {
        return format(date);
    }
}
