package com.iqinbao.android.songstv.music;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.iqinbao.android.songstv.R;

/**音乐的设置
 * Created by admin on 2016/9/30.
 */
public class Music {
    private boolean music_isfor = false;

    private static MediaPlayer mp = null;
    private static int musicnumber;//定义一个整型用load（）；来设置suondID
    private static SoundPool sp;//声明一个SoundPool

    /**
     * 自定义按键音
     */
    public static void key_tone(Context context) {
        sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        musicnumber = sp.load(context, R.raw.sound_click, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级

    }

    public static void play_key() {
        sp.play(musicnumber, 1, 1, 0, 0, 1);
    }


    public static void play(Context context, int resource) {
        stop(context);
        try {
            mp = MediaPlayer.create(context, resource);
            mp.setLooping(true);
            mp.start();
        }catch (Exception e){
        }
    }

    public static void stop(Context context) {
        // TODO Auto-generated method stub
        try {
            if (mp != null) {
                mp.stop();
                mp.release();
                mp = null;
            }
        }catch (Exception e){
        }
    }

    public static void pause(Context context) {
        // TODO Auto-generated method stub
        try {
            if (mp != null) {
                mp.pause();
            }
        }catch (Exception e){
        }
    }
    public static void restart(Context context) {
        // TODO Auto-generated method stub
        try {
            if (mp != null) {
                mp.start();
            }
        }catch (Exception e){
        }
    }
}
