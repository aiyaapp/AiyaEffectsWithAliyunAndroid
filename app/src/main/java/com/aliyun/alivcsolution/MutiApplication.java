/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */

package com.aliyun.alivcsolution;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.VcPlayerLog;
import com.aliyun.common.crash.CrashHandler;
import com.aliyun.common.httpfinal.QupaiHttpFinal;
import com.aliyun.downloader.DownloaderManager;
import com.aliyun.vodplayer.downloader.AliyunDownloadConfig;
import com.aliyun.vodplayer.downloader.AliyunDownloadManager;
/**
 * Created by Mulberry on 2018/2/24.
 */
public class MutiApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        loadLibs();
        QupaiHttpFinal.getInstance().initOkHttpFinal();
        com.aliyun.vod.common.httpfinal.QupaiHttpFinal.getInstance().initOkHttpFinal();
        //Logger.setDebug(true);
        initDownLoader();
        //        localCrashHandler();
        //        new NativeCrashHandler().registerForNativeCrash(this);


        VcPlayerLog.enableLog();
        initLeakcanary();//初始化内存检测

        //初始化播放器
        AliVcMediaPlayer.init(getApplicationContext());

        //设置保存密码。此密码如果更换，则之前保存的视频无法播放
        AliyunDownloadConfig config = new AliyunDownloadConfig();
        config.setSecretImagePath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/aliyun/encryptedApp.dat");
        //        config.setDownloadPassword("123456789");
        //设置保存路径。请确保有SD卡访问权限。
        config.setDownloadDir(Environment.getExternalStorageDirectory().getAbsolutePath()+"/test_save/");
        //设置同时下载个数
        config.setMaxNums(2);

        AliyunDownloadManager.getInstance(this).setDownloadConfig(config);

    }

    private void loadLibs(){
        System.loadLibrary("live-openh264");
        System.loadLibrary("QuCore-ThirdParty");
        System.loadLibrary("QuCore");
        System.loadLibrary("FaceAREngine");
        System.loadLibrary("AliFaceAREngine");
    }

    private void initDownLoader() {
        DownloaderManager.getInstance().init(this);
    }


    private void localCrashHandler() {
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
    }

    private void initLeakcanary() {
        //LeakCanary.install(this);
    }

}
