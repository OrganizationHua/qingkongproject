package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.EventData;
import com.iwangcn.qingkong.ui.model.NewsInfo;
import com.iwangcn.qingkong.utils.AbViewUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 搜索结果
 */
public class SearchResultAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<EventData> mList;
    private Context mContext;

    public SearchResultAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<EventData> dataList) {
        mList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mList == null) {
            mList = new ArrayList<EventData>();
        }
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_search_result_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.search_result_item_title);
            viewHolder.time = (TextView) convertView.findViewById(R.id.search_result_item_time);
            viewHolder.from = (TextView) convertView.findViewById(R.id.search_result_item_from);
            viewHolder.tvEvent = (TextView) convertView.findViewById(R.id.search_result_item_event);
            viewHolder.tagFlowLayout = (TagFlowLayout) convertView.findViewById(R.id.search_tag_flowlayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        EventData eventData = mList.get(position);
        NewsInfo bean = eventData.getData();
        if (!TextUtils.isEmpty(bean.getTitle())) {
            viewHolder.title.setText(bean.getTitle());
        }
//        if (!TextUtils.isEmpty(bean.getPubtime())) {
//            viewHolder.time.setText(bean.getPubtime());
//        }
        if (!TextUtils.isEmpty(bean.getNumb())) {
            viewHolder.from.setText(bean.getNumb());
        }
        if (!TextUtils.isEmpty(bean.getContent())) {
            viewHolder.tvEvent.setText(bean.getTitle());
        }
        if (!TextUtils.isEmpty(bean.getKeywords())) {

            TagAdapter<String> tagAdapter = new TagAdapter<String>(initDatas(bean.getKeywords())) {
                @Override
                public View getView(FlowLayout parent, int position, String o) {

                    TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv,
                            parent, false);
                    tv.setText(o);
                    tv.setBackground(AbViewUtil.getShapeDrawable(mContext.getString(R.string.tag_normal)));
                    return tv;
                }
            };
            viewHolder.tagFlowLayout.setAdapter(tagAdapter);
        }

        return convertView;
    }

    static class ViewHolder {
        public TextView title;
        public TextView time;
        public TextView from;
        public TextView tvEvent;
        public TagFlowLayout tagFlowLayout;
    }

    private List<String> initDatas(String keywords) {
        List<String> itemData = new ArrayList<String>(3);
        itemData.add(keywords);
        return itemData;
    }
}