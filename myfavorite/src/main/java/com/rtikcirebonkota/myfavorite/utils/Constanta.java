package com.rtikcirebonkota.myfavorite.utils;

import android.annotation.SuppressLint;

import com.rtikcirebonkota.myfavorite.BuildConfig;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constanta {
    public final static String URL_POSTER = "http://image.tmdb.org/t/p/w185";
    public final static String URL_BACKDROP = "http://image.tmdb.org/t/p/w780";
    public final static String DETAIL_MOVIE = "detail_movie";
    public final static String DETAIL_FMOVIE = "detail_fmovie";
    public final static String DETAIL_TV = "detail_tv";
    public final static String DETAIL_MOVIE_ID = "detail_movie_id";
    public final static String DETAIL_MOVIE_TITLE = "detail_movie_title";
    public final static String DETAIL_MOVIE_POSTER = "detail_movie_poster";
    public final static String DETAIL_MOVIE_OVERVIEW = "detail_movie_overview";
    public final static String DETAIL_MOVIE_RELEASE = "detail_movie_release";
    public final static String DETAIL_MOVIE_COUNT = "detail_movie_count";
    public final static String DETAIL_MOVIE_BACKDROP = "detail_movie_backdrop";
    public final static String DETAIL_MOVIE_AVERAGE = "detail_movie_average";
    private final static String DATE_FORMAT = "dd MMMM yyyy";
    private final static String DATE_FORMAT_DAY = "EEEE, dd - MM - yyyy";
    public static String API_KEY = BuildConfig.TMDB_API_KEY;

    private static String format(String date, String format) {
        String result = "";

        @SuppressLint("SimpleDateFormat") DateFormat old = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date oldDate = old.parse(date);
            @SuppressLint("SimpleDateFormat") DateFormat newFormat = new SimpleDateFormat(format);
            result = newFormat.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getDate(String date) {
        return format(date, DATE_FORMAT);
    }

    public static String getDateDay(String date) {
        return format(date, DATE_FORMAT_DAY);
    }
}
