package com.alivc.live.pusher.demo;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import com.aiyaapp.aiya.AYLicenseManager;
import com.aiyaapp.aiya.AyCore;
import com.aiyaapp.aiya.AyFaceTrack;
import com.alivc.live.pusher.LogUtil;

import java.io.File;

//import com.squareup.leakcanary.LeakCanary;

public class LiveApplication extends Application {

    public static Context sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new ConnectivityChangedReceiver(), filter);
        if(BuildConfig.DEBUG) {
            LogUtil.enalbeDebug();
        } else {
            LogUtil.disableDebug();
        }
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);

        // ---------- 哎吖科技添加 开始 ----------
        AYLicenseManager.initLicense(getApplicationContext(), "477de67d19ba39fb656a4806c803b552", new AyCore.OnResultCallback() {
            @Override
            public void onResult(int ret) {
                Log.d("哎吖科技", "License初始化结果 : " + ret);
            }
        });

        // copy数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                String dstPath = getExternalCacheDir() + "/aiya/effect";
                if (!new File(dstPath).exists()) {
                    AyFaceTrack.deleteFile(new File(dstPath));
                    AyFaceTrack.copyFileFromAssets("modelsticker", dstPath, getAssets());
                }
            }
        }).start();
        // ---------- 哎吖科技添加 结束 ----------
    }



    class ConnectivityChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

}
