package com.iqinbao.android.songstv.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.iqinbao.android.songstv.R;
import com.iqinbao.android.songstv.base.BaseFragmentActivity;
import com.iqinbao.android.songstv.utils.ImageLoaderUtils;

public class ImageActivity extends BaseFragmentActivity {
    private ImageView imageView;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mContext=this;
        imageView= (ImageView) findViewById(R.id.image);
        Intent intent=getIntent();
        String playurl_h = intent.getStringExtra("playurl_h");
        ImageLoaderUtils.display(mContext,imageView,playurl_h,R.drawable.back_image,R.drawable.back_image);
    }
}
