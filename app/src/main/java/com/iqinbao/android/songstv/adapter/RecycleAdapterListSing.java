package com.iqinbao.android.songstv.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.iqinbao.android.songstv.R;
import com.iqinbao.android.songstv.beanstv.SongEntity;
import com.iqinbao.android.songstv.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 右边的歌曲展示
 * Created by admin on 2016/8/22.
 */
public class RecycleAdapterListSing extends RecyclerView.Adapter<VHDetail> {

    private Context mContext;
    //歌曲
    private List<SongEntity.CatContentsBean> singList = new ArrayList<SongEntity.CatContentsBean>();
    private OnRecyItemClickListener onRecyItemClickListener;
    private OnItemSelectListener mSelectListener;
    private int currentPosition;

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        mSelectListener = listener;
    }
    public void setOnRecyItemClickListener(OnRecyItemClickListener onRecyItemClickListener) {
        this.onRecyItemClickListener = onRecyItemClickListener;
    }

    public RecycleAdapterListSing(List<SongEntity.CatContentsBean> singList) {
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
        View view = View.inflate(parent.getContext(), R.layout.item_list_text_image, null);
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
        ImageLoaderUtils.display_item_sing(mContext, holder.imageViewlist, pic_s, 344, 264);
        String text=(position+1)+"."+intro;
//        if (position==(singList.size()-1)){
//            holder.relativeLayout_text_image.setNextFocusDownId(R.id.relativeLayout_text_image);
//        }
        holder.textView_name.setText(text);
        holder.relativeLayout_text_image.setFocusable(true);
        holder.relativeLayout_text_image.setTag(position);
        holder.relativeLayout_text_image.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //此处就是设置textview为选中状态，方可以实现效果
                    holder.textView_name.setSelected(true);
                    currentPosition = (int) holder.relativeLayout_text_image.getTag();
                    if (mSelectListener!=null){
                        mSelectListener.onItemSelect(holder.relativeLayout_text_image, position);
                    }
                } else {
                    //此处就是设置textview为未选中状态，方可以实现效果
                    holder.textView_name.setSelected(false);
                }
            }
        });
        holder.relativeLayout_text_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyItemClickListener.onClickItem(position);
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
        return singList==null?0: singList.size();
    }


    /**
     * 回调，给Recycle添加监听事件，并且传递position
     */
    public interface OnRecyItemClickListener {

        void onClickItem(int position);
    }

    /**
     * 选中时回调接口
     */
    public interface OnItemSelectListener {
        void onItemSelect(View view_sing, int position);
    }
}
