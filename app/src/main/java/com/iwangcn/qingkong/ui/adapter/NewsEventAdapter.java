package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.EventInfo;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新闻事件Adapter
 * Created by czh on 2017/4/12.
 */

public class NewsEventAdapter extends BaseAdapter {
    private List<EventInfo> mList;
    private Context mContext;

    public NewsEventAdapter(Context context) {
        this.mContext = context;
    }

    public void setDataList(List<EventInfo> dataList) {
        mList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mList == null) {
            mList = new ArrayList<EventInfo>();
        }
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.activity_news_event_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
         EventInfo model = mList.get(position);
        if (position == 0) {
            viewHolder.imgIconUp.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgIconUp.setVisibility(View.GONE);
        }
        if (position == mList.size() - 1) {
            viewHolder.imgIconDown.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgIconDown.setVisibility(View.GONE);
        }
        if (model.isSelect()) {
            viewHolder.imgBlueCircle.setVisibility(View.GONE);
            viewHolder.imgGreenCircl.setVisibility(View.VISIBLE);
            viewHolder.linContent.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgBlueCircle.setVisibility(View.VISIBLE);
            viewHolder.imgGreenCircl.setVisibility(View.GONE);
            viewHolder.linContent.setVisibility(View.GONE);
        }
        viewHolder.linBlueCircl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i <mList.size()-1; i++) {
                    if(i==position){
                        mList.get(i).setSelect(true);
                    }else{
                        mList.get(i).setSelect(false);
                    }
                }
                notifyDataSetChanged();
            }
        });
        TagAdapter<String> tagAdapter = new TagAdapter<String>(initDatas()) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {

                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv,
                        parent, false);
                tv.setText(o);
                return tv;
            }
        };
        viewHolder.tagFlowLayout.setAdapter(tagAdapter);
        return convertView;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.timeline_up)
        public ImageView imgIconUp;//时间轴

        @BindView(R.id.timeline_down)
        public ImageView imgIconDown;

        @BindView(R.id.news_title)
        public TextView tvTitle;

        @BindView(R.id.newsevent_lin_content)
        public LinearLayout linContent;

        @BindView(R.id.newsevent_item_blue_circle)
        public ImageView imgBlueCircle;//小蓝点

        @BindView(R.id.newsevent_item_green_circle)
        public ImageView imgGreenCircl;//小绿点

        @BindView(R.id.lin_blue_circle)
        public LinearLayout linBlueCircl;//小蓝点点击范围

        @BindView(R.id.tag_flowlayout)
        public TagFlowLayout tagFlowLayout;//小蓝点点击范围

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    private List<String> initDatas() {
        List<String> itemData = new ArrayList<String>(3);

        for (int i = 0; i < 5; i++) {
            itemData.add("新闻标签");

        }
        return itemData;
    }
}
