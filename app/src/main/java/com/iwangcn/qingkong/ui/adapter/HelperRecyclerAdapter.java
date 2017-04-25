package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.HelperInfo;
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

public class HelperRecyclerAdapter extends BaseRecyclerViewAdapter<HelperInfo> {
    public HelperRecyclerAdapter(Context context, List<HelperInfo> list) {
        super(context, list);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_helper_item;
    }

    @Override
    public void bindData(RecyclerView.ViewHolder viewholder, HelperInfo helperInfo) {
        HelperViewHolder holder = (HelperViewHolder) viewholder;
        if (!TextUtils.isEmpty(helperInfo.getTitle())) {
            holder.title.setText(helperInfo.getTitle());
        }

        if (!TextUtils.isEmpty(helperInfo.getPubtime()+"")) {
            holder.tvTime.setText(helperInfo.getPubtime()+"");
        }
        if (!TextUtils.isEmpty(helperInfo.getSource())) {
            holder.tvFrom.setText(helperInfo.getSource());
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
        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "已跟进");
            }
        });
        holder.btnNORealte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "与我无关");
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
        return new HelperViewHolder(view);
    }

    static class HelperViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        public TextView title;//标题

        @BindView(R.id.tv_time)
        public TextView tvTime;//时间

        @BindView(R.id.tv_from)
        public TextView tvFrom;//来源

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

    private List<String> initDatas() {
        List<String> itemData = new ArrayList<String>(3);

        for (int i = 0; i < 10; i++) {
            itemData.add("规范");
        }

        return itemData;
    }
}
