package com.iqinbao.android.songstv.db01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iqinbao.android.songstv.beanstv.ClientVersion;
import com.iqinbao.android.songstv.beanstv.SongEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 2016/9/21.
 */
public class SQLOperateImpl implements SQLOperate {
    public static final int IP_Dissertation = 1;  //
    public static final int FocusImage = 2;     //焦点图
    public static final int Dissertation = 3;//
    public static final int Sing = 4;
    private DBOpneHelper dbOpenHelper;

    public SQLOperateImpl(Context context) {
        dbOpenHelper = new DBOpneHelper(context);
    }

    /**
     * 根据不同的type，存储不同的数据
     * (包含两个主题表，一个焦点图表)
     *
     * @param catContentsBeanList
     * @param type
     */
    @Override
    public void add(List<SongEntity.CatContentsBean> catContentsBeanList, int type)throws Exception {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();  //手动设置开始事务
        ContentValues values = new ContentValues();
        for (SongEntity.CatContentsBean catContentsBean : catContentsBeanList) {
            values.put(Table.CONID, catContentsBean.getConid());
            values.put(Table.CATID, catContentsBean.getCatid());
            values.put(Table.TITLE, catContentsBean.getTitle());
            values.put(Table.INTRO, catContentsBean.getIntro());
            values.put(Table.PIC_S, catContentsBean.getPic_s());
            values.put(Table.PIC_SH, catContentsBean.getPic_sh());
            values.put(Table.PIC_B, catContentsBean.getPic_b());
            values.put(Table.PIC_BH, catContentsBean.getPic_bh());
            values.put(Table.PLAYURL, catContentsBean.getPlayurl());
            values.put(Table.PLAYURL_H, catContentsBean.getPlayurl_h());
            values.put(Table.STAR, catContentsBean.getStar());
            values.put(Table.CREATE_TIME, catContentsBean.getCreate_time());
            values.put(Table.UPDATE_TIME, catContentsBean.getUpdate_time());
            values.put(Table.HITS, catContentsBean.getHits());
            switch (type) {
                case IP_Dissertation:
                    db.insert(Table.QINBAO_TABLE_IPDissertation, null, values);
                    break;
                case FocusImage:
                    db.insert(Table.QINBAO_TABLE_FocusImage, null, values);
                    break;
                case Dissertation:
                    db.insert(Table.QINBAO_TABLE_Dissertation, null, values);
                    break;
            }
        }
        db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        //无论有无异常，都结束事务
        db.endTransaction();

        db.close();


    }

    /**
     * 添加所有的歌单和歌曲
     *
     * @param songEntity_list
     */
    @Override
    public void add(List<SongEntity> songEntity_list) throws Exception{

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();  //手动设置开始事务
        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();
        for (SongEntity songEntity : songEntity_list) {
            values.put(Table.PARENTID, songEntity.getParentid());
            values.put(Table.CATID, songEntity.getCatid());
            values.put(Table.CATNAME, songEntity.getCatname());
            values.put(Table.INTRODUCTION, songEntity.getIntroduction());
            values.put(Table.ADS, songEntity.getAds());
            values.put(Table.CATPIC, songEntity.getCatpic());
            db.insert(Table.QINBAO_TABLE_Song, null, values);
            List<SongEntity.CatContentsBean> cat_contents = songEntity.getCat_contents();
            for (SongEntity.CatContentsBean catContentsBean : cat_contents) {
                values1.put(Table.CONID, catContentsBean.getConid());
                values1.put(Table.CATID, songEntity.getCatid());
                values1.put(Table.TITLE, catContentsBean.getTitle());
                values1.put(Table.INTRO, catContentsBean.getIntro());
                values1.put(Table.PIC_S, catContentsBean.getPic_s());
                values1.put(Table.PIC_SH, catContentsBean.getPic_sh());
                values1.put(Table.PIC_B, catContentsBean.getPic_b());
                values1.put(Table.PIC_BH, catContentsBean.getPic_bh());
                values1.put(Table.PLAYURL, catContentsBean.getPlayurl());
                values1.put(Table.PLAYURL_H, catContentsBean.getPlayurl_h());
                values1.put(Table.STAR, catContentsBean.getStar());
                values1.put(Table.CREATE_TIME, catContentsBean.getCreate_time());
                values1.put(Table.UPDATE_TIME, catContentsBean.getUpdate_time());
                values1.put(Table.HITS, catContentsBean.getHits());
                values1.put(Table.SID,catContentsBean.getSid());
                db.insert(Table.QINBAO_TABLE_Sing, null, values1);
            }
        }
        db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        //无论有无异常，都结束事务
        db.endTransaction();
        db.close();
    }

