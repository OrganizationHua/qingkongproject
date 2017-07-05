package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.EventData;
import com.iwangcn.qingkong.ui.model.EventDataVo;
import com.iwangcn.qingkong.ui.model.NewsInfo;
import com.iwangcn.qingkong.ui.model.QkTagModel;
import com.iwangcn.qingkong.utils.AbDateUtil;
import com.iwangcn.qingkong.utils.AbViewUtil;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新闻事件Adapter
 * Created by czh on 2017/4/12.
 */

public class NewsListAdapter extends BaseAdapter {
    private List<EventDataVo> mList;
    private Context mContext;

    public NewsListAdapter(Context context) {
        this.mContext = context;
    }

    public void setDataList(List<EventDataVo> dataList) {
        mList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mList == null) {
            mList = new ArrayList<EventDataVo>();
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
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.activity_news_event_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final EventDataVo eventDataVo = mList.get(position);
        EventData eventData = eventDataVo.getEventData();
        NewsInfo model = eventData.getData();
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
        if (eventDataVo.isSelect()) {
            viewHolder.imgBlueCircle.setVisibility(View.GONE);
            viewHolder.imgGreenCircl.setVisibility(View.VISIBLE);
            viewHolder.linContent.setVisibility(View.VISIBLE);
            viewHolder.newsYear.setTextColor(mContext.getResources().getColor(R.color.font_green));
            viewHolder.newsDay.setTextColor(mContext.getResources().getColor(R.color.font_green));
        } else {
            viewHolder.imgBlueCircle.setVisibility(View.VISIBLE);
            viewHolder.imgGreenCircl.setVisibility(View.GONE);
            //  AbViewUtil.collapse(viewHolder.linContent);
            viewHolder.linContent.setVisibility(View.GONE);
            viewHolder.newsYear.setTextColor(mContext.getResources().getColor(R.color.font_gray));
            viewHolder.newsDay.setTextColor(mContext.getResources().getColor(R.color.font_gray));
        }
        if (!TextUtils.isEmpty(model.getTitle())) {
            viewHolder.tvTitle.setText(model.getTitle());
        }
        if (!TextUtils.isEmpty(model.getAuthor())) {
            viewHolder.newsFrom.setText(model.getAuthor());
        }
        if (!TextUtils.isEmpty(model.getContent())) {
            viewHolder.newsBrief.setText(model.getContent().trim());
        }
        viewHolder.newsTime.setText(AbDateUtil.getStringByFormat(model.getUpdateTime(), "yyyy-MM-dd"));
        viewHolder.newsYear.setText(AbDateUtil.getStringByFormat(model.getUpdateTime(), "yyyy"));
        viewHolder.newsDay.setText(AbDateUtil.getStringByFormat(model.getUpdateTime(), "MM-dd"));
        if (position != 0) {//判断是否显示年份
            if (AbDateUtil.getYear(model.getPubtime()) != AbDateUtil.getYear(mList.get(position - 1).getEventData().getUpdateTime())) {
                viewHolder.newsYear.setVisibility(View.VISIBLE);
            } else {
                viewHolder.newsYear.setVisibility(View.GONE);
            }
        }
        viewHolder.linBlueCircl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mList.get(position).isSelect()) {
                    return;
                } else {
                    //把之前选中的取消
                    for (int i = 0; i < mList.size() - 1; i++) {
                        if (mList.get(i).isSelect()) {
                            mList.get(i).setSelect(false);
                            break;
                        }
                    }
                    //选中当前的
                    mList.get(position).setSelect(true);
                    AbViewUtil.expand(viewHolder.linContent);
                    notifyDataSetChanged();
                }
            }
        });
        viewHolder.tagFlowLayout.setAdapter(new QKTagAdapter(mContext, getTagList(mList.get(position))));
        return convertView;
    }

    private List<QkTagModel> getTagList(EventDataVo eventDataVo) {
        EventData eventData = eventDataVo.getEventData();
        NewsInfo model = eventData.getData();
        List<QkTagModel> list = new ArrayList<>();
        if (!TextUtils.isEmpty(model.getKeywords())) {
            String[] keyworsArr = model.getKeywords().split(",");
            for (String tagString : keyworsArr) {
                if (!TextUtils.isEmpty(tagString)) {
                    list.add(new QkTagModel(1, tagString, 1, eventDataVo.getAutoId()));
                }
            }
        }
        if (eventDataVo.getBusinessLabels() != null) {
            for (String tagString : eventDataVo.getBusinessLabels()) {
                if (!TextUtils.isEmpty(tagString)) {
                    list.add(new QkTagModel(2, tagString, 1, eventDataVo.getAutoId()));
                }
            }
        }
        if (eventDataVo.getSelfLabels() != null) {
            for (String tagString : eventDataVo.getSelfLabels()) {
                if (!TextUtils.isEmpty(tagString)) {
                    list.add(new QkTagModel(3, tagString, 1, eventDataVo.getAutoId()));
                }
            }
        }
        return list;
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
        public TagFlowLayout tagFlowLayout;

        @BindView(R.id.newsevent_time)
        public TextView newsTime;//发布事件

        @BindView(R.id.newsevent_from)
        public TextView newsFrom;
        @BindView(R.id.newsevent_brief)
        public TextView newsBrief;

        @BindView(R.id.newslist_item_year)
        public TextView newsYear;

        @BindView(R.id.newslist_item_day)
        public TextView newsDay;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private List<String> initDatas(String keywords) {
        List<String> itemData = new ArrayList<String>(3);
        itemData.add(keywords);
        return itemData;
    }
}
