package com.iqinbao.android.songstv.beanstv;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2016/9/7.
 */
public class SongEntity implements Serializable {

    /**
     * parentid : 1414
     * catid : 1419
     * catname : 咕力学说话
     * introduction : 在此输入栏目简介
     * ads :
     * catpic : http://www.iqinbao.com/upload/201512/2802/5680daa65f0ff_2015122802.png
     * cat_contents : [{"conid":"40070","catid":"1419","title":"帮帮我吧","intro":"帮帮我吧","pic_s":"http://s.61baobao.com/mobile/guliguli/images/5_bbw_1.png","pic_sh":"","pic_b":"","pic_bh":"","playurl":"http://s.61baobao.com/2016/mp4/1280/020010125.mp4","playurl_h":"","star":"","create_time":"1473222944","update_time":"1473222944","hits":"0"}]
     * order : 50
     */

    private String parentid;
    private String catid;
    private String catname;
    private String introduction;
    private String ads;
    private String catpic;
    private String order;
    /**
     * conid : 40070
     * catid : 1419
     * title : 帮帮我吧
     * intro : 帮帮我吧
     * pic_s : http://s.61baobao.com/mobile/guliguli/images/5_bbw_1.png
     * pic_sh :
     * pic_b :
     * pic_bh :
     * playurl : http://s.61baobao.com/2016/mp4/1280/020010125.mp4
     * playurl_h :
     * star :
     * create_time : 1473222944
     * update_time : 1473222944
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

    public static class CatContentsBean  implements Serializable{
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
        private String sid;

        public String getSid() {
            return sid;
        }
        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getConid() {return conid;}
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
