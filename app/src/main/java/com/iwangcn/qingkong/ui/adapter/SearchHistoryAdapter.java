package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iwangcn.qingkong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索
 */
public class SearchHistoryAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> mList;

    public SearchHistoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<String> dataList) {
        mList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mList == null) {
            mList = new ArrayList<String>();
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
            convertView = inflater.inflate(R.layout.activity_search_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.search_item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String bean = mList.get(position);
        if (!TextUtils.isEmpty(bean)) {
            viewHolder.name.setText(bean);
        }
        return convertView;
    }

    static class ViewHolder {
        public TextView name;
    }
}