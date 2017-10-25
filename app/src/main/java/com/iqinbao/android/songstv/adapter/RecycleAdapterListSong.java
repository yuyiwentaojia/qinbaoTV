package com.iqinbao.android.songstv.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.iqinbao.android.songstv.R;
import com.iqinbao.android.songstv.beanstv.SongEntity;
import com.iqinbao.android.songstv.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 左边的歌单的分类展示
 * Created by admin on 2016/8/22.
 */
public class RecycleAdapterListSong extends RecyclerView.Adapter<VHSong> {

    private Context mContext;
    //歌单
    private List<SongEntity> songList = new ArrayList<SongEntity>();
    private OnRecyItemClickListener onRecyItemClickListener;
    private OnItemSelectListener mSelectListener;
    private int currentPosition , focusFlag , focusNumFlag;
    public void setOnItemSelectListener(OnItemSelectListener listener) {
        mSelectListener = listener;
    }

    public void onClickItem(View view, int position) {
        onRecyItemClickListener.onClickItem(view, position);
    }

    public void setOnRecyItemClickListener(OnRecyItemClickListener onRecyItemClickListener) {
        this.onRecyItemClickListener = onRecyItemClickListener;
    }
    public RecycleAdapterListSong(Context mContext) {
        this.mContext = mContext;

    }
    public RecycleAdapterListSong(List<SongEntity> songList) {
        this.songList = songList;

    }

    /**
     * 加载
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public VHSong onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_list_text, null);
        VHSong vh = new VHSong(view);
        mContext = parent.getContext();
        return vh;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    /**
     * 数据与viewholder的绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final VHSong holder, final int position) {
        ++focusNumFlag;
        if(position == 0 && focusNumFlag == 1){
            focusFlag = 1;
        }
        String title = songList.get(position).getCatname();
        holder.textView_song_list.setText(title);
        holder.textView_song_list.setGravity(Gravity.CENTER);
        holder.textView_song_list.setTag(position);
        holder.textView_song_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.textView_song_list.setBackgroundResource(R.drawable.topic_name_active);
                if(onRecyItemClickListener != null) {
                    LogUtils.e("currentPosition--111---",currentPosition+"");
                    onRecyItemClickListener.onClickItem(holder.textView_song_list, currentPosition);
                }
            }
        });

        holder.textView_song_list.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    currentPosition = (int) holder.textView_song_list.getTag();
                    holder.textView_song_list.setBackgroundResource(R.drawable.topic_name_inactive);
                    holder.textView_song_list.setTextColor(mContext.getResources().getColor(R.color.sing_list_text_bg));
                    holder.textView_song_list.setTextSize(TypedValue.COMPLEX_UNIT_PX,mContext.getResources().getDimensionPixelSize(R.dimen.w_40));
                    if(mSelectListener != null) {
                        mSelectListener.onItemSelect(holder.textView_song_list, currentPosition);
                    }
                    if(focusFlag == 1){
                        holder.textView_song_list.performClick();
                        focusFlag = 0;
                    }

                }else {
                    holder.textView_song_list.setBackgroundResource(0);
                    holder.textView_song_list.setTextColor(mContext.getResources().getColor(R.color.sing_list_text_bg));
                    holder.textView_song_list.setTextSize(TypedValue.COMPLEX_UNIT_PX,mContext.getResources().getDimensionPixelSize(R.dimen.w_40));
                }
            }
        });


//        holder.linearLayout_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        holder.linearLayout_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//            }
//        });

    }


    /**
     * 获取数据的总条目
     *
     * @return
     */
    @Override
    public int getItemCount() {return songList==null?0: songList.size();}


    /**
     * 回调，给Recycle添加监听事件，并且传递position
     */
    public interface OnRecyItemClickListener {

        void onClickItem(View view,int position);
    }

    public interface OnItemSelectListener {
        void onItemSelect(View view, int position);
    }
}
