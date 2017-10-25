package com.iqinbao.android.songstv.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by lli on 2017/1/22. 得到渠道的渠道名称
 */
public class AppInfo {
    public static String getChannelName(Context ctx) {
        if (ctx == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                /**注意此处为ApplicationInfo
                 * 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，
                 * 而不是某activity标签中，所以用ApplicationInfo
                 */
                ApplicationInfo applicationInfo =
                        packageManager.getApplicationInfo
                                (ctx.getPackageName(),
                                        PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.getString("UMENG_CHANNEL");
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }
}
