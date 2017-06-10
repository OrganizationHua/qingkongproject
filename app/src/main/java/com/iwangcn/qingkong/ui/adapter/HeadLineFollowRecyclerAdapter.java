package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.HeadLineFollowEvent;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.model.HeadLineModel;
import com.iwangcn.qingkong.ui.model.QkTagModel;
import com.iwangcn.qingkong.utils.AbDateUtil;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
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
        this.headLineFollowEvent = headLineFollowEvent;
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
        if (helperModel.getEventData().getData() != null) {


            if (!TextUtils.isEmpty(helperModel.getEventData().getData().getTitle())) {
                holder.title.setText(helperModel.getEventData().getData().getTitle());
            }

            if (!TextUtils.isEmpty(helperModel.getEventData().getData().getUpdateTime() + "")) {
                holder.tvTime.setText(AbDateUtil.formatDateStrGetDay(helperModel.getEventData().getData().getUpdateTime()));
            }
            if (!TextUtils.isEmpty(helperModel.getEventData().getData().getSource())) {
                holder.tvFrom.setText(helperModel.getEventData().getData().getSource());
            }
            if (!TextUtils.isEmpty(helperModel.getEventData().getData().getContent())) {
                if (type == 0) {
                    holder.tvContent.setVisibility(View.VISIBLE);
                    holder.tvContent.setText(helperModel.getEventData().getData().getContent());
                } else {
                    holder.tvContent.setVisibility(View.GONE);
                }
            }
            //是否置顶
            if (!TextUtils.equals(helperModel.getTop() + "", "0")) {
                holder.tv_is_top.setText("置顶");
                holder.img_is_top.setImageResource(R.drawable.genjin_btn_top);
            } else if (!TextUtils.equals(helperModel.getTop() + "", "1")) {
                holder.tv_is_top.setText("取消置顶");
                holder.img_is_top.setImageResource(R.drawable.genjin_btn_untop);
            }
            List<QkTagModel> list = new ArrayList<>();
            list.add(new QkTagModel(0, (String) SpUtils.get(mContext, helperModel.getEventData().getDataType() + "", "1")));
            if (helperModel.getEventData().getDataType() == 1 || helperModel.getEventData().getDataType() == 5) {
                if (!TextUtils.isEmpty(helperModel.getEventData().getData().getKeywords())) {
                    list.add(new QkTagModel(1, helperModel.getEventData().getData().getKeywords()));

                }
            }
            if (helperModel.getBusinessLabels() != null && helperModel.getBusinessLabels().size() != 0) {
                for (int i = 0; i < helperModel.getBusinessLabels().size(); i++) {
                    if (!TextUtils.isEmpty(helperModel.getBusinessLabels().get(i))) {
                        list.add(new QkTagModel(2, helperModel.getBusinessLabels().get(i)));
                    }
                }
            }
            if (helperModel.getSelfLabels() != null && helperModel.getSelfLabels().size() != 0) {
                for (int j = 0; j < helperModel.getSelfLabels().size(); j++) {
                    if (!TextUtils.isEmpty(helperModel.getSelfLabels().get(j))) {
                        list.add(new QkTagModel(3, helperModel.getSelfLabels().get(j)));
                    }
                }
            }

            holder.tagFlowLayout.setAdapter(new QKTagAdapter(mContext, list));
        }
        //取消跟进
        holder.llCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headLineFollowEvent.doCancleFollow(helperModel.getAutoId() + "", pos);
            }
        });
        //取消置顶 置顶
        holder.llSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.equals(helperModel.getTop() + "", "0")) {
                    headLineFollowEvent.doFollowSetUp(helperModel.getAutoId() + "", pos);
                } else if (!TextUtils.equals(helperModel.getTop() + "", "1")) {
                    headLineFollowEvent.doFollowSetUpCancleTop(helperModel.getAutoId() + "", pos);
                }

            }
        });
        //处理完成
        holder.llFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headLineFollowEvent.doFollowDone(helperModel.getAutoId() + "", pos);
            }
        });
        //重新处理
        holder.llReprocess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headLineFollowEvent.doFollowReprocess(helperModel.getAutoId() + "", pos);
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

        @BindView(R.id.img_is_top)
        public ImageView img_is_top;//置顶

        @BindView(R.id.tv_is_top)
        public TextView tv_is_top;//置顶

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
