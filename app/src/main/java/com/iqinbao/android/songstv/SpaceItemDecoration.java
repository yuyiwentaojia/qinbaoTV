package com.iqinbao.android.songstv;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by qinbao-app-1 on 2016/9/20.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int spaceLeft,spaceTop,spaceBottom,space;

    public SpaceItemDecoration(int spaceLefte,int spaceTop,int spaceBottom,int space) {
        this.spaceLeft = spaceLefte;
        this.spaceTop=spaceTop;
        this.spaceBottom=spaceBottom;
        this.space=space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=spaceLeft;
        outRect.bottom=spaceBottom;
        outRect.top=spaceTop;
        if(parent.getChildPosition(view) != 0){
            outRect.top = spaceTop;
        }else {
            outRect.top=space;
        }

    }
}
