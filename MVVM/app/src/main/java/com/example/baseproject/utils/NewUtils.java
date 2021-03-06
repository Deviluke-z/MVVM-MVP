package com.example.baseproject.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.baseproject.R;

public class NewUtils {
    public Drawable getAppIconByPackageName(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return AppCompatResources.getDrawable(context, R.drawable.ic_launcher_foreground);
    }

    public String getAppName(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
        } catch (final PackageManager.NameNotFoundException exception) {
            exception.printStackTrace();
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "Unknown");
    }
}
