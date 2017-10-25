package com.iqinbao.android.songstv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.iqinbao.android.songstv.adapter.RecycleAdapterListSing;
import com.iqinbao.android.songstv.base.BaseFragmentActivity;
import com.iqinbao.android.songstv.beanstv.SongEntity;
import com.iqinbao.android.songstv.db01.SQLOperateImpl;
import com.iqinbao.android.songstv.music.Music;
import com.iqinbao.android.songstv.utils.ActivityCollector;
import com.iqinbao.android.songstv.utils.LogUtils;
import com.iqinbao.android.songstv.utils.Tools;
import com.iqinbao.android.songstv.view.SpacesItemDecoration;

import org.evilbinary.tv.widget.BorderEffect;
import org.evilbinary.tv.widget.BorderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lli on 2017/10/20.
 */

public class DissertationActivityNew extends BaseFragmentActivity {
    private RecyclerView recyclerView_detail;
    //    private RecyclerViewTV  recyclerView_category_list;
    //private TextView textView_category;
    private Context mContext;
    //歌单集合
    private List<SongEntity> songList = new ArrayList<SongEntity>();
    //歌曲集合
    private List<SongEntity.CatContentsBean> singList = new ArrayList<SongEntity.CatContentsBean>();
    private ImageView imageView;

    //设置背景图片，使用完之后对图片进行操作，以便系统对他进行回收
    private ScrollView category_list_scroll_layout;
    private RelativeLayout relativeLayout;
    private LinearLayout category_list_layout;

