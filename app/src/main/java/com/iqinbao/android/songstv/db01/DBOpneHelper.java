package com.iqinbao.android.songstv.db01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iqinbao.android.songstv.App;
import com.iqinbao.android.songstv.utils.MySharedPreferences;

/**
 * Created by admin on 2016/9/21.
 */
public class DBOpneHelper extends SQLiteOpenHelper {
    private Context context;
    private static final int VERSION = 4;//版本
    private static final String DB_NAME = "Qinbao.db";//数据库名
    public DBOpneHelper(Context context) {
        super(context, DB_NAME, null, VERSION);

    }
    public static int getVersion(){
        return VERSION;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("PRAGMA synchronous = OFF;");
        db.execSQL(Table.CREATE_TABLE_Dissertation);
        db.execSQL(Table.CREATE_TABLE_FocusImage);
        db.execSQL(Table.CREATE_TABLE_IPDissertation);
        db.execSQL(Table.CREATE_TABLE_Song);
        db.execSQL(Table.CREATE_TABLE_Sing);
        db.execSQL(Table.CREATE_TABLE_Collection);
        db.execSQL(Table.CREATE_TABLE_Record);
        db.execSQL(Table.CREATE_TABLE_ADS1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // ||写成&&了
        if (oldVersion==1){
            context=App.getContext();
            MySharedPreferences.getInstanc(context).deleteConfig("list_ver");
            /**
             * 1.每次数据库升级时 都需要删除保存在首选项里面数据版本信息 确保能重新下载数据
             * 2.此次升级后给歌单添加一个新的字段  保存了history和collection里面的信息 其他数据
             *在数据下载后 被删除   添加进新的数据
             */
            db.execSQL(Table.DELETE_TABLE_SING);
            db.execSQL(Table.CREATE_TABLE_Sing);
            db.execSQL(Table.CREATE_TABLE_ADS1);
//            //将要修改的表改名字
//            db.execSQL(Table.RENAME_TABLE_SING);
//            //创建新表
//            db.execSQL(Table.CREATE_TABLE_Sing1);
//            //将临时表导入新表中去
//
        }else if (oldVersion==2){
            context=App.getContext();
            MySharedPreferences.getInstanc(context).deleteConfig("list_ver");
            db.execSQL(Table.CREATE_TABLE_ADS1);
        }else if (oldVersion==3){
            context=App.getContext();
            MySharedPreferences.getInstanc(context).deleteConfig("list_ver");
        }
    }
}
