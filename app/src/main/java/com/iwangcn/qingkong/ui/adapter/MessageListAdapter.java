package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.MessageListEvent;
import com.iwangcn.qingkong.providers.UserManager;
import com.iwangcn.qingkong.ui.model.HelperFeedbackDetail;
import com.iwangcn.qingkong.utils.AbDateUtil;

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
        if (!TextUtils.equals(UserManager.getUserInfo().getAutoId() + "", helperInfo.getUserId() + "")) {
            holder.tv_username.setTextColor(mContext.getResources().getColor(R.color.home_blue));
            holder.tv_message.setTextColor(mContext.getResources().getColor(R.color.home_blue));
        } else {
            holder.tv_username.setTextColor(mContext.getResources().getColor(R.color.font_black));
            holder.tv_message.setTextColor(mContext.getResources().getColor(R.color.font_black));
        }
        if (!TextUtils.isEmpty(helperInfo.getUserName())) {
            holder.tv_username.setText(helperInfo.getUserName() + ":");
        }

        if (!TextUtils.isEmpty(helperInfo.getUpdateTime() + "")) {
            holder.tv_time.setText(AbDateUtil.formatDateStrGetDay(helperInfo.getUpdateTime()));
        }

        if (!TextUtils.isEmpty(helperInfo.getMessage())) {
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
