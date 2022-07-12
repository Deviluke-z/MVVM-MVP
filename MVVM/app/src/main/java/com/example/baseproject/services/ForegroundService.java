package com.example.baseproject.services;

import static com.example.baseproject.services.CreateNotification.NOTIFICATION_CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.baseproject.R;
import com.example.baseproject.utils.NewUtils;

import java.util.List;

// have not finished yet
public class ForegroundService extends Service {

    private static final String TAG = "Foreground Service";

    private static final String ACTION_NAME = "ACTION_NAME";
    private static final int ACTION_CLOSE = 1;
    private static final int ACTION_UPDATE = 2;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");

        createNotification();

        int action = intent.getIntExtra(ACTION_NAME, -1);
        handleAction(action);

        return START_NOT_STICKY;
    }

    private void handleAction(int action) {
        switch (action) {
            case ACTION_CLOSE:
                stopSelf();
                stopForeground(true);
                Log.d(TAG, "Stop Service");
                break;
            case ACTION_UPDATE:
                updateNotification();
                break;
        }
    }

    private void updateNotification() {
        UsageStatsManager usageStatsManager =
                (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);

        long currentTime = System.currentTimeMillis();
        long startTime = (long) currentTime - 604800000;

        List<UsageStats> listUsageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_WEEKLY, startTime, currentTime);

        UsageStats recentUse = null;
        for (UsageStats usageStats : listUsageStats) {
            if (recentUse == null || recentUse.getLastTimeUsed() < usageStats.getLastTimeUsed()) {
                recentUse = usageStats;
            }
        }

        RemoteViews remoteViews = new
                RemoteViews(getPackageName(), R.layout.foreground_service_layout);

        if (recentUse != null && recentUse.getPackageName() != null) {
            remoteViews.setTextViewText(
                    R.id.tvAppOnForeground,
                    new NewUtils().getAppName(getApplicationContext(), recentUse.getPackageName()));
            Log.d(TAG, "set text tvAppOnForeground " +
                    new NewUtils().getAppName(getApplicationContext(), recentUse.getPackageName()));
        }

        // not working
        /*createNotification();*/

        remoteViews.setOnClickPendingIntent(R.id.btnClose, getPendingIntent(ACTION_CLOSE));
        remoteViews.setOnClickPendingIntent(R.id.btnUpdate, getPendingIntent(ACTION_UPDATE));

        Notification notification = new
                NotificationCompat.Builder(getApplication(), NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setCustomBigContentView(remoteViews)
                .setCustomContentView(remoteViews)
                .build();

        NotificationManager notificationManager =
                (NotificationManager)
                        getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(1, notification);
        Log.d(TAG, "Update Notification");
    }

    private void createNotification() {
        RemoteViews remoteViews = new
                RemoteViews(getPackageName(), R.layout.foreground_service_layout);

        remoteViews.setOnClickPendingIntent(R.id.btnClose, getPendingIntent(ACTION_CLOSE));
        remoteViews.setOnClickPendingIntent(R.id.btnUpdate, getPendingIntent(ACTION_UPDATE));

        Notification notification = new
                NotificationCompat.Builder(getApplication(), NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setCustomContentView(remoteViews)
                .setCustomBigContentView(remoteViews)
                .build();

        startForeground(1, notification);
    }

    private PendingIntent getPendingIntent(int action) {
        Intent intent = new Intent(getApplicationContext(), ForegroundService.class);
        intent.putExtra(ACTION_NAME, action);
        return PendingIntent.getService(getApplicationContext(), action, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