    /**
     * 删除所有表内的数据
     */
    @Override
    public void deleteAll()throws Exception{
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();  //手动设置开始事务
        Cursor cursor_ipdissertation = db.query(Table.QINBAO_TABLE_IPDissertation, null, null, null, null, null, null);
        Cursor cursor_focusImage = db.query(Table.QINBAO_TABLE_FocusImage, null, null, null, null, null, null);
        Cursor cursor_dissertation = db.query(Table.QINBAO_TABLE_Dissertation, null, null, null, null, null, null);
        Cursor cursor_song = db.query(Table.QINBAO_TABLE_Song, null, null, null, null, null, null);
        Cursor cursor_sing = db.query(Table.QINBAO_TABLE_Sing, null, null, null, null, null, null);
        if (cursor_ipdissertation != null) {
            db.delete(Table.QINBAO_TABLE_IPDissertation, null, null);
        }
        if (cursor_focusImage != null) {
            db.delete(Table.QINBAO_TABLE_FocusImage, null, null);
        }
        if (cursor_dissertation != null) {
            db.delete(Table.QINBAO_TABLE_Dissertation, null, null);
        }
        if (cursor_song != null) {
            db.delete(Table.QINBAO_TABLE_Song, null, null);
        }
        if (cursor_sing != null) {
            db.delete(Table.QINBAO_TABLE_Sing, null, null);
        }
        db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        //无论有无异常，都结束事务
        cursor_ipdissertation.close();
        db.endTransaction();
        db.close();
    }

    /**
     * 根据type 获取对应三个表的数据
     *
     * @param type 类型 多吉==咕力咕力==起司公主
     * @ram catid 0==app版本
     * @return
     */
    @Override
    public List<SongEntity.CatContentsBean> query(int type) throws Exception{
        List<SongEntity.CatContentsBean> catContentsBeanList = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        db.beginTransaction();  //手动设置开始事务
        Cursor cursor_ipdissertation = db.query(Table.QINBAO_TABLE_IPDissertation, null, null, null, null, null, null);
        Cursor cursor_focusImage = db.query(Table.QINBAO_TABLE_FocusImage, null, null, null, null, null, null);
        Cursor cursor_dissertation = db.query(Table.QINBAO_TABLE_Dissertation, null, null, null, null, null, null);
        catContentsBeanList = new ArrayList<SongEntity.CatContentsBean>();
        switch (type) {
            case IP_Dissertation:
                if (cursor_ipdissertation != null) {
                    while (cursor_ipdissertation.moveToNext()) {
                        SongEntity.CatContentsBean catContentsBean = new SongEntity.CatContentsBean();
                        query(cursor_ipdissertation, catContentsBean);
                        catContentsBeanList.add(catContentsBean);
                    }
                }
                break;
            case FocusImage:
                if (cursor_focusImage != null) {
                    while (cursor_focusImage.moveToNext()) {
                        SongEntity.CatContentsBean catContentsBean = new SongEntity.CatContentsBean();
                        query(cursor_focusImage, catContentsBean);
                        catContentsBeanList.add(catContentsBean);
                    }
                }
                break;
            case Dissertation:
                if (cursor_dissertation != null) {
                    while (cursor_dissertation.moveToNext()) {
                        SongEntity.CatContentsBean catContentsBean = new SongEntity.CatContentsBean();
                        query(cursor_dissertation, catContentsBean);
                        catContentsBeanList.add(catContentsBean);
                    }
                }
                break;
        }
        db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        //无论有无异常，都结束事务
        db.endTransaction();
        cursor_ipdissertation.close();
        cursor_focusImage.close();
        cursor_dissertation.close();
        db.close();
        return catContentsBeanList;
    }

