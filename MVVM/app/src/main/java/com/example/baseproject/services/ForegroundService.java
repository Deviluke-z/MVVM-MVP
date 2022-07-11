/*
package com.example.baseproject.services;

import static com.example.baseproject.services.CreateNotification.NOTIFICATION_CHANNEL_ID;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.baseproject.R;

// have not finished yet
public class OnForegroundService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotification();

        return START_NOT_STICKY;
    }

    private void createNotification() {
        RemoteViews remoteViews = new
                RemoteViews(getPackageName(), R.layout.foreground_service_layout);

        Notification mNotification = new
                NotificationCompat.Builder(getApplication(), NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setCustomContentView(remoteViews)
                .setCustomBigContentView(remoteViews)
                .build();
        startForeground(1, mNotification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
*/
