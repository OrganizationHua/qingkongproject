package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.HeadLineFollowEvent;
import com.iwangcn.qingkong.ui.model.HeadLineModel;
import com.iwangcn.qingkong.utils.AbDateUtil;
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

public class HeadLineFollowRecyclerAdapter extends BaseRecyclerViewAdapter<HeadLineModel> {
    private int type;
    private HeadLineFollowEvent headLineFollowEvent;
    public HeadLineFollowRecyclerAdapter(Context context, List<HeadLineModel> list, int type, HeadLineFollowEvent headLineFollowEvent) {
        super(context, list);
        this.type = type;
        this.headLineFollowEvent=headLineFollowEvent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_headline_item;
    }

    @Override
    public void bindData(RecyclerView.ViewHolder viewholder, final HeadLineModel helperModel, final int pos) {
        HelperFollowViewHolder holder = (HelperFollowViewHolder) viewholder;
        if (type == 0) {
            holder.llReprocess.setVisibility(View.GONE);
            holder.llFragment.setVisibility(View.VISIBLE);
        } else {
            holder.llReprocess.setVisibility(View.VISIBLE);
            holder.llFragment.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(helperModel.getEventData().getData().getTitle())) {
            holder.title.setText(helperModel.getEventData().getData().getTitle());
        }

        if (!TextUtils.isEmpty(helperModel.getEventData().getData().getUpdateTime()+"")) {
            holder.tvTime.setText(AbDateUtil.formatDateStrGetDay(helperModel.getEventData().getData().getUpdateTime()));
        }
        if (!TextUtils.isEmpty(helperModel.getEventData().getData().getSource())) {
            holder.tvFrom.setText(helperModel.getEventData().getData().getSource());
        }
        if (!TextUtils.isEmpty(helperModel.getEventData().getData().getContent())) {
            holder.tvContent.setText(helperModel.getEventData().getData().getContent());
        }

        if (!TextUtils.isEmpty(helperModel.getLabels())) {
            TagAdapter<String> tagAdapter = new TagAdapter<String>(Arrays.asList(helperModel.getLabels().split(","))) {
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
                headLineFollowEvent.doCancleFollow(new Long(helperModel.getAutoId()).intValue()+"",pos);
            }
        });
        holder.llSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headLineFollowEvent.doFollowSetUpCancleTop(helperModel.getAutoId()+"",pos);
            }
        });
        holder.llFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headLineFollowEvent.doFollowDone(helperModel.getAutoId()+"",pos);
            }
        });
        holder.tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "已查看");
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

        public HelperFollowViewHolder(View itemView) {
            super(itemView);
        }
    }

}
