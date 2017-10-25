package com.iqinbao.android.songstv;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ihome.android.market2.aidl.AppOperateAidl;
import com.iqinbao.android.songstv.activity.CollectionActivity;
import com.iqinbao.android.songstv.activity.ImageActivity;
import com.iqinbao.android.songstv.activity.Suggest;
import com.iqinbao.android.songstv.activity.WebActivity;
import com.iqinbao.android.songstv.base.BaseFragmentActivity;
import com.iqinbao.android.songstv.beanstv.AdsEnity;
import com.iqinbao.android.songstv.beanstv.AppOperateReqInfo;
import com.iqinbao.android.songstv.beanstv.SongEntity;
import com.iqinbao.android.songstv.db01.SQLOperateImpl;
import com.iqinbao.android.songstv.music.Music;
import com.iqinbao.android.songstv.task.AsyncUpdate;
import com.iqinbao.android.songstv.task.AsyncUpdateType;
import com.iqinbao.android.songstv.task.GetSongsTask;
import com.iqinbao.android.songstv.task.GetVerTask;
import com.iqinbao.android.songstv.utils.ActivityCollector;
import com.iqinbao.android.songstv.utils.AppInfo;
import com.iqinbao.android.songstv.utils.DataDownload;
import com.iqinbao.android.songstv.utils.ImageLoaderUtils;
import com.iqinbao.android.songstv.utils.Tools;
import com.umeng.analytics.MobclickAgent;

import org.evilbinary.tv.widget.BorderEffect;
import org.evilbinary.tv.widget.BorderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * 首页界面  9.6
 */
public class MainActivity extends BaseFragmentActivity implements AsyncUpdate, View.OnClickListener, Runnable {
        public static final int NUll = 1;  //无操作
        public static final int WEB = 2;     //打开网页
        public static final int SONG = 3;//歌单
        public static final int SING = 4;    //歌曲
        public static final int DISSERTATION = 5;//专题
        public static final int IMAGE_FULL_SCREEN = 6;//全屏显示图片
        private GetSongsTask getSongsTask;
        private GetVerTask getVerTask;
        private Context mContext;
        //private HomeWatcher mHomeWatcher;
        private List<SongEntity.CatContentsBean> cat_context = new ArrayList<SongEntity.CatContentsBean>();
        //列表
        private List<SongEntity> songList = new ArrayList<SongEntity>();
        //焦点图
        List<SongEntity.CatContentsBean> cat_contents_image_focus = new ArrayList<SongEntity.CatContentsBean>();
        //ip—主题
        List<SongEntity.CatContentsBean> cat_contents_ip_dissertation = new ArrayList<SongEntity.CatContentsBean>();
        //主题
        List<SongEntity.CatContentsBean> cat_contents_dissertation = new ArrayList<SongEntity.CatContentsBean>();
        private ImageView imageView_setting_item_fuhao;
        private ImageView imageView_network;
        private TextView textView_time;
        //首页的前半部分
        private RelativeLayout relativeLayout_homepage;
        //多吉
        private ImageView imageView_dissertation_duoji;
        //咕力咕力
        private ImageView imageView_dissertation_guliguli;
        //起司公主
        private ImageView imageView_dissertation_qisigongzhu;
        //查看全部
        private ImageView imageView_all;
        //最近播放
        private ImageView imageView_recent_play;
        private ImageView imageView_1;
        private ImageView imageView_2;
        private ImageView imageView_3;
        private ImageView imageView_4;
        //背景图片
        private ImageView imageView_home;
        //播放记录
        private ImageView imageView_play_record;
        //我的收藏
        private ImageView imageView_mine_collection;
        //主题对应歌单列表
        private LinearLayout linearLayout_image;
        //歌单;
        private SQLOperateImpl sqlOperate;
        List<SongEntity.CatContentsBean> recordList = new ArrayList<SongEntity.CatContentsBean>();
        //map集合存放id与对应所有歌单
        //Map<String, SongEntity> map = new HashMap<String, SongEntity>();
        //四个歌单
        private List<String> list_song_four;
        //小米商店水印
        private ImageView imageView;
        //获取app的渠道名称
        private String channelName="";
        static List<AdsEnity.ContentsBean> list01=new ArrayList<>();
        //四个焦点图
        private List<SongEntity.CatContentsBean> ipDissertationList = new ArrayList<SongEntity.CatContentsBean>();
        //主题
        private List<SongEntity.CatContentsBean> dissertationList = new ArrayList<SongEntity.CatContentsBean>();
        private List<SongEntity.CatContentsBean> focusImageList = new ArrayList<SongEntity.CatContentsBean>();
        private Handler handler;
        private boolean isForNetwork = false;
        private BorderView borderView_item ;
        private RelativeLayout relativeLayout_set;
        private LinearLayout linearLayout;
        private String playurl_h_1 = "", playurl_h_2 = "", playurl_h_3 = "", playurl_h_4 = "";
        private int playurl_1, playurl_2, playurl_3, playurl_4;
        private String catid = "";
        private SQLOperateImpl sqlOperateimpl;
        private RelativeLayout relativeLayout_dissertation;
        private RelativeLayout relativeLayout_imageView_1;
        private boolean flag_image_2 = false;
        private float borderView_11_num;
        private float borderView_item_num;
        private float borderView_dissertation_num;
        private boolean imageView_recent_play_flag = false, imageView_play_record_flag = false, imageView_mine_collection_flag = false,
                imageView_dissertation_duoji_flag = false, imageView_dissertation_guliguli_flag = false, imageView_dissertation_qisigongzhu_flag = false,
                imageView_all_flag_left = false, imageView_all_flag_up = false, imageView_all_flag_right = false, imageView_all_flag_down = false, imageView_1_flag_left = false, imageView_1_flag = false, imageView_2_flag = false,
                imageView_dissertation_duoji_flag_up = false, imageView_dissertation_duoji_flag_down = false, imageView_1_flag_up = false, imageView_2_flag_down_right = false;
        private BorderEffect borderEffect;
        private boolean first_flag = false;
        public static final int TYPE_ETHERNET = 9;
        public static final int TYPE_WIFI = 1;
        private boolean is_resume = false;
        private String conid="";
        private AppOperateAidl appOperateService	= null;
        private final ApkUpdateHandle updater = new ApkUpdateHandle();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mContext = this;
            ActivityCollector.addActivity(this);
            getAdsInfo();
            Intent intent01=getIntent();
            conid=intent01.getStringExtra("sid");
            Music.play(this, R.raw.bg);
            is_resume = true;

