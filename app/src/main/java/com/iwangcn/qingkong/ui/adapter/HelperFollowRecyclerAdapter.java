package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.HelperFollowEvent;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.activity.MessageListActivity;
import com.iwangcn.qingkong.ui.model.HelperListModel;
import com.iwangcn.qingkong.ui.model.QkTagModel;
import com.iwangcn.qingkong.utils.AbDateUtil;
import com.iwangcn.qingkong.utils.GlideUtils;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
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
        if (type == 0) {
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
            holder.tvTime.setText(AbDateUtil.formatDateStrGetDay(helperModel.getHelperInfo().getUpdateTime()));
        }
        if (!TextUtils.isEmpty(helperModel.getHelperInfo().getSource())) {
            holder.tvFrom.setText(helperModel.getHelperInfo().getSource());
        }
        if (!TextUtils.isEmpty(helperModel.getHelperInfo().getContent())) {
            holder.tvContent.setText(helperModel.getHelperInfo().getContent());
        }
        //是否置顶
        if (!TextUtils.equals(helperModel.getHelperProcess().getTop() + "", "0")) {
            holder.tv_is_top.setText("置顶");
            holder.img_is_top.setImageResource(R.drawable.genjin_btn_top);
        } else if (!TextUtils.equals(helperModel.getHelperProcess().getTop() + "", "1")) {
            holder.tv_is_top.setText("取消置顶");
            holder.img_is_top.setImageResource(R.drawable.genjin_btn_untop);
        }
        if (!TextUtils.isEmpty(helperModel.getHelperInfo().getFollowCount())) {
            holder.btn_leave_message.setText("留言（" + helperModel.getHelperInfo().getFollowCount() + "）");
        }
        if (!TextUtils.isEmpty(helperModel.getHelperInfo().getPics())) {

            List<String> listPic = Arrays.asList(helperModel.getHelperInfo().getPics().split(","));
            for (int i = 0; i < listPic.size(); i++) {
                GlideUtils.loadImageView(mContext, listPic.get(i), holder.imageView, R.drawable.default_icon_bg, R.drawable.default_icon_bg);
            }
        }

        List<QkTagModel> list = new ArrayList<>();
        list.add(new QkTagModel(0, (String) SpUtils.get(mContext, helperModel.getHelperInfo().getDataType() + "", "1")));

        if (helperModel.getHelperProcess().getBusinessLabels() != null && helperModel.getHelperProcess().getBusinessLabels().size() != 0) {
            for (int i = 0; i < helperModel.getHelperProcess().getBusinessLabels().size(); i++) {
                if (!TextUtils.isEmpty(helperModel.getHelperProcess().getBusinessLabels().get(i))) {
                    list.add(new QkTagModel(2, helperModel.getHelperProcess().getBusinessLabels().get(i)));
                }
            }
        }
        if (helperModel.getHelperProcess().getSelfLabels() != null && helperModel.getHelperProcess().getSelfLabels().size() != 0) {
            for (int j = 0; j < helperModel.getHelperProcess().getSelfLabels().size(); j++) {
                if (!TextUtils.isEmpty(helperModel.getHelperProcess().getSelfLabels().get(j))) {
                    list.add(new QkTagModel(3, helperModel.getHelperProcess().getSelfLabels().get(j)));
                }
            }
        }

        holder.tagFlowLayout.setAdapter(new QKTagAdapter(mContext, list));

        //取消跟进
        holder.llCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperFollowEvent.doCancleFollow(helperModel.getHelperProcess().getAutoId() + "", pos);
            }
        });
        //取消置顶 置顶
        holder.llSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.equals(helperModel.getHelperProcess().getTop() + "", "0")) {
                    helperFollowEvent.doFollowSetUp(helperModel.getHelperProcess().getAutoId() + "", pos);
                } else if (!TextUtils.equals(helperModel.getHelperProcess().getTop() + "", "1")) {
                    helperFollowEvent.doFollowSetUpCancleTop(helperModel.getHelperProcess().getAutoId() + "", pos);
                }
            }
        });
        //处理完成
        holder.llFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperFollowEvent.doFollowDone(helperModel.getHelperProcess().getAutoId() + "", pos);
            }
        });
        //重新处理
        holder.llReprocess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperFollowEvent.doFollowReprocess(helperModel.getHelperProcess().getAutoId() + "", pos);
            }
        });
        holder.tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "已查看");
            }
        });
        //留言
        holder.btn_leave_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageListActivity.class).putExtra("message", helperModel.getHelperInfo())
                        .putExtra("autoId", helperModel.getHelperProcess().getAutoId() + "");
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

        @BindView(R.id.btn_leave_message)
        public Button btn_leave_message;//留言

        public HelperFollowViewHolder(View itemView) {
            super(itemView);
        }
    }

}
