package com.iqinbao.android.songstv.view;


import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iqinbao.android.songstv.R;

/**
 * 这是设置recycleview的item间隔，
 * A {@link android.support.v7.widget.RecyclerView.ItemDecoration} implementations that
 * calculates the outer of the item that should space.
 * Just consider the orientation of VERTICAL.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int topBottom;
    private int leftRight;
    private int mSpace;
    private int mSpanCount;
    private int mRadixX;
    private int mCountInFirstLine = 1;
    private int orientation;
private Context mContext;
    /**
     * 需要通过构造方法传入space 间隔距离，spanCount 是网格布局时设置的行列数，orientation是网格布局的排列方向。
     * @param space
     * @param spanCount
     * @param orientation
     */
    public SpacesItemDecoration(int space, int spanCount, int orientation, Context mContext) {
        this.mSpace = space;
        this.mSpanCount = spanCount;
        this.orientation=orientation;
        this.mRadixX = space / spanCount;
        this.mContext=mContext;
    }
    public SpacesItemDecoration(int bottom,int right, int space, int spanCount, int orientation, Context mContext) {
        this.topBottom = bottom;
        this.leftRight = right;
        this.mSpace = space;
        this.mSpanCount = spanCount;
        this.orientation=orientation;
        this.mRadixX = space / spanCount;
        this.mContext=mContext;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        final int position = params.getViewPosition();
        final int spanSize;
        final int index;

        if (params instanceof GridLayoutManager.LayoutParams) {
            GridLayoutManager.LayoutParams gridParams = (GridLayoutManager.LayoutParams) params;
            spanSize = gridParams.getSpanSize();
            index = gridParams.getSpanIndex();
            //calculate real num in line
            if (index == 0 && mSpanCount > 1 && position == 0) {
                int tempPosition = position;
                int countInLine = 0;
                int spanIndex;
                do {
                    countInLine++;
                    if (tempPosition < state.getItemCount() - 1) {
                        spanIndex = ((GridLayoutManager) parent.getLayoutManager()).getSpanSizeLookup().getSpanIndex(++tempPosition, mSpanCount);
                    } else {
                        spanIndex = 0;
                    }
                } while (spanIndex != 0);

                if (position == 0)
                    mCountInFirstLine = countInLine;
            }
        } else {
            spanSize = 1;
            index = 0;
        }

        // invalid value
        if (spanSize < 1 || index < 0 || spanSize > mSpanCount)
            return;
        if (orientation== GridLayoutManager.HORIZONTAL){
            outRect.bottom = mSpace - mRadixX * index;
            outRect.top = mRadixX + mRadixX * (index + spanSize - 1);
            // set top to all in first lane
            if (position < mCountInFirstLine) {
                outRect.left =15;
            }else {
                outRect.left =12;
            }
            outRect.right =12;


        }else if (orientation== GridLayoutManager.VERTICAL) {
            outRect.left = mSpace - mRadixX * index;
            outRect.right = mRadixX + mRadixX * (index + spanSize - 1);
            // int dimension_first = (int) mContext.getResources().getDimension(R.dimen.recyclerView_detail__first);
            int dimension_first = (int) mContext.getResources().getDimension(R.dimen.w_10);
            // int dimension_other = (int) mContext.getResources().getDimension(R.dimen.recyclerView_detail_other);
            int dimension_other = (int) mContext.getResources().getDimension(R.dimen.w_15);
            if (position < mCountInFirstLine) {
                outRect.top = dimension_first;
            } else {
                outRect.top = dimension_other;
            }
            outRect.bottom = dimension_other;
        }


//        int position = parent.getChildAdapterPosition(view); // item position
//        int column = position % 4; // item column
//
//        if (true) {
//            outRect.left = mSpace - column * mSpace / 4; // spacing - column * ((1f / spanCount) * spacing)
//            outRect.right = (column + 1) * mSpace / 4; // (column + 1) * ((1f / spanCount) * spacing)
//
//            if (position < 4) { // top edge
//                outRect.top = mSpace;
//            }
//            outRect.bottom = mSpace; // item bottom
//        } else {
//            outRect.left = column * mSpace / 4; // column * ((1f / spanCount) * spacing)
//            outRect.right = mSpace - (column + 1) * mSpace / 4; // spacing - (column + 1) * ((1f / panCount) * spacing)
//            if (position >= 4) {
//                outRect.top = mSpace; // item top
//            }
//        }


//            outRect.left = mSpace;
//            outRect.right = mSpace;
//            outRect.bottom = mSpace;
//            if (parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1
//                    || parent.getChildLayoutPosition(view) == 2 || parent.getChildLayoutPosition(view) == 3) {
//                outRect.top = 0;
//            } else {
//                outRect.top = mSpace;
//            }
//        }

    }

//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
//        //判断总的数量是否可以整除
//        int totalCount = layoutManager.getItemCount();
//        int surplusCount = totalCount % layoutManager.getSpanCount();
//        int childPosition = parent.getChildAdapterPosition(view);
//        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {//竖直方向的
//            if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) {
//                //后面几项需要bottom
//                outRect.bottom = topBottom;
//            } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
//                outRect.bottom = topBottom;
//            }
//            if ((childPosition + 1) % layoutManager.getSpanCount() == 0) {//被整除的需要右边
//                outRect.right = leftRight;
//            }
//            outRect.top = topBottom;
//            outRect.left = leftRight;
//        } else {
//            if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) {
//                //后面几项需要右边
//                outRect.right = leftRight;
//            } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
//                outRect.right = leftRight;
//            }
//            if ((childPosition + 1) % layoutManager.getSpanCount() == 0) {//被整除的需要下边
//                outRect.bottom = topBottom;
//            }
//            outRect.top = topBottom;
//            outRect.left = leftRight;
//        }
//    }
//
//
//    }

}
