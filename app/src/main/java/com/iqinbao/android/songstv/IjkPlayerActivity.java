package com.iqinbao.android.songstv;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iqinbao.android.songstv.adapter.MySingAdapter;
import com.iqinbao.android.songstv.adapter.TestAdapter;
import com.iqinbao.android.songstv.base.BaseFragmentActivity;
import com.iqinbao.android.songstv.beanstv.AdsEnity;
import com.iqinbao.android.songstv.beanstv.SongEntity;
import com.iqinbao.android.songstv.db01.SQLOperateImpl;
import com.iqinbao.android.songstv.music.Music;
import com.iqinbao.android.songstv.utils.ActivityCollector;
import com.iqinbao.android.songstv.utils.LogUtils;
import com.iqinbao.android.songstv.utils.LoggingInterceptors;
import com.iqinbao.android.songstv.utils.MyToast;
import com.iqinbao.android.songstv.utils.Timeturn;
import com.iqinbao.android.songstv.utils.Tools;
import com.iqinbao.android.songstv.videocache.CacheListener;
import com.iqinbao.android.songstv.videocache.HttpProxyCacheServer;
import com.iqinbao.player.IRenderView;
import com.iqinbao.player.IjkVideoView;
import com.umeng.analytics.MobclickAgent;

import org.evilbinary.tv.widget.BorderView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by Administrator on 2016/9/1.
 */
public class IjkPlayerActivity extends BaseFragmentActivity implements SurfaceHolder.Callback, CacheListener, View.OnClickListener, View.OnFocusChangeListener {
    private Button collect, circle, switchcraft, carame;
    private MySingAdapter myAdapter;
    private ImageView imageView01, imageView02;
    private List<SongEntity.CatContentsBean> singList = new ArrayList<>();
    ;
    private List<SongEntity.CatContentsBean> otherList = new ArrayList<>();
    private List<SongEntity.CatContentsBean> collectionList = new ArrayList<>();
    private List<SongEntity.CatContentsBean> recordList = new ArrayList<>();
    private SQLOperateImpl sqlOperateimpl;
    private int category;
    private BorderView borderView;
    private boolean isFirst = true;
    private boolean isHasFocus = true;
    boolean isFirstPosition = true;
    private String pic_s;
    private String tittle;
    private int index = 0;
    private RecyclerView recycler_play;
    private int position = 0;
    private int position1 = 0;
    private int currentProgress = 0;
    private long waitTime = 2000;
    private long touchTime = 0;
    private String[] urlArray01 = new String[0];
    private String[] urlArray02 = new String[0];
    private String[] conidArray01 = new String[0];
    private String[] conidArray02 = new String[0];
    private int size;
    private boolean isStartshow = false;
    private ImageView progressBar, borderCamera, noCamera;
    private boolean looperPlay = false;
    private boolean isCollection = true;
    private boolean clickEvent = false;
    private boolean isplaying = false;
    private RelativeLayout mLinearLayout01;
    private LinearLayout mLinearLayout02, linear_player02;
    private SeekBar playSeekbar;
    private Timeturn timeturn;
    private Timer timer;
    private IMediaPlayer player01;
    private TextView playSize, playCurrentTime, playTittle, playTittle1, adsText;
    private Context mContext;
    private String url;
    private IjkVideoView videoView;
    private HttpProxyCacheServer proxy;
    private boolean is_resume = false;
    private AnimationDrawable animationDrawable;
    private RecyclerView.LayoutManager lm;
    private boolean move = false;
    private int firstItem = 0;
    private int lastItem = 0;
    private boolean kaiguan = true;
    private int j = 0;
    //日志
    private TextView log_txt;
    boolean is_player_ijk = false;
    private RecyclerView test_recyclerView;
    private JSONObject object = new JSONObject();
    private HashMap<Integer, String> map = new HashMap<Integer, String>();
    int percent = 0;
    String percent1 = "当前缓存：";
    private TestAdapter testAdapter;
    private boolean isCarema = false;
    //测网速
    boolean testNetSpeed = false;
    private List<String> mdata = new ArrayList<>();
    private String serviceUrl = "";
    private String playUrl = "";
    private String videosize = "";
//    int p=0;
    //加广告

    //初始值设置为true是为了有广告的时候 能够顺利的播放到下一首
    private boolean isFirstTime = true;
    private boolean isHaveAdsInfo = false;
    private String adsurl = "";
    private String[] adsUrl = new String[0];
    private boolean isplayAds = true;
    private boolean isHide = false;
    private boolean isNext = true;
    private boolean isKeyMove = false;
    int adsSize = 0;
    List<AdsEnity.ContentsBean> list01 = new ArrayList<>();
    //开始摄像头功能
    private SurfaceView surfaceView;
    private Camera camera = null;
    private boolean isHaveCamere = false;
    private boolean hasCamera = true;
    private SurfaceHolder mHolder;
    private boolean isCaremaShow = false;
    private boolean isPreView = false;
    int cameraNum = 0;
    int testNum = 0;
    //优化添加的东西
//    StringBuffer string01=new StringBuffer("");
//    StringBuffer string=new StringBuffer("");
    int timesize = 0;
    int timesize01 = 0;
    private long touchTime2 = 0;
    private long waitTimecamera = 86400000;
    private int index1 = 0;
//    private final VideoProgressUpdater updater = new VideoProgressUpdater();

    //项目优化：使用单例线程池：让插入数据库的操作在线程池里面进行

    private ExecutorService mSingleThreadExecutor = null;

    private boolean isRunOpen = false;


