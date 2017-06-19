package com.iwangcn.qingkong.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.activity.PicActivity;
import com.iwangcn.qingkong.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;


public class ImageAdapter extends BaseAdapter<ImageAdapter.MyViewHolder> {

    DisplayMetrics dm;

    public ImageAdapter(Context context, List<String> listDatas) {
        super(context, listDatas);
        dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_grid, parent, false);
        //动态设置ImageView的宽高，根据自己每行item数量计算
        //dm.widthPixels-dip2px(20)即屏幕宽度-左右10dp+10dp=20dp再转换为px的宽度，最后/3得到每个item的宽高
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((dm.widthPixels - dip2px(42)) / 3, (dm.widthPixels - dip2px(42)) / 3);
        view.setLayoutParams(lp);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final String url = listDatas.get(position);//转换
        GlideUtils.loadImageView(context, url, holder.iv, R.drawable.default_icon, R.drawable.default_icon);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PicActivity.class).putExtra("position", position).putStringArrayListExtra("url",(ArrayList<String>) listDatas);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;

        public MyViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
        }
    }

    int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
