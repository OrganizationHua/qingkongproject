package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.NewEventModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czh on 2017/4/12.
 */

public class NewsEventAdapter extends RecyclerView.Adapter<NewsEventAdapter.ViewHolder> {
    private Context mContext;
    private List<NewEventModel> mList;

    public NewsEventAdapter(Context context) {
        this.mContext = context;
    }

    public void setDataList(List<NewEventModel> mList) {
        this.mList = mList;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_news_event_item, viewGroup, false));

    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
       // viewHolder.mTextView.setText(mList.get(position));
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if (mList == null) {

            mList = new ArrayList<NewEventModel>();
        }
        return mList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);
        }
    }
}