    //系统硬件信息：
    Process p = null;
    private String Product = "未知";
    private String CPU_ABI = "未知";
    private String SDK = "未知";
    private String availMmorye = "未知";
    private String CPUInfo = "未知";
    private String Result = "未知";
    private String conid = "";
    private String play_sid = "";
    private HashMap<String, String> umengmap = new HashMap<>();
    private String[] sidArray01 = new String[0];
    private String[] sidArray02 = new String[0];
    private final Handler mHandler = new MyHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijkplayer);
        mContext = this;
        ActivityCollector.addActivity(this);
        timeturn = new Timeturn();
        proxy = App.getProxy(getApplicationContext());
        Music.stop(getApplicationContext());
        is_resume = true;
        list01 = MainActivity.getAdasInfo();
        findViews();
        initExecutorService();
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mHolder = surfaceView.getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);
        setViews();
        setListeners();
        //第一次进入播放页面，播放的视频

        try {
            startVideo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //显示右边的视频的Item
        initRecyclerView();
    }


    private final class MyHandler extends Handler {
        //        //弱应用handler 防止内存泄漏
//        private WeakReference<IjkPlayerActivity> weakReference;
//        MyHandler(IjkPlayerActivity sms_verification) {
//            this.weakReference = new WeakReference<>(sms_verification);
//        }
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (clickEvent == true) {
                    if (player01 != null && isHide == false) {
                        //显示菜单
                        showMenu();
                    }
                } else {
                    //隐藏菜单
                    hideMenu();
                    if (isHaveCamere == false && noCamera.getVisibility() == View.VISIBLE) {
                        carame.setBackgroundResource(R.drawable.camera_normal);
                        isCarema = false;
                        isHaveCamere = false;
                        //有广告的时候默认隐藏摄像头 隐藏摄像头不能让摄像头移动
                        disEnlargeSurView();
                        noCamera.setVisibility(View.GONE);
                        surfaceView.setVisibility(View.INVISIBLE);
                        borderCamera.setVisibility(View.INVISIBLE);
                    }

                }
                if (kaiguan) {
                    clickEvent = false;
                }

            } else if (msg.what == 1) {
                if (testNetSpeed) {
                    JSONObject jsonObject01;
                    jsonObject01 = LoggingInterceptors.getdata();
                    try {
                        int height = Integer.parseInt(jsonObject01.getString("videosize"));
                        double h01 = height / 1048576;
                        BigDecimal b01 = new BigDecimal(h01);
                        double f2 = b01.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        serviceUrl = "服务器地址：" + jsonObject01.get("service");
                        videosize = "当前视频大小：" + f2 + "M";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    playUrl = "当前视频地址：" + url;
                    //保留两位小数

                    percent1 = "当前缓存：" + percent + "%";
                    map.put(2, percent1);
                    map.put(3, videosize);
                    map.put(4, playUrl);
                    map.put(5, serviceUrl);
                    testAdapter.notifyDataSetChanged();
                }

                int current = videoView.getCurrentPosition();
                int total = videoView.getDuration();
                String string = timeturn.stringForTime(current);
                playCurrentTime.setText(string);
                playSeekbar.setProgress(current);

                playTittle.setText(tittle);
                if (isHide) {
                    timesize = (total - current) / 1000;
                    timesize01 = (int) Math.floor(timesize);
                    adsText.setText("");
                    adsText.setText("广告 " + timesize01);
                }
                if (isStartshow == false) {
//                    imageView.setVisibility(View.INVISIBLE);
                } else {
//                    imageView.setVisibility(View.VISIBLE);
                }
                if (videoView.isPlaying()) {
                    isplaying = true;
                    if (isHasFocus == true) {
                        switchcraft.setBackgroundResource(R.drawable.start1);
                    } else {
                        switchcraft.setBackgroundResource(R.drawable.start);
                    }
                } else {
                    isplaying = false;
                    if (isHasFocus == true) {
                        switchcraft.setBackgroundResource(R.drawable.pause);
                    } else {
                        switchcraft.setBackgroundResource(R.drawable.pause1);
                    }

                }
                if (isplayAds && isCaremaShow) {
                    if (linear_player02.getVisibility() == View.VISIBLE) {
                        //正常显示摄像头
                    } else {
                        //放大摄像头：
                    }
                }
                //摄像头中途损毁或人为的拔掉USB摄像头时 给用户的提示
                cameraNum = Camera.getNumberOfCameras();
                if (cameraNum <= 0 && borderCamera.getVisibility() == View.VISIBLE) {
                    isHaveCamere = false;
                    noCamera.setVisibility(View.VISIBLE);
                }

            } else if (msg.what == 2) {
//                list01= (List<AdsEnity.ContentsBean>) msg.obj;
            } else if (msg.what == 3) {
                surfaceView.setVisibility(View.INVISIBLE);
                borderCamera.setVisibility(View.VISIBLE);
                noCamera.setVisibility(View.VISIBLE);
                isHaveCamere = false;
            } else if (msg.what == 4) {
                surfaceView.setVisibility(View.INVISIBLE);
                borderCamera.setVisibility(View.VISIBLE);
                noCamera.setVisibility(View.VISIBLE);
                isHaveCamere = false;
            } else if (msg.what == 5) {
                surfaceView.setVisibility(View.INVISIBLE);
                borderCamera.setVisibility(View.VISIBLE);
                noCamera.setVisibility(View.VISIBLE);
                isHaveCamere = false;
            } else if (msg.what == 6) {
                //让摄像头的边框和摄像头一块出来
                borderCamera.setVisibility(View.VISIBLE);
            }
            super.handleMessage(msg);

        }
    }

    private void initExecutorService() {
        mSingleThreadExecutor = Executors.newSingleThreadExecutor();
    }

    private void ExecutorServiceThread(ExecutorService mSingleThreadExecutor) {
        mSingleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (sqlOperateimpl == null) {
                    sqlOperateimpl = new SQLOperateImpl(mContext);
                }
                try {
                    sqlOperateimpl.insertRecord(tittle, url, pic_s);
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        progressBar.setBackgroundResource(R.drawable.processbar_frame);
        animationDrawable = (AnimationDrawable) progressBar.getBackground();
        animationDrawable.start();
    }

    private void initRecyclerView() {
        isRunOpen = false;
        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(mContext);
        recycler_play.setLayoutManager(linearLayout);
        lm = recycler_play.getLayoutManager();
        if (isFirst == true) {
            int spaceBottom = getResources().getDimensionPixelSize(R.dimen.w_14);
            int spacingLeft = getResources().getDimensionPixelSize(R.dimen.w_28);
            int spaceTop = getResources().getDimensionPixelSize(R.dimen.w_14);
            //第一行的时候，到顶部的距离
            int space = getResources().getDimensionPixelSize(R.dimen.w_18);
            recycler_play.addItemDecoration(new SpaceItemDecoration(spacingLeft, spaceTop, spaceBottom, space));
            isFirst = false;
        }
        if (category == 1 && collectionList.size() > 0) {
            myAdapter = new MySingAdapter(collectionList);
            recycler_play.setAdapter(myAdapter);
            myAdapter.setOnRecyItemClickListener(new MySingAdapter.OnRecyItemClickListener() {
                @Override
                public void onClickItem(int position, View view) {
                    switchPlay(position);
                }
            });
        } else if (category == 2 && recordList.size() > 0) {
            myAdapter = new MySingAdapter(recordList);
            recycler_play.setAdapter(myAdapter);
            myAdapter.setOnRecyItemClickListener(new MySingAdapter.OnRecyItemClickListener() {
                @Override
                public void onClickItem(int position, View view) {
                    switchPlay(position);
                }
            });
        } else if (category == 3 && singList.size() > 0) {
            myAdapter = new MySingAdapter(singList);
            recycler_play.setAdapter(myAdapter);
            myAdapter.setOnRecyItemClickListener(new MySingAdapter.OnRecyItemClickListener() {
                @Override
                public void onClickItem(int position, View view) {
                    switchPlay(position);
                    ExecutorServiceThread(mSingleThreadExecutor);
                }
            });
            /*
            把index传递给adapter，让adapter知道正在播放的是哪个Item
             */
        } else if (category == 4 && otherList.size() > 0) {
            myAdapter = new MySingAdapter(otherList);
            recycler_play.setAdapter(myAdapter);
            myAdapter.setOnRecyItemClickListener(new MySingAdapter.OnRecyItemClickListener() {
                @Override
                public void onClickItem(int position, View view) {
                    switchPlay(position);
                    ExecutorServiceThread(mSingleThreadExecutor);
                }
            });
        }
        try {
            myAdapter.setIndex(index);
        } catch (NullPointerException e) {
            MyToast.showToast(getApplicationContext(), "播放出错，请重新启动", 800);
        }

        moveToposition(index);
        recycler_play.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (recycler_play.getChildCount() > 0) {
                        int size = recycler_play.getChildCount();
                        recycler_play.getChildAt(size - 1).requestFocus();
                        recycler_play.getChildAt(size - 1).requestFocusFromTouch();
                        recycler_play.getChildAt(size - 1).setFocusableInTouchMode(true);
                    }
                }
            }
        });

        /*
        让视频播放的位置随进度条变化而变化
         */
        playSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LogUtils.e("progress-------",progress+"");
                if (fromUser == true) {
                    LogUtils.e("onProgressChanged----------",progress+"");
                    currentProgress = progress;
                    playSeekbar.setThumb(getResources().getDrawable(R.drawable.thumb_photo));
                    clickEvent = true;
                    videoView.seekTo(progress);
                } else {
                    playSeekbar.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        mHolder = holder;
        if (!isPreView) {
            PackageManager pm = getPackageManager();
            boolean permission = (PackageManager.PERMISSION_GRANTED ==
                    pm.checkPermission("android.permission.CAMERA", "com.iqinbao.android.songstv"));
            int numcamera = Camera.getNumberOfCameras();

            if (permission && numcamera > 0) {
                try {
                    for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
                        Camera.CameraInfo info = new Camera.CameraInfo();
                        Camera.getCameraInfo(i, info);
                        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT && camera == null) {
                            camera = Camera.open(i);
                            isRunOpen = true;
                        }
                    }
                } catch (RuntimeException e) {
                    surfaceView.setVisibility(View.INVISIBLE);
                    borderCamera.setVisibility(View.VISIBLE);
                    noCamera.setVisibility(View.VISIBLE);
                    isHaveCamere = false;
                }
                if (isRunOpen == false) {
                    surfaceView.setVisibility(View.INVISIBLE);
                    borderCamera.setVisibility(View.VISIBLE);
                    noCamera.setVisibility(View.VISIBLE);
                    isHaveCamere = false;
                }
                if (camera != null) {
                    try {
                        //又报了一个RuntimeException:Method called after release
                        camera.setPreviewDisplay(holder);
                    } catch (IOException e) {
                        e.printStackTrace();
                        camera.setPreviewCallback(null);
                        camera.stopPreview();
                        camera.release();
                        camera = null;
                    } catch (RuntimeException e) {
                        surfaceView.setVisibility(View.INVISIBLE);
                        borderCamera.setVisibility(View.VISIBLE);
                        noCamera.setVisibility(View.VISIBLE);
                        isHaveCamere = false;
                    }
                    Camera.Parameters parameters = camera.getParameters();
                    camera.setParameters(parameters);
                    try {
                        //事件反馈 一天之内只能反馈一次
                        long currentTime3 = System.currentTimeMillis();
                        if (currentTime3 - touchTime2 >= waitTimecamera) {
                            MobclickAgent.onEvent(mContext, "startCamera");
                            touchTime2 = System.currentTimeMillis();
                        }
                        isPreView = true;
                        camera.startPreview();
                        //让摄像头的边框和摄像头一块出来
                        borderCamera.setVisibility(View.VISIBLE);
                        System.out.println("camera.startpreview");
                    } catch (RuntimeException e) {
                        isPreView = false;
//                                System.out.println(" fail camera.startpreview");
//                                Message msg=Message.obtain();
//                                msg.what=3;
//                                handler.sendMessage(msg);
                        surfaceView.setVisibility(View.INVISIBLE);
                        borderCamera.setVisibility(View.VISIBLE);
                        noCamera.setVisibility(View.VISIBLE);
                        isHaveCamere = false;
                    }
                }
            } else if (permission == false) {
//                        Message msg=Message.obtain();
//                        msg.what=4;
//                        handler.sendMessage(msg);
                surfaceView.setVisibility(View.INVISIBLE);
                borderCamera.setVisibility(View.VISIBLE);
                noCamera.setVisibility(View.VISIBLE);
                isHaveCamere = false;
            } else if (numcamera <= 0) {
//                        Message msg=Message.obtain();
//                        msg.what=5;
//                        handler.sendMessage(msg);
                surfaceView.setVisibility(View.INVISIBLE);
                borderCamera.setVisibility(View.VISIBLE);
                noCamera.setVisibility(View.VISIBLE);
                isHaveCamere = false;
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        System.out.println("surfaceDestroyed");
        try {
            if (camera != null) {
                camera.setPreviewCallback(null);
                camera.stopPreview();
                camera.release();
                camera = null;
                isPreView = false;
                testNum = testNum - 1;
            }
        } catch (Exception e) {
        }
    }

    /**
     * Listener to update the UI upon connectionclass change.
     */
