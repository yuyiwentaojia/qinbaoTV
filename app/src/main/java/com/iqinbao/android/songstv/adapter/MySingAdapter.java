package com.iqinbao.android.songstv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.iqinbao.android.songstv.R;
import com.iqinbao.android.songstv.beanstv.SongEntity;
import com.iqinbao.android.songstv.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinbao-app-1 on 2016/8/22.
 */
public class MySingAdapter extends RecyclerView.Adapter<VHDetail>  {
    private Context mContext;
    private Map<Integer,View> map=new HashMap<>();
    private View view01;
    private int index=0;
    //歌曲
    private List<SongEntity.CatContentsBean> singList = new ArrayList<SongEntity.CatContentsBean>();
    private OnRecyItemClickListener onRecyItemClickListener;
    private OnItemSelectListener mSelectListener;


    public void setIndex(int index){
        this.index=index;
    }
    public void setOnItemSelectListener(OnItemSelectListener listener) {
        mSelectListener = listener;
    }
    public void setOnRecyItemClickListener(OnRecyItemClickListener onRecyItemClickListener) {
        this.onRecyItemClickListener = onRecyItemClickListener;
    }

    public MySingAdapter(List<SongEntity.CatContentsBean> singList) {
        this.singList = singList;
    }

    /**
     * 加载
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public VHDetail onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_mysing, null);
        VHDetail vh = new VHDetail(view);
        mContext = parent.getContext();
        return vh;
    }

    /**
     * 数据与viewholder的绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final VHDetail holder, final int position) {
        String pic_s = singList.get(position).getPic_s();
        String intro = singList.get(position).getTitle();
        ImageLoaderUtils.display3(mContext, holder.imageViewlist, pic_s, 272, 208);
        //刚刚跳转进来，设置播放视频的颜色
        if (position==index){
            view01=holder.textView_name;
            holder.textView_name.setBackgroundResource(R.mipmap.lay04);
            map.put(2,holder.textView_name);
        }else {
            holder.textView_name.setBackgroundResource(R.drawable.item_list_text_bg);
        }
        holder.textView_name.setText(intro);
        holder.relativeLayout_text_image.setFocusable(true);
        holder.relativeLayout_text_image.setTag(position);
        holder.itemView.setFocusable(true);
        holder.itemView.setNextFocusLeftId(R.id.bt_onAndoff);
        holder.itemView.setNextFocusRightId(R.id.bt_onAndoff);
        holder.itemView.setTag(holder.textView_name);
        holder.relativeLayout_text_image.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //此处就是设置textview为选中状态，方可以实现效果
                    holder.textView_name.setSelected(true);
                } else {
                    //   view.setBackgroundResource(R.color.transparent);
                    //此处就是设置textview为选中状态，方可以实现效果
                    holder.textView_name.setSelected(false);
                }
            }
        });
        holder.relativeLayout_text_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map.get(2)!=null){
                    map.put(1,map.get(2));
                }
                map.put(2,holder.textView_name);
                //先把所有的颜色清除
//                    map.get(0).setBackgroundResource(R.drawable.d28);
                if (map.get(1) != null) {
                    map.get(1).setBackgroundResource(R.drawable.d28);
                }
                if (map.get(2)!=null) {
                    map.get(2).setBackgroundResource(R.mipmap.lay04);
                }
                holder.textView_name.setBackgroundResource(R.mipmap.lay04);
                //点击之后再设置颜色
                onRecyItemClickListener.onClickItem(position,holder.textView_name);
            }
        });

    }

    /**
     * 获取数据的总条目
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return singList.size();
    }



    /**
     * 回调，给Recycle添加监听事件，并且传递position
     */
    public interface OnRecyItemClickListener {

        void onClickItem(int position,View view);
    }

    /**
     * 选中时回调接口
     */
    public interface OnItemSelectListener {
        void onItemSelect(View view_sing, int position);
    }


}
