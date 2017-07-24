package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.HelperEvent;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.model.HelperInfo;
import com.iwangcn.qingkong.ui.model.QkTagModel;
import com.iwangcn.qingkong.utils.AbAppUtil;
import com.iwangcn.qingkong.utils.AbDateUtil;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by RF on 2017/4/22.
 */

public class HelperRecyclerAdapter extends BaseRecyclerViewAdapter<HelperInfo> {
    private HelperEvent helperEvent;

    public HelperRecyclerAdapter(Context context, List<HelperInfo> list, HelperEvent helperEvent) {
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
        if (helperInfo.getPicList()!=null||helperInfo.getPicList().isEmpty()) {
            ImageAdapter imageAdapter = new ImageAdapter(mContext, helperInfo.getPicList());
            holder.rv_grid.setLayoutManager(new GridLayoutManager(mContext, 3));
            holder.rv_grid.setAdapter(imageAdapter);
            holder.rv_grid.setVisibility(View.VISIBLE);
        } else {
            holder.rv_grid.setVisibility(View.GONE);
        }
//            for (int i = 0; i < listPic.size(); i++) {
//                GlideUtils.loadImageView(mContext, listPic.get(i), holder.imageView, R.drawable.default_icon, R.drawable.default_icon);
//            }
//            holder.imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, PicActivity.class).putExtra("imgurl", helperInfo.getPics());
//                    mContext.startActivity(intent);
//                }
//            });
//        }
        List<QkTagModel> list = new ArrayList<>();
        list.add(new QkTagModel(0, (String) SpUtils.get(mContext, helperInfo.getDataType() + "", "1"), 2, helperInfo.getAutoId()));
        List<String> listTag = JSON.parseArray(helperInfo.getLabels(), String.class);
        if (listTag != null && listTag.size() != 0) {
            for (int j = 0; j < listTag.size(); j++) {
                if (!TextUtils.isEmpty(listTag.get(j))) {
                    list.add(new QkTagModel(3, listTag.get(j), 2, helperInfo.getAutoId()));
                }
            }
//            list.add(new QkTagModel(4, "图片", 2, helperInfo.getAutoId()));
        }

        holder.tagFlowLayout.setAdapter(new QKTagAdapter(mContext, list));


        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperEvent.doHelperFollow(new Long(helperInfo.getAutoId()).intValue(), position);
            }
        });
        holder.btnNORealte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperEvent.doDelete(new Long(helperInfo.getAutoId()).intValue(), position);
            }
        });
        holder.tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    AbAppUtil.openBrowser(mContext, helperInfo.getUrl() != null ? helperInfo.getUrl() : "");
                }

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

        @BindView(R.id.tv_content)
        public TextView tvContent;//内容

        @BindView(R.id.rv_grid)
        public RecyclerView rv_grid;//内容

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