//    private class ConnectionChangedListener
//            implements ConnectionClassManager.ConnectionClassStateChangeListener {
//        @Override
//        public void onBandwidthStateChange(ConnectionQuality bandwidthState) {
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    netSpeed=mConnectionClassManager.getDownloadKBitsPerSecond()+"kb/s";
//                    querity=mConnectionClassManager.getCurrentBandwidthQuality()+"";
//                }
//            });
//        }
//    }



    /*
    点击右边的Item播放视频
     */
    private void switchPlay(int position) {
        long s1 = System.currentTimeMillis();
        isFirstPosition = true;
        position1 = position;
        if (category == 1) {
            if (collectionList.size() > 0 && collectionList != null) {
                url = collectionList.get(position).getPlayurl();
                tittle = collectionList.get(position).getTitle();
                pic_s = collectionList.get(position).getPic_s();
                size = collectionList.size();
                urlArray02 = new String[collectionList.size()];
                for (int i = 0; i < size; i++) {
                    urlArray02[i] = collectionList.get(i).getPlayurl();
                }
            }
        } else if (category == 2) {
            if (recordList.size() > 0 && recordList != null) {
                size = recordList.size();
                url = recordList.get(position).getPlayurl();
                tittle = recordList.get(position).getTitle();
                pic_s = recordList.get(position).getPic_s();
                urlArray02 = new String[recordList.size()];
                for (int i = 0; i < size; i++) {
                    urlArray02[i] = recordList.get(i).getPlayurl();
                }
            }

        } else if (category == 3) {
            if (singList.size() > 0 && singList != null) {
                size = singList.size();
                play_sid = singList.get(position).getSid();
                conid = singList.get(position).getConid();
                url = singList.get(position).getPlayurl();
                tittle = singList.get(position).getTitle();
                pic_s = singList.get(position).getPic_s();
                urlArray02 = new String[size];
                conidArray02 = new String[size];
                sidArray02 = new String[size];
                for (int i = 0; i < size; i++) {
                    sidArray02[i] = singList.get(i).getSid();
                    conidArray02[i] = singList.get(i).getConid();
                    urlArray02[i] = singList.get(i).getPlayurl();
                }
            }
        } else if (category == 4) {
            size = otherList.size();
            if (otherList.size() > 0) {
                url = otherList.get(position).getPlayurl();
                tittle = otherList.get(position).getTitle();
                pic_s = otherList.get(position).getPic_s();
                urlArray02 = new String[otherList.size()];
                for (int i = 0; i < size; i++) {
                    urlArray02[i] = otherList.get(i).getPlayurl();
                }
            }

        }
        myAdapter.setIndex(position);
        collect.setBackgroundResource(R.drawable.d08);
        playTittle.setText("" + tittle);
        progressBar.setVisibility(View.VISIBLE);
        if (animationDrawable != null) {
            animationDrawable.start();
        }
        //关闭、释放之前播放的视频
        videoView.stopPlayback();
        proxy.unregisterCacheListener();
        if (isplayAds && position != 0) {
            playAdas();
        } else {
            if (urlArray02.length > 0) {
                if (is_player_ijk) {
                    videoView.setVideoPath((urlArray02[position]));
                } else {
                    proxy.registerCacheListener(this, urlArray02[position]);
                    videoView.setVideoPath(proxy.getProxyUrl(urlArray02[position]));
                }
                videoView.start();
                try {
                    if (conidArray02 != null && conidArray02.length > position + 1) {
                        conid = conidArray02[position];
                        Tools.addPlayNum(getApplicationContext(), conid);
                        umengmap.put("conid", conid);
                    }
                    if (sidArray02 != null && sidArray02.length > position + 1) {
                        play_sid = sidArray02[position];
                        umengmap.put("sid", play_sid);
                    }
                    MobclickAgent.onEvent(mContext, "play");
                    MobclickAgent.onEvent(mContext, "play_new", umengmap);
                } catch (Exception e) {
                }
                isplayAds = true;
                isHide = false;
                isNext = false;
            }
        }
        collect.setBackgroundResource(R.drawable.d08);
        isCollection = true;
    }

    private void insertdataRecord() {
        if (sqlOperateimpl == null) {
            sqlOperateimpl = new SQLOperateImpl(mContext);
        }
        try {
            sqlOperateimpl.insertRecord(tittle, url, pic_s);
        } catch (Exception e) {
        }
    }

    @Override
    protected void findViews() {
//        imageView = (ImageView) findViewById(R.id.imageView5);
        noCamera = (ImageView) findViewById(R.id.nocamera);
        borderCamera = (ImageView) findViewById(R.id.camera_image);
        progressBar = (ImageView) findViewById(R.id.progressBar);
        log_txt = (TextView) findViewById(R.id.log_txt);
        recycler_play = (RecyclerView) findViewById(R.id.recycler_player);
        mLinearLayout01 = (RelativeLayout) findViewById(R.id.linear_player01);
        linear_player02 = (LinearLayout) findViewById(R.id.linear_player03);
        mLinearLayout02 = (LinearLayout) findViewById(R.id.linear_player02);
        playSeekbar = (SeekBar) findViewById(R.id.seekBar_player);
        playSeekbar.setNextFocusUpId(R.id.recycler_player);
        playSeekbar.setNextFocusDownId(R.id.bt_onAndoff);





        videoView = (IjkVideoView) findViewById(R.id.video_player);
        collect = (Button) findViewById(R.id.bt_collect);
        circle = (Button) findViewById(R.id.bt_circle);
        switchcraft = (Button) findViewById(R.id.bt_onAndoff);
        carame = (Button) findViewById(R.id.bt_carema);
        playSize = (TextView) findViewById(R.id.player_size);
        playCurrentTime = (TextView) findViewById(R.id.player_currentime);
        playTittle = (TextView) findViewById(R.id.player_tittle);
        playTittle1 = (TextView) findViewById(R.id.player_tittle01);
        test_recyclerView = (RecyclerView) findViewById(R.id.test_recyclerView);


        //视频播放信息
        showtestInfo();
        //添加广告相关内容
        adsText = (TextView) findViewById(R.id.text7);
        adsText.setTextColor(0xff085c72);
        imageView01 = (ImageView) findViewById(R.id.imageView);
        imageView02 = (ImageView) findViewById(R.id.imageView6);


        playTittle1.setTextColor(0x7ff1f1f1);
        playSize.setTextColor(0x7ff1f1f1);

        borderView = new BorderView(mContext);
//        borderView.setBackgroundResource(R.drawable.border_red);
        borderView.attachTo(recycler_play);
        //把暂停开始按钮设置成默认焦点
        switchcraft.setFocusable(true);
        switchcraft.setFocusableInTouchMode(true);
        switchcraft.requestFocus();
        switchcraft.requestFocusFromTouch();
        switchcraft.setNextFocusUpId(R.id.seekBar_player);
        playSeekbar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && !isKeyMove) {
                    MyToast.showToast(getApplicationContext(), "左右键控制快进后退", 800);
                }else {
                    isKeyMove = false;
                }
            }
        });

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = 0;
                mHandler.sendMessage(msg);
            }
        }, 2000, 6000);
    }

    private void showtestInfo() {

        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(info);
        long size = info.availMem / (1024 * 1024);
        availMmorye = "系统剩余内存:" + size + "M";
        StringBuilder phoneInfo = new StringBuilder();
        Product = "生产厂家：" + android.os.Build.PRODUCT + System.getProperty("line.separator");
        CPU_ABI = "CPU架构：" + android.os.Build.CPU_ABI + System.getProperty("line.separator");
        SDK = "SDK:" + android.os.Build.VERSION.SDK + System.getProperty("line.separator");
        map.put(0, Product);
        map.put(1, availMmorye);
        map.put(6, CPU_ABI);
        map.put(8, SDK);
        map.put(2, percent1);
        map.put(5, serviceUrl);
        map.put(4, playUrl);
        if (map.size() != 0 && map != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            test_recyclerView.setLayoutManager(linearLayoutManager);
            testAdapter = new TestAdapter(mContext, map);
            test_recyclerView.setAdapter(testAdapter);
        }
    }

    @Override
    protected void setViews() {
        videoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        initIjkplayer();

    }

    @Override
    protected void setListeners() {
        carame.setOnClickListener(this);
        videoView.setOnClickListener(this);
        collect.setOnClickListener(this);
        circle.setOnClickListener(this);
        switchcraft.setOnClickListener(this);
        switchcraft.setOnFocusChangeListener(this);
        circle.setOnFocusChangeListener(this);
        collect.setOnFocusChangeListener(this);
        playSeekbar.setOnClickListener(this);
        carame.setOnFocusChangeListener(this);
        playSeekbar.setOnFocusChangeListener(this);


        videoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                if (animationDrawable != null && animationDrawable.isRunning()) {
                    animationDrawable.stop();
                }
                if (looperPlay == true) {
                    mp.setLooping(true);
                }
                player01 = mp;
                count = 0;
                progressBar.setVisibility(View.GONE);
                playSeekbar.setMax(videoView.getDuration());
                LogUtils.e("videoView.getDuration()=====",videoView.getDuration()+"");
                int videosize = videoView.getDuration();
                String size = "/" + timeturn.stringForTime(videosize);
                playSize.setText(size);
                if (isHide) {
                    hideMenu();
                    adsText.setVisibility(View.VISIBLE);
                    imageView01.setVisibility(View.VISIBLE);
                    imageView02.setVisibility(View.VISIBLE);
                } else {
                    imageView01.setVisibility(View.INVISIBLE);
                    imageView02.setVisibility(View.INVISIBLE);
                    adsText.setVisibility(View.INVISIBLE);
                }
            }
        });
        videoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                switch (videoView.getId()) {
                    case R.id.video_player:
                        isRunOpen = false;
                        if (looperPlay == false) {
                            mp.setDisplay(null);
                            mp.reset();
                            index = position1;
//                            index = index + 1;
//                            if (index > size - 1) {
//                                index = 0;
//                            }
                            isFirstPosition = false;
                            collect.setBackgroundResource(R.drawable.d08);
                            isCollection = true;
                            //让adapter获得正在播放视频的位置
                            myAdapter.setIndex(index);
                            myAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.VISIBLE);
                            if (animationDrawable != null) {
                                animationDrawable.start();
                            }
                            proxy.unregisterCacheListener();

                            //第一次执行的时候不会判断是否有视频广告
                            if (isFirstTime) {
                                int size01 = 0;
                                try {
                                    size01 = list01.get(0).getCat_contents().size();
                                } catch (Exception e) {
                                }
                                if (list01 != null && size01 != 0) {
                                    isHaveAdsInfo = true;
                                } else {
                                    isHaveAdsInfo = false;
                                }
                                isFirstTime = false;
                            }
                            if (isHaveAdsInfo) {
                                //为真就隐藏菜单
                                if (isplayAds) {
                                    playAdas();
                                    isNext = true;
                                } else {
                                    if (urlArray01.length > 0) {
                                        if (isNext) {
                                            index = index + 1;
                                            if (index > size - 1) {
                                                index = 0;
                                            }
                                            position1 = index;
                                        }
                                        adsText.setVisibility(View.INVISIBLE);
                                        imageView01.setVisibility(View.INVISIBLE);
                                        imageView02.setVisibility(View.INVISIBLE);
                                        if (is_player_ijk) {
                                            videoView.setVideoPath((urlArray01[index]));
                                        } else {
                                            proxy.registerCacheListener(IjkPlayerActivity.this, urlArray01[index]);
                                            videoView.setVideoPath(proxy.getProxyUrl(urlArray01[index]));
                                        }
                                        videoView.start();
                                        try {
                                            if (conidArray01 != null && conidArray01.length > index + 1) {
                                                conid = urlArray01[index];
                                                umengmap.put("conid", conid);
                                                Tools.addPlayNum(getApplicationContext(), conid);
                                            }
                                            if (sidArray01 != null && sidArray01.length > index + 1) {
                                                play_sid = sidArray01[index];
                                                umengmap.put("sid", play_sid);
                                            }
                                            MobclickAgent.onEvent(mContext, "new_play", umengmap);
                                            MobclickAgent.onEvent(mContext, "play");
                                        } catch (Exception e) {
                                        }
                                        isHide = false;
                                        isplayAds = true;
                                        isNext = false;
                                        ExecutorServiceThread(mSingleThreadExecutor);
                                        myAdapter.setIndex(index);
                                        moveToposition(index);
                                        myAdapter.notifyDataSetChanged();
                                    }
                                }
                            } else {
                                if (urlArray01.length > 0) {
//                                if (isNext){
                                    index = index + 1;
                                    if (index > size - 1) {
                                        index = 0;
                                    }
                                    position1 = index;
//                                }
                                    adsText.setVisibility(View.INVISIBLE);
                                    imageView01.setVisibility(View.INVISIBLE);
                                    imageView02.setVisibility(View.INVISIBLE);

                                    if (is_player_ijk) {
                                        videoView.setVideoPath((urlArray01[index]));
                                    } else {
                                        proxy.registerCacheListener(IjkPlayerActivity.this, urlArray01[index]);
                                        videoView.setVideoPath(proxy.getProxyUrl(urlArray01[index]));
                                    }
                                    videoView.start();
                                    try {
                                        if (conidArray01 != null && conidArray01.length > index + 1) {
                                            conid = conidArray01[index];
                                            umengmap.put("conid", conid);
                                            Tools.addPlayNum(getApplicationContext(), conid);
                                        }
                                        if (sidArray01 != null && sidArray01.length > index1) {
                                            play_sid = sidArray01[index];
                                            umengmap.put("sid", play_sid);
                                        }
                                        MobclickAgent.onEvent(mContext, "new_play", umengmap);
                                        MobclickAgent.onEvent(mContext, "play");
                                    } catch (Exception e) {
                                    }


                                    isHide = false;
                                    isplayAds = true;
                                    isNext = false;
                                    myAdapter.setIndex(index);
                                    moveToposition(index);
                                    myAdapter.notifyDataSetChanged();
                                }
                            }
                            if (category == 1 && collectionList.size() > 0 && collectionList != null) {
                                tittle = collectionList.get(index).getTitle();
                            } else if (category == 2 && recordList.size() > 0 && recordList != null) {
                                tittle = recordList.get(index).getTitle();
                            } else if (category == 3 && singList.size() > 0 && singList != null) {
                                tittle = singList.get(index).getTitle();
                                url = singList.get(index).getPlayurl();
                                play_sid = singList.get(index).getSid();
                                pic_s = singList.get(index).getPic_s();
                                conid = singList.get(index).getConid();
                                ExecutorServiceThread(mSingleThreadExecutor);
                            } else if (category == 4) {
                                url = otherList.get(index).getPlayurl();
                                tittle = otherList.get(index).getTitle();
                                pic_s = otherList.get(index).getPic_s();
                                ExecutorServiceThread(mSingleThreadExecutor);
                            }
                        }
                }
            }
        });
        videoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                switch (what) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        //do something when buffering start
