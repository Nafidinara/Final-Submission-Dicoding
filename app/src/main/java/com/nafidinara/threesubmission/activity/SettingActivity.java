package com.nafidinara.threesubmission.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.nafidinara.threesubmission.R;
import com.nafidinara.threesubmission.notification.MyDaily;
import com.nafidinara.threesubmission.notification.MyRelease;

import java.util.Calendar;
import java.util.Objects;

public class SettingActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    Switch daily , release;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        daily = findViewById(R.id.daily);
        release = findViewById(R.id.release_switch);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int dailyy = sharedPref.getInt("daily_notification",0);
        int releasee = sharedPref.getInt("RELEASE_notification",0);
        if (dailyy  ==1) {
            daily.setChecked(true);
        }else {
            daily.setChecked(false);
        }
        if (releasee ==1){
            release.setChecked(true);
        }else {
            release.setChecked(false);
        }
        switchHandling();

    }

    private void switchHandling() {
        daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setDaily(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("daily_notification",1);
                    editor.commit();

                }else {
                    dailyOff(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("daily_notification",0);
                    editor.commit();

                }
            }
        });

        release.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("release_notification",1);
                    editor.commit();
                    setRelease(getApplicationContext());
                }else {
                    releaseOff(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("release_notification",0);
                    editor.commit();
                }
            }
        });
    }

    public void setDaily(Context context){
        Intent intent = new Intent(context,MyDaily.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,7);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d("daily","daily");
        if (alarmManager !=null){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        }
    }

    public void dailyOff(Context context){
        Log.d("daily","daily");

        Intent intent = new Intent(context, MyDaily.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);
    }

    public void setRelease(Context context){
        Intent intent = new Intent(context, MyRelease.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,111,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager !=null){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        }
    }

    public void releaseOff(Context context){
        Intent intent = new Intent(context,MyRelease.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,111,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);
    }
}
