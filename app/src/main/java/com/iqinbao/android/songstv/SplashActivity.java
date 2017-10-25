package com.iqinbao.android.songstv;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.iqinbao.android.songstv.R;
import com.iqinbao.android.songstv.base.BaseFragmentActivity;
import com.iqinbao.android.songstv.beanstv.ClientVersion;
import com.iqinbao.android.songstv.db01.SQLOperateImpl;
import com.iqinbao.android.songstv.music.Music;
import com.iqinbao.android.songstv.utils.AppInfo;
import com.squareup.picasso.Picasso;

/**
 * 启动页面·16.9.5  fuhao
 */
public class SplashActivity extends BaseFragmentActivity {
    //启动默认图片
    private ImageView imageView_default_startover;
    private SQLOperateImpl sqlOperate;
    //延迟4秒
    private static final long SPLASH_DELAY_MILLIS = 3000;
    //广告默认地址
    private  String imageurl ="";
    String sid="";
    private int playUrl;
    private int sign=0;
    private int conid=0;
    private SQLOperateImpl sqlOperateimpl;
    int version=1;
    private ClientVersion clientVersion;
    private  String adsurl ="";
    private int  width=1920;
    private int height=1080;

    private String channelName="";
    private Bitmap bitmap=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        WindowManager wm01 = this.getWindowManager();
        width  = wm01.getDefaultDisplay().getWidth();
        height = wm01.getDefaultDisplay().getHeight();
        if (width>1920 ||height>1080){
            width=1920;
            height=1080;
        }
        findData();
        findViews();
        setViews();
        setListeners();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            sid=bundle.getString("sid");
            Intent intent01 = new Intent(SplashActivity.this, MainActivity.class);
            intent01.putExtra("sid",sid);
            SplashActivity.this.startActivity(intent01);
            SplashActivity.this.finish();
        }else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
                    goHome();
                }
            }, SPLASH_DELAY_MILLIS);
        }
    }

    private void findData() {
        if (sqlOperate==null){
            sqlOperate=new SQLOperateImpl(getApplicationContext());
        }
        try {
            clientVersion = sqlOperate.queryForAdvertisement();
        }catch (Exception e){
        }
        if (clientVersion!=null){
            try {
                adsurl=clientVersion.getAds_kaiping();
            }catch (Exception e){
            }

        }
    }

    @Override
    protected void findViews() {
        imageView_default_startover = (ImageView) findViewById(R.id.imageView_default_startover);

    }

    @Override
    protected void setViews() {
        //当贝渠道：启动页需要加载上当被市场的loge
        channelName= AppInfo.getChannelName(getApplication());
        if (adsurl!=null && adsurl.length()!=0){
            Picasso.with(getApplicationContext()).load(adsurl).
                    error(R.drawable.qidong).resize(width,height).
                    into(imageView_default_startover);
        }else{
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                if (channelName.equals("tv10")){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qidong_dangbei, options);
                }else {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qidong, options);
                }
                imageView_default_startover.setImageBitmap(bitmap);
            }catch (OutOfMemoryError e){
            }
        }
        Music.play(this,R.raw.start);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
    }

    /**
     * 跳转从启动页面跳转到主页面 16.9.5.
     */
    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Music.pause(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Music.play(this, R.raw.start);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            Bitmap bitmap1 = ((BitmapDrawable) imageView_default_startover.getDrawable()).getBitmap();
            if (bitmap1 != null && !bitmap1.isRecycled()) {
                bitmap1.recycle();
                bitmap1 = null;
            }
            System.gc();
        }catch(Exception e){

        }

    }
}
