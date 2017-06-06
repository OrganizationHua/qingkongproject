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
import com.iwangcn.qingkong.ui.model.HelperFeedbackDetail;
import com.iwangcn.qingkong.ui.model.HelperInfo;
import com.iwangcn.qingkong.utils.AbDateUtil;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by RF on 2017/4/22.
 */

public class MessageListAdapter extends BaseRecyclerViewAdapter<HelperFeedbackDetail> {
    private MessageListEvent helperEvent;

    public MessageListAdapter(Context context, List<HelperFeedbackDetail> list, MessageListEvent helperEvent) {
        super(context, list);
        this.helperEvent = helperEvent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.message_list_item;
    }

    @Override
    public void bindData(RecyclerView.ViewHolder viewholder, final HelperFeedbackDetail helperInfo, final int position) {
        HelperViewHolder holder = (HelperViewHolder) viewholder;
        if (!TextUtils.isEmpty(helperInfo.getUserName())) {
            holder.tv_username.setText(helperInfo.getUserName());
        }

        if (!TextUtils.isEmpty(helperInfo.getUpdateTime() + "")) {
            holder.tv_time.setText(AbDateUtil.formatDateStrGetDay(helperInfo.getUpdateTime()));
        }

        if (!TextUtils.isEmpty(helperInfo.getUserName())) {
            holder.tv_message.setText(helperInfo.getMessage());
        }

    }

    @Override
    public BaseViewHolder onCreateItemView(View view) {
        return new HelperViewHolder(view);
    }

    static class HelperViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_username)
        public TextView tv_username;//发送消息名称

        @BindView(R.id.tv_message)
        public TextView tv_message;//内容

        @BindView(R.id.tv_time)
        public TextView tv_time;//时间

        public HelperViewHolder(View itemView) {
            super(itemView);
        }
    }
}
