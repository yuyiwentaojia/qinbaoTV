package com.iqinbao.android.songstv.task;

import android.content.Context;


import com.iqinbao.android.songstv.DataInsertDB;
import com.iqinbao.android.songstv.beanstv.GsonDataResult;
import com.iqinbao.android.songstv.beanstv.SongEntity;
import com.iqinbao.android.songstv.db01.SQLOperateImpl;
import com.iqinbao.android.songstv.utils.Constants;
import com.iqinbao.android.songstv.utils.GsonUtil;
import com.iqinbao.android.songstv.utils.Tools;
import com.iqinbao.android.songstv.utils.ZHttp;
import com.iqinbao.android.songstv.utils.codec.Base64;

import java.util.ArrayList;
import java.util.List;

public class GetSongsTask extends AbsCommonTask {
    String TAG = "GetSongsTask";
    //所有歌单列表
    List<SongEntity> songEntity_list = new ArrayList<SongEntity>();
    //焦点图
    List<SongEntity.CatContentsBean> cat_contents_image_focus=new ArrayList<>();
    //ip 主题
    List<SongEntity.CatContentsBean> cat_contents_ip_dissertation=new ArrayList<>();
    //主题
    List<SongEntity.CatContentsBean> cat_contents_dissertation=new ArrayList<>();
    SQLOperateImpl sqlOperate =new SQLOperateImpl(mContext);
    public List<SongEntity.CatContentsBean> getCat_contents_image_focus() {
        return cat_contents_image_focus;
    }
    public List<SongEntity.CatContentsBean> getCat_contents_ip_dissertation() {
        return cat_contents_ip_dissertation;
    }
    public List<SongEntity.CatContentsBean> getCat_contents_dissertation() {
        return cat_contents_dissertation;
    }
    public List<SongEntity> getSongEntity_list() {
        return songEntity_list;
    }

    public GetSongsTask(AsyncUpdate asyncUpdate, Context mContext,
                        int asyncUpdateType) {
        super(asyncUpdate, mContext, asyncUpdateType);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Integer doInBackground(Object... params) {
        // TODO Auto-generated method stub
        if (Tools.checkConnection(mContext)) {
            try {
                String url = "http://api.iqinbao.com/api/lists/1414?t=aa";
                String rsp = ZHttp.getString(url);
                Base64 decoder = new Base64();
                byte[] b = decoder.decode(rsp.getBytes());
                rsp = Tools.getDESData(b, "http://www.iqinbao.com");
                String rsp2 = Tools.GetDeCompress(rsp);
                rsp = rsp2.replace("order", "orders");
                rsp ="{\"data\":"+rsp+"}";
                GsonDataResult categoryResult = GsonUtil.getInstance().fromJson(rsp, GsonDataResult.class);
                if (categoryResult != null) {
                    for (SongEntity songEntity : categoryResult.getContents()) {
                        if (songEntity.getCatid().equals( Constants.Config.QINBAO_TV)){
                        }else if (songEntity.getCatid().equals( Constants.Config.FIRST_PARE)){
                        }else if (songEntity.getCatid().equals( Constants.Config.IMAGE_FOCUS)) {
                            cat_contents_image_focus = songEntity.getCat_contents();
                            for(SongEntity.CatContentsBean bean : cat_contents_image_focus){
                                bean.setCatid(songEntity.getCatid());
                            }
                        } else if (songEntity.getCatid().equals( Constants.Config.IP_DISSERTATION)) {
                            cat_contents_ip_dissertation = songEntity.getCat_contents();
                            for(SongEntity.CatContentsBean bean : cat_contents_ip_dissertation){
                                bean.setCatid(songEntity.getCatid());
                            }

                        } else if (songEntity.getCatid().equals(Constants.Config.DISSERTATION) ) {
                            cat_contents_dissertation.addAll(songEntity.getCat_contents());
                            for(SongEntity.CatContentsBean bean : cat_contents_dissertation){
                                bean.setCatid(songEntity.getCatid());
                            }
                        } else {
                            songEntity_list.add(songEntity);
                           // String catid = songEntity.getCatid();
                          //  map.put(catid,songEntity);
                        }
                    }
                    DataInsertDB dataInsertDB = new DataInsertDB(songEntity_list, cat_contents_dissertation,
                            cat_contents_ip_dissertation, cat_contents_image_focus, mContext);
                    dataInsertDB.insertDatabase();
                    return SUCCESS;
                } else {



                    return FAIL;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return ERROR;
            }
        } else {
            return NO_NETWORK;
        }

    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

    }
}
