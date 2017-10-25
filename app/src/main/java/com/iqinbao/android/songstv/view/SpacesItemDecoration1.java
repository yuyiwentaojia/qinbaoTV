package com.iqinbao.android.songstv.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by admin on 2016/9/12.
 */
public class SpacesItemDecoration1 extends RecyclerView.ItemDecoration {


        private int space;

        public SpacesItemDecoration1(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if(parent.getChildPosition(view) != 0)
                outRect.top = space;
        }

}
