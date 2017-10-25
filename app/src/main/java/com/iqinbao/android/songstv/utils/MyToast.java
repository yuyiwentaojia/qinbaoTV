package com.iqinbao.android.songstv.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by lli on 2016/11/17.
 */
public class MyToast  {
    private static boolean isFirstTime=true;
    private static Toast mToast;
    private static Handler mHandle=new Handler();
    private static Runnable r=new Runnable() {
        @Override
        public void run() {
            try {
                mToast.cancel();
            }catch (NullPointerException e){
            }
        }
    };
    public  static void showToast(Context context,String text,int duration){
        if (isFirstTime==false){
            mHandle.removeCallbacks(r);
        }
        if (mToast==null){
            mToast=Toast.makeText(context,text,duration);
        }else {
            mToast.setText(text);
        }
        mHandle.postDelayed(r,1000);
        mToast.show();
    }
}
