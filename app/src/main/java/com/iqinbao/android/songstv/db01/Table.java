package com.iqinbao.android.songstv.db01;

/**
 * Created by admin on 2016/9/21.
 */
public class Table {
    public static final String QINBAO_TABLE_ADS1="advertisement1";//广告
    public static final String QINBAO_TABLE_Dissertation = "dissertation";//专题 首页的下方专题表
    public static final String QINBAO_TABLE_IPDissertation = "ipDissertation";//专题 首页的上方专题表
    public static final String QINBAO_TABLE_FocusImage = "focusImage";// 首页的焦点图片表
    public static final String QINBAO_TABLE_Song = "song";// 歌单表
    public static final String QINBAO_TABLE_Sing = "sing";// 歌曲表
    public static final String QINBAO_TABLE_Record = "record";// 播放记录
    public static final String QINBAO_TABLE_Collection = "collection";// 收藏
    public static final String QINBAO_TABLE_RENEMASING = "renamesing";// 收藏


    public static final String _ID = "_id";//表中的列名
    public static final String CONID = "conid";//表中的列名
    public static final String CATID = "catid";//表中的列名
    public static final String TITLE = "title";//表中的列名
    public static final String INTRO = "intro";//表中的列名
    public static final String PIC_S = "pic_s";//表中的列名
    public static final String PIC_SH = "pic_sh";//表中的列名
    public static final String PIC_B = "pic_b";//表中的列名
    public static final String PIC_BH = "pic_bh";//表中的列名
    public static final String PLAYURL = "playurl";//表中的列名
    public static final String PLAYURL_H = "playurl_h";//表中的列名
    public static final String STAR = "star";//表中的列名
    public static final String CREATE_TIME = "create_time";//表中的列名
    public static final String UPDATE_TIME = "update_time";//表中的列名
    public static final String HITS = "hits";//表中的列名
    public static final String SID = "sid";//


    public static final String PARENTID = "parentid";//表中的列名
    public static final String CATNAME = "catname";//表中的列名
    public static final String INTRODUCTION = "introduction";//表中的列名
    public static final String ADS = "ads";//表中的列名
    public static final String CATPIC = "catpic";//表中的列名


    //广告数据库的各列
//    public static final String CATID= "catid";
    //版本
    public static final String ANDROID_VERSION="android_version";
    //下载地址
    public static final String ANDROID_DOWNURL="android_downurl" ;
    public static final String IOS_VERSION="ios_version" ;
    public static final String IOS_DOWNURL="ios_downurl" ;
    public static final String PC_VERSION="pc_version" ;
    public static final String PC_DOWNURL="pc_downurl" ;
    //下载软件名字
    public static final String SOFTNAME="softname" ;
    public static final String ISAPP ="isapp" ;
    public static final String ADS_INTERVAL="ads_interval" ;
    public static final String ADS_BAIDU="ads_baidu";
    public static final String ADS_BAIDUX="ads_baidux";
    public static final String ADS_QQ="ads_qq";
    public static final String ADS_1="ads_1" ;
    public static final String ADS_2="ads_2" ;
    public static final String ADS_3="ads_3" ;
    public static final String ADS_KAIPING="ads_kaiping" ;
    public static final String ADS_KAIPING_LINK="ads_kaiping_link" ;
    //连接地址
    public static final String APP_URL="app_url" ;
    //当前时间
    public static final String CURRENT_TIME="current_time" ;

