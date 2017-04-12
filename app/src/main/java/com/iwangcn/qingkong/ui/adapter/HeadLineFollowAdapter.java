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
import com.iwangcn.qingkong.ui.model.HelperModel;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * demo Adapter Created by zhchen on 15/8/5
 */
public class HeadLineFollowAdapter extends BaseAdapter {
    private List<HelperModel> mList;
    private Context mContext;
    private int type;
    public HeadLineFollowAdapter(Context context,int type) {
        this.mContext = context;
        this.type=type;
    }

    public void setDataList(List<HelperModel> dataList) {
        mList = dataList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (mList == null) {
            mList = new ArrayList<HelperModel>();
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
                    R.layout.fragment_headline_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(type==1){
            viewHolder.llReprocess.setVisibility(View.GONE);
            viewHolder.llFragment.setVisibility(View.VISIBLE);
        }else {
            viewHolder.llReprocess.setVisibility(View.VISIBLE);
            viewHolder.llFragment.setVisibility(View.GONE);
        }
        HelperModel model = mList.get(position);
        if (!TextUtils.isEmpty(model.getTitle())) {
            viewHolder.title.setText(model.getTitle());
        }

        if (!TextUtils.isEmpty(model.getTime())) {
            viewHolder.tvTime.setText(model.getTime());
        }
        if (!TextUtils.isEmpty(model.getFrom())) {
            viewHolder.tvFrom.setText(model.getFrom());
        }
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
        viewHolder.llCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "取消跟进");
            }
        });
        viewHolder.llSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "置顶");
            }
        });
        viewHolder.llFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "处理完成");
            }
        });
        viewHolder.tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "查看原文");
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        public TextView title;//标题

        @BindView(R.id.tv_time)
        public TextView tvTime;//时间

        @BindView(R.id.tv_from)
        public TextView tvFrom;//来源

        @BindView(R.id.ll_cancle_follow)
        public LinearLayout llCancle;//取消跟进

        @BindView(R.id.ll_set_top)
        public LinearLayout llSet;//置顶

        @BindView(R.id.ll_processed_finished)
        public LinearLayout llFinish;//处理完成

        @BindView(R.id.ll_reprocess)
        public LinearLayout llReprocess;//

        @BindView(R.id.ll_fragment)
        public LinearLayout llFragment;//

        @BindView(R.id.tv_scan)
        public TextView tvScan;//查看新闻

        @BindView(R.id.tag_flowlayout)
        public TagFlowLayout tagFlowLayout;//标签

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private List<String> initDatas() {
        List<String> itemData = new ArrayList<String>(3);

        for (int i = 0; i < 10; i++) {
            itemData.add("规范");

        }

        return itemData;
    }
}
