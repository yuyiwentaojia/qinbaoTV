package com.iqinbao.android.songstv.beanstv;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qinbao-app-1 on 2016/10/27.
 */
public class AdsEnity implements Serializable {


    /**
     * catid : 1328
     * android_version :
     * android_downurl :
     * ios_version :
     * ios_downurl :
     * pc_version : 1466490241
     * pc_downurl :
     * softname :
     * isapp : 1
     * ads_interval : 0
     * ads_baidu : null
     * ads_baidux : null
     * ads_qq : null
     * ads_1 : null
     * ads_2 : null
     * ads_3 : null
     * app_url : http://www.iqinbao.com/app/api/1328
     */

    private AppBean app;
    /**
     * parentid : 270
     * catid : 1328
     * catname : 视频广告
     * introduction : 在此输入栏目简介
     * ads :
     * catpic :
     * cat_contents : [{"conid":"35973","catid":"1328","title":"妈妈到哪去了","intro":"妈妈到哪去了","pic_s":"","pic_sh":"","pic_b":"1","pic_bh":"","playurl":"http://s.61baobao.com/2016/mp4/test/ad3.mp4","playurl_h":"","star":"0","create_time":"1465798407","update_time":"1466490236","hits":"0"},{"conid":"35972","catid":"1328","title":"爸爸到哪去了","intro":"爸爸到哪去了","pic_s":"","pic_sh":"","pic_b":"1","pic_bh":"","playurl":"http://s.61baobao.com/2016/mp4/test/ad2.mp4","playurl_h":"","star":"0","create_time":"1465798398","update_time":"1466490230","hits":"0"},{"conid":"35947","catid":"1328","title":"宝宝到哪去了","intro":"宝宝到哪去了","pic_s":"","pic_sh":"","pic_b":"1","pic_bh":"","playurl":"http://s.61baobao.com/2016/mp4/test/ad1.mp4","playurl_h":"","star":"0","create_time":"1465794409","update_time":"1466490225","hits":"0"}]
     * order : 50
     */

    private List<ContentsBean> contents;

    public AppBean getApp() {
        return app;
    }

    public void setApp(AppBean app) {
        this.app = app;
    }

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }

    public static class AppBean {
        private String catid;
        private String android_version;
        private String android_downurl;
        private String ios_version;
        private String ios_downurl;
        private String pc_version;
        private String pc_downurl;
        private String softname;
        private String isapp;
        private String ads_interval;
        private Object ads_baidu;
        private Object ads_baidux;
        private Object ads_qq;
        private Object ads_1;
        private Object ads_2;
        private Object ads_3;
        private String app_url;

        public String getCatid() {
            return catid;
        }

        public void setCatid(String catid) {
            this.catid = catid;
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

        public String getSoftname() {
            return softname;
        }

        public void setSoftname(String softname) {
            this.softname = softname;
        }

        public String getIsapp() {
            return isapp;
        }

        public void setIsapp(String isapp) {
            this.isapp = isapp;
        }

        public String getAds_interval() {
            return ads_interval;
        }

        public void setAds_interval(String ads_interval) {
            this.ads_interval = ads_interval;
        }

        public Object getAds_baidu() {
            return ads_baidu;
        }

        public void setAds_baidu(Object ads_baidu) {
            this.ads_baidu = ads_baidu;
        }

        public Object getAds_baidux() {
            return ads_baidux;
        }

        public void setAds_baidux(Object ads_baidux) {
            this.ads_baidux = ads_baidux;
        }

        public Object getAds_qq() {
            return ads_qq;
        }

        public void setAds_qq(Object ads_qq) {
            this.ads_qq = ads_qq;
        }

        public Object getAds_1() {
            return ads_1;
        }

        public void setAds_1(Object ads_1) {
            this.ads_1 = ads_1;
        }

        public Object getAds_2() {
            return ads_2;
        }

        public void setAds_2(Object ads_2) {
            this.ads_2 = ads_2;
        }

        public Object getAds_3() {
            return ads_3;
        }

        public void setAds_3(Object ads_3) {
            this.ads_3 = ads_3;
        }

        public String getApp_url() {
            return app_url;
        }

        public void setApp_url(String app_url) {
            this.app_url = app_url;
        }
    }

    public static class ContentsBean {
        private String parentid;
        private String catid;
        private String catname;
        private String introduction;
        private String ads;
        private String catpic;
        private String order;
        /**
         * conid : 35973
         * catid : 1328
         * title : 妈妈到哪去了
         * intro : 妈妈到哪去了
         * pic_s :
         * pic_sh :
         * pic_b : 1
         * pic_bh :
         * playurl : http://s.61baobao.com/2016/mp4/test/ad3.mp4
         * playurl_h :
         * star : 0
         * create_time : 1465798407
         * update_time : 1466490236
         * hits : 0
         */

        private List<CatContentsBean> cat_contents;

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getCatid() {
            return catid;
        }

        public void setCatid(String catid) {
            this.catid = catid;
        }

        public String getCatname() {
            return catname;
        }

        public void setCatname(String catname) {
            this.catname = catname;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getAds() {
            return ads;
        }

        public void setAds(String ads) {
            this.ads = ads;
        }

        public String getCatpic() {
            return catpic;
        }

        public void setCatpic(String catpic) {
            this.catpic = catpic;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public List<CatContentsBean> getCat_contents() {
            return cat_contents;
        }

        public void setCat_contents(List<CatContentsBean> cat_contents) {
            this.cat_contents = cat_contents;
        }

        public static class CatContentsBean {
            private String conid;
            private String catid;
            private String title;
            private String intro;
            private String pic_s;
            private String pic_sh;
            private String pic_b;
            private String pic_bh;
            private String playurl;
            private String playurl_h;
            private String star;
            private String create_time;
            private String update_time;
            private String hits;

            public String getConid() {
                return conid;
            }

            public void setConid(String conid) {
                this.conid = conid;
            }

            public String getCatid() {
                return catid;
            }

            public void setCatid(String catid) {
                this.catid = catid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getPic_s() {
                return pic_s;
            }

            public void setPic_s(String pic_s) {
                this.pic_s = pic_s;
            }

            public String getPic_sh() {
                return pic_sh;
            }

            public void setPic_sh(String pic_sh) {
                this.pic_sh = pic_sh;
            }

            public String getPic_b() {
                return pic_b;
            }

            public void setPic_b(String pic_b) {
                this.pic_b = pic_b;
            }

            public String getPic_bh() {
                return pic_bh;
            }

            public void setPic_bh(String pic_bh) {
                this.pic_bh = pic_bh;
            }

            public String getPlayurl() {
                return playurl;
            }

            public void setPlayurl(String playurl) {
                this.playurl = playurl;
            }

            public String getPlayurl_h() {
                return playurl_h;
            }

            public void setPlayurl_h(String playurl_h) {
                this.playurl_h = playurl_h;
            }

            public String getStar() {
                return star;
            }

            public void setStar(String star) {
                this.star = star;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getHits() {
                return hits;
            }

            public void setHits(String hits) {
                this.hits = hits;
            }
        }
    }
}
