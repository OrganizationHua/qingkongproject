package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.NewsInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 搜索结果
 */
public class SearchResultAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<NewsInfo> mList;

    public SearchResultAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<NewsInfo> dataList) {
        mList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mList == null) {
            mList = new ArrayList<NewsInfo>();
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NewsInfo bean = mList.get(position);
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
            viewHolder.tvEvent.setText(bean.getContent());
        }
        return convertView;
    }

    static class ViewHolder {
        public TextView title;
        public TextView time;
        public TextView from;
        public TextView tvEvent;
    }
}