package com.example.baseproject.viewmodel;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.ArrayMap;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.example.baseproject.model.AppModel;
import com.example.baseproject.utils.NewUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppUsageViewModel extends BaseObservable {

    private final MutableLiveData<List<AppModel>> mMutableListAppModel;
    private final Context mContext;
    ArrayMap<String, UsageStats> usageMap = new ArrayMap<>();

    public AppUsageViewModel(Context mContext) {
        this.mContext = mContext;
        mMutableListAppModel = new MutableLiveData<>();
        showUsage();
    }

    public MutableLiveData<List<AppModel>> getMutableAppList() {
        return mMutableListAppModel;
    }

    private void showUsage() {
        // start new thread for getting app usage
        HandlerThread handlerThread = new HandlerThread("getAppUsageThread");
        handlerThread.start();

        // check if app usage thread isAlive
        Handler handler = new Handler(handlerThread.getLooper());
        // runnable on the thread that handler is attached
        handler.post(() -> {
            List<AppModel> mListAppModel = new ArrayList<>();

            UsageStatsManager usageStatsManager = (UsageStatsManager)
                    mContext.getSystemService(Context.USAGE_STATS_SERVICE);

            long currentTime = System.currentTimeMillis();
            // a week
            long startTime = (long) currentTime - 604800000;

            List<UsageStats> listUsageStats = usageStatsManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_WEEKLY, startTime, currentTime);

            for (UsageStats usageStats : listUsageStats) {

                if (!isAppAvailable(usageStats)) {
                    continue;
                }

                UsageStats mExistingUsageStat = usageMap.get(usageStats.getPackageName());
                // check if the package name is already in list or not?
                if (mExistingUsageStat == null) {
                    usageMap.put(usageStats.getPackageName(), usageStats);
                } else {
                    mExistingUsageStat.add(usageStats);
                }
            }

            // re-build listUsageStats
            listUsageStats.clear();
            listUsageStats.addAll(usageMap.values());

            for (UsageStats usageStats : listUsageStats) {

                AppModel mAppModel = new AppModel();

                mAppModel.setAppName(new NewUtils().getAppName(
                        mContext,
                        usageStats.getPackageName()
                ));

                mAppModel.setAppIcon(new NewUtils().getAppIconByPackageName(
                        mContext,
                        usageStats.getPackageName()));
                mAppModel.setAppUsage(usageStats.getTotalTimeInForeground());

                // Log for debug
                /*Log.d("ViewModel", "Time in milliseconds: " + usageStats.getTotalTimeInForeground() + ", App: " + usageStats.getPackageName());*/

                if (mAppModel.getAppUsage() != 0) {
                    mListAppModel.add(mAppModel);
                }
            }

            // rearrange app usage list (max duration on top)
            // so i don't need to take the most app usage duration out xD
            Collections.sort(mListAppModel, (z1, z2) ->
                    Long.compare(z2.getAppUsage(), z1.getAppUsage()));

            Handler mainHandler = new Handler(mContext.getMainLooper());
            mainHandler.post(() -> mMutableListAppModel.setValue(mListAppModel));
        });
    }

    // check if application is available through package name
    private boolean isAppAvailable(UsageStats usageStats) {
        try {
            mContext.getPackageManager().getApplicationInfo(usageStats.getPackageName(), 0);
            return true;
        } catch (PackageManager.NameNotFoundException exception) {
            return false;
        }
    }
}