    List<SongEntity.CatContentsBean> singCatList = new ArrayList<SongEntity.CatContentsBean>();
    //对应歌单的歌曲
    private Map<Integer, List<SongEntity.CatContentsBean>> map = new HashMap<Integer, List<SongEntity.CatContentsBean>>();
    private BorderView borderView;
    private BorderView borderView1;
    private boolean isFirst = true;
    private SQLOperateImpl sqlOperateimpl;
    private View v_song;
    private int position1;
    private Boolean flag = true;
    private int type = 0;
    RecycleAdapterListSing recycleAdapterListSing;
    boolean is_left = true;
    boolean isFirst_second = true;
    private BorderEffect borderEffect;
    private boolean is_resume=false;
    private boolean is_first_for=true;
    private View view_sing;
    private int  width=1920;
    private int height=1080;
    private int click_flag = 1;
    private int position_flag = 0;
    private int CATEGORY_LIST_ITEM_FLAG = 30000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dissertation_new);
        mContext = this;
        ActivityCollector.addActivity(this);
        is_resume=true;
        borderEffect=new BorderEffect();
        borderView = new BorderView(mContext);
        borderView1 = new BorderView(mContext);
        sqlOperateimpl = new SQLOperateImpl(mContext);
        findViews();
        try {
            setViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setListeners();



    }

    @Override
    protected void findViews() {
        WindowManager wm01 = this.getWindowManager();
        width  = wm01.getDefaultDisplay().getWidth();
        height = wm01.getDefaultDisplay().getHeight();
        if (width>1920 ||height>1080){
            width=1920;
            height=1080;
        }
        /**
         * 对背景图片进行操作，方便后续的回收
         */
        imageView= (ImageView) findViewById(R.id.dissert_image);
        /**
         * 对图片进行裁剪，减少内存开销（美工把图片缩小了一半）
         */
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=1;
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.topic_bg,options);
        imageView.setImageBitmap(bitmap);

        recyclerView_detail = (RecyclerView) findViewById(R.id.recyclerView_detail);

        category_list_layout = (LinearLayout) findViewById(R.id.category_list_layout);
    }

    @Override
    protected void setViews() throws Exception {
        Intent intent = getIntent();
        String playurl = intent.getStringExtra("playurl");
        if (playurl.equals("")) {

        } else {
            if (playurl.equals("0")) {
                songList = sqlOperateimpl.queryForSong();
                for (int i = 0; i < songList.size(); i++) {
                    String catid = songList.get(i).getCatid();
                    singList = sqlOperateimpl.queryForSing(catid);
                    map.put(i, singList);
                    setRecyclerView_detail(map);
                }

            } else {
                String title = intent.getStringExtra("title");
                List<String> list_song_all = Tools.gainIDFromString(playurl);
                for (int i = 0; i < list_song_all.size(); i++) {
                    String s = list_song_all.get(i);
                    songList.add(sqlOperateimpl.queryForSong(s));
                    singList = sqlOperateimpl.queryForSing(s);
                    map.put(i, singList);
                    setRecyclerView_detail(map);
                }
            }

        }

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //实例化SharedPreferences对象（第一步）
        final SharedPreferences mySharedPreferences = getSharedPreferences("select",
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        final SharedPreferences.Editor editor = mySharedPreferences.edit();


        category_list_layout.removeAllViews();
        LinearLayout.LayoutParams layoutParams  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        for(int i = 0;i< songList.size();++i){
            final int position = i;
            final Button b1 = new Button(this);
            b1.setText(songList.get(i).getCatname());
            b1.setGravity(Gravity.CENTER);
            b1.setPadding(0,10,0,10);
            b1.setTextSize(20);//此处
            b1.setTextColor(getResources().getColor(R.color.white_qinbao));
            b1.setBackgroundResource(0);
            b1.setId(position + CATEGORY_LIST_ITEM_FLAG);
            category_list_layout.addView (b1,layoutParams);
            b1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(final View view, boolean b) {
                    if(view.hasFocus()){
                        LogUtils.e("onFocusChange---------",position+"");
//                        if(click_flag == 1 && position == 0){
//                            view.performClick();
//                        }else {
//                            view.setBackgroundResource(R.drawable.topic_name_inactive);
//                        }

//                        view.setNextFocusRightId(R.id.recyclerView_detail);
                        if(position == songList.size() - 1){
                            view.setNextFocusDownId(view.getId());
                            view.setNextFocusUpId(view.getId()-1);
                        }else if(position == 0){
                            view.setNextFocusDownId(view.getId() + 1);
                        }else{
                            LogUtils.e("setNextFocus---------","mid");
                            view.setNextFocusDownId(view.getId()+1);
                            view.setNextFocusUpId(view.getId()-1);
                        }

                        view.setBackgroundResource(R.drawable.topic_name_active);
//                    view.setId(position + CATEGORY_LIST_ITEM_FLAG);
                        is_left = true;
                        if (v_song != null & mySharedPreferences.getBoolean("boolean", flag) == false) {
                            borderView.onGlobalFocusChanged(view_sing,v_song);
//                    v_song.setBackgroundResource(R.color.transparent);
                            v_song.setBackgroundResource(R.drawable.topic_name_active);
                            v_song.setFocusable(true);
                            v_song.requestFocus();
                            position1 = mySharedPreferences.getInt("position", position);
                            flag = true;
                            editor.putBoolean("boolean", flag);
                            type = 1;
                            editor.putInt("type", type);
                            editor.commit();
                        } else {
                            //保存数据
                            editor.putInt("position", position);
                            flag = true;
                            editor.putBoolean("boolean", flag);
                            type = 2;
                            editor.putInt("type", type);
                            //提交当前数据
                            editor.commit();
                            position1 = position;
                        }
                        number_current = position1 + 1;
                        number_count = songList.size();
                        // view.setNextFocusRightId(R.id.relativeLayout_text_image);
                        final List<SongEntity.CatContentsBean> singList = map.get(position1);
                        final String catid = songList.get(position1).getCatid();


                        singCatList.clear();
                        singCatList.addAll(singList);
                        if (!recyclerView_detail.isComputingLayout()) {
                            recycleAdapterListSing.notifyDataSetChanged();
                        }
                        recycleAdapterListSing.setOnItemSelectListener(new RecycleAdapterListSing.OnItemSelectListener() {
                            @Override
                            public void onItemSelect(View view_1sing, int position) {

                                view_sing=view_1sing;
                                if (singList.size() <= 4) {
                                    view_1sing.setNextFocusDownId(view_1sing.getId());
//                                    if (position == (singList.size() - 1)) {
//                                        view_sing.setNextFocusDownId(R.id.relativeLayout_text_image);
//                                    }
                                }
                                if (singList.size() > 0 ) {
                                    if (position % 4 == 0) {
//                                for (int i = 0 ;i < recycleAdapterListSong.getItemCount();i++) {
//                                    recyclerView_category_list.getLayoutManager().findViewByPosition(i).setFocusable(false);
//                                }
//                                recyclerView_category_list.getLayoutManager().findViewByPosition(view.getId() - CATEGORY_LIST_ITEM_FLAG).setFocusable(true);
                                        if (view.getId() - CATEGORY_LIST_ITEM_FLAG < mLayoutManager.findFirstVisibleItemPosition() ||
                                                view.getId() - CATEGORY_LIST_ITEM_FLAG > mLayoutManager.findLastVisibleItemPosition()){
                                            mLayoutManager.scrollToPositionWithOffset(view.getId() - CATEGORY_LIST_ITEM_FLAG,0);
                                            mLayoutManager.setStackFromEnd(true);
//                                    recyclerView_category_list.smoothScrollToPosition(view.getId() - CATEGORY_LIST_ITEM_FLAG); //滚动到指定的位置
                                        }
                                        view_1sing.setNextFocusLeftId(view.getId());
                                    }

                                    if(position < 4){
//                                    position_flag = 1;
//                                    category_list_layout.getChildAt(0).setFocusable(false);
//                                    view_1sing.setNextFocusUpId(view_1sing.getId());
                                    }
                                    LogUtils.e("Math.ceil-------",(Math.ceil((double)(position + 1) / (double)4))+"");
//                                if(Math.ceil((double)(position + 1) / (double)4) == Math.ceil((double)(singList.size()) / (double)4)){
//                                    view_1sing.setNextFocusDownId(view_1sing.getId());
//                                }
                                }
                                x = position + 1;
                                is_left = false;
                                if (position1 == mySharedPreferences.getInt("position", position)) {
                                    if (v_song != null) {
                                        if (mySharedPreferences.getInt("type", type) == 2) {
                                            v_song = view;
                                            view.setBackgroundResource(R.drawable.topic_name_inactive);
                                        } else {
                                            v_song.setBackgroundResource(R.drawable.topic_name_inactive);
                                        }
                                    } else {
                                        v_song = view;
                                        view.setBackgroundResource(R.drawable.topic_name_inactive);
                                    }
                                } else {
                                    v_song = view;
                                    view.setBackgroundResource(R.drawable.topic_name_inactive);
                                }
//                        flag = false;
                                editor.putBoolean("boolean", flag);
                                editor.commit();


                            }
                        });
                        recycleAdapterListSing.setOnRecyItemClickListener(new RecycleAdapterListSing.OnRecyItemClickListener() {
                            @Override
                            public void onClickItem(int position) {
                                Intent intent = new Intent(DissertationActivityNew.this, IjkPlayerActivity.class);
                                int category = 3;
                                intent.putExtra("category", category);
                                intent.putExtra("catid", catid);
                                intent.putExtra("position", position + "");
                                startActivity(intent);
                            }
                        });


                    }else {
                        click_flag = 0;
                        view.setBackgroundResource(0);
                    }
                }
            });
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                }
            });
        }


    }

    @Override
    protected void setListeners() {
    }

    /**
     * 默认详情展示。
     */

    public void setRecyclerView_detail(Map<Integer, List<SongEntity.CatContentsBean>> map) {
        singCatList.clear();
        if (map != null) {
            singCatList.addAll(map.get(0));
            final String catid = songList.get(0).getCatid();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
            recyclerView_detail.setLayoutManager(gridLayoutManager);
            recyclerView_detail.setItemAnimator(null);
            recycleAdapterListSing = new RecycleAdapterListSing(singCatList);
            if (isFirst) {

                int dimension = (int) getResources().getDimension(R.dimen.w_70);
                int right = (int) getResources().getDimension(R.dimen.suggest);
                int bottom = (int) getResources().getDimension(R.dimen.space_30);
                recyclerView_detail.addItemDecoration(new SpacesItemDecoration(bottom,right,dimension, 4, GridLayoutManager.VERTICAL, mContext));
                isFirst = false;
            }
            recyclerView_detail.setAdapter(recycleAdapterListSing);
            recycleAdapterListSing.setOnRecyItemClickListener(new RecycleAdapterListSing.OnRecyItemClickListener() {
                @Override
                public void onClickItem(int position) {
                    Intent intent = new Intent(DissertationActivityNew.this, IjkPlayerActivity.class);
                    int category = 3;
                    intent.putExtra("category", category);
                    intent.putExtra("catid", catid);
                    intent.putExtra("position", position + "");
                    startActivity(intent);
                }
            });

//            recyclerView_category_list.setFocusable(false);
//            borderEffect.setScale(1.1f);
//            borderView.setEffect(borderEffect);
//            borderView.setBackgroundResource(R.drawable.select_item_4);
//            borderView.attachTo(recyclerView_category_list);
        }

    }

    int x = 0;
    int row_num = 0;
    int toatl_row_num = 0;
    private long mPreKeytime = 0;
    private long time = 0;
    private int number_current = 0;
    private int number_count = 0;

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
        int size = singCatList.size();
        //行数
        row_num = x % 4 == 0 ? x / 4 : (x / 4 + 1);
        //总行数
        toatl_row_num = size % 4 == 0 ? size / 4 : (size / 4 + 1);
