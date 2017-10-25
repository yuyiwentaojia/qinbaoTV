package com.iqinbao.android.songstv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iqinbao.android.songstv.R;
import com.iqinbao.android.songstv.beanstv.SongEntity;
import com.iqinbao.android.songstv.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RecordAdapter extends BaseAdapter {
//Collection
    private List<SongEntity.CatContentsBean> mDatas;
    private final LayoutInflater mInflater;
    Context mContext;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    int index = 1;

    public void notifyData(int position) {
        mDatas.remove(position);
        this.notifyDataSetChanged();
    }

    public RecordAdapter(Context context, List<SongEntity.CatContentsBean> data) {
        mDatas = data;
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void notifyDataSetChanged(int position){
        mDatas.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_collection01, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindViewData(position, viewHolder);
        return convertView;
    }

    private void bindViewData(final int position, final ViewHolder viewHolder) {
        SongEntity.CatContentsBean record = mDatas.get(position);
        viewHolder.textView.setText(record.getTitle());
      //  ImageLoaderUtils.display(mContext,viewHolder.mImageView,record.getPic_s());
        ImageLoaderUtils.display1(mContext,viewHolder.mImageView,record.getPic_s(),344,264);
        if (index==1) {
            viewHolder.mTopImageView.setVisibility(View.GONE);
        }else if (index==2){
            //显示删除图标
            viewHolder.mTopImageView.setVisibility(View.VISIBLE);
        }

//        if(position%4 == 0){
//            viewHolder.itemView.setTag(R.integer.tag_view_postion,0);
//        }
//        else{
//            viewHolder.itemView.setTag(R.integer.tag_view_postion,1);
//        }

    }

    class ViewHolder {
        View itemView;
        TextView textView;
        ImageView mImageView,mTopImageView;
        public ViewHolder(View view) {
            this.itemView = view;
            mImageView = (ImageView) view.findViewById(R.id.item_image);
            mTopImageView = (ImageView) view.findViewById(R.id.item_image_top);
            textView= (TextView) view.findViewById(R.id.textView_name);
        }
    }
}
