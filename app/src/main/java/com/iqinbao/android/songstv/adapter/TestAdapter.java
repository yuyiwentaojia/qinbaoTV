package com.iqinbao.android.songstv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iqinbao.android.songstv.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by qinbao-app-1 on 2016/11/3.
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {
    private Context context;
    private JSONObject jsonObject;
    HashMap<Integer, String> map01;
    List<String> mdata01;

//    public TestAdapter(Context mContext, JSONObject json) {
//        context=mContext;
//        jsonObject=json;
//    }
//
    public TestAdapter(Context mContext, HashMap<Integer, String> map) {
        context=mContext;
        map01=map;
    }

//    public TestAdapter(Context mContext, List<String> mdata) {
//        context=mContext;
//        mdata01=mdata;
//    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_test,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView01.setText(map01.get(position));
//        String text=map01.get(position);
//        holder.textView01.setText(text);
//        try {
//            String percent= jsonObject.getString("netSpeed");
//            holder.textView01.setText(percent);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public int getItemCount() {
        int size=map01.size();
        return size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private TextView textView01;
        public MyViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            textView01= (TextView) view.findViewById(R.id.test_text01);
        }
    }
}