//如果is_left为false时，才走监听事件，否则，按照系统的来走。
//        if(size == 0 || is_left){
//            return super.dispatchKeyEvent(event);
//        }

        //fix for one bug for up key and change the tab
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
//            LogUtils.e("number_count---000----",number_count+"
            if (is_left) {
                if (number_current == 1) {
                    return true;
                }
            } else {
                if (row_num == 1) {
                    return true;
                }
            }
        } else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            if (is_left) {
                if (number_current == number_count && number_count > 1) {
                    return true;
                }
            } else {
                if (row_num == toatl_row_num && toatl_row_num > 1) {
                    return true;
                }
            }
        }else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT){
                    if (is_first_for){
                        borderView1.setBackgroundResource(R.drawable.border_white_dissertation);
                        borderView1.attachTo(recyclerView_detail);
                        is_first_for=false;
                    }
//                     if (position_flag == 1){
//                         category_list_layout.getChildAt(0).setFocusable(true);
//                     }
                    }

//    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT){
//        if (position_flag == 1){
//            position_flag = 0;
//            category_list_layout.getChildAt(0).setFocusable(true);
//        }
//    }
//
//    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP){
//        if (position_flag == 1){
//            position_flag = 0;
//            category_list_layout.getChildAt(0).setFocusable(false);
//        }
//    }

// else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK){
//            this.finish();
//        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e("onResume","onResume");
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
        LogUtils.e("onPause","onPause");
        if (is_resume) {
            is_resume = false;
        } else {
            //  mHomeWatcher.stopWatch();// 在onPause中停止监听，不然会报错的。
            // Music.stop(mContext);
            Music.pause(mContext);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        LogUtils.e("onDestroy","onDestroy");
    }
}