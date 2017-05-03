package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
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

/**
 * Created by RF on 2017/4/22.
 */

public class HelperFollowRecyclerAdapter extends BaseRecyclerViewAdapter<HelperModel> {
    private int type;

    public HelperFollowRecyclerAdapter(Context context, List<HelperModel> list, int type) {
        super(context, list);
        this.type = type;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_helperfollow_item;
    }

    @Override
    public void bindData(RecyclerView.ViewHolder viewholder, HelperModel helperModel,int pos) {
        HelperFollowViewHolder holder = (HelperFollowViewHolder) viewholder;
        if (type == 1) {
            holder.llReprocess.setVisibility(View.GONE);
            holder.llFragment.setVisibility(View.VISIBLE);
        } else {
            holder.llReprocess.setVisibility(View.VISIBLE);
            holder.llFragment.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(helperModel.getTitle())) {
            holder.title.setText(helperModel.getTitle());
        }

        if (!TextUtils.isEmpty(helperModel.getTime())) {
            holder.tvTime.setText(helperModel.getTime());
        }
        if (!TextUtils.isEmpty(helperModel.getFrom())) {
            holder.tvFrom.setText(helperModel.getFrom());
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
        holder.tagFlowLayout.setAdapter(tagAdapter);
        holder.llCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "取消跟进");
            }
        });
        holder.llSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "置顶");
            }
        });
        holder.llFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "处理完成");
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

    private List<String> initDatas() {
        List<String> itemData = new ArrayList<String>(3);

        for (int i = 0; i < 10; i++) {
            itemData.add("规范");
        }

        return itemData;
    }
}
