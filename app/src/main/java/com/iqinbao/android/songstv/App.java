package com.iqinbao.android.songstv;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.iqinbao.android.songstv.videocache.HttpProxyCacheServer;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public class App extends Application {
    private List<Activity> list=new LinkedList<Activity>();
    private HttpProxyCacheServer proxy;
    private static Context mContext;
    public static HttpProxyCacheServer getProxy(Context context) {
        App app = (App) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
//        File cacheRoot = StorageUtils.getIndividualCacheDirectory(this);
//        return new HttpProxyCacheServer(this,cacheRoot);
        return new HttpProxyCacheServer(this);
    }

    public static Context getContext(){
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        CrashReport.initCrashReport(getApplicationContext());
    }
}
