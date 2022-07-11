package com.example.baseproject.model;

import android.graphics.drawable.Drawable;

public class AppModel {

    private String mAppName;
    private Drawable mAppIcon;
    private long mAppUsage;
    private String mAppUsageString;

    public AppModel() {
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String mAppName) {
        this.mAppName = mAppName;
    }

    public long getAppUsage() {
        return mAppUsage;
    }

    public void setAppUsage(long mAppUsage) {
        this.mAppUsage = mAppUsage;
    }

    public Drawable getAppIcon() {
        return mAppIcon;
    }

    public void setAppIcon(Drawable mAppIcon) {
        this.mAppIcon = mAppIcon;
    }

    public void getTotalUsedTime() {
        String finalTimerString = "";
        String secondsString;
        int hours = (int) (mAppUsage / (1000 * 60 * 60));
        int minutes = (int) (mAppUsage % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((mAppUsage % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hours > 0) {
            finalTimerString = hours + " hours ";
        }
        secondsString = seconds + " seconds";
        finalTimerString = finalTimerString + minutes + " minutes " + secondsString;
        mAppUsageString = finalTimerString;
    }

    public String getAppUsageString() {
        getTotalUsedTime();
        return mAppUsageString;
    }

    public void setAppUsageString(String mAppUsageString) {
        this.mAppUsageString = mAppUsageString;
    }
}
