package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;

import com.iwangcn.qingkong.business.MessageListEvent;
import com.iwangcn.qingkong.ui.activity.FollowDetailActivity;
import com.iwangcn.qingkong.ui.model.HelperInfo;
import com.iwangcn.qingkong.utils.AbDateUtil;
import com.iwangcn.qingkong.utils.GlideUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by RF on 2017/4/22.
 */

public class MessageListAdapter extends BaseRecyclerViewAdapter<HelperInfo> {
    private MessageListEvent helperEvent;

    public MessageListAdapter(Context context, List<HelperInfo> list, MessageListEvent helperEvent) {
        super(context, list);
        this.helperEvent = helperEvent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_helper_item;
    }

    @Override
    public void bindData(RecyclerView.ViewHolder viewholder, final HelperInfo helperInfo, final int position) {
        HelperViewHolder holder = (HelperViewHolder) viewholder;
        if (!TextUtils.isEmpty(helperInfo.getTitle())) {
            holder.title.setText(helperInfo.getTitle());
        }

        if (!TextUtils.isEmpty(helperInfo.getUpdateTime() + "")) {
            holder.tvTime.setText(AbDateUtil.formatDateStrGetDay(helperInfo.getUpdateTime()));
        }
        if (!TextUtils.isEmpty(helperInfo.getSource())) {
            holder.tvFrom.setText(helperInfo.getSource());
        }
        if (!TextUtils.isEmpty(helperInfo.getContent())) {
            holder.tvContent.setText(helperInfo.getContent());
        }

    }

    @Override
    public BaseViewHolder onCreateItemView(View view) {
        return new HelperViewHolder(view);
    }

    static class HelperViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        public TextView title;//标题

        @BindView(R.id.tv_time)
        public TextView tvTime;//时间

        @BindView(R.id.tv_from)
        public TextView tvFrom;//来源

        @BindView(R.id.tv_content)
        public TextView tvContent;//内容

        @BindView(R.id.img_pic)
        public ImageView imageView;//内容

        @BindView(R.id.ll_follow)
        public LinearLayout btnFollow;//跟进

        @BindView(R.id.ll_no_relate)
        public LinearLayout btnNORealte;//与我无关

        @BindView(R.id.tv_scan)
        public TextView tvScan;//查看新闻

        @BindView(R.id.tag_flowlayout)
        public TagFlowLayout tagFlowLayout;//标签

        public HelperViewHolder(View itemView) {
            super(itemView);
        }
    }
}
