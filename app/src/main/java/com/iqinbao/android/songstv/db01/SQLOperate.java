package com.iqinbao.android.songstv.db01;

import com.iqinbao.android.songstv.beanstv.ClientVersion;
import com.iqinbao.android.songstv.beanstv.SongEntity;

import java.util.List;

/**
 * Created by admin on 2016/9/21.
 */
public interface SQLOperate {
    public void add(List<SongEntity.CatContentsBean> catContentsBeanList, int type) throws Exception;
    //歌单添加
    public void add(List<SongEntity> songEntity_list) throws Exception;
    public void deleteAll() throws Exception;
    public List<SongEntity.CatContentsBean> query(int type) throws Exception;
    public SongEntity queryForSong(String catid) throws Exception;
    public List<SongEntity> queryForSong() throws Exception;
    public List<SongEntity.CatContentsBean> queryForSing(String catid) throws Exception;
    public List<SongEntity.CatContentsBean> queryForSingBySid(String catid) throws Exception;
    public SongEntity.CatContentsBean queryForSing1(String playurl) throws Exception;
    public SongEntity.CatContentsBean queryForDissertation(String conid) throws Exception;
    public void insertCollection(String title,String Url,String pic_s) throws Exception;
    public void insertRecord(String title,String Url,String pic_s) throws Exception;
    public void deleteCollection(String title) throws Exception;
    public void deleteRecord(String title) throws Exception;
    public List<SongEntity.CatContentsBean> queryCollection() throws Exception;
    public List<SongEntity.CatContentsBean> queryRecord() throws Exception;
    public  SongEntity.CatContentsBean  queryForSingConid(String conid) throws Exception;
    //广告地址的插入
    public void insertForAdvertisement(ClientVersion clientVersion) throws Exception;
    //根据conid查询一条歌曲
    public ClientVersion queryForAdvertisement() throws Exception;
    public SongEntity.CatContentsBean queryForSingSid(String catid) throws Exception;



}
