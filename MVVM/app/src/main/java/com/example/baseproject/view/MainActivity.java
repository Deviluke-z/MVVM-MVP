package com.example.baseproject.view;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.baseproject.R;
import com.example.baseproject.databinding.ActivityMainBinding;
import com.example.baseproject.viewmodel.AppUsageViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mActivityMainBinding;
    private AppAdapter mAppAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mActivityMainBinding.btnStart.setOnClickListener(v -> checkPermission());
    }

    public void checkPermission() {
        AppOpsManager appOpsManagerCompat =
                (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOpsManagerCompat.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                getPackageName());
        boolean granted = mode == AppOpsManager.MODE_ALLOWED;

        if (granted) {
            initView();
            // Log for debug
            /*Log.d("MainActivity", "initView");*/
        } else {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
    }

    // ez binding
    private void initView() {
        mAppAdapter = new AppAdapter();
        AppUsageViewModel mViewModel = new AppUsageViewModel(this);
        mActivityMainBinding.setMainModel(mViewModel);
        mActivityMainBinding.executePendingBindings();

        mActivityMainBinding.rvAppUsage.setAdapter(mAppAdapter);
        mActivityMainBinding.rvAppUsage.setLayoutManager(new LinearLayoutManager(this));

        mViewModel.getMutableAppList().observe(
                this,
                appList -> mAppAdapter.setData(appList));
    }
}