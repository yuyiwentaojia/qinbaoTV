package com.iqinbao.android.songstv.activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iqinbao.android.songstv.IjkPlayerActivity;
import com.iqinbao.android.songstv.R;
import com.iqinbao.android.songstv.adapter.CollectionAdapter;
import com.iqinbao.android.songstv.adapter.RecordAdapter;
import com.iqinbao.android.songstv.base.BaseFragmentActivity;
import com.iqinbao.android.songstv.beanstv.SongEntity;
import com.iqinbao.android.songstv.bridge.EffectNoDrawBridge;
import com.iqinbao.android.songstv.db01.SQLOperateImpl;
import com.iqinbao.android.songstv.music.Music;
import com.iqinbao.android.songstv.utils.ActivityCollector;
import com.iqinbao.android.songstv.utils.MyToast;
import com.iqinbao.android.songstv.view.GridViewTV;
import com.iqinbao.android.songstv.view.MainUpView;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends BaseFragmentActivity{
    private Context mContext;
    private TextView textView;
    private ImageView imageView;
    private SQLOperateImpl sqlOperate;
    private boolean isFirst=true;
    private int index = 1;
    private boolean isRecordClick=false;
    private List<SongEntity.CatContentsBean> collectionList=new ArrayList<SongEntity.CatContentsBean>();
    private List<SongEntity.CatContentsBean> recordList=new ArrayList<SongEntity.CatContentsBean>();
    RecordAdapter recordAdapter;
    CollectionAdapter collectionAdapter;
    private MainUpView mainUpView1;
    GridViewTV gridView;
    TextView bt_record_txt;
    private View mOldView;
    private RelativeLayout relativeLayout;
    int x = 0;
    boolean isDelele = false;
    private boolean is_resume=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        mContext=this;
        ActivityCollector.addActivity(this);
//        Music.key_tone(mContext);
        is_resume=true;
        try {
            gainDataFromDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        findView();
        initRecyclerView();
    }

    private void gainDataFromDB() throws Exception {
        sqlOperate=new SQLOperateImpl(mContext);
        collectionList=sqlOperate.queryCollection();
        recordList=sqlOperate.queryRecord();
        int size01=recordList.size();
    }

    private void findView() {
        textView= (TextView) findViewById(R.id.textView4);
        gridView = (GridViewTV) findViewById(R.id.gridView);
        mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
        imageView= (ImageView) findViewById(R.id.collect_image);
//        relativeLayout= (RelativeLayout) findViewById(R.id.collect_line);
//        relativeLayout.setBackgroundResource(R.drawable.otherpage1);
        /**
         * 对图片进行裁剪，防止OOM异常
         */
//        Picasso.with(this).load(R.drawable.otherpage).resize(960,540).centerCrop().into(imageView);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=1;
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.otherpage1,options);
        imageView.setImageBitmap(bitmap);
        // 建议使用 NoDraw.
        mainUpView1.setEffectBridge(new EffectNoDrawBridge());
        EffectNoDrawBridge bridget = (EffectNoDrawBridge) mainUpView1.getEffectBridge();
        bridget.setDrawShadowPadding(-14);
        bridget.setTranDurAnimTime(200);
        // 设置移动边框的图片.
//        mainUpView1.setUpRectResource(R.drawable.border_collection_white);
//        mainUpView1.setUpRectDrawable(ImageLoaderUtils.display(CollectionActivity.this));
//        mainUpView1.setUpRectDrawable(getResources().getDrawable(R.drawable.ic_image_loading));
//        mainUpView1.setDrawUpRectPadding(-12);
        // 移动方框缩小的距离.
//        mainUpView1.setDrawUpRectPadding(new Rect(10, 10, 10, 10));
//        mainUpView1.setDrawShadowRectPadding(new Rect(-20, -20, -20, -20));

        bt_record_txt= (TextView) findViewById(R.id.bt_record_txt);

        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 这里注意要加判断是否为NULL.
                 * 因为在重新加载数据以后会出问题.
                 */
                if(mOldView != null){
                    mOldView.findViewById(R.id.border_lin).setBackgroundResource(0);
                }
                if (view != null) {
                    x = position;
//                    view.setBackgroundResource(R.drawable.border_collection_white);
                    view.findViewById(R.id.border_lin).setBackgroundResource(R.drawable.border_collection_white);
                    mainUpView1.setFocusView(view, mOldView, 1.1f);
                }
                mOldView = view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(isDelele) {
                    if (isRecordClick == true) {
                        deleteRecordItem(position);
                        recordAdapter.notifyData(position);
                        int count = recordAdapter.getCount();
                        if(count ==0){
                            mainUpView1.setVisibility(View.GONE);
                        }
                    }
                    else{
                        deleteCollectionItem(position);
                        collectionAdapter.notifyData(position);
                        int count = collectionAdapter.getCount();
                        if(count ==0){
                            mainUpView1.setVisibility(View.GONE);
                        }
                    }
                    MyToast.showToast(getApplicationContext(),"删除成功",800);
                }else {
                    if (isRecordClick==true) {
                        Intent intent = new Intent(CollectionActivity.this, IjkPlayerActivity.class);
                        intent.putExtra("position",String.valueOf(position));
                        int text=position;
                        intent.putExtra("category",2);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(CollectionActivity.this, IjkPlayerActivity.class);
                        intent.putExtra("position",String.valueOf(position));
                        intent.putExtra("category",1);
                        startActivity(intent);
                    }

                }
            }
        });

        mFirstHandler.sendMessageDelayed(mFirstHandler.obtainMessage(), 200);
    }

    // 延时请求初始位置的item.
    Handler mFirstHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            gridView.setDefualtSelect(0);
        }
    };

    /**
     * 初始化
     * */
    private void initRecyclerView() {
        Intent intent=getIntent();
        String name=intent.getStringExtra("which");
        int result=Integer.valueOf(name);
        //点击哪个按钮，哪个按钮获取焦点
        switch (result){
            case 1:
                bt_record_txt.setText("我的收藏");
                isRecordClick=false;
                useCollectionList();
                break;
            case 2:
                bt_record_txt.setText("播放记录");
                isRecordClick=true;
                useRecordList();
                break;
        }
    }

    /**
     * 我的收藏
     * */
    private void useCollectionList() {
        collectionAdapter = new CollectionAdapter(CollectionActivity.this,collectionList);
        gridView.setAdapter(collectionAdapter);
    }

    /**
     * 播放记录
     * */
    private void useRecordList() {
        recordAdapter = new RecordAdapter(CollectionActivity.this,recordList);
        gridView.setAdapter(recordAdapter);
    }

    /**
     * 删除一条播放记录
     * */
    private void deleteRecordItem(int position) {
        String title=recordList.get(position).getTitle();
        SQLOperateImpl sqlOperate=new SQLOperateImpl(mContext);
        try {
            sqlOperate.deleteRecord(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一条收藏记录
     * */
    private void deleteCollectionItem(int position) {
        String title=collectionList.get(position).getTitle();
        SQLOperateImpl sqlOperate=new SQLOperateImpl(mContext);
        try {
            sqlOperate.deleteCollection(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private long mPreKeytime = 0;
    private  long time=0;
    boolean isFirst_second=true;
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
        //fix for one bug for up key and change the tab
        if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            Log.d("======", "dispatchKeyEvent====left===="+x);
            if(x%4 == 0){
                return true;
            }
        }else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            this.finish();
        }
        else if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT){
            Log.d("======", "dispatchKeyEvent====right===="+x);
        }

//        else if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP)
//        {
//            Log.d("======", "dispatchKeyEvent====up===="+x);
//        }
//        else if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN){
//            Log.d("======", "dispatchKeyEvent====down===="+x);
//        }

        //KEYCODE_MENU
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode()==KeyEvent.KEYCODE_MENU){
            if (isFirst==true){
                textView.setText("退出");
                isFirst=false;
            }else {
                textView.setText("开启");
                isFirst=true;
            }
            isDelele = !isDelele;
            if(isDelele) {index = 2;}
            else{index = 1;}
            if (isRecordClick == true) {
                recordNotifyData();
            } else {
                collectionNotifyData();
            }
        }

        if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode()==KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 播放记录
     * */
    void recordNotifyData(){
        recordAdapter.setIndex(index);
        recordAdapter.notifyDataSetChanged();
    }

    /**
     * 我的收藏
     * */
    void collectionNotifyData(){
        collectionAdapter.setIndex(index);
        collectionAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (is_resume){
            is_resume=false;
        }else {
           // Music.play(this, R.raw.bg);
            Music.restart(mContext);
        }
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (is_resume) {
            is_resume = false;
        } else {
            //  mHomeWatcher.stopWatch();// 在onPause中停止监听，不然会报错的。
          //  Music.stop(mContext);
            Music.pause(mContext);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        try {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
        }catch (Exception e){

        }

    }
}