            sqlOperateimpl = new SQLOperateImpl(mContext);
            borderView_11_num = getResources().getDimension(R.dimen.w_22);
            borderView_item_num = getResources().getDimension(R.dimen.w_16);
            borderView_dissertation_num = getResources().getDimension(R.dimen.w_5);
            borderView_item = new BorderView(mContext);
            borderEffect = new BorderEffect();
            borderEffect.setMargin((int) -borderView_dissertation_num);
            borderView_item.setEffect(borderEffect);
            borderView_item.setBackgroundResource(R.drawable.border_white_homepage);
            if (first_flag) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        borderEffect.setMargin((int) -borderView_item_num);
                        borderView_item.setEffect(borderEffect);
                    }
                }, 1000);

            }
            imageView_network = (ImageView) findViewById(R.id.imageView_network);
            textView_time = (TextView) findViewById(R.id.textView_time);
            handler = new Handler() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                public void handleMessage(Message msg) {
                    isForNetwork = Tools.checkConnection(mContext);
                    if (isForNetwork) {
                        if (Tools.getType() == TYPE_ETHERNET) {
                            imageView_network.setBackgroundResource(R.drawable.network);
                        } else if (Tools.getType() == TYPE_WIFI) {
                            imageView_network.setBackgroundResource(R.drawable.wifi);
                        } else {
                            imageView_network.setBackgroundResource(R.drawable.network);
                        }
                    } else {
                        imageView_network.setBackgroundResource(R.drawable.network_failed);
                    }
                    textView_time.setText((String) msg.obj);
                }
            };
            new Thread(this).start();

            findViews();
            setViews();
            setListeners();
        }
    private class ApkUpdateHandle extends Handler{
        public void start() {
            sendEmptyMessage(1);
        }

        public void stop() {
            removeMessages(1);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    new AlertDialog.Builder(mContext)
                            .setTitle("提示")
                            .setMessage("检测到有新版本")
                            .setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    appUpdate();
                                }
                            }).show();
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private ServiceConnection appOperateConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            appOperateService = AppOperateAidl.Stub.asInterface(service);
        }
            public void onServiceDisconnected(ComponentName name) {
                appOperateService = null;
            }
        };


        private void turnIntoPlayActivity() {
            if (conid!=null){
                Intent intent=new Intent(MainActivity.this,IjkPlayerActivity.class);
                String position001=""+0;
                intent.putExtra("category",4);
                intent.putExtra("position",position001);
                intent.putExtra("catid",conid);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    String str = sdf.format(new Date());
                    handler.sendMessage(handler.obtainMessage(100, str));
                    Thread.sleep(30000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void findViews() {
            imageView_setting_item_fuhao = (ImageView) findViewById(R.id.imageView_setting_item_fuhao);
            relativeLayout_set = (RelativeLayout) findViewById(R.id.relativeLayout_set);
            relativeLayout_homepage = (RelativeLayout) findViewById(R.id.relativeLayout_homepage);
            borderView_item.attachTo(relativeLayout_homepage);
            relativeLayout_dissertation = (RelativeLayout) findViewById(R.id.relativeLayout_dissertation);
            borderView_item.attachTo(relativeLayout_dissertation);
            relativeLayout_imageView_1 = (RelativeLayout) findViewById(R.id.relativeLayout_imageView_1);
            borderView_item.attachTo(relativeLayout_imageView_1);
            imageView_dissertation_duoji = (ImageView) findViewById(R.id.imageView_dissertation_duoji);




            //小米渠道：需要在首页上显示小米商店的水印
            imageView= (ImageView) findViewById(R.id.imageView);
            channelName= AppInfo.getChannelName(getApplication());
            if (channelName.equals("tv1")){
                imageView.setVisibility(View.VISIBLE);
            }else {
                imageView.setVisibility(View.GONE);
            }


            /**
             * 对背景图片进形裁剪，防止出现OOM异常
             */
            imageView_home= (ImageView) findViewById(R.id.home_image);
    //        Picasso.with(mContext).load(R.drawable.homepage).resize(960,540).centerCrop().into(imageView_home);
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=2;
            Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.homepage,options);
            imageView_home.setImageBitmap(bitmap);
            imageView_dissertation_duoji.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    imageView_dissertation_duoji.setNextFocusUpId(R.id.imageView_dissertation_duoji);
                    if (imageView_dissertation_duoji_flag) {
                        borderEffect.setMargin((int) -borderView_11_num);
                        borderView_item.setEffect(borderEffect);
                        imageView_dissertation_duoji_flag = false;
                    } else if (imageView_dissertation_duoji_flag_down) {
                        borderEffect.setMargin((int) -borderView_dissertation_num);
                        borderView_item.setEffect(borderEffect);
                        imageView_dissertation_duoji_flag_down = false;
                    } else {
                        borderEffect.setMargin((int) -borderView_item_num);
                        borderView_item.setEffect(borderEffect);
                        imageView_dissertation_duoji_flag_up = true;
                    }


                }
            });
            imageView_dissertation_guliguli = (ImageView) findViewById(R.id.imageView_dissertation_guliguli);
            imageView_dissertation_guliguli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (imageView_dissertation_guliguli_flag) {
                        borderEffect.setMargin((int) -borderView_11_num);
                        borderView_item.setEffect(borderEffect);
                        imageView_dissertation_guliguli_flag = false;
                    } else {
                        borderEffect.setMargin((int) -borderView_dissertation_num);
                        borderView_item.setEffect(borderEffect);
                    }
                }
                //  }
            });
            imageView_dissertation_qisigongzhu = (ImageView) findViewById(R.id.imageView_dissertation_qisigongzhu);
            imageView_dissertation_qisigongzhu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (imageView_dissertation_qisigongzhu_flag) {
                        borderEffect.setMargin((int) -borderView_11_num);
                        borderView_item.setEffect(borderEffect);
                        imageView_dissertation_qisigongzhu_flag = false;
                    } else {
                        borderEffect.setMargin((int) -borderView_dissertation_num);
                        borderView_item.setEffect(borderEffect);
                    }
                }
            });
            imageView_all = (ImageView) findViewById(R.id.imageView_all);
            imageView_all.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        borderEffect.setMargin((int) -borderView_dissertation_num);
                        borderView_item.setEffect(borderEffect);
                        imageView_all_flag_right = false;
                        imageView_all_flag_down = false;
                    } else {
                        if (imageView_all_flag_right) {
                            borderEffect.setMargin((int) -borderView_11_num);
                            borderView_item.setEffect(borderEffect);
                            imageView_all_flag_right = false;

                        } else if (imageView_all_flag_down) {
                            borderEffect.setMargin((int) -borderView_item_num);
                            borderView_item.setEffect(borderEffect);
                            imageView_all_flag_down = false;
                        }
                    }
                }
            });
            imageView_recent_play = (ImageView) findViewById(R.id.imageView_recent_play);
            imageView_recent_play.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (imageView_recent_play_flag) {
                        borderEffect.setMargin((int) -borderView_dissertation_num);
                        borderView_item.setEffect(borderEffect);
                        imageView_recent_play_flag = false;
                    } else {
                        borderEffect.setMargin((int) -borderView_item_num);
                        borderView_item.setEffect(borderEffect);
                    }
                }
            });
            imageView_1 = (ImageView) findViewById(R.id.imageView_1);
            imageView_1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (imageView_1_flag) {
                        borderEffect.setMargin((int) -borderView_item_num);
                        borderView_item.setEffect(borderEffect);
                        imageView_1_flag = false;
                    } else if (imageView_1_flag_left) {
                        borderEffect.setMargin((int) -borderView_dissertation_num);
                        borderView_item.setEffect(borderEffect);
                        imageView_1_flag_left = false;
                    } else if (imageView_1_flag_up) {
                        borderEffect.setMargin((int) -borderView_item_num);
                        borderView_item.setEffect(borderEffect);
                    } else {
                        borderEffect.setMargin((int) -borderView_item_num);
                        borderView_item.setEffect(borderEffect);
                    }
                }
            });
            imageView_2 = (ImageView) findViewById(R.id.imageView_2);

            imageView_3 = (ImageView) findViewById(R.id.imageView_3);
            imageView_3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    borderEffect.setMargin((int) -borderView_item_num);
                    borderView_item.setEffect(borderEffect);
                }
            });
            imageView_4 = (ImageView) findViewById(R.id.imageView_4);
            imageView_4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    borderEffect.setMargin((int) -borderView_item_num);
                    borderView_item.setEffect(borderEffect);
                }
            });
            imageView_play_record = (ImageView) findViewById(R.id.imageView_play_record);
            imageView_play_record.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (imageView_play_record_flag) {
                        borderEffect.setMargin((int) -borderView_11_num);
                        borderView_item.setEffect(borderEffect);
                        imageView_play_record_flag = false;
                    } else {
                        borderEffect.setMargin((int) -borderView_item_num);
                        borderView_item.setEffect(borderEffect);
                    }
                }
                //   }
            });
            imageView_mine_collection = (ImageView) findViewById(R.id.imageView_mine_collection);
            imageView_mine_collection.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (imageView_mine_collection_flag) {
                        borderEffect.setMargin((int) -borderView_11_num);
                        borderView_item.setEffect(borderEffect);
                        imageView_mine_collection_flag = false;
                    } else {
                        borderEffect.setMargin((int) -borderView_item_num);
                        borderView_item.setEffect(borderEffect);
                    }
                }
                //  }
            });

            final BorderView borderView11 = new BorderView(mContext);

            relativeLayout_set.setFocusable(false);
            borderView11.attachTo(relativeLayout_set);
            imageView_setting_item_fuhao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    imageView_setting_item_fuhao.setNextFocusLeftId(R.id.imageView_2);
                    if (hasFocus) {
                        imageView_setting_item_fuhao.setBackgroundResource(R.color.transparent);
                        borderView11.setBackgroundResource(R.drawable.set_select_1);
                        borderView_item.setBackgroundResource(R.drawable.border_white_homepage);
                    } else {
                        imageView_setting_item_fuhao.setBackgroundResource(R.drawable.set);
                        borderView11.setBackgroundResource(R.color.transparent);
                        borderEffect.setMargin((int) -borderView_item_num);
                        borderView_item.setEffect(borderEffect);

                    }
                }
            });
            imageView_2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    imageView_2.setNextFocusUpId(R.id.imageView_setting_item_fuhao);
                    imageView_2.setNextFocusRightId(R.id.imageView_setting_item_fuhao);
                    if (imageView_2_flag) {
                        borderEffect.setMargin((int) -borderView_11_num);
                        borderView_item.setEffect(borderEffect);
                        imageView_2_flag = false;
                    } else if (imageView_2_flag_down_right) {
                        borderEffect.setMargin((int) -borderView_item_num);
                        borderView_item.setEffect(borderEffect);
                    } else {
                        borderEffect.setMargin((int) -borderView_item_num);
                        borderView_item.setEffect(borderEffect);
                    }
                }
                // }
            });
            linearLayout_image = (LinearLayout) findViewById(R.id.linearLayout_image);
            ImageLoaderUtils.display_three_dissertation(MainActivity.this, imageView_dissertation_duoji, R.drawable.duojiduoli, R.drawable.duojiduoli, 242, 110);
            ImageLoaderUtils.display_three_dissertation(MainActivity.this, imageView_dissertation_guliguli, R.drawable.guliguli, R.drawable.guliguli, 242, 110);
            ImageLoaderUtils.display_three_dissertation(MainActivity.this, imageView_dissertation_qisigongzhu, R.drawable.qisigongzhu, R.drawable.qisigongzhu, 242, 110);
            ImageLoaderUtils.display_three_dissertation(MainActivity.this, imageView_all, R.drawable.gedan_all, R.drawable.gedan_all, 242, 110);
        }

        @Override
        protected void setViews() {
            getVerTask();
        }

        void getSongsTask() {
            getSongsTask = new GetSongsTask(this, MainActivity.this, AsyncUpdateType.GET_SONG_LIST_TASK);
            getSongsTask.setShowProgressDialog(true);
            getSongsTask.execute();
        }

        /**
         * 获得版本信息
         * */
        void getVerTask(){
            getVerTask = new GetVerTask(this, MainActivity.this, AsyncUpdateType.GET_VER_TASK);
            getVerTask.setShowProgressDialog(true);
            getVerTask.execute();
        }

        void getAdsInfo(){
            DataDownload data = new DataDownload();
            data.downloadData();
            data.setListCallBack(new DataDownload.ListCallBack() {
                @Override
                public void getLists(List<AdsEnity.ContentsBean> list) {
                    list01=list;
                }
            });
        }

        @Override
        protected void setListeners() {
            imageView_setting_item_fuhao.setOnClickListener(this);
            imageView_dissertation_duoji.setOnClickListener(this);
            imageView_dissertation_guliguli.setOnClickListener(this);
            imageView_dissertation_qisigongzhu.setOnClickListener(this);
            imageView_all.setOnClickListener(this);
            imageView_recent_play.setOnClickListener(this);
            imageView_1.setOnClickListener(this);
            imageView_2.setOnClickListener(this);
            imageView_3.setOnClickListener(this);
            imageView_4.setOnClickListener(this);
            imageView_play_record.setOnClickListener(this);
            imageView_mine_collection.setOnClickListener(this);

        }

        public static List<AdsEnity.ContentsBean>  getAdasInfo(){
            return list01;
        }



        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView_setting_item_fuhao:
                    Intent intent_setting = new Intent(MainActivity.this, Suggest.class);
                    is_resume = true;
                    startActivity(intent_setting);
                    break;
                case R.id.imageView_dissertation_duoji:
                    Intent intent_duoji = new Intent(MainActivity.this, DissertationActivityNew.class);
                    is_resume = true;
                    String title12 = "";
                    String playurl2 = "";
                    if (ipDissertationList.size() != 0) {
                        playurl2 = ipDissertationList.get(2).getPlayurl();
                        title12 = ipDissertationList.get(2).getTitle();
                    }
                    intent_duoji.putExtra("title", title12);
                    intent_duoji.putExtra("playurl", playurl2);
                    startActivity(intent_duoji);
                    break;
                case R.id.imageView_dissertation_guliguli:
                    is_resume = true;
                    Intent intent_guliguli = new Intent(MainActivity.this, DissertationActivityNew.class);
                    String playurl1 = "";
                    String title11 = "";
                    if (ipDissertationList.size() != 0) {
                        playurl1 = ipDissertationList.get(1).getPlayurl();
                        title11 = ipDissertationList.get(1).getTitle();
                    }
                    intent_guliguli.putExtra("playurl", playurl1);
                    intent_guliguli.putExtra("title", title11);
                    startActivity(intent_guliguli);
                    break;
                case R.id.imageView_dissertation_qisigongzhu:
                    is_resume = true;
                    Intent intent_qisigonzhu = new Intent(MainActivity.this, DissertationActivityNew.class);
                    String playurl0 = "";
                    String title10 = "";
                    if (ipDissertationList.size() != 0) {
                        playurl0 = ipDissertationList.get(0).getPlayurl();
                        title10 = ipDissertationList.get(0).getTitle();
                    }
                    intent_qisigonzhu.putExtra("title", title10);
                    intent_qisigonzhu.putExtra("playurl", playurl0);
                    startActivity(intent_qisigonzhu);
                    break;
                case R.id.imageView_all:
                    is_resume = true;
                    Intent intent_all = new Intent(MainActivity.this, DissertationActivityNew.class);
                    String playurlall = "0";
                    intent_all.putExtra("playurl", playurlall);
                    startActivity(intent_all);
                    break;
                case R.id.imageView_recent_play:
                    try {
                        if (sqlOperateimpl.queryRecord() == null) {

                        } else {
                            recordList = sqlOperateimpl.queryRecord();
                            if (recordList.size() > 0) {
                                Intent intent = new Intent(MainActivity.this, IjkPlayerActivity.class);
                                intent.putExtra("category", 2);
                               // int current = recordList.size() - 1;
                                String position = 0 + "";
                                intent.putExtra("position", position);
                                startActivity(intent);
                            } else if (catid.equals("")) {

                            } else {
                                Intent intent = new Intent(MainActivity.this, IjkPlayerActivity.class);
                                String position = 0 + "";
                                int category = 3;
                                intent.putExtra("category", category);
                                intent.putExtra("catid", catid);
                                intent.putExtra("position", position);
                                startActivity(intent);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.imageView_1:
                    try {
                        showForType(playurl_1, playurl_h_1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.imageView_2:
                    try {
                        showForType(playurl_2, playurl_h_2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.imageView_3:
                    try {
                        showForType(playurl_3, playurl_h_3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.imageView_4:
                    try {
                        showForType(playurl_4, playurl_h_4);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.imageView_play_record:
                    is_resume = true;
                    Intent intent_play_record = new Intent(MainActivity.this, CollectionActivity.class);
                    intent_play_record.putExtra("which", String.valueOf(2));
                    startActivity(intent_play_record);
                    break;
                case R.id.imageView_mine_collection:
                    is_resume = true;
                    Intent intent_collection = new Intent(MainActivity.this, CollectionActivity.class);
                    intent_collection.putExtra("which", String.valueOf(1));
                    startActivity(intent_collection);
                    break;
            }
        }

        /**
         * 获取到不同的type值，执行不同的点击事件。9.7 fuhao
         *
         * @param type
         */
        private void showForType(int type, String playurl_h) throws Exception {
            switch (type) {
                case NUll:
                    break;
                case WEB:
                    if (playurl_h!=null){
                        Intent intent_web = new Intent(MainActivity.this, WebActivity.class);
                        intent_web.putExtra("playurl", playurl_h);
                        startActivity(intent_web);
                    }
                    break;
                case SONG:
                    if (sqlOperateimpl!=null) {
                        if (sqlOperateimpl.queryForSong(playurl_h) != null) {
                            Intent intent_songlist = new Intent(MainActivity.this, IjkPlayerActivity.class);
                            String position = 0 + "";
                            int category = 3;
                            intent_songlist.putExtra("category", category);
                            intent_songlist.putExtra("catid", playurl_h);
                            intent_songlist.putExtra("position", position);
                            startActivity(intent_songlist);
                        }
                    }
                    break;
                case SING:
                   if (sqlOperateimpl!=null){
                       if ( sqlOperateimpl.queryForSingConid(playurl_h)!=null){
                           SongEntity.CatContentsBean catContentsBean = sqlOperateimpl.queryForSingConid(playurl_h);
                           String catid="";
                           if (catContentsBean.getCatid()!=null){
                               catid = catContentsBean.getCatid();
                               List<SongEntity.CatContentsBean> singList1 = sqlOperateimpl.queryForSing(catid);

                               if (singList1!=null){
                                   String position_sing = 0 + "";
                                   if (singList1.size()>0){
                                       for (int i = 0; i < singList1.size(); i++) {
                                           if (singList1.get(i).getConid().equals(playurl_h)) {
                                               position_sing = String.valueOf(i);
                                               break;
                                           }
                                       }
                                   }
                                   Intent intent_sing = new Intent(MainActivity.this, IjkPlayerActivity.class);
                                   int category_sing = 3;
                                   intent_sing.putExtra("category", category_sing);
                                   intent_sing.putExtra("catid", catid);
                                   intent_sing.putExtra("position", position_sing);
                                   startActivity(intent_sing);
                               }
                           }
                       }


                   }
                    break;
                case DISSERTATION:
                    is_resume = true;
                    if (sqlOperateimpl!=null){
                        if ( sqlOperateimpl.queryForDissertation(playurl_h)!=null){
                            SongEntity.CatContentsBean dissertation = sqlOperateimpl.queryForDissertation(playurl_h);
                            if (dissertation!=null){

                                String playurl = dissertation.getPlayurl();
                                String title = dissertation.getTitle();
                                Intent intent_dissertation = new Intent(MainActivity.this, DissertationActivityNew.class);
                                intent_dissertation.putExtra("title", title);
                                intent_dissertation.putExtra("playurl", playurl);
                                startActivity(intent_dissertation);
                            }
                        }
                    }

                    break;
                case IMAGE_FULL_SCREEN:
                    if (playurl_h!=null){
                        Intent intent_image_full_screen = new Intent(MainActivity.this, ImageActivity.class);
                        intent_image_full_screen.putExtra("playurl_h", playurl_h);
                        startActivity(intent_image_full_screen);
                    }
                    break;


            }
        }

        @Override
        public void updateViews(int asyncUpdateType, int result) {
            try {
                if(asyncUpdateType == AsyncUpdateType.GET_VER_TASK){
                    Bundle args = new Bundle();
                    Intent intent = new Intent(AppOperateAidl.class.getName());
                    intent.putExtras(args);
                    bindService(intent,appOperateConn,Context.BIND_AUTO_CREATE);
                    if(result == SUCCESS){
                        getSongsTask();
                        appCheckUpdate();
                    }
                    else if(result == NO_MESSAGE){
                        if (songList != null) {
                            initView();
                            appCheckUpdate();
                        }
                    }else if (result == NO_NETWORK) {
                        showToast(R.string.no_network_txt);
                        if (songList != null) {
                            initView();
                        }
                    }
                    else{
                        //第一次失败
                        if(songList.size() == 0 ){
    //                        Tools.showToast(mContext, "2222");
                            getSongsTask();
                        }
                        else{
                            showToast(R.string.error_message_txt);
                        }
                    }
                }
                else if(asyncUpdateType == AsyncUpdateType.GET_SONG_LIST_TASK){
                    if (result == SUCCESS) {
                        cat_contents_image_focus = getSongsTask.getCat_contents_image_focus();
                        cat_contents_ip_dissertation = getSongsTask.getCat_contents_ip_dissertation();
                        cat_contents_dissertation = getSongsTask.getCat_contents_dissertation();
                        songList = getSongsTask.getSongEntity_list();
                        initView();

                    } else if (result == NO_MESSAGE) {
                        showToast(R.string.no_message_txt);
                        if (songList != null) {
                            initView();
                        }
                    } else if (result == ERROR) {
                        showToast(R.string.error_message_txt);
                        if (songList != null) {
                            initView();
                        }
                    } else if (result == NO_NETWORK) {
                        showToast(R.string.no_network_txt);
                        if (songList != null) {
                            initView();
                        }
                    } else if (result == FAIL) {
                        showToast(R.string.error_message_txt);
                        if (songList != null) {
                            initView();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        private void appCheckUpdate() {
            new Thread() {
                @Override
                public void run() {
                    try {
                        if (appOperateService == null) {
                            Intent buyservice = new Intent(AppOperateAidl.class.getName());
                            bindService(buyservice, appOperateConn, Context.BIND_AUTO_CREATE);
                            sleep(2000);
                        }
                        if (appOperateService != null) {
                            boolean isNeedUpdate = appOperateService.appUpdateCheck();
                            if (isNeedUpdate==false) {

                            }else{
                                updater.start();
                            }
                        }
                        else {
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Thread.currentThread().interrupt();
                    }
                }
            }.start();

        }

        private void appUpdate() {
            final AppOperateReqInfo info = new AppOperateReqInfo("customAPPIDspS365920000000000000001111", "UPDATE","");
            new Thread() {
                @Override
                public void run() {
                    try {
                        if (appOperateService == null) {
                            Intent oprateservice = new Intent(AppOperateAidl.class.getName());
                            bindService(oprateservice, appOperateConn, Context.BIND_AUTO_CREATE);
                            sleep(2000);
                        }

                        if (appOperateService != null) {
                            String result = appOperateService.appOperate(new Gson().toJson(info));
                            if (result != null && !TextUtils.isEmpty(result)) {
                            }else{
                            }
                        }



                        else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Thread.currentThread().interrupt();
                    }
                }
            }.start();

        }

        private void initView() throws Exception {

            if (sqlOperateimpl.query(SQLOperateImpl.IP_Dissertation) != null) {
                ipDissertationList = sqlOperateimpl.query(SQLOperateImpl.IP_Dissertation);
            }
            if (sqlOperateimpl.query(SQLOperateImpl.FocusImage) != null) {
                focusImageList = sqlOperateimpl.query(SQLOperateImpl.FocusImage);
            }
            if (focusImageList.size() == 4) {
                String pic_s_4 = focusImageList.get(0).getPic_s();
                playurl_h_4 = focusImageList.get(0).getPlayurl_h();
                playurl_4 = Integer.parseInt(focusImageList.get(0).getPlayurl());
                String pic_s_3 = focusImageList.get(1).getPic_s();
                playurl_h_3 = focusImageList.get(1).getPlayurl_h();
                playurl_3 = Integer.parseInt(focusImageList.get(1).getPlayurl());
                String pic_s_2 = focusImageList.get(2).getPic_s();
                playurl_h_2 = focusImageList.get(2).getPlayurl_h();
                playurl_2 = Integer.parseInt(focusImageList.get(2).getPlayurl());
                String pic_s_1 = focusImageList.get(3).getPic_s();
                playurl_h_1 = focusImageList.get(3).getPlayurl_h();
                playurl_1 = Integer.parseInt(focusImageList.get(3).getPlayurl());

                ImageLoaderUtils.display_foucus(MainActivity.this, imageView_1, pic_s_1, R.drawable.image_1);
                ImageLoaderUtils.display_foucus(MainActivity.this, imageView_2, pic_s_2, R.drawable.image_2);
                ImageLoaderUtils.display_foucus(MainActivity.this, imageView_3, pic_s_3, R.drawable.image_3);
                ImageLoaderUtils.display_foucus(MainActivity.this, imageView_4, pic_s_4, R.drawable.image_4);

            }
            if (sqlOperateimpl.query(SQLOperateImpl.Dissertation) != null) {
                dissertationList = sqlOperateimpl.query(SQLOperateImpl.Dissertation);
            }
            for (int i = 0; i < dissertationList.size(); i++) {
                String song_four = dissertationList.get(i).getPlayurl_h();
                list_song_four = Tools.gainIDFromString(song_four);
                linearLayout_image.addView(addView1(i));
                borderView_item.attachTo(linearLayout);
            }
            if (!first_flag) {
                first_flag = true;
                imageView_recent_play.setFocusable(first_flag);
                imageView_recent_play.requestFocus();
            }
            turnIntoPlayActivity();
        }


        private void showToast(int resources) {
            Tools.showToast(mContext, getResources().getString(resources));
        }

        /**
         * 对应主题歌单的添加。9.8 fuhao
         *
         * @return
         */
        private View addView1(final int i) throws Exception {
            // TODO 动态添加布局(xml方式)
            LayoutParams lp = new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            LayoutInflater inflater3 = LayoutInflater.from(mContext);
            View view = inflater3.inflate(R.layout.imageview_list, null);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout_list_image);
            ImageView imageView_item = (ImageView) view.findViewById(R.id.imageView_item);
            LinearLayout relativeLayout_1 = (LinearLayout) view.findViewById(R.id.relativeLayout_1);
            ImageView imageView1 = (ImageView) view.findViewById(R.id.imageView1);
            TextView textView_name_1 = (TextView) view.findViewById(R.id.textView_name1);
            LinearLayout relativeLayout_2 = (LinearLayout) view.findViewById(R.id.relativeLayout_2);
            ImageView imageView2 = (ImageView) view.findViewById(R.id.imageView2);
            TextView textView_name_2 = (TextView) view.findViewById(R.id.textView_name2);
            LinearLayout relativeLayout_3 = (LinearLayout) view.findViewById(R.id.relativeLayout_3);
            ImageView imageView3 = (ImageView) view.findViewById(R.id.imageView3);
            TextView textView_name_3 = (TextView) view.findViewById(R.id.textView_name3);
            LinearLayout relativeLayout_4 = (LinearLayout) view.findViewById(R.id.relativeLayout_4);
            ImageView imageView4 = (ImageView) view.findViewById(R.id.imageView4);
            TextView textView_name_4 = (TextView) view.findViewById(R.id.textView_name4);
            String pic_s = dissertationList.get(i).getPic_s();
            ImageLoaderUtils.display_dissertation(mContext, imageView_item, pic_s, 244, 250);
            String s1 = list_song_four.get(0);
            SongEntity songs1 = sqlOperateimpl.queryForSong(s1);
            final String catid1 = songs1.getCatid();
            catid = catid1;
            String catpic1 = songs1.getCatpic();
            String catname1 = songs1.getCatname();
            textView_name_1.setText(catname1);
            ImageLoaderUtils.display2(this, imageView1, catpic1, 480, 270);
            relativeLayout_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, IjkPlayerActivity.class);
                    String position = 0 + "";
                    int category = 3;
                    intent.putExtra("category", category);
                    intent.putExtra("catid", catid1);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });

            String s2 = list_song_four.get(1);
            SongEntity songs2 = sqlOperateimpl.queryForSong(s2);
            final String catid2 = songs2.getCatid();
            String catpic2 = songs2.getCatpic();
            String catname2 = songs2.getCatname();
            textView_name_2.setText(catname2);
            ImageLoaderUtils.display2(this, imageView2, catpic2, 480, 270);
            relativeLayout_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, IjkPlayerActivity.class);
                    String position = 0 + "";
                    int category = 3;
                    intent.putExtra("category", category);
                    intent.putExtra("catid", catid2);
                    intent.putExtra("position", position);

                    startActivity(intent);
                }
            });

            String s3 = list_song_four.get(2);
            SongEntity songs3 = sqlOperateimpl.queryForSong(s3);
            final String catid3 = songs3.getCatid();
            String catpic3 = songs3.getCatpic();
            String catname3 = songs3.getCatname();
            textView_name_3.setText(catname3);
            ImageLoaderUtils.display2(this, imageView3, catpic3, 480, 270);
            relativeLayout_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, IjkPlayerActivity.class);
                    String position = 0 + "";
                    int category = 3;
                    intent.putExtra("category", category);
                    intent.putExtra("catid", catid3);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
            String s4 = list_song_four.get(3);
            SongEntity songs4 = sqlOperateimpl.queryForSong(s4);
            final String catid4 = songs4.getCatid();
            String catpic4 = songs4.getCatpic();
            String catname4 = songs4.getCatname();
            textView_name_4.setText(catname4);
            ImageLoaderUtils.display2(this, imageView4, catpic4, 480, 270);

            relativeLayout_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, IjkPlayerActivity.class);
                    String position = 0 + "";
                    int category = 3;
                    intent.putExtra("category", category);
                    intent.putExtra("catid", catid4);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
            imageView_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DissertationActivityNew.class);
                    String playurl = dissertationList.get(i).getPlayurl();
                    String title = dissertationList.get(i).getTitle();
                    is_resume = true;
                    intent.putExtra("title", title);
                    intent.putExtra("playurl", playurl);
                    startActivity(intent);
                }
            });
            view.setLayoutParams(lp);
            return view;

        }
        boolean isFirst_second = true;

        /**
         * 退出处理。
         *
         * @param
         * @param event
         * @return
         */
        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            if (isFirst_second) {
    //            Music.play_key();
                isFirst_second = false;
            } else {
                isFirst_second = true;
            }
            // 如果是返回键,直接返回到桌面
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                showExitGameAlert();
            } else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
                imageView_recent_play_flag = true;
                imageView_play_record_flag = true;
                imageView_mine_collection_flag = true;
                imageView_dissertation_duoji_flag_up = true;
                imageView_1_flag_up = true;
                imageView_all_flag_up = true;
            } else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                imageView_all_flag_down = true;
                imageView_1_flag = true;
                imageView_dissertation_duoji_flag_down = true;
                imageView_2_flag_down_right = true;

            } else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                imageView_1_flag_left = true;
                imageView_2_flag = true;
                imageView_dissertation_duoji_flag_down = true;
                imageView_all_flag_left = true;
            } else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                imageView_dissertation_duoji_flag = true;
                imageView_dissertation_guliguli_flag = true;
                imageView_dissertation_qisigongzhu_flag = true;
                imageView_all_flag_right = true;
                imageView_1_flag = true;
                imageView_2_flag_down_right = true;
            }
            return super.dispatchKeyEvent(event);
        }

        AlertDialog dlg;
        private void showExitGameAlert() {
             dlg = new AlertDialog.Builder(this, R.style.dialog).create();
            if (dlg!=null){
                dlg.show();
            }
            final Window window = dlg.getWindow();
    // *** 主要就是在这里实现这种效果的.
    // 设置窗口的内容页面,show_exit_dialog.xml文件中定义view内容

            window.setContentView(R.layout.show_exit_dialog);
            RelativeLayout relativeLayout_exit = (RelativeLayout) window.findViewById(R.id.relativeLayout_exit);
            BorderView borderView = new BorderView(mContext);
            BorderEffect borderEffect_tan = new BorderEffect();
            borderEffect_tan.setScale(1.2f);
            borderView.setEffect(borderEffect_tan);
            borderView.attachTo(relativeLayout_exit);

    // 为确认按钮添加事件,执行退出应用操作
            final ImageView ok = (ImageView) window.findViewById(R.id.btn_ok);
            ok.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    // System.exit(0);
                    if(dlg != null)
                    dlg.dismiss();
                    Music.stop(mContext);
                    ActivityCollector.finishAll();
                    //退出程序，销毁所占用的内存
                    MobclickAgent.onKillProcess(mContext);
                    System.exit(0);
                   // MainActivity.this.finish();
                    //android.os.Process.killProcess(android.os.Process.myPid());
                }
            });

            // 关闭alert对话框架
            final ImageView cancel = (ImageView) window.findViewById(R.id.btn_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(dlg != null) {
                        dlg.cancel();
                    }

                }
            });
        }

        @Override
        protected void onPause() {
            // TODO Auto-generated method stub
            super.onPause();
            if (is_resume) {
                is_resume = false;
            } else {
              //  mHomeWatcher.stopWatch();// 在onPause中停止监听，不然会报错的。
               Music.pause(mContext);
            }
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if(appOperateService != null){
                //取消绑定
                unbindService(appOperateConn);
            }
            ActivityCollector.removeActivity(this);
            /**
             * 退出界面时，对图片进形回收
             */
           // mHomeWatcher.stopWatch();// 在onPause中停止监听，不然会报错的。
            Music.stop(mContext);
            updater.stop();
            if(dlg != null) {
                try {
                    dlg.dismiss();
                } catch (Exception e) {
                    System.out.println("myDialog取消，失败！");
                    // TODO: handle exception
                }
            }
        }

        @Override
        protected void onResume() {
            // TODO Auto-generated method stub
            super.onResume();
            if (is_resume) {
                is_resume = false;
            } else {
                Music.restart(mContext);
            }
        }
}
