package com.iqinbao.android.songstv.utils;


import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;


/**
 * Created by Administrator on 2015/8/28.
 */
public class ZHttp {
    private static OkHttpClient mOkHttpClient;

    public static OkHttpClient getHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (ZHttp.class) {
                OkHttpClient builder = new OkHttpClient();
                builder.setConnectTimeout(10, TimeUnit.SECONDS);
                builder.setWriteTimeout(10, TimeUnit.SECONDS);
                builder.setReadTimeout(30, TimeUnit.SECONDS);
                builder.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
                mOkHttpClient = builder;
            }
        }

        return mOkHttpClient;
    }

    public static Response execute(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = getHttpClient().newCall(request).execute();

            if (response.isSuccessful())
                return response;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void AysncExec(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        getHttpClient().newCall(request).enqueue(callback);
    }

    /**
     * 通过get请求，获取json实例
     *
     * @param urlStr 请求地址
     */
    public static String getString(String urlStr) {
        try {
            Response response = execute(urlStr);

            if (response == null)
                return null;
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] getBytes(String url) {
        try {
            Response response = execute(url);

            if (null == response)
                return null;

            return response.body().bytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