//                        log_txt.setVisibility(View.VISIBLE);
                        log_txt.setText(Formatter.formatFileSize(getApplicationContext(), extra) + "/s");
                        progressBar.setVisibility(View.VISIBLE);
                        if (animationDrawable != null) {
                            animationDrawable.start();
                        }
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //do something when buffering end
                        log_txt.setVisibility(View.GONE);
                        log_txt.setText(Formatter.formatFileSize(getApplicationContext(), extra) + "/s");
                        progressBar.setVisibility(View.GONE);
                        if (animationDrawable != null) {
                            animationDrawable.stop();
                        }
                        break;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                        //download speed
//                        log_txt.setVisibility(View.VISIBLE);
                        log_txt.setText(Formatter.formatFileSize(getApplicationContext(), extra) + "/s");
                        break;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                        //do something when video rendering
                        log_txt.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });
        videoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                //视频加载失败，每隔3s重新连接一次`
                if (count < 10) {
                    mHandler.post(errorPlayCurrentThread);
                } else {
                    //如果30s之后还是加载不出来，就播放下一首
                    mHandler.postDelayed(errorPlayCurrentThread, 3000);
                }
                return true;
            }
        });
    }

    public void getLog() {
        System.out.println("--------func start--------"); // 方法启动
        try {
            ArrayList<String> cmdLine = new ArrayList<String>();   //设置命令   logcat -d 读取日志
            cmdLine.add("logcat");
            cmdLine.add("-d");
            cmdLine.add("logcat");
            cmdLine.add("-w");

            ArrayList<String> clearLog = new ArrayList<String>();  //设置命令  logcat -c 清除日志
            clearLog.add("logcat");
            clearLog.add("-c");

            Process process = Runtime.getRuntime().exec(cmdLine.toArray(new String[cmdLine.size()]));   //捕获日志
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));    //将捕获内容转换为BufferedReader


