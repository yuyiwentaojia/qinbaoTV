package com.iqinbao.android.songstv.view;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by qinbao-app-1 on 2016/9/2.
 */

public class MyVideoView extends VideoView {

        public MyVideoView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        public MyVideoView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyVideoView(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            // TODO Auto-generated method stub

            int width = getDefaultSize(0, widthMeasureSpec);
            int height = getDefaultSize(0, heightMeasureSpec);
            setMeasuredDimension(width, height);
        }

    }