    //创建数据库语句，STUDENT_TABLE，_ID ，NAME的前后都要加空格  主题
    public static final String CREATE_TABLE_Dissertation = "create table " + QINBAO_TABLE_Dissertation + " ( " + _ID + " Integer primary key autoincrement," + CONID + " varchar," + CATID + " varchar," + TITLE + " varchar," + INTRO + " varchar," + PIC_S + " varchar," + PIC_SH + " varchar," + PIC_B + " varchar," + PIC_BH + " varchar," + PLAYURL + " varchar," + PLAYURL_H + " varchar," + STAR+ " varchar," + CREATE_TIME + " varchar," + UPDATE_TIME + " varchar," + HITS + " varchar)";
    //创建数据库语句，STUDENT_TABLE，_ID ，NAME的前后都要加空格   ip主题
    public static final String CREATE_TABLE_IPDissertation = "create table " + QINBAO_TABLE_IPDissertation + " ( " + _ID + " Integer primary key autoincrement," + CONID + " varchar," + CATID + " varchar," + TITLE + " varchar," + INTRO + " varchar," + PIC_S + " varchar," + PIC_SH + " varchar," + PIC_B + " varchar," + PIC_BH + " varchar," + PLAYURL + " varchar," + PLAYURL_H + " varchar," + STAR+ " varchar," + CREATE_TIME + " varchar," + UPDATE_TIME + " varchar," + HITS + " varchar)";
    //创建数据库语句，STUDENT_TABLE，_ID ，NAME的前后都要加空格   焦点图
    public static final String CREATE_TABLE_FocusImage = "create table " + QINBAO_TABLE_FocusImage + " ( " + _ID + " Integer primary key autoincrement," + CONID + " varchar," + CATID + " varchar," + TITLE + " varchar," + INTRO + " varchar," + PIC_S + " varchar," + PIC_SH + " varchar," + PIC_B + " varchar," + PIC_BH + " varchar," + PLAYURL + " varchar," + PLAYURL_H + " varchar," + STAR+ " varchar," + CREATE_TIME + " varchar," + UPDATE_TIME + " varchar," + HITS + " varchar)";
    //创建数据库语句，STUDENT_TABLE，_ID ，NAME的前后都要加空格   歌单
    public static final String CREATE_TABLE_Song = "create table " + QINBAO_TABLE_Song + " ( " + _ID + " Integer primary key autoincrement," + PARENTID + " varchar," + CATID + " varchar," + CATNAME+ " varchar," + INTRODUCTION + " varchar," + ADS + " varchar," + CATPIC + " varchar)";
    //创建数据库语句，STUDENT_TABLE，_ID ，NAME的前后都要加空格    歌曲
    public static final String CREATE_TABLE_Sing = "create table " + QINBAO_TABLE_Sing + " ( " + _ID + " Integer primary key autoincrement," + CONID + " varchar," + CATID + " varchar," + TITLE + " varchar," + INTRO + " varchar," + PIC_S + " varchar," + PIC_SH + " varchar," + PIC_B + " varchar," + PIC_BH + " varchar," + PLAYURL + " varchar," + PLAYURL_H + " varchar," + STAR+ " varchar," + CREATE_TIME + " varchar," + UPDATE_TIME + " varchar," + HITS + " varchar," + SID + " varchar)";
    //创建数据库语句，STUDENT_TABLE，_ID ，NAME的前后都要加空格    歌曲
    public static final String CREATE_TABLE_Record = "create table " + QINBAO_TABLE_Record + " ( " + _ID + " Integer primary key autoincrement," + CONID + " varchar," + CATID + " varchar," + TITLE + " varchar," + INTRO + " varchar," + PIC_S + " varchar," + PIC_SH + " varchar," + PIC_B + " varchar," + PIC_BH + " varchar," + PLAYURL + " varchar," + PLAYURL_H + " varchar," + STAR+ " varchar," + CREATE_TIME + " varchar," + UPDATE_TIME + " varchar," + HITS + " varchar)";
    //创建数据库语句，STUDENT_TABLE，_ID ，NAME的前后都要加空格    歌曲
    public static final String CREATE_TABLE_Collection = "create table " + QINBAO_TABLE_Collection + " ( " + _ID + " Integer primary key autoincrement," + CONID + " varchar," + CATID + " varchar," + TITLE + " varchar," + INTRO + " varchar," + PIC_S + " varchar," + PIC_SH + " varchar," + PIC_B + " varchar," + PIC_BH + " varchar," + PLAYURL + " varchar," + PLAYURL_H + " varchar," + STAR+ " varchar," + CREATE_TIME + " varchar," + UPDATE_TIME + " varchar," + HITS + " varchar)";
    //创建广告数据库
    public static final String CREATE_TABLE_ADS1= "create table " + QINBAO_TABLE_ADS1 + " ( " + _ID + " Integer primary key autoincrement," + CATID + " varchar," + ANDROID_VERSION + " varchar," + ANDROID_DOWNURL + " varchar," + IOS_VERSION + " varchar," + IOS_DOWNURL + " varchar," + PC_VERSION + " varchar," + PC_DOWNURL + " varchar," + SOFTNAME + " varchar," + ISAPP + " varchar," + ADS_INTERVAL + " varchar," + ADS_BAIDU + " varchar," + ADS_BAIDUX + " varchar," + ADS_QQ + " varchar," + ADS_1 + " varchar," + ADS_2 + " varchar," + ADS_3 + " varchar," + ADS_KAIPING + " varchar," + ADS_KAIPING_LINK + " varchar," + APP_URL + " varchar," + CURRENT_TIME + " varchar)";




    public static final String RENAME_TABLE_SING="alter table QINBAO_TABLE_Sing rename to QINBAO_TABLE_RENEMASING";
    public static final String CREATE_TABLE_Sing1 = "create table " + QINBAO_TABLE_Sing + " ( " + _ID + " Integer primary key autoincrement," + CONID + " varchar," + CATID + " varchar," + TITLE + " varchar," + INTRO + " varchar," + PIC_S + " varchar," + PIC_SH + " varchar," + PIC_B + " varchar," + PIC_BH + " varchar," + PLAYURL + " varchar," + PLAYURL_H + " varchar," + STAR+ " varchar," + CREATE_TIME + " varchar," + UPDATE_TIME + " varchar," + HITS + " varchar," + SID + " varchar)";
    public static final String DELETE_TABLE_SING="drop table sing";



}