//                Runtime.runFinalizersOnExit(true);
            String str = null;
            while ((str = bufferedReader.readLine()) != null)    //开始读取日志，每次读取一行
            {
                Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));  //清理日志....这里至关重要，不清理的话，任何操作都将产生新的日志，代码进入死循环，直到bufferreader满
//                System.out.println(str);    //输出，在logcat中查看效果，也可以是其他操作，比如发送给服务器..
                if (log_txt != null) {
                    log_txt.setText(log_txt.getText() + "\n\r" + str);
                }
            }
            if (str == null) {
                System.out.println("--   is null   --");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------func end--------");
    }

    //初始化ijkplayer
    private void initIjkplayer() {
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
//            IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
        } catch (Throwable e) {
            Log.e("GiraffePlayer", "loadLibraries error", e);
        }
    }


    //播放
    private void startVideo() throws Exception {
        //获取播放视频的数据
        isRunOpen = false;
        havaData();
        mHandler.post(updataThread);
        if (isplayAds && position != 0) {
            playAdas();
        } else {
            if (urlArray01.length > 0) {
                if (is_player_ijk) {
                    videoView.setVideoPath((urlArray01[position]));
                } else {
                    proxy.registerCacheListener(this, urlArray01[position]);
                    videoView.setVideoPath(proxy.getProxyUrl(urlArray01[position]));
                }
                videoView.start();
                try {
                    if (conid != null && !conid.equals("")) {
                        Tools.addPlayNum(getApplicationContext(), conid);
                        umengmap.put("conid", conid);
                    }
                    if (play_sid != null && !play_sid.equals("")) {
                        umengmap.put("sid", play_sid);
                    }
                    MobclickAgent.onEvent(mContext, "new_play", umengmap);
                    MobclickAgent.onEvent(mContext, "play");
                } catch (Exception e) {
                }
                isplayAds = true;
                isHide = false;
                isNext = true;
            }
        }
    }

    /**
     * 播放的广告
     */
    private void playAdas() {
        int size01 = 0;
        try {
            size01 = list01.get(0).getCat_contents().size();
        } catch (Exception e) {
        }
        if (list01 != null && size01 > 0) {
            Random random = new Random();
            isHide = true;
            isNext = false;
            hideMenu();
            if (isCaremaShow) {
                noCamera.setVisibility(View.GONE);
                surfaceView.setVisibility(View.INVISIBLE);
                borderCamera.setVisibility(View.GONE);
                carame.setBackgroundResource(R.drawable.camera_normal);
                isCarema = false;
                isCaremaShow = false;
                isHaveCamere = false;
            }
            int num = 0;
            num = random.nextInt(size01);
            adsUrl = new String[size01];
            for (int i = 0; i < size01; i++) {
                adsUrl[i] = list01.get(0).getCat_contents().get(i).getPlayurl();
            }
            adsurl = adsUrl[num];
            proxy.registerCacheListener(IjkPlayerActivity.this, adsurl);
            videoView.setVideoPath(proxy.getProxyUrl(adsurl));
            videoView.start();
            isplayAds = false;
            isHaveAdsInfo = true;
        } else {
            isHaveAdsInfo = false;
            playCurrentVideo();
            isHide = false;
            isplayAds = false;
        }
    }

    private void showMenu() {
        mLinearLayout01.setVisibility(View.VISIBLE);
        mLinearLayout02.setVisibility(View.VISIBLE);
        linear_player02.setVisibility(View.VISIBLE);
        //正常显示摄像头
        if (isCaremaShow) {
            disEnlargeSurView();
        }
    }

    private void hideMenu() {
        mLinearLayout01.setVisibility(View.INVISIBLE);
        linear_player02.setVisibility(View.INVISIBLE);
        mLinearLayout02.setVisibility(View.INVISIBLE);
        switchcraft.setFocusable(true);
        switchcraft.requestFocus();
        switchcraft.requestFocusFromTouch();
        switchcraft.setFocusableInTouchMode(true);
        //将摄像头移动到底部
        if (isCaremaShow && isHaveCamere && isHide == false) {
            enlargeSurview();
        }

    }

    private void havaData() throws Exception {
        Intent intent = getIntent();
        position = Integer.parseInt(intent.getStringExtra("position"));
        category = intent.getIntExtra("category", 0);
        if (isFirstPosition == true) {
            position1 = position;
//            isFirstPosition = false;
        } else {
            position1 = index;
        }
        if (category == 1) {
            index = Integer.parseInt(intent.getStringExtra("position"));
            if (sqlOperateimpl == null) {
                sqlOperateimpl = new SQLOperateImpl(getApplicationContext());
            }
            collectionList = sqlOperateimpl.queryCollection();
            if (collectionList != null && collectionList.size() > 0) {
                size = collectionList.size();
                urlArray01 = new String[size];
                pic_s = collectionList.get(position1).getPic_s();
                url = collectionList.get(position1).getPlayurl();
                tittle = collectionList.get(position1).getTitle();
                for (int i = 0; i < size; i++) {
                    urlArray01[i] = collectionList.get(i).getPlayurl();
                }
            }
        } else if (category == 2) {
            index = Integer.parseInt(intent.getStringExtra("position"));
            if (sqlOperateimpl == null) {
                sqlOperateimpl = new SQLOperateImpl(getApplicationContext());
            }
            recordList = sqlOperateimpl.queryRecord();
            if (recordList != null && recordList.size() > 0) {
                size = recordList.size();
                urlArray01 = new String[size];
                pic_s = recordList.get(position1).getPic_s();
                url = recordList.get(position1).getPlayurl();
                tittle = recordList.get(position1).getTitle();
                for (int i = 0; i < size; i++) {
                    urlArray01[i] = recordList.get(i).getPlayurl();
                }
            }

        } else if (category == 3) {
            index = Integer.parseInt(intent.getStringExtra("position"));
            String id = intent.getStringExtra("catid");
            if (sqlOperateimpl == null) {
                sqlOperateimpl = new SQLOperateImpl(getApplicationContext());
            }
            singList = sqlOperateimpl.queryForSing(id);
            if (singList != null && singList.size() > 0) {
                size = singList.size();
                urlArray01 = new String[size];
                conidArray01 = new String[size];
                sidArray01 = new String[size];
                conid = singList.get(position1).getConid();
                play_sid = singList.get(position1).getSid();
                pic_s = singList.get(position1).getPic_s();
                url = singList.get(position1).getPlayurl();
                tittle = singList.get(position1).getTitle();
                ExecutorServiceThread(mSingleThreadExecutor);
                for (int i = 0; i < size; i++) {
                    urlArray01[i] = singList.get(i).getPlayurl();
                    conidArray01[i] = singList.get(i).getConid();
                    sidArray01[i] = singList.get(i).getSid();
                }
            }
        } else if (category == 4) {
            //我这里写的只是测试数据，具体的还需要两边对接
            index1 = Integer.parseInt(intent.getStringExtra("position"));
            //实是sid,而不是catid
            String id = intent.getStringExtra("catid");
            if (sqlOperateimpl == null) {
                sqlOperateimpl = new SQLOperateImpl(mContext);
            }
            try {
                SongEntity.CatContentsBean list = sqlOperateimpl.queryForSingSid(id);
                String catid0014 = list.getCatid();

                String catId = "";
                otherList = sqlOperateimpl.queryForSing(catid0014);
                size = otherList.size();
                int k = 0;
                int pos = 0;
                while (!id.equals(catId)) {
                    pos = pos + 1;
                    if (pos >= size) {
                        pos = 0;
                    }
                    catId = otherList.get(pos).getSid();
                }
                index = pos;
                position1 = pos;
                urlArray01 = new String[size];
                pic_s = list.getPic_s();
                url = list.getPlayurl();
                tittle = list.getTitle();
                ExecutorServiceThread(mSingleThreadExecutor);
                for (int i = 0; i < size; i++) {
                    urlArray01[i] = otherList.get(i).getPlayurl();
                }
            } catch (Exception e) {
                MyToast.showToast(getApplicationContext(), "未找到该资源", 800);
                finish();
            }
        }
        playTittle.setText("" + tittle);
    }
    /*
    启动一个线程监测进度条显示视频播放的进程
     */
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//
//            super.handleMessage(msg);
//        }
//    };

    private void disEnlargeSurView() {
        if (borderCamera.getVisibility() == View.VISIBLE) {
            RelativeLayout.LayoutParams params01 = (RelativeLayout.LayoutParams) borderCamera.getLayoutParams();
            int right01 = getResources().getDimensionPixelSize(R.dimen.w_328);
            int bottom02 = getResources().getDimensionPixelSize(R.dimen.w_122);
            params01.setMargins(0, 0, right01, bottom02);
            borderCamera.requestLayout();
        }

        if (surfaceView.getVisibility() == View.VISIBLE) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) surfaceView.getLayoutParams();
            int right = getResources().getDimensionPixelSize(R.dimen.w_320);
            int bottom = getResources().getDimensionPixelSize(R.dimen.w_122);
            params.setMargins(0, 0, right, bottom);
            surfaceView.requestLayout();
        }

    }

    private void enlargeSurview() {
        if (borderCamera.getVisibility() == View.VISIBLE) {
            RelativeLayout.LayoutParams params01 = (RelativeLayout.LayoutParams) borderCamera.getLayoutParams();
            int right01 = getResources().getDimensionPixelSize(R.dimen.w_328);
            int bottom02 = getResources().getDimensionPixelSize(R.dimen.w_122);
            params01.setMargins(right01, bottom02, 0, 0);
            borderCamera.requestLayout();
        }
        if (surfaceView.getVisibility() == View.VISIBLE) {
            int left = getResources().getDimensionPixelSize(R.dimen.w_320);
            int top = getResources().getDimensionPixelSize(R.dimen.w_122);
            RelativeLayout.LayoutParams btnLp = (RelativeLayout.LayoutParams) surfaceView.getLayoutParams();
            btnLp.setMargins(left, top, 0, 0);
            surfaceView.requestLayout();
        }

    }

    Runnable updataThread = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(updataThread, 500);
            Message msg = Message.obtain();
            msg.what = 1;
            mHandler.sendMessage(msg);
        }
    };


    @Override
    public void onCacheAvailable(File file, String url, int percentsAvailable) {
//        handler.post(updataThread);
        percent = percentsAvailable;
        playSeekbar.setSecondaryProgress(percentsAvailable);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.bt_onAndoff:
                if (hasFocus) {
                    if (videoView.isPlaying()) {
                        switchcraft.setBackgroundResource(R.drawable.start1);
                    } else {
                        switchcraft.setBackgroundResource(R.drawable.pause);
                    }
                    isHasFocus = true;
                } else {
                    if (videoView.isPlaying()) {
                        switchcraft.setBackgroundResource(R.drawable.start);
                    } else {
                        switchcraft.setBackgroundResource(R.drawable.pause1);
                    }
                    isHasFocus = false;
                }
                switchcraft.setNextFocusUpId(R.id.seekBar_player);
                switchcraft.setNextFocusRightId(R.id.bt_carema);
                break;
            case R.id.bt_carema:
                if (hasFocus) {
                    if (isCarema) {
                        carame.setBackgroundResource(R.drawable.camera_open_select);
                    } else {
                        carame.setBackgroundResource(R.drawable.camera_select);
                    }
                } else {
                    if (isCarema) {
                        carame.setBackgroundResource(R.drawable.camera_open_normal);
                    } else {
                        carame.setBackgroundResource(R.drawable.camera_normal);
                    }
                }
                carame.setNextFocusRightId(R.id.bt_circle);
                carame.setNextFocusLeftId(R.id.bt_onAndoff);
                carame.setNextFocusUpId(R.id.seekBar_player);
                break;
            case R.id.bt_circle:
                if (hasFocus) {
                    if (looperPlay == true) {
                        circle.setBackgroundResource(R.drawable.d09);
                    } else {
                        circle.setBackgroundResource(R.drawable.d02);
                    }
                } else {
                    if (looperPlay == true) {
                        circle.setBackgroundResource(R.drawable.d11);
                    } else {
                        circle.setBackgroundResource(R.drawable.d07);
                    }
                }
                circle.setNextFocusRightId(R.id.bt_collect);
                circle.setNextFocusLeftId(R.id.bt_carema);
                circle.setNextFocusUpId(R.id.seekBar_player);
                break;
            case R.id.bt_collect:
                if (hasFocus) {
                    if (isCollection == false) {
                        collect.setBackgroundResource(R.drawable.d20);
                    } else {
                        collect.setBackgroundResource(R.drawable.d01);
                    }
                } else {
                    if (isCollection == false) {
                        collect.setBackgroundResource(R.drawable.d10);
                    } else {
                        collect.setBackgroundResource(R.drawable.d08);
                    }
                }
                collect.setNextFocusLeftId(R.id.bt_circle);
                collect.setNextFocusUpId(R.id.seekBar_player);
                break;
            case R.id.seekBar_player:
                if (hasFocus) {
                    if(!isKeyMove){
                        MyToast.showToast(getApplicationContext(), "左右键控制快进后退", 800);
                        playSeekbar.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb));
                    }else {
                        playSeekbar.setThumb(getResources().getDrawable(R.drawable.thumb_photo));
                    }

                } else {
                    playSeekbar.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb));
                    isKeyMove = false;
                }
                break;


        }
    }


    @Override
    public void onClick(View v) {
        //视频开关
        switch (v.getId()) {
            case R.id.bt_onAndoff:
                clickEvent = true;
                if (isHide == false) {
                    if (videoView.isPlaying()) {
                        videoView.pause();
                        clickEvent = true;
                        kaiguan = false;
                        switchcraft.setBackgroundResource(R.drawable.pause);
                        //显示菜单
                        mHandler.post(updataThread);
//                        updater.start();
                        isplaying = false;
                        isStartshow = true;
                        showMenu();
                    } else {
                        videoView.start();
                        kaiguan = true;
                        clickEvent = true;
                    /*
                    菜单隐藏后，点击暂停按钮后，要求菜单键出现
                     */
                        switchcraft.setBackgroundResource(R.drawable.start1);
//                        updater.start();
                        mHandler.post(updataThread);
                        isplaying = true;
                        isStartshow = false;
                        //显示菜单
                        showMenu();
                    }

                }

                switchcraft.setNextFocusRightId(R.id.bt_carema);
                break;
            //开始摄像头：
            case R.id.bt_carema:
                clickEvent = true;
                if (isCarema == false && isHide == false) {
                    isCarema = true;
                    carame.setBackgroundResource(R.drawable.camera_open_select);
                    //播放的不是广告时才开启摄像头
                    startCamera();
                } else {
                    isCarema = false;
                    carame.setBackgroundResource(R.drawable.camera_select);
                    //关闭摄像头
                    stopCamera();
                }
                break;
            case R.id.bt_circle:
                if (player01 == null) {
                    MyToast.showToast(getApplicationContext(), "设置失败 请重新设置", 800);
                } else {
                    if (looperPlay == false) {
                        try {
                            player01.setLooping(true);
                            circle.setBackgroundResource(R.drawable.d09);
                            looperPlay = true;
                        } catch (Exception e) {
                        }
                    } else {
                        try {
                            player01.setLooping(false);
                            circle.setBackgroundResource(R.drawable.d02);
                            looperPlay = false;
                        } catch (Exception e) {
                        }
                    }
                }

                circle.setNextFocusLeftId(R.id.bt_carema);
                circle.setNextFocusRightId(R.id.bt_collect);
                break;
            case R.id.bt_collect:
                if (isCollection == true) {
                    MyToast.showToast(getApplicationContext(), "收藏成功", 800);
//                    Toast.makeText(IjkPlayerActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    isCollection = false;
                    collect.setBackgroundResource(R.drawable.d20);
                    if (sqlOperateimpl == null) {
                        sqlOperateimpl = new SQLOperateImpl(mContext);
                    }
                    try {
                        sqlOperateimpl.insertCollection(tittle, url, pic_s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(IjkPlayerActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    collect.setBackgroundResource(R.drawable.d08);
                    isCollection = true;
                    if (sqlOperateimpl == null) {
                        sqlOperateimpl = new SQLOperateImpl(mContext);
                    }
                    try {
                        sqlOperateimpl.deleteCollection(tittle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
        }
    }

    private void stopCamera() {
        //有摄像头时执行
        if (isCaremaShow) {
            isRunOpen = false;
            isCaremaShow = false;
            noCamera.setVisibility(View.GONE);
            surfaceView.setVisibility(View.INVISIBLE);
            borderCamera.setVisibility(View.INVISIBLE);
        }
    }

    private void startCamera() {
        if (surfaceView.getVisibility() == View.INVISIBLE) {
            isCaremaShow = true;
            isHaveCamere = true;
            surfaceView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mLinearLayout02.getVisibility() == View.VISIBLE) {
            clickEvent = true;
        }
        if (mLinearLayout02.getVisibility() == View.INVISIBLE) {
            carame.setFocusable(false);
            circle.setFocusable(false);
            collect.setFocusable(false);
            recycler_play.setFocusable(false);
        } else {
            carame.setFocusable(true);
            recycler_play.setFocusable(true);
            circle.setFocusable(true);
            collect.setFocusable(true);
        }


        if (keyCode == KeyEvent.KEYCODE_MENU) {
            j = j + 1;
            if (j == 10) {
                testNetSpeed = true;
                String cache = "percent: " + percent;
//                test_recyclerView.setVisibility(View.VISIBLE);
            }

            if (mLinearLayout02.getVisibility() == View.INVISIBLE) {
                showMenu();
                isHide = false;
            } else {
                hideMenu();
                isHide = false;
            }

        } else {
            j = 0;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            if (mLinearLayout02.getVisibility() == View.INVISIBLE) {
                showMenu();
                isHide = false;
                isKeyMove = true;
                playSeekbar.requestFocus();
                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
                    videoView.seekTo(videoView.getCurrentPosition() + 5005);//1s等于182200/182
                }
                if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
                    videoView.seekTo(videoView.getCurrentPosition() - 5005);
                }
                //不知道为什么自动变成默认5s了
            }
        }


        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /*
            两秒内点击两次才退出播放界面
             */
            clickEvent = false;
            if (isHide == false) {
                long currentTime = System.currentTimeMillis();
                if (mLinearLayout02.getVisibility() == View.VISIBLE) {
                    //隐藏菜单 摄像头没有开启成功
                    if (isCaremaShow && isHaveCamere == false) {
                        noCamera.setVisibility(View.INVISIBLE);
                        borderCamera.setVisibility(View.INVISIBLE);
                        surfaceView.setVisibility(View.INVISIBLE);
                        carame.setBackgroundResource(R.drawable.camera_normal);
                        isCarema = false;
                        isHaveCamere = false;
                    }
                    hideMenu();
                }
                if ((currentTime - touchTime) >= waitTime) {
                    j = 0;
                    MyToast.showToast(getApplicationContext(), "再按一次 退出播放", 800);
                    testNetSpeed = false;
                    test_recyclerView.setVisibility(View.INVISIBLE);
                    touchTime = currentTime;
                    switchcraft.setFocusable(true);
                    switchcraft.requestFocus();
                    switchcraft.requestFocusFromTouch();
                    switchcraft.setFocusableInTouchMode(true);
                    return false;
                } else {
                    if (videoView != null) {
                        videoView.stopPlayback();
                        App.getProxy(getApplicationContext()).unregisterCacheListener();
                    }
                    finish();
                    is_resume = false;
                }
            } else {
                /**
                 * 播放的要是广告的话，按返回键时退出广告播放
                 */
                if (videoView != null) {
                    videoView.stopPlayback();
                    proxy.unregisterCacheListener();
                }
                if (isCaremaShow && isHaveCamere == false) {
                    carame.setBackgroundResource(R.drawable.camera_normal);
                    isCarema = false;
                    isHaveCamere = false;
                }
                adsText.setVisibility(View.INVISIBLE);
                imageView01.setVisibility(View.INVISIBLE);
                imageView02.setVisibility(View.INVISIBLE);
                if (isFirstPosition == false) {
                    position1 = position1 + 1;
                    if (position1 >= size) {
                        position1 = 0;
                    }
                    //应该删除
                    index = position1;
                    if (category == 1) {
                        tittle = collectionList.get(position1).getTitle();
                    } else if (category == 2) {
                        tittle = recordList.get(position1).getTitle();
                    } else if (category == 3) {
                        tittle = singList.get(position1).getTitle();
                        conid = singList.get(position1).getConid();
                        play_sid = singList.get(position1).getSid();
                    }
                    position = position1;
                    myAdapter.setIndex(index);
                    myAdapter.notifyDataSetChanged();
                }
                playCurrentVideo();
                isCarema = false;
                isCaremaShow = false;
                isHaveCamere = false;
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
            isPreView = false;
            testNum = testNum - 1;
        }
        //释放动画资源
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (videoView != null) {
            videoView.suspend();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSingleThreadExecutor != null) {
            mSingleThreadExecutor.shutdownNow();
        }

        ActivityCollector.removeActivity(this);
        pic_s = "";
        if (videoView != null && videoView.isPlaying()) {
            videoView.stopPlayback();
        }
        App.getProxy(mContext).unregisterCacheListener();
        timer.cancel();
        mHandler.removeCallbacksAndMessages(null);
        if (is_resume) {
            is_resume = false;
        } else {
            Music.play(mContext, R.raw.bg);
        }
        recycler_play.removeAllViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        videoView.start();
    }

    private void playCurrentVideo() {
        isFirstPosition = false;
        carame.setBackgroundResource(R.drawable.camera_normal);

        progressBar.setVisibility(View.VISIBLE);
        if (animationDrawable != null) {
            animationDrawable.start();
        }
        videoView.stopPlayback();
        proxy.unregisterCacheListener();
        if (urlArray01.length > 0) {
            if (is_player_ijk) {
                videoView.setVideoPath((urlArray01[position1]));
            } else {
                proxy.registerCacheListener(this, urlArray01[position1]);
                videoView.setVideoPath(proxy.getProxyUrl(urlArray01[position1]));
            }
            videoView.start();
            try {
                if (conid != null && !conid.equals("")) {
                    Tools.addPlayNum(getApplicationContext(), conid);
                    umengmap.put("conid", conid);
                }
                if (play_sid != null && !play_sid.equals("")) {
                    umengmap.put("sid", play_sid);
                }
                MobclickAgent.onEvent(mContext, "new_play", umengmap);
                MobclickAgent.onEvent(mContext, "play");
            } catch (Exception e) {

            }
            isNext = false;
            isplayAds = true;
            isHide = false;
        }
    }

    int count = 0;
    Runnable errorPlayCurrentThread = new Runnable() {
        @Override
        public void run() {
            ++count;
            if (count == 10) {
                //播放下一首
                position1 = position + 1;
                if (position1 > size - 1) {
                    position1 = position1 - 1;
                }
                playCurrentVideo();
                myAdapter.setIndex(position1);
                mHandler.removeCallbacks(errorPlayCurrentThread);
            } else {
                playCurrentVideo();
            }
        }
    };

    private void moveToposition(int position) {
        /*
        从外部点击进去时 firstItem、lastItem都为0
         */
        recycler_play.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                firstItem = ((LinearLayoutManager) lm).findFirstVisibleItemPosition();
                lastItem = ((LinearLayoutManager) lm).findLastVisibleItemPosition();
            }
        });
        if (position <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            recycler_play.scrollToPosition(position);
        } else if (firstItem < position && position <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            recycler_play.scrollToPosition(position);
        } else {
//            当要置顶的项在当前显示的最后一项的后面时
            int space = getResources().getDimensionPixelSize(R.dimen.w_208);
            recycler_play.scrollTo(0, space);
//             这里这个变量是用在RecyclerView滚动监听里面的
            recycler_play.scrollToPosition(position);
            move = true;

        }
    }


}