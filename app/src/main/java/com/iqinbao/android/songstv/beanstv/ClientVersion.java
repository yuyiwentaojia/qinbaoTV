package com.iqinbao.android.songstv.beanstv;

import java.io.Serializable;

/**
 * 版本升级对象
 * @author SuLongKun
 *
 */
@SuppressWarnings("serial")
public class ClientVersion implements Serializable{
	private String catid;
	//版本
	private String android_version;
	//下载地址
	private String android_downurl;
	private String ios_version;
	private String ios_downurl;
	private String pc_version;
	private String pc_downurl;
	//下载软件名字
	private String softname;
	private String isapp;
	private String ads_interval;
    private String ads_baidu;
	private String ads_baidux;
	private String ads_qq;
	private String ads_1;
	private String ads_2;
	private String ads_3;
	private String ads_kaiping;
	private String ads_kaiping_link;
	//连接地址
	private String app_url;
	//当前时间
	private String current_time;
	public String getList_ver() {
		return list_ver;
	}

	public void setList_ver(String list_ver) {
		this.list_ver = list_ver;
	}

	//标识列表是否更新
	private String list_ver;

	public String getCurrent_time() {
		return current_time;
	}

	public void setCurrent_time(String current_time) {
		this.current_time = current_time;
	}

	public String getSoftname() {
		return softname;
	}
	public void setSoftname(String softname) {
		this.softname = softname;
	}
	public String getAndroid_version() {
		return android_version;
	}
	public void setAndroid_version(String android_version) {
		this.android_version = android_version;
	}
	public String getAndroid_downurl() {
		return android_downurl;
	}
	public void setAndroid_downurl(String android_downurl) {
		this.android_downurl = android_downurl;
	}
	public String getApp_url() {
		return app_url;
	}
	public void setApp_url(String app_url) {
		this.app_url = app_url;
	}

	public String getCatid() {
		return catid;
	}

	public void setCatid(String catid) {
		this.catid = catid;
	}

	public String getAds_kaiping_link() {
		return ads_kaiping_link;
	}

	public void setAds_kaiping_link(String ads_kaiping_link) {
		this.ads_kaiping_link = ads_kaiping_link;
	}

	public String getIos_version() {
		return ios_version;
	}

	public void setIos_version(String ios_version) {
		this.ios_version = ios_version;
	}

	public String getIos_downurl() {
		return ios_downurl;
	}

	public void setIos_downurl(String ios_downurl) {
		this.ios_downurl = ios_downurl;
	}

	public String getPc_version() {
		return pc_version;
	}

	public void setPc_version(String pc_version) {
		this.pc_version = pc_version;
	}

	public String getPc_downurl() {
		return pc_downurl;
	}

	public void setPc_downurl(String pc_downurl) {
		this.pc_downurl = pc_downurl;
	}

	public String getAds_interval() {
		return ads_interval;
	}

	public void setAds_interval(String ads_interval) {
		this.ads_interval = ads_interval;
	}

	public String getIsapp() {
		return isapp;
	}

	public void setIsapp(String isapp) {
		this.isapp = isapp;
	}

	public String getAds_baidu() {
		return ads_baidu;
	}

	public void setAds_baidu(String ads_baidu) {
		this.ads_baidu = ads_baidu;
	}

	public String getAds_baidux() {
		return ads_baidux;
	}

	public void setAds_baidux(String ads_baidux) {
		this.ads_baidux = ads_baidux;
	}

	public String getAds_qq() {
		return ads_qq;
	}

	public void setAds_qq(String ads_qq) {
		this.ads_qq = ads_qq;
	}

	public String getAds_1() {
		return ads_1;
	}

	public void setAds_1(String ads_1) {
		this.ads_1 = ads_1;
	}

	public String getAds_2() {
		return ads_2;
	}

	public void setAds_2(String ads_2) {
		this.ads_2 = ads_2;
	}

	public String getAds_3() {
		return ads_3;
	}

	public void setAds_3(String ads_3) {
		this.ads_3 = ads_3;
	}

	public String getAds_kaiping() {
		return ads_kaiping;
	}

	public void setAds_kaiping(String ads_kaiping) {
		this.ads_kaiping = ads_kaiping;
	}

}
