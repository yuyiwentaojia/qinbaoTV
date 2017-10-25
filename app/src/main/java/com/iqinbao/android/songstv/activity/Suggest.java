package com.iqinbao.android.songstv.activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iqinbao.android.songstv.R;
import com.iqinbao.android.songstv.base.BaseFragmentActivity;
import com.iqinbao.android.songstv.music.Music;
import com.iqinbao.android.songstv.utils.ActivityCollector;
import com.iqinbao.android.songstv.utils.MyToast;
import com.iqinbao.android.songstv.utils.Tools;
import com.iqinbao.android.songstv.videocache.StorageUtils;
import com.umeng.analytics.MobclickAgent;

import org.evilbinary.tv.widget.BorderView;

import java.io.File;
import java.util.Map;

public class Suggest extends BaseFragmentActivity implements View.OnClickListener {
    private LinearLayout linearLayout01,linearLayout02;
    private long touchTime = 0;
    private ImageView bt_misplay,bt_carlton,bt_nosynch,bt_fuzzy,bt_inconvenient,imageView;
    private ImageView imageView_text;
    private RelativeLayout relativeLayout;
    private Context mContext;
    private BorderView borderView;
    private long waitTime=86400000;
    private int cannotplay=0;
    private int nonfluency=0;
    private int nosynch=0;
    private int inconvenient=0;
    private int fuzzy=0;
    Map<String,Integer> map01,map02,map03,map04,map05;
    private TextView textView_version;
    private boolean is_resume=false;
    private MyToast myToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        mContext = this;
        ActivityCollector.addActivity(this);
//        CloseActivity.add(this);
        myToast=new MyToast();
//        Music.key_tone(mContext);
        is_resume=true;
        borderView=new BorderView(mContext);
        borderView.setBackgroundResource(R.drawable.border_collection_white);
        findViews();
        setViews();
        setListeners();
        String versionName="版本信息:V"+getInformationAPP(mContext);
        textView_version.setText(versionName);
        MobclickAgent.setDebugMode(true);
    }
    //获取当前版本号
    private String getInformationAPP(Context context) {
            String versionName = "";
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
                versionName = packageInfo.versionName;
                if (TextUtils.isEmpty(versionName)) {
                    return "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return versionName;
    }

    protected void findViews() {
        imageView_text= (ImageView) findViewById(R.id.image_text);
        relativeLayout= (RelativeLayout) findViewById(R.id.suggest_line);
        linearLayout01= (LinearLayout) findViewById(R.id.line1);
        linearLayout01.setFocusable(false);
        borderView.attachTo(linearLayout01);
        linearLayout02= (LinearLayout) findViewById(R.id.line2);
        linearLayout02.setFocusable(false);
        borderView.attachTo(linearLayout02);
        bt_misplay= (ImageView) findViewById(R.id.bt_misplay);
        bt_fuzzy= (ImageView) findViewById(R.id.bt_fuzzy);
        bt_carlton= (ImageView) findViewById(R.id.bt_carlton);
        bt_inconvenient= (ImageView) findViewById(R.id.bt_inconvenient);
        bt_nosynch= (ImageView) findViewById(R.id.bt_nosynch);
        textView_version= (TextView) findViewById(R.id.textView_version);
        imageView= (ImageView) findViewById(R.id.suggest_image);
        imageAdapter();

    }

    private void imageAdapter() {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=1;
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.otherpage1,options);
        imageView.setImageBitmap(bitmap);
        /**
         * 内存不足时让图片回收
         */
//        Picasso.with(mContext).load(R.drawable.otherpage).resize(960,540).centerCrop().into(imageView);
//        Picasso.with(mContext).load(R.drawable.s001).resize(302,232).centerCrop().into(bt_misplay);
//        Picasso.with(mContext).load(R.drawable.s004).resize(302,232).centerCrop().into(bt_fuzzy);
//        Picasso.with(mContext).load(R.drawable.s002).resize(302,232).centerCrop().into(bt_carlton);
//        Picasso.with(mContext).load(R.drawable.s005).resize(302,232).centerCrop().into(bt_inconvenient);
//        Picasso.with(mContext).load(R.drawable.s003).resize(302,232).centerCrop().into(bt_nosynch);


    }

    protected void setViews() {
    }

    protected void setListeners() {
        bt_nosynch.setOnClickListener(this);
        bt_carlton.setOnClickListener(this);
        bt_inconvenient.setOnClickListener(this);
        bt_fuzzy.setOnClickListener(this);
        bt_misplay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_fuzzy:
                //一天时间内只能点击一次 waitTime 24小时的毫秒数
                long currentTime1=System.currentTimeMillis();
                if (currentTime1-touchTime>waitTime){
                    fuzzy=fuzzy+1;
                    touchTime=System.currentTimeMillis();
                    MobclickAgent.onEvent(mContext,"fb_fuzzy");
                }
                break;
            case R.id.bt_carlton:
                long currentTime2=System.currentTimeMillis();
                if (currentTime2-touchTime>waitTime){
                    nonfluency=nonfluency+1;
                    touchTime=System.currentTimeMillis();
                    MobclickAgent.onEvent(mContext,"fb_nonfluency");
                }
                break;
            case R.id.bt_inconvenient:
                long currentTime3=System.currentTimeMillis();
                if (currentTime3-touchTime>waitTime){
                    inconvenient=inconvenient+1;
                    touchTime=System.currentTimeMillis();
                    MobclickAgent.onEvent(mContext,"fb_inconvenient");
                }
                break;
            case R.id.bt_misplay:
                long currentTime4=System.currentTimeMillis();
                if (currentTime4-touchTime>waitTime){
                    cannotplay=cannotplay+1;
                    touchTime=System.currentTimeMillis();
                    MobclickAgent.onEvent(mContext,"fb_CannotPlay");
                }
                break;
            case R.id.bt_nosynch:
                long currentTime5=System.currentTimeMillis();
                if (currentTime5-touchTime>waitTime){
                    nosynch=nosynch+1;
                    touchTime=System.currentTimeMillis();
                    MobclickAgent.onEvent(mContext,"fb_nosynch");
                }
                break;
        }
        myToast.showToast(getApplicationContext(),"反馈成功",800);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (is_resume){
            is_resume=false;
        }else {
           // Music.play(this, R.raw.bg);
            Music.restart(mContext);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        if (is_resume) {
            is_resume = false;
        } else {
            //  mHomeWatcher.stopWatch();// 在onPause中停止监听，不然会报错的。
           // Music.stop(mContext);
            Music.pause(mContext);
        }
    }
    private long mPreKeytime = 0;
    private  long time=0;
    boolean isFirst_second=true;
    /**
     *
     *
     * @param
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        mPreKeytime = time;
        time = System.currentTimeMillis();
        if (time - mPreKeytime < 100) {
            return true;
        }
        if (isFirst_second) {
//            Music.play_key();
            isFirst_second = false;
        } else {
            isFirst_second = true;
        }
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            this.finish();
        }
        else if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_MENU){
            clearData();
        }
        return super.dispatchKeyEvent(event);
    }


    //清除缓存
    void clearData(){
        final ProgressDialog progressDialog = ProgressDialog
                .show(mContext, "", "正在删除缓存中...",
                        true, false);
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(
                    Void... params) {
                File file = StorageUtils.getIndividualCacheDirectory(Suggest.this);
                ///data/user/0/com.iqinbao.android.songstv/cache/music-cache
                Tools.delFolder(file.getPath());
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                progressDialog.dismiss();
            }
        };
        progressDialog.show();
        task.execute();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        clearImageView();
    }

    private void clearImageView() {
        Bitmap leftBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        imageView.setImageBitmap(null);
        try {
            if (leftBitmap != null && !leftBitmap.isRecycled()) {
                leftBitmap.recycle();
                leftBitmap = null;
            }
        }catch (Exception e){
        }

    }
}
