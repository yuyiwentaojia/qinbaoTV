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
 * Created by qinbao-app-1 on 2016/8/22.
 */
public class MyAdapterplay extends RecyclerView.Adapter<MyAdapterplay.MyViewHolder> {
    private Context mContext;
    private List<SongEntity.CatContentsBean> cat_context;

    private View.OnFocusChangeListener mOnFocusChangeListener;
    private OnRecyclerItemClick mOnItemClickListener=null;

    public MyAdapterplay(Context mContext, List<SongEntity.CatContentsBean> cat_context) {
        this.mContext=mContext;
        this.cat_context=cat_context;
    }

    public interface OnRecyclerItemClick{
         void onClick(View parent, int position);
    }
    public void setOnRecyItemClickListener (OnRecyclerItemClick mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }
    @Override
    public MyAdapterplay.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_collection,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyAdapterplay.MyViewHolder holder, final int position) {
        String pic_s = cat_context.get(position).getPic_s();
        Picasso.with(mContext).load(pic_s).resize(250, 200).centerCrop().into(holder.mImageView);
        holder.itemView.setTag(position);
        holder.textView.setText(cat_context.get(position).getTitle());
        holder.itemView.setFocusable(true);
        holder.textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                mOnItemClickListener.onClick(v,position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return cat_context.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        ImageView mImageView;
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            mImageView= (ImageView) view.findViewById(R.id.item_image_top);
            textView= (TextView) view.findViewById(R.id.textView_name);
        }
    }

}
