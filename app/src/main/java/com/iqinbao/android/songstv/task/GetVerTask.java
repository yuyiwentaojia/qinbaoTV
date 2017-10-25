package com.iqinbao.android.songstv.task;

import android.content.Context;

import com.iqinbao.android.songstv.beanstv.ClientVersion;
import com.iqinbao.android.songstv.db01.SQLOperateImpl;
import com.iqinbao.android.songstv.utils.JsonUtils;
import com.iqinbao.android.songstv.utils.Tools;
import com.iqinbao.android.songstv.utils.ZHttp;
import com.iqinbao.android.songstv.utils.codec.Base64;

/**
 * 获得版本信息
 * Created by Administrator on 2016/11/3.
 */
public class GetVerTask extends AbsCommonTask {
    String list_ver_local="";
    private SQLOperateImpl sqlOperateimpl;
    public GetVerTask(AsyncUpdate asyncUpdate, Context mContext, int asyncUpdateType) {
        super(asyncUpdate, mContext, asyncUpdateType);
    }

    @Override
    protected Integer doInBackground(Object... params) {
        if (Tools.checkConnection(mContext)){
            try {
                String url = "http://api.iqinbao.com/api/ver/1414?t=aa";
                String rsp = ZHttp.getString(url);
                Base64 decoder = new Base64();
                byte[] b = decoder.decode(rsp.getBytes());
                rsp = Tools.getDESData(b, "http://www.iqinbao.com");
                String rsp2 = Tools.GetDeCompress(rsp);
                rsp = rsp2.replace("order", "orders");
                ClientVersion clientVersion = JsonUtils.deserialize(rsp, ClientVersion.class);
                //保存版本信息
                new SQLOperateImpl(mContext).insertForAdvertisement(clientVersion);

                String list_ver = clientVersion.getList_ver();

                list_ver_local = Tools.getKeyValueString(mContext, "list_ver");
                //判断本地标识是否要更新
                sqlOperateimpl=new SQLOperateImpl(mContext);
                if(list_ver != null && list_ver.length() > 0 && list_ver.equals(list_ver_local)){
                    return NO_MESSAGE;
                }
                else{
                    //更新本地版本标识信息
                    Tools.setKeyValueString(mContext, list_ver, "list_ver");
                    return SUCCESS;
                }
            }catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return ERROR;
            }
        }
        else {
            return NO_NETWORK;
        }
    }

}
