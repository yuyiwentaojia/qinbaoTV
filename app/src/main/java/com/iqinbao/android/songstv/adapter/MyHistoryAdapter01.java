package com.iqinbao.android.songstv.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iqinbao.android.songstv.R;
import com.iqinbao.android.songstv.beanstv.SongEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by qinbao-app-1 on 2016/9/9.
 */
public class MyHistoryAdapter01 extends RecyclerView.Adapter<MyHistoryAdapter01.MyViewHolder> {
    List<SongEntity.CatContentsBean> recordList;
    Context context;
    private int index;
    private OnRecyclerViewItemClick mOnItemClickListener=null;
    public MyHistoryAdapter01(Context mContext, List<SongEntity.CatContentsBean> recordList, int index) {
        this.recordList = recordList;
        this.context = mContext;
        this.index=index;
    }

    public interface OnRecyclerViewItemClick{
        void onClick(View view, int position);
    }

    public void setOnRecyItemClickListener (OnRecyclerViewItemClick mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_collection, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (index==1) {
            String pic_s = recordList.get(position).getPic_s();
            Picasso.with(context).load(pic_s).resize(348,264).centerCrop().into(holder.mImageView);
            holder.mTopImageView.setVisibility(View.GONE);
            holder.textView.setText(recordList.get(position).getTitle());
        }else if (index==2){
            //显示删除图标
            String pic_s = recordList.get(position).getPic_s();
            holder.mTopImageView.setVisibility(View.VISIBLE);
            Picasso.with(context).load(pic_s).resize(348,264).centerCrop().into(holder.mImageView);
            holder.textView.setText(recordList.get(position).getTitle());
        }
        holder.itemView.setTag(position);
        holder.itemView.setFocusable(true);
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    holder.textView.setSelected(true);
                    holder.textView.setTextColor(Color.RED);
                }else {
                    holder.textView.setSelected(false);
                    holder.textView.setTextColor(Color.WHITE);
                }
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v, position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        ImageView mImageView,mTopImageView;
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            mImageView = (ImageView) view.findViewById(R.id.item_image);
            mTopImageView = (ImageView) view.findViewById(R.id.item_image_top);
            textView= (TextView) view.findViewById(R.id.textView_name);
        }
    }
}