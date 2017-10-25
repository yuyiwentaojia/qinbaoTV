package com.iqinbao.android.songstv;

import android.content.Context;

import com.iqinbao.android.songstv.beanstv.SongEntity;
import com.iqinbao.android.songstv.db01.SQLOperateImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuhao on 2016/9/8.
 */
public class DataInsertDB {

    private Context mContext;
    //焦点图
    List<SongEntity.CatContentsBean> cat_contents_image_focus = new ArrayList<SongEntity.CatContentsBean>();
    //ip—主题
    List<SongEntity.CatContentsBean> cat_contents_ip_dissertation = new ArrayList<SongEntity.CatContentsBean>();
    //主题
    List<SongEntity.CatContentsBean> cat_contents_dissertation = new ArrayList<SongEntity.CatContentsBean>();
    //列表
    private List<SongEntity> songList = new ArrayList<SongEntity>();

    public DataInsertDB(List<SongEntity> songList, List<SongEntity.CatContentsBean> cat_contents_dissertation, List<SongEntity.CatContentsBean> cat_contents_ip_dissertation, List<SongEntity.CatContentsBean> cat_contents_image_focus, Context mContext) {
        this.songList = songList;
        this.cat_contents_dissertation = cat_contents_dissertation;
        this.cat_contents_ip_dissertation = cat_contents_ip_dissertation;
        this.cat_contents_image_focus = cat_contents_image_focus;
        this.mContext = mContext;
    }

    public void insertDatabase ()throws Exception{
       // long time_1=System.currentTimeMillis();
        SQLOperateImpl sqlOperateimpl = new SQLOperateImpl(mContext);
        sqlOperateimpl.deleteAll();
        sqlOperateimpl.add(cat_contents_image_focus,SQLOperateImpl.FocusImage);
        sqlOperateimpl.add(cat_contents_ip_dissertation,SQLOperateImpl.IP_Dissertation);
        sqlOperateimpl.add(cat_contents_dissertation,SQLOperateImpl.Dissertation);
        sqlOperateimpl.add(songList);
       // long time_2=System.currentTimeMillis();
        //long l = (time_2 - time_1) ;
       // LogUtils.e("l",l+"");
    }





}
