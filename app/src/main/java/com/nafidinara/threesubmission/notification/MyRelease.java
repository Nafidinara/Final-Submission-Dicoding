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
import com.nafidinara.threesubmission.connection.ApiService;
import com.nafidinara.threesubmission.connection.RetroConfig;
import com.nafidinara.threesubmission.model.Movie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRelease extends BroadcastReceiver {
    private String getDateToday(){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }
    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String api = RetroConfig.getApiKey();
        ApiService service = RetroConfig.getRetrofit().create(ApiService.class);
        Call<Movie> movieCall = service.getRelease(api,getDateToday(),getDateToday());
        //call api release
        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                assert response.body() != null;
                ArrayList<Movie> movies = response.body().getResults();
                for (int i = 0; i < movies.size();i++){
                    Log.d("test",movies.get(i).getTitle());
                    Intent release = new Intent(context, MainActivity.class);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,111,release,PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationManager daily_notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder daily_builder = new NotificationCompat.Builder(context,movies.get(i).getTitle())
                            .setContentIntent(pendingIntent)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                            .setContentTitle(context.getResources().getString(R.string.app_name))
                            .setContentText(movies.get(i).getTitle())
                            .setAutoCancel(true);
                    Notification notification = daily_builder.build();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        NotificationChannel channel = new NotificationChannel(movies.get(i).getTitle(),movies.get(i).getTitle(), NotificationManager.IMPORTANCE_DEFAULT);
                        daily_builder.setChannelId(movies.get(i).getTitle());

                        if (daily_notificationManager != null) {
                            daily_notificationManager.createNotificationChannel(channel);
                        }
                    }
                    if (daily_notificationManager !=null){
                        daily_notificationManager.notify(i,notification);
                    }
                }


            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }
}
