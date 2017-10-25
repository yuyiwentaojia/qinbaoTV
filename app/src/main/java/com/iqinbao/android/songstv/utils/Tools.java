package com.iqinbao.android.songstv.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.iqinbao.android.songstv.net.HttpUtils;
import com.iqinbao.android.songstv.utils.codec.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * Created by Administrator on 2016/8/30.
 */
public class Tools {
    private static int type=-1;

    public static int getType() {
        return type;
    }



    public static void addPlayNum(final Context context, final String catid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                setPlayNum(context, catid);
            }
        }).start();
    }

    static void setPlayNum(final Context context, final String conid) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.get("http://api.iqinbao.com/api/update_hits/"
                + conid, new HttpUtils.HttpCallback() {

            @Override
            public void onError(String msg) {
                int songNum = Tools.getSongNum(context, conid);
                Tools.setSongNum(context, songNum + 1, conid);
            }

            @Override
            public void onSuccess(String data) {
                if (data.length() > 0) {
                    int songNum = Tools.getSongNum(context, conid);
                    // // Log.i("====VideoViewBuffer====", "========结束..."+songNum);
                    if (songNum == 0) {
                        Tools.setSongNum(context, 0, conid);
                    } else {
                        Tools.setSongNum(context, songNum - 1, conid);
                        setPlayNum(context, conid);
                    }
                }
            }
        });
    }

    public static void setSongNum(Context context, int num, String conid) {
        MySharedPreferences.getInstanc(context)
                .saveConfig("" + conid, "" + num);
    }

    public static int getSongNum(Context context, String conid) {
        String str = MySharedPreferences.getInstanc(context).getConfig(
                "" + conid);
        if (str.length() == 0)
            return 0;
        else {
            return Integer.valueOf(str);
        }
    }


    // 检测网络连接
    public static boolean checkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            type=networkInfo.getType();
            return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 判断WIFI网络是否可用
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 分离
     * @param s
     * @return
     */
    public static List<String> gainIDFromString(String s) {

        String str2 = s.replace(" ", "");//去掉所有空格

        List<String> list = Arrays.asList(str2.split(","));
        return list;
    }

    public static String getDESData(byte[] b, String keyValue) {
        String str = "";
        try {
            SecureRandom sr = new SecureRandom();
            byte rawKeyData[] = keyValue.getBytes();
            DESKeySpec dks = new DESKeySpec(rawKeyData);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            byte encryptedData[] = b;
            byte[] decryptedData = cipher.doFinal(encryptedData);
            str = new String(decryptedData);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return str;
    }

    /**
     * zip 解压
     * */
    public static String GetDeCompress(String src)
    {
        int BUFFER = 1024 * 8;// 缓冲区
        if (src == null || src.isEmpty()) {
            return src;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPInputStream gunzip = null;
        String des = null;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(
                    new Base64().decode(src.getBytes()));
            gunzip = new GZIPInputStream(in);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = gunzip.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            des = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(gunzip!=null)
            {
                try {
                    gunzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return des;
    }

    public static void setKeyValueString(Context context, String value, String key) {
        MySharedPreferences.getInstanc(context).saveConfig(key, "" + value);
    }

    public static String getKeyValueString(Context context, String key) {
        String str = MySharedPreferences.getInstanc(context).getConfig(key);
        return str;
    }


//删除文件夹
//param folderPath 文件夹完整绝对路径

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下所有文件
//param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                LogUtils.w("===========","=====删除缓存成功======="+temp.getPath());
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

//
//    /**
//     * 儿歌排序
//     * */
//    public static void songSort(List<SongEntity> songList) {
//        try {
//            Comparator sortCom = new Comparator<SongEntity>() {
//
//                @Override
//                public int compare(SongEntity lhs, SongEntity rhs) {
//                    int lhs_star = Integer
//                            .parseInt(lhs.getStar().length() > 0 ? lhs
//                                    .getStar() : "0");
//                    int rhs_star = Integer
//                            .parseInt(rhs.getStar().length() > 0 ? rhs
//                                    .getStar() : "0");
//
//                    if (lhs_star < rhs_star) {
//                        return 1;
//                    } else if (lhs_star == rhs_star) {
//                        return 0;
//                    } else if (lhs_star > rhs_star) {
//                        return -1;
//                    }
//                    return 0;
//                }
//            };
//            Collections.sort(songList, sortCom);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