    /**
     * 根据catid 获取歌单
     *
     * @param catid
     * @return
     */
    @Override
    public SongEntity queryForSong(String catid)throws Exception {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(Table.QINBAO_TABLE_Song, null, Table.CATID + "=?", new String[]{catid}, null, null, null);
        SongEntity songEntity = null;
        if (cursor != null && cursor.moveToFirst()) {
            songEntity = new SongEntity();
            int _id = cursor.getInt(cursor.getColumnIndex(Table._ID));
            String parentid = cursor.getString(cursor.getColumnIndex(Table.PARENTID));
            String catid1 = cursor.getString(cursor.getColumnIndex(Table.CATID));
            String catname = cursor.getString(cursor.getColumnIndex(Table.CATNAME));
            String introduction = cursor.getString(cursor.getColumnIndex(Table.INTRODUCTION));
            String ads = cursor.getString(cursor.getColumnIndex(Table.ADS));
            String catpic = cursor.getString(cursor.getColumnIndex(Table.CATPIC));
            songEntity.setParentid(parentid);
            songEntity.setCatid(catid1);
            songEntity.setCatname(catname);
            songEntity.setIntroduction(introduction);
            songEntity.setAds(ads);
            songEntity.setCatpic(catpic);
        }
        db.close();
        cursor.close();
        return songEntity;
    }

    /**
     * 获取所有的歌单
     *
     * @return
     */
    @Override
    public List<SongEntity> queryForSong()throws Exception {
        List<SongEntity> songEntityList = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        db.beginTransaction();  //手动设置开始事务
        Cursor cursor_song = db.query(Table.QINBAO_TABLE_Song, null, null, null, null, null, null);
        songEntityList = new ArrayList<SongEntity>();
        if (cursor_song != null) {
            while (cursor_song.moveToNext()) {
                SongEntity songEntity = new SongEntity();
                int _id = cursor_song.getInt(cursor_song.getColumnIndex(Table._ID));
                String parentid = cursor_song.getString(cursor_song.getColumnIndex(Table.PARENTID));
                String catid1 = cursor_song.getString(cursor_song.getColumnIndex(Table.CATID));
                String catname = cursor_song.getString(cursor_song.getColumnIndex(Table.CATNAME));
                String introduction = cursor_song.getString(cursor_song.getColumnIndex(Table.INTRODUCTION));
                String ads = cursor_song.getString(cursor_song.getColumnIndex(Table.ADS));
                String catpic = cursor_song.getString(cursor_song.getColumnIndex(Table.CATPIC));
                songEntity.setParentid(parentid);
                songEntity.setCatid(catid1);
                songEntity.setCatname(catname);
                songEntity.setIntroduction(introduction);
                songEntity.setAds(ads);
                songEntity.setCatpic(catpic);

                songEntityList.add(songEntity);
            }
        }
        db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        //无论有无异常，都结束事务
        db.endTransaction();
        cursor_song.close();
        db.close();
        return songEntityList;
    }



    public List<SongEntity.CatContentsBean> queryForSingBySid(String catid)throws Exception {
        List<SongEntity.CatContentsBean> catContentsBeanList = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        db.beginTransaction();  //手动设置开始事务
        Cursor cursor_sing = db.query(Table.QINBAO_TABLE_Sing, null, Table.SID + "=?", new String[]{catid}, null, null, null);
        catContentsBeanList = new ArrayList<SongEntity.CatContentsBean>();
        if (cursor_sing != null) {
            while (cursor_sing.moveToNext()) {
                SongEntity.CatContentsBean catContentsBean = new SongEntity.CatContentsBean();
                query(cursor_sing, catContentsBean);
                catContentsBeanList.add(catContentsBean);
            }
        }
        db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        //无论有无异常，都结束事务
        db.endTransaction();
        cursor_sing.close();
        db.close();
        return catContentsBeanList;
    }


