package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.NewsModel;
import com.iwangcn.qingkong.ui.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * demo Adapter Created by zhchen on 15/8/5
 */
public class NewsAdapter extends BaseAdapter {
    private List<NewsModel> mList;
    private Context mContext;

    public NewsAdapter(Context context) {
        this.mContext = context;
    }

    public void setDataList(List<NewsModel> dataList) {
        mList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mList == null) {
            mList = new ArrayList<NewsModel>();
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
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.fragment_home_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NewsModel model = mList.get(position);
        if (!TextUtils.isEmpty(model.getTitle())){
            viewHolder.title.setText(model.getTitle());
        }
        if (!TextUtils.isEmpty(model.getNumb())){
            viewHolder.tvNumb.setText(model.getNumb());
        }
        if (!TextUtils.isEmpty(model.getTime())){
            viewHolder.tvTime.setText(model.getTime());
        }
        viewHolder.linCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(mContext, "收藏按钮");
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.news_title)
        public TextView title;//标题
        @BindView(R.id.new_item_num)
        public TextView tvNumb;//新闻数量
        @BindView(R.id.new_item_time)
        public TextView tvTime;//新闻数量
        @BindView(R.id.homefragment_lin_collect)
        public LinearLayout linCollect;//收藏
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}