package com.iqinbao.android.songstv.utils;

import android.content.Context;
import android.widget.ImageView;

import com.iqinbao.android.songstv.R;
import com.iqinbao.android.songstv.view.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Description : 图片加载工具类
 * Author : hhh
 * Date   : 15/12/21
 */
public class ImageLoaderUtils {

    public static void display(Context context, ImageView imageView, String url,int placeholder, int error) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Picasso.with(context).load(url).placeholder(placeholder).error(error).into(imageView);

    }
    /**
     * 加载本地图片，解决图片过大造成的OOM异常
     */
    public static void localLoad(Context context, ImageView imageView, String url, int width, int hight) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Picasso.with(context).load(url).resize(width,hight).centerCrop().into(imageView);
    }



    /**
     * 收藏和播放记录。
     * @param context
     * @param imageView
     * @param url
     * @param width
     * @param height
     */
    public static void display1(Context context, ImageView imageView, String url,int width,int height) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusType(28,1).oval(false).build();
        if(url.length()>0) {
            Picasso.with(context).load(url).transform(transformation).placeholder(R.drawable.item_sing)
                    .error(R.drawable.item_sing).resize(width,height).centerCrop().into(imageView);
        }
        else{
            imageView.setImageResource(R.drawable.item_sing);
        }
    }
    public static void display3(Context context, ImageView imageView, String url,int width,int height) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusType(26,1).oval(false).build();
        if(url.length()>0) {
            Picasso.with(context).load(url).transform(transformation).placeholder(R.drawable.d27)
                    .error(R.drawable.item_sing).resize(width,height).centerCrop().into(imageView);
        }
        else{
            imageView.setImageResource(R.drawable.item_sing);
        }
    }
    /**
     * 专题加载。
     * @param context
     * @param imageView
     * @param url
     * @param width
     * @param height
     */
    public static void display_dissertation(Context context, ImageView imageView, String url,int width,int height) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusType(24,1).oval(false).build();
        if(url.length()>0) {
            Picasso.with(context).load(url).transform(transformation).placeholder(R.drawable.dissertation)
                    .error(R.drawable.dissertation).resize(width,height).centerCrop().into(imageView);
        }
        else{
            imageView.setImageResource(R.drawable.dissertation);
        }
    }

    /**
     * 歌单
     * @param context
     * @param imageView
     * @param url
     * @param width
     * @param height
     */
    public static void display2(Context context, ImageView imageView, String url,int width,int height) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusType(25,2).oval(false).build();

        if(url.length()>0) {
            Picasso.with(context).load(url).transform(transformation).placeholder(R.drawable.main_sing_item_bg)
                    .error(R.drawable.main_sing_item_bg).resize(width,height).centerCrop().into(imageView);
        }
        else{
//            imageView.setImageResource(R.drawable.gedan_load);
            imageView.setImageResource(R.drawable.main_sing_item_bg);
        }

    }

    /**
     * 歌曲加载
     * @param context
     * @param imageView
     * @param url
     * @param width
     * @param height
     */
    public static void display_item_sing(Context context, ImageView imageView, String url,int width,int height) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusType(28,1).oval(false).build();
        if(url.length()>0) {
            Picasso.with(context).load(url).transform(transformation).placeholder(R.drawable.item_sing)
                    .error(R.drawable.item_sing).resize(width,height).centerCrop().into(imageView);
        }
        else{
            imageView.setImageResource(R.drawable.item_sing);
        }

    }
    public static  void display_foucus(Context context, ImageView imageView, String url,int placeholder){
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusType(24,1).oval(false).build();
        if(url.length()>0) {
            Picasso.with(context).load(url).transform(transformation).placeholder(placeholder)
                    .error(placeholder).into(imageView);
        }
        else{
            Picasso.with(context).load(placeholder).transform(transformation)
                    .into(imageView);
        }
    }
    public static void display_three_dissertation(Context context, ImageView imageView, int placeholder, int id,int width,int height) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusType(24,1).oval(false).build();
        Picasso.with(context).load(id).transform(transformation).placeholder(placeholder)
                .error(placeholder).resize(width, height).centerCrop().into(imageView);
    }

}
