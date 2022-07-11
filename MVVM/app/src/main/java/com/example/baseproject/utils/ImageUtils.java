package com.example.baseproject.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class ImageUtils {
    @BindingAdapter("icon")
    public static void setAppIcon(ImageView mAppIcon, Drawable drawable) {
        mAppIcon.setImageDrawable(drawable);
    }
}
