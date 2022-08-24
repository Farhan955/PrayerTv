package com.tbum.prayertv.Alarms;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


import java.util.Calendar;

public class MyAlarmManager {

    Context context;
    AlarmManager alarmManager;

    public MyAlarmManager(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(long alarmTimeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(alarmTimeMillis);

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(alarmTimeMillis, pendingIntent), pendingIntent);
    }

    public void cancelTimer() {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.cancel(sender);
    }

}