    /**
     * 根据catid 获取对应歌单的所有歌曲信息。
     *
     * @param catid
     * @return
     */
    @Override
    public List<SongEntity.CatContentsBean> queryForSing(String catid) throws Exception{
        List<SongEntity.CatContentsBean> catContentsBeanList = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        db.beginTransaction();  //手动设置开始事务
        Cursor cursor = db.query(Table.QINBAO_TABLE_Sing, null, Table.CATID + "=?", new String[]{catid}, null, null, null);
        catContentsBeanList = new ArrayList<SongEntity.CatContentsBean>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                SongEntity.CatContentsBean catContentsBean = new SongEntity.CatContentsBean();
//                query(cursor_sing, catContentsBean)
                int _id = cursor.getInt(cursor.getColumnIndex(Table._ID));
                String conid = cursor.getString(cursor.getColumnIndex(Table.CONID));
                String catid01 = cursor.getString(cursor.getColumnIndex(Table.CATID));
                String title = cursor.getString(cursor.getColumnIndex(Table.TITLE));
                String intro = cursor.getString(cursor.getColumnIndex(Table.INTRO));
                String pic_s = cursor.getString(cursor.getColumnIndex(Table.PIC_S));
                String pic_sh = cursor.getString(cursor.getColumnIndex(Table.PIC_SH));
                String pic_b = cursor.getString(cursor.getColumnIndex(Table.PIC_B));
                String pic_bh = cursor.getString(cursor.getColumnIndex(Table.PIC_BH));
                String playurl = cursor.getString(cursor.getColumnIndex(Table.PLAYURL));
                String playurl_h = cursor.getString(cursor.getColumnIndex(Table.PLAYURL_H));
                String star = cursor.getString(cursor.getColumnIndex(Table.STAR));
                String create_time = cursor.getString(cursor.getColumnIndex(Table.CREATE_TIME));
                String update_time = cursor.getString(cursor.getColumnIndex(Table.UPDATE_TIME));
                String hits = cursor.getString(cursor.getColumnIndex(Table.HITS));
                String sid = cursor.getString(cursor.getColumnIndex(Table.SID));
                catContentsBean.setConid(conid);
                catContentsBean.setCatid(catid01);
                catContentsBean.setTitle(title);
                catContentsBean.setIntro(intro);
                catContentsBean.setPic_s(pic_s);
                catContentsBean.setPic_sh(pic_sh);
                catContentsBean.setPic_b(pic_b);
                catContentsBean.setPic_bh(pic_bh);
                catContentsBean.setPlayurl(playurl);
                catContentsBean.setPlayurl_h(playurl_h);
                catContentsBean.setStar(star);
                catContentsBean.setCreate_time(create_time);
                catContentsBean.setUpdate_time(update_time);
                catContentsBean.setHits(hits);
                catContentsBean.setSid(sid);
                catContentsBeanList.add(catContentsBean);
            }
        }
        db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        //无论有无异常，都结束事务
        db.endTransaction();
        db.close();
        return catContentsBeanList;
    }

    /**
     * 根据歌曲url获取歌曲信息。
     *
     * @param conid
     * @return
     */
    @Override
    public SongEntity.CatContentsBean queryForSingConid(String conid)throws Exception {
        SongEntity.CatContentsBean catContentsBean = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(Table.QINBAO_TABLE_Sing, null, Table.CONID + "=?", new String[]{conid}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            catContentsBean = new SongEntity.CatContentsBean();
//            query(cursor_sing, catContentsBean);
            int _id = cursor.getInt(cursor.getColumnIndex(Table._ID));
            String conid01 = cursor.getString(cursor.getColumnIndex(Table.CONID));
            String catid = cursor.getString(cursor.getColumnIndex(Table.CATID));
            String title = cursor.getString(cursor.getColumnIndex(Table.TITLE));
            String intro = cursor.getString(cursor.getColumnIndex(Table.INTRO));
            String pic_s = cursor.getString(cursor.getColumnIndex(Table.PIC_S));
            String pic_sh = cursor.getString(cursor.getColumnIndex(Table.PIC_SH));
            String pic_b = cursor.getString(cursor.getColumnIndex(Table.PIC_B));
            String pic_bh = cursor.getString(cursor.getColumnIndex(Table.PIC_BH));
            String playurl = cursor.getString(cursor.getColumnIndex(Table.PLAYURL));
            String playurl_h = cursor.getString(cursor.getColumnIndex(Table.PLAYURL_H));
            String star = cursor.getString(cursor.getColumnIndex(Table.STAR));
            String create_time = cursor.getString(cursor.getColumnIndex(Table.CREATE_TIME));
            String update_time = cursor.getString(cursor.getColumnIndex(Table.UPDATE_TIME));
            String hits = cursor.getString(cursor.getColumnIndex(Table.HITS));
            String sid = cursor.getString(cursor.getColumnIndex(Table.SID));
            catContentsBean.setConid(conid01);
            catContentsBean.setCatid(catid);
            catContentsBean.setTitle(title);
            catContentsBean.setIntro(intro);
            catContentsBean.setPic_s(pic_s);
            catContentsBean.setPic_sh(pic_sh);
            catContentsBean.setPic_b(pic_b);
            catContentsBean.setPic_bh(pic_bh);
            catContentsBean.setPlayurl(playurl);
            catContentsBean.setPlayurl_h(playurl_h);
            catContentsBean.setStar(star);
            catContentsBean.setCreate_time(create_time);
            catContentsBean.setUpdate_time(update_time);
            catContentsBean.setHits(hits);
            catContentsBean.setHits(sid);
        }
        db.close();
        cursor.close();
        return catContentsBean;
    }

    public SongEntity.CatContentsBean queryForSingSid(String conid)throws Exception {
        SongEntity.CatContentsBean catContentsBean = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor_sing = db.query(Table.QINBAO_TABLE_Sing, null, Table.SID + "=?", new String[]{conid}, null, null, null);
        if (cursor_sing != null && cursor_sing.moveToFirst()) {
            catContentsBean = new SongEntity.CatContentsBean();
            query(cursor_sing, catContentsBean);
        }
        db.close();
        cursor_sing.close();
        return catContentsBean;
    }

    /**
     * 根据歌曲url获取歌曲信息。
     *
     * @param playurl
     * @return
     */
    @Override
    public SongEntity.CatContentsBean queryForSing1(String playurl) throws Exception{
        SongEntity.CatContentsBean catContentsBean = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor_sing = db.query(Table.QINBAO_TABLE_Sing, null, Table.PLAYURL + "=?", new String[]{playurl}, null, null, null);
        if (cursor_sing != null && cursor_sing.moveToFirst()) {
            catContentsBean = new SongEntity.CatContentsBean();
            query(cursor_sing, catContentsBean);
        }
        db.close();
        cursor_sing.close();
        return catContentsBean;
    }
    /**
     * 根据id获取的主题的信息。
     *
     * @param conid
     * @return
     */
    @Override
    public SongEntity.CatContentsBean queryForDissertation(String conid) throws Exception{
        SongEntity.CatContentsBean catContentsBean = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor_dissertation = db.query(Table.QINBAO_TABLE_Dissertation, null, Table.CONID + "=?", new String[]{conid}, null, null, null);
        if (cursor_dissertation != null && cursor_dissertation.moveToFirst()) {
            catContentsBean = new SongEntity.CatContentsBean();
            query(cursor_dissertation, catContentsBean);
        }
        db.close();
        cursor_dissertation.close();
        return catContentsBean;
    }

    @Override
    public void insertCollection(String title, String url, String pic_s)throws Exception {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(Table.QINBAO_TABLE_Collection, null, Table.TITLE + "=?", new String[]{title}, null, null, null);
        if (cursor != null) {
            db.delete(Table.QINBAO_TABLE_Collection, Table.TITLE + "=?", new String[]{title});
        }
        ContentValues values = new ContentValues();
        values.put(Table.TITLE, title);
        values.put(Table.PLAYURL, url);
        values.put(Table.PIC_S, pic_s);
        db.insert(Table.QINBAO_TABLE_Collection, null, values);
        cursor.close();
        db.close();
    }


    @Override
    public void insertRecord(String title, String url, String pic_s)throws Exception {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(Table.QINBAO_TABLE_Record, null, Table.TITLE + "=?", new String[]{title}, null, null, null);
        if (cursor != null) {
            db.delete(Table.QINBAO_TABLE_Record, Table.TITLE + "=?", new String[]{title});
        }
        ContentValues values = new ContentValues();
        values.put(Table.TITLE, title);
        values.put(Table.PLAYURL, url);
        values.put(Table.PIC_S, pic_s);
        db.insert(Table.QINBAO_TABLE_Record, null, values);
        cursor.close();
        db.close();
    }

    @Override
    public void deleteCollection(String title) throws Exception{
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();  //手动设置开始事务

        Cursor cursor_collection = db.query(Table.QINBAO_TABLE_Collection, null, Table.TITLE + "=?", new String[]{title}, null, null, null);

        if (cursor_collection != null) {
            db.delete(Table.QINBAO_TABLE_Collection, Table.TITLE + "=?", new String[]{title});
        }
        db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        //无论有无异常，都结束事务
        db.endTransaction();
        cursor_collection.close();
        db.close();


    }

    @Override
    public void deleteRecord(String title)throws Exception {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();  //手动设置开始事务
        Cursor cursor_collection = db.query(Table.QINBAO_TABLE_Record, null, Table.TITLE + "=?", new String[]{title}, null, null, null);

        if (cursor_collection != null) {
            db.delete(Table.QINBAO_TABLE_Record, Table.TITLE + "=?", new String[]{title});
        }
        db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        //无论有无异常，都结束事务
        db.endTransaction();
        cursor_collection.close();
        db.close();

    }

    @Override
    public List<SongEntity.CatContentsBean> queryCollection() throws Exception{
        List<SongEntity.CatContentsBean> songEntityList = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        db.beginTransaction();  //手动设置开始事务
        Cursor cursor_song = db.query(Table.QINBAO_TABLE_Collection, null, null, null, null, null, null);
        songEntityList = new ArrayList<SongEntity.CatContentsBean>();
        if (cursor_song != null) {
            while (cursor_song.moveToNext()) {
                SongEntity.CatContentsBean songEntity = new SongEntity.CatContentsBean();
                String url = cursor_song.getString(cursor_song.getColumnIndex(Table.PLAYURL));
                String title = cursor_song.getString(cursor_song.getColumnIndex(Table.TITLE));
                String pic_s = cursor_song.getString(cursor_song.getColumnIndex(Table.PIC_S));
                songEntity.setPlayurl(url);
                songEntity.setPic_s(pic_s);
                songEntity.setTitle(title);
                songEntityList.add(songEntity);
                 /*
                让songEntityList倒叙排列
                 */
                Collections.reverse(songEntityList);
            }
        }
        db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        //无论有无异常，都结束事务
        db.endTransaction();
        db.close();
        cursor_song.close();
        return songEntityList;
    }

    @Override
    public List<SongEntity.CatContentsBean> queryRecord() throws Exception{
        List<SongEntity.CatContentsBean> songEntityList = null;
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();  //手动设置开始事务
        Cursor cursor_song = db.query(Table.QINBAO_TABLE_Record, null, null, null, null, null, null);
        songEntityList = new ArrayList<SongEntity.CatContentsBean>();
        if (cursor_song != null) {
            while (cursor_song.moveToNext()) {
                SongEntity.CatContentsBean songEntity = new SongEntity.CatContentsBean();
                String url = cursor_song.getString(cursor_song.getColumnIndex(Table.PLAYURL));
                String title = cursor_song.getString(cursor_song.getColumnIndex(Table.TITLE));
                String pic_s = cursor_song.getString(cursor_song.getColumnIndex(Table.PIC_S));
                songEntity.setPlayurl(url);
                songEntity.setPic_s(pic_s);
                songEntity.setTitle(title);
                songEntityList.add(songEntity);
                /*
                让songEntityList倒叙排列
                 */
                Collections.reverse(songEntityList);
            }
        }
        db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        //无论有无异常，都结束事务
        db.endTransaction();
        cursor_song.close();
        db.close();
        return songEntityList;
    }


    public void query(Cursor cursor, SongEntity.CatContentsBean catContentsBean)throws Exception {
        int _id = cursor.getInt(cursor.getColumnIndex(Table._ID));
        String conid = cursor.getString(cursor.getColumnIndex(Table.CONID));
        String catid = cursor.getString(cursor.getColumnIndex(Table.CATID));
        String title = cursor.getString(cursor.getColumnIndex(Table.TITLE));
        String intro = cursor.getString(cursor.getColumnIndex(Table.INTRO));
        String pic_s = cursor.getString(cursor.getColumnIndex(Table.PIC_S));
        String pic_sh = cursor.getString(cursor.getColumnIndex(Table.PIC_SH));
        String pic_b = cursor.getString(cursor.getColumnIndex(Table.PIC_B));
        String pic_bh = cursor.getString(cursor.getColumnIndex(Table.PIC_BH));
        String playurl = cursor.getString(cursor.getColumnIndex(Table.PLAYURL));
        String playurl_h = cursor.getString(cursor.getColumnIndex(Table.PLAYURL_H));
        String star = cursor.getString(cursor.getColumnIndex(Table.STAR));
        String create_time = cursor.getString(cursor.getColumnIndex(Table.CREATE_TIME));
        String update_time = cursor.getString(cursor.getColumnIndex(Table.UPDATE_TIME));
        String hits = cursor.getString(cursor.getColumnIndex(Table.HITS));
        catContentsBean.setConid(conid);
        catContentsBean.setCatid(catid);
        catContentsBean.setTitle(title);
        catContentsBean.setIntro(intro);
        catContentsBean.setPic_s(pic_s);
        catContentsBean.setPic_sh(pic_sh);
        catContentsBean.setPic_b(pic_b);
        catContentsBean.setPic_bh(pic_bh);
        catContentsBean.setPlayurl(playurl);
        catContentsBean.setPlayurl_h(playurl_h);
        catContentsBean.setStar(star);
        catContentsBean.setCreate_time(create_time);
        catContentsBean.setUpdate_time(update_time);
        catContentsBean.setHits(hits);
    }

    /**
     * 广告数据库的插入
     *
     * @param clientVersion
     * @return
     */
    @Override
    public void insertForAdvertisement(ClientVersion clientVersion) throws Exception{
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor_ads = db.query(Table.QINBAO_TABLE_ADS1, null, null, null, null, null, null);
        if (cursor_ads != null) {
            db.delete(Table.QINBAO_TABLE_ADS1, null, null);
        }
        ContentValues values = new ContentValues();
        values.put(Table.CATID, clientVersion.getCatid());
        values.put(Table.ANDROID_VERSION, clientVersion.getAndroid_version());
        values.put(Table.ANDROID_DOWNURL, clientVersion.getAndroid_downurl());
        values.put(Table.IOS_VERSION, clientVersion.getIos_version());
        values.put(Table.IOS_DOWNURL, clientVersion.getIos_downurl());
        values.put(Table.PC_VERSION, clientVersion.getPc_version());
        values.put(Table.PC_DOWNURL, clientVersion.getPc_downurl());
        values.put(Table.SOFTNAME, clientVersion.getSoftname());
        values.put(Table.ISAPP, clientVersion.getIsapp());
        values.put(Table.ADS_INTERVAL, clientVersion.getAds_interval());
        values.put(Table.ADS_BAIDU, clientVersion.getAds_baidu());
        values.put(Table.ADS_BAIDUX, clientVersion.getAds_baidux());
        values.put(Table.ADS_QQ, clientVersion.getAds_qq());
        values.put(Table.ADS_1, clientVersion.getAds_1());
        values.put(Table.ADS_2, clientVersion.getAds_2());
        values.put(Table.ADS_3, clientVersion.getAds_3());
        values.put(Table.ADS_KAIPING, clientVersion.getAds_kaiping());
        values.put(Table.ADS_KAIPING_LINK, clientVersion.getAds_kaiping_link());
        values.put(Table.APP_URL, clientVersion.getApp_url());
        //  当前时间的插入
        String time = String.valueOf(System.currentTimeMillis());
        values.put(Table.CURRENT_TIME, time);
        db.insert(Table.QINBAO_TABLE_ADS1, null, values);
        cursor_ads.close();
        db.close();

    }

    /**
     * 查询广告地址
     *
     * @return
     */
    @Override
    public ClientVersion queryForAdvertisement()throws Exception {
        ClientVersion clientVersion = null;
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor_ads = db.query(Table.QINBAO_TABLE_ADS1, null, null, null, null, null, null);
        clientVersion = new ClientVersion();
        if (cursor_ads != null) {
            while (cursor_ads.moveToNext()) {
                String catid = cursor_ads.getString(cursor_ads.getColumnIndex(Table.CATID));
                //版本
                String android_version = cursor_ads.getString(cursor_ads.getColumnIndex(Table.ANDROID_VERSION));
                //下载地址
                String android_downurl = cursor_ads.getString(cursor_ads.getColumnIndex(Table.ANDROID_DOWNURL));
                String ios_version = cursor_ads.getString(cursor_ads.getColumnIndex(Table.IOS_VERSION));
                String ios_downurl = cursor_ads.getString(cursor_ads.getColumnIndex(Table.IOS_DOWNURL));
                String pc_version = cursor_ads.getString(cursor_ads.getColumnIndex(Table.PC_VERSION));
                String pc_downurl = cursor_ads.getString(cursor_ads.getColumnIndex(Table.PC_DOWNURL));
                //下载软件名字
                String softname = cursor_ads.getString(cursor_ads.getColumnIndex(Table.SOFTNAME));
                String isapp = cursor_ads.getString(cursor_ads.getColumnIndex(Table.ISAPP));
                String ads_interval = cursor_ads.getString(cursor_ads.getColumnIndex(Table.ADS_INTERVAL));
                String ads_baidu = cursor_ads.getString(cursor_ads.getColumnIndex(Table.ADS_BAIDU));
                String ads_baidux = cursor_ads.getString(cursor_ads.getColumnIndex(Table.ADS_BAIDUX));
                String ads_qq = cursor_ads.getString(cursor_ads.getColumnIndex(Table.ADS_QQ));
                String ads_1 = cursor_ads.getString(cursor_ads.getColumnIndex(Table.ADS_1));
                String ads_2 = cursor_ads.getString(cursor_ads.getColumnIndex(Table.ADS_2));
                String ads_3 = cursor_ads.getString(cursor_ads.getColumnIndex(Table.ADS_3));
                String ads_kaiping = cursor_ads.getString(cursor_ads.getColumnIndex(Table.ADS_KAIPING));
                String ads_kaiping_link = cursor_ads.getString(cursor_ads.getColumnIndex(Table.ADS_KAIPING_LINK));
                //连接地址
                String app_url = cursor_ads.getString(cursor_ads.getColumnIndex(Table.APP_URL));
                //当前时间
                String current_time = cursor_ads.getString(cursor_ads.getColumnIndex(Table.CURRENT_TIME));
                clientVersion.setCatid(catid);
                clientVersion.setAndroid_version(android_version);
                clientVersion.setAndroid_downurl(android_downurl);
                clientVersion.setIos_version(ios_version);
                clientVersion.setIos_downurl(ios_downurl);
                clientVersion.setPc_version(pc_version);
                clientVersion.setPc_downurl(pc_downurl);
                clientVersion.setSoftname(softname);
                clientVersion.setIsapp(isapp);
                clientVersion.setAds_interval(ads_interval);
                clientVersion.setAds_baidu(ads_baidu);
                clientVersion.setAds_baidux(ads_baidux);
                clientVersion.setAds_1(ads_1);
                clientVersion.setAds_2(ads_2);
                clientVersion.setAds_3(ads_3);
                clientVersion.setAds_kaiping(ads_kaiping);
                clientVersion.setAds_kaiping_link(ads_kaiping_link);
                clientVersion.setApp_url(app_url);
                clientVersion.setCurrent_time(current_time);
                cursor_ads.close();
                db.close();
            }
        }
        return clientVersion;
    }
}
