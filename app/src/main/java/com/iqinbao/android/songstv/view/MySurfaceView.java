package com.iqinbao.android.songstv.view;

import android.content.Context;
import android.view.SurfaceView;

/**
 * Created by lli on 2016/11/29.
 */
public class MySurfaceView extends SurfaceView {
    public MySurfaceView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
