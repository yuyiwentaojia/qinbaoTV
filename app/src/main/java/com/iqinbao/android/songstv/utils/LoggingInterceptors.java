package com.iqinbao.android.songstv.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/4.
 */
public class LoggingInterceptors implements Interceptor {
    int i=0;
    public static JSONObject jsonObject=new JSONObject();
    static String playurl="";
    public static String serviceUrl="";
    public static String videosize="";
    public static JSONObject getdata(){
        return  jsonObject;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        long t1 = System.nanoTime();
        Request request = chain.request();
        playurl= String.valueOf(request.url());
        i=i+1;
        LogUtils.w("=====",String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection()+"测试数据", request.headers()));
        serviceUrl= String.valueOf(chain.connection().socket());
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        LogUtils.w("=====",String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
//        playurl= String.valueOf(response.request().url());
        videosize= response.header("Content-Length");
        try {
            jsonObject.put("videosize",videosize);
            jsonObject.put("service",serviceUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}
