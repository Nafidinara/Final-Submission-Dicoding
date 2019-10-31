package com.nafidinara.threesubmission.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.nafidinara.threesubmission.R;
import com.nafidinara.threesubmission.activity.MainActivity;

public class MyDaily extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        Intent daily_intent1 = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,100,daily_intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("daily","daily");

        NotificationManager daily_notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder daily_builder = new NotificationCompat.Builder(context,"0")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.notif))
                .setAutoCancel(true);
        Notification notification = daily_builder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("100", "100", NotificationManager.IMPORTANCE_DEFAULT);
            daily_builder.setChannelId("100");

            if (daily_notificationManager != null) {
                daily_notificationManager.createNotificationChannel(channel);
            }
        }
        if (daily_notificationManager !=null){
            daily_notificationManager.notify(100,notification);
        }
    }
}
