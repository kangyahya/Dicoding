package com.rtikcirebonkota.thecataloguemovie.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;

import com.rtikcirebonkota.thecataloguemovie.MainActivity;
import com.rtikcirebonkota.thecataloguemovie.R;

import java.util.Calendar;

import static com.rtikcirebonkota.thecataloguemovie.utils.Constanta.NOTIFICATION_CHANNEL_ID;
import static com.rtikcirebonkota.thecataloguemovie.utils.Constanta.NOTIFICATION_CHANNEL_NAME;
import static com.rtikcirebonkota.thecataloguemovie.utils.Constanta.NOTIFICATION_ID;
import static com.rtikcirebonkota.thecataloguemovie.utils.Constanta.REQUEST_CODE_DAILY;

public class DailyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        dailyNotification(context, context.getString(R.string.app_name));
    }
    private void dailyNotification(Context context, String title) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent pendingIntent = TaskStackBuilder.create(context).addNextIntent(intent).getPendingIntent(REQUEST_CODE_DAILY, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setContentTitle(title)
                .setContentText(context.getString(R.string.message_daily))
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(uriTone)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{1000,1000,1000,1000,1000});
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            if(notificationManager!=null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        if(notificationManager!=null) {
            notificationManager.notify(REQUEST_CODE_DAILY, builder.build());
        }

    }
    public void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReceiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent =PendingIntent.getBroadcast(context,REQUEST_CODE_DAILY,intent,0);

        if(alarmManager!=null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,DailyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_DAILY, intent,0);
        alarmManager.cancel(pendingIntent);
    }

}
