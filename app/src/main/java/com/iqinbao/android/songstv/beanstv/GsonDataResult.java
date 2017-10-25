package com.iqinbao.android.songstv.beanstv;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
public class GsonDataResult implements Serializable {
    List<SongEntity> data;
    public List<SongEntity> getContents() {
        return data;
    }
    public void setContents(List<SongEntity> contents) {
        this.data = contents;
    }
}
