package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.SearchResultVo;
import com.iwangcn.qingkong.utils.AbDateUtil;
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
    private List<SearchResultVo> mList;
    private Context mContext;

    public SearchResultAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<SearchResultVo> dataList) {
        mList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mList == null) {
            mList = new ArrayList<SearchResultVo>();
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
        final SearchResultVo bean = mList.get(position);
        if (!TextUtils.isEmpty(bean.getTitle())) {
            viewHolder.title.setText(bean.getTitle());
        }
        if (bean.getPubtime() != 0) {
            viewHolder.time.setText(AbDateUtil.formatDateStrGetDay(bean.getPubtime()));
        }

        if (!TextUtils.isEmpty(bean.getWebSite())) {
            viewHolder.from.setText(bean.getWebSite());
        }
        if (!TextUtils.isEmpty(bean.getEventName())) {
            viewHolder.tvEvent.setText(bean.getEventName());
        }
        if (!TextUtils.isEmpty(bean.getKeywords())) {

            TagAdapter<String> tagAdapter = new TagAdapter<String>(initDatas(bean.getKeywords())) {
                @Override
                public View getView(FlowLayout parent, int position, String o) {

                    TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv,
                            parent, false);
                    tv.setText(o);
                    if(bean.isFollowUp()){//如果是已经跟进的橙色
                        tv.setBackground(AbViewUtil.getShapeDrawable(mContext.getString(R.string.tag_color_orange)));
                    }else{
                        tv.setBackground(AbViewUtil.getShapeDrawable(mContext.getString(R.string.tag_normal)));
                    }
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