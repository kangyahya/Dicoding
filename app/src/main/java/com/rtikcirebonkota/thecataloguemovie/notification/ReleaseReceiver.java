package com.rtikcirebonkota.thecataloguemovie.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.rtikcirebonkota.thecataloguemovie.DetailMovieActivity;
import com.rtikcirebonkota.thecataloguemovie.MainActivity;
import com.rtikcirebonkota.thecataloguemovie.R;
import com.rtikcirebonkota.thecataloguemovie.api.BaseApiService;
import com.rtikcirebonkota.thecataloguemovie.api.RetrofitClient;
import com.rtikcirebonkota.thecataloguemovie.model.MovieResult;
import com.rtikcirebonkota.thecataloguemovie.model.ResponseMovie;
import com.rtikcirebonkota.thecataloguemovie.utils.Constanta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rtikcirebonkota.thecataloguemovie.utils.Constanta.API_KEY;
import static com.rtikcirebonkota.thecataloguemovie.utils.Constanta.NOTIFICATION_CHANNEL_ID;

public class ReleaseReceiver extends BroadcastReceiver{
    private final int REQUEST_CODE_RELEASE = 13;
    private List<MovieResult> movieResultArrayList = new ArrayList<>(), movie;

    @Override
    public void onReceive(Context context, Intent intent) {
        getReleaseToday(context);
    }
    private void getReleaseToday(Context context){
        BaseApiService apiService = RetrofitClient.getClient().create(BaseApiService.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = Calendar.getInstance().getTime();
        final String now = dateFormat.format(date);
        Call<ResponseMovie> movieCall = apiService.getUpcoming(API_KEY,now,now);
        movieCall.enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        movie = response.body().getResults();
                        movieResultArrayList.addAll(movie);
                    }
                    releaseTodayNotification(context);
                }
            }
            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {

            }
        });
    }
    private void releaseTodayNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_notifications_black_24dp);



        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            if(notificationManager!=null){
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Intent intent;
        PendingIntent pendingIntent;

        int numMovie = 0;
        try {
            numMovie =((movieResultArrayList.size()>0)? movieResultArrayList.size() :0 );
        }catch (Exception e){
            e.printStackTrace();
        }
        String msg ="";
        if (numMovie==0){
            msg = context.getString(R.string.no_movie_release);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = TaskStackBuilder.create(context)
                    .addNextIntent(intent)
                    .getPendingIntent(REQUEST_CODE_RELEASE, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle(context.getString(R.string.release_today_reminder))
                    .setContentText(msg)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setLargeIcon(largeIcon)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(uriTone)
                    .setAutoCancel(true);
            if (notificationManager != null){
                notificationManager.notify(0,builder.build());
            }

       } else {
            intent = new Intent(context,DetailMovieActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            Long movieId, moviecoun;
            String movieTitle, moviePoster, movieBackdrop, movieover,movierelease ;
            Double movieavg,moviepop;
            for (int i =0;i<numMovie;i++){
                msg = movieResultArrayList.get(i).getTitle() + " " + context.getString(R.string.released_message);
                movieId = movieResultArrayList.get(i).getId();
                movieTitle = movieResultArrayList.get(i).getTitle();
                moviePoster = movieResultArrayList.get(i).getPosterPath();
                movieBackdrop = movieResultArrayList.get(i).getBackdropPath();
                movieover = movieResultArrayList.get(i).getOverview();
                movierelease = movieResultArrayList.get(i).getReleaseDate();
                movieavg = movieResultArrayList.get(i).getVoteAverage();
                moviecoun = movieResultArrayList.get(i).getVoteCount();
                moviepop = movieResultArrayList.get(i).getPopularity();
                MovieResult movie = new MovieResult(movieId,movieTitle,moviePoster,movieBackdrop,movierelease,movieavg,movieover,moviecoun,moviepop);
                intent.putExtra(Constanta.DETAIL_MOVIE, movie);
                pendingIntent = TaskStackBuilder.create(context).addNextIntent(intent).getPendingIntent(i,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentTitle(context.getString(R.string.release_today_reminder))
                        .setContentText(msg)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setSound(uriTone)
                        .setAutoCancel(true);
                if (notificationManager!=null){
                    notificationManager.notify(i,builder.build());
                }
            }
        }
    }
    public void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReceiver.class);
        MovieResult movie = new MovieResult();
        intent.putExtra("movietitle", movie.getTitle());
        intent.putExtra("movieid", movie.getId());
        intent.putExtra("movieposter", movie.getPosterPath());
        intent.putExtra("movieback", movie.getBackdropPath());
        intent.putExtra("moviedate", movie.getReleaseDate());
        intent.putExtra("movierating", movie.getVoteAverage());
        intent.putExtra("movieover", movie.getOverview());
        intent.putExtra("id", 100);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,REQUEST_CODE_RELEASE, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager!=null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,REQUEST_CODE_RELEASE,intent,0);
        if (alarmManager!=null){
            alarmManager.cancel(pendingIntent);
        }

    }

}
