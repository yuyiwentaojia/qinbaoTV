package com.iqinbao.android.songstv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.iqinbao.android.songstv.R;

/**歌单
 * Created by admin on 2016/9/12.
 */
public class VHSong extends RecyclerView.ViewHolder{
//    public LinearLayout linearLayout_text;
//    public ImageView imageView_song_list;
    public Button textView_song_list;

    public VHSong(View itemView) {
        super(itemView);
//        imageView_song_list= (ImageView) itemView.findViewById(imageView_song_list);
//        linearLayout_text= (LinearLayout) itemView.findViewById(linearLayout_text);
        textView_song_list= (Button) itemView.findViewById(R.id.textView_song_list);
    }
}
