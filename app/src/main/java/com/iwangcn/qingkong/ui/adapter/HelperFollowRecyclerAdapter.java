package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.HelperFollowEvent;
import com.iwangcn.qingkong.ui.activity.MessageListActivity;
import com.iwangcn.qingkong.ui.model.HelperListModel;
import com.iwangcn.qingkong.utils.GlideUtils;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by RF on 2017/4/22.
 */

public class HelperFollowRecyclerAdapter extends BaseRecyclerViewAdapter<HelperListModel> {
    private int type;
    private HelperFollowEvent helperFollowEvent;

    public HelperFollowRecyclerAdapter(Context context, List<HelperListModel> list, int type, HelperFollowEvent helperFollowEvent) {
        super(context, list);
        this.type = type;
        this.helperFollowEvent = helperFollowEvent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_helperfollow_item;
    }

    @Override
    public void bindData(RecyclerView.ViewHolder viewholder, final HelperListModel helperModel, final int pos) {
        HelperFollowViewHolder holder = (HelperFollowViewHolder) viewholder;
        if (type == 1) {
            holder.llReprocess.setVisibility(View.GONE);
            holder.llFragment.setVisibility(View.VISIBLE);
        } else {
            holder.llReprocess.setVisibility(View.VISIBLE);
            holder.llFragment.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(helperModel.getHelperInfo().getTitle())) {
            holder.title.setText(helperModel.getHelperInfo().getTitle());
        }

        if (!TextUtils.isEmpty(helperModel.getHelperInfo().getUpdateTime() + "")) {
            holder.tvTime.setText(helperModel.getHelperInfo().getUpdateTime());
        }
        if (!TextUtils.isEmpty(helperModel.getHelperInfo().getSource())) {
            holder.tvFrom.setText(helperModel.getHelperInfo().getSource());
        }
        if (!TextUtils.isEmpty(helperModel.getHelperInfo().getContent())) {
            holder.tvContent.setText(helperModel.getHelperInfo().getContent());
        }
        if (!TextUtils.isEmpty("")) {
            holder.btn_leave_message.setText("留言（" + "3" + "）");
        }
        if (!TextUtils.isEmpty(helperModel.getHelperInfo().getPics())) {
            List<String> listPic = Arrays.asList(helperModel.getHelperInfo().getPics().split(","));
            for (int i = 0; i < listPic.size(); i++) {
                GlideUtils.loadImageView(mContext, listPic.get(i), holder.imageView);
            }
        }
        if (!TextUtils.isEmpty(helperModel.getHelperInfo().getLabels())) {
            TagAdapter<String> tagAdapter = new TagAdapter<String>(Arrays.asList(helperModel.getHelperInfo().getLabels().split(","))) {
                @Override
                public View getView(FlowLayout parent, int position, String o) {

                    TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv,
                            parent, false);
                    tv.setText(o);
                    return tv;
                }
            };
            holder.tagFlowLayout.setAdapter(tagAdapter);
        }
        holder.llCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperFollowEvent.doCancleFollow(new Long(helperModel.getHelperInfo().getAutoId()).intValue() + "", pos);
            }
        });
        holder.llSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperFollowEvent.doFollowSetUpCancleTop(new Long(helperModel.getHelperInfo().getAutoId()).intValue() + "", pos);
            }
        });
        holder.llFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperFollowEvent.doFollowDone(new Long(helperModel.getHelperInfo().getAutoId()).intValue() + "", pos);
            }
        });
        holder.tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "已查看");
            }
        });
        holder.btn_leave_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageListActivity.class).putExtra("message", helperModel.getHelperInfo());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public BaseViewHolder onCreateItemView(View view) {
        return new HelperFollowViewHolder(view);
    }

    static class HelperFollowViewHolder extends BaseViewHolder {
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

        @BindView(R.id.btn_leave_message)
        public Button btn_leave_message;//留言

        public HelperFollowViewHolder(View itemView) {
            super(itemView);
        }
    }

}
