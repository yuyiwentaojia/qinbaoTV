package com.iqinbao.android.songstv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iqinbao.android.songstv.R;

/**歌曲
 * Created by admin on 2016/8/22.
 */
public class VHDetail extends RecyclerView.ViewHolder {
    public ImageView imageViewlist;
    public RelativeLayout relativeLayout_text_image;
public TextView textView_name;
    public VHDetail(View itemView) {
        super(itemView);
        imageViewlist = (ImageView) itemView.findViewById(R.id.iv_item);
        textView_name= (TextView) itemView.findViewById(R.id.textView_name);
        relativeLayout_text_image = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_text_image);
    }
}


