package com.iqinbao.android.songstv.utils;


import com.google.gson.Gson;
import com.iqinbao.android.songstv.beanstv.AdsEnity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import java.net.URLDecoder;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
/**
 * 主要是从服务器中获取数据，并通过回掉传递
 * Created by admin on 2016/8/22.
 */
public class DataDownload {
    private String url="http://api.iqinbao.com/app/api/1502/api.json\n";
    private ListCallBack mListCallBack;
    private final android.os.Handler mHandler = new android.os.Handler();
    public void setListCallBack(ListCallBack listCallBack) {
        mListCallBack = listCallBack;
    }
    /**
     * 网络请求和数据下载
     */
    public void downloadData() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (url != null) {
                    OkHttpUtils
                            .get()
                            .url(url)
                            .build()
                            .execute(new Callback<AdsEnity>() {
                                @Override
                                public AdsEnity parseNetworkResponse(Response response) throws Exception {
                                    String string = response.body().string();
                                    String decode = URLDecoder.decode(string, "UTF-8");
                                    Gson gson = new Gson();
                                    AdsEnity dataQinbao = gson.fromJson(decode, AdsEnity.class);
                                    return dataQinbao;
                                }

                                @Override
                                public void onError(Call call, Exception e) {
                                    int i=0;
                                    i=i+1;
                                }

                                @Override
                                public void onResponse(Call call, AdsEnity response) {
                                    List<AdsEnity.ContentsBean> contentsBean=response.getContents();
                                    mListCallBack.getLists(contentsBean);

                                }


                            });

                }

            }
        });

    }


    /**
     * 回调，获取list集合。
     */
    public interface ListCallBack {
        void getLists(List<AdsEnity.ContentsBean> list);
    }

}
