package com.iqinbao.android.songstv.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
	private Context mContext;
	public static final String perfer_name = "SurfingScene_prefenrences";
	/**
	 * 是否用3G网络
	 */
	public static final String IS3G = "IS3G";
	/**
	 * 是否用边听边下载
	 */
	public static final String ISLISDOWN = "ISLisDown";
	/**
	 * 距离睡眠时间
	 */
	public static final String DISTANCT_SLEEP = "DISTANCT_SLEEP";
	/**
	 * 判断存储文件的标识
	 * */
	public static final String DOWN_FILE_SAVE = "DOWN_FILE_SAVE";

	public static final String SO = "SO";
	public static final String SO1 = "SO1";
	// 本地儿歌
	public static final String LOC_SONG = "LOC_SONG";
	private static MySharedPreferences instanc;

	public MySharedPreferences(Context mContext) {
		this.mContext = mContext;
	}

	public static MySharedPreferences getInstanc(Context context) {
		if (null == instanc) {
			instanc = new MySharedPreferences(context);
		}
		return instanc;
	}

	//删除首选项里面的内容
	public void deleteConfig(String key) {
		SharedPreferences setting = this.mContext.getSharedPreferences(
				perfer_name, 0);
		SharedPreferences.Editor editor = setting.edit();
		editor.remove(key);
		editor.commit();
	}


	/**
	 * 存储String类型的字符到配置文件里
	 * 
	 * @param key
	 * @param value
	 */
	public void saveConfig(String key, String value) {
		SharedPreferences setting = this.mContext.getSharedPreferences(
				perfer_name, 0);
		SharedPreferences.Editor editor = setting.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 读取String类型的配置文件
	 * 
	 * @param key
	 * @return
	 */
	public String getConfig(String key) {
		SharedPreferences setting = this.mContext.getSharedPreferences(
				perfer_name, 0);
		return setting.getString(key, "");
	}

	public void saveIntConfig(String key, int value) {
		SharedPreferences setting = this.mContext.getSharedPreferences(
				perfer_name, 0);
		SharedPreferences.Editor editor = setting.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public int getIntConfig(String key) {
		SharedPreferences setting = this.mContext.getSharedPreferences(
				perfer_name, 0);
		return setting.getInt(key, 30 * 60);
	}

	public void saveBoolConfig(String key, boolean value) {
		SharedPreferences setting = this.mContext.getSharedPreferences(
				perfer_name, 0);
		SharedPreferences.Editor editor = setting.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getBoolConfig(String key) {
		SharedPreferences setting = this.mContext.getSharedPreferences(
				perfer_name, 0);
		return setting.getBoolean(key, true);
	}

}
