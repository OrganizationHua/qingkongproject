package com.iwangcn.qingkong.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.FavoriteEvent;
import com.iwangcn.qingkong.net.BaseBean;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.ui.model.EventInfo;
import com.iwangcn.qingkong.ui.model.EventInfoVo;
import com.iwangcn.qingkong.ui.view.BessleAnimation.BesselAnimation;
import com.iwangcn.qingkong.utils.AbDateUtil;
import com.iwangcn.qingkong.utils.GlideUtils;
import com.iwangcn.qingkong.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 时间Adapter
 * demo Adapter Created by zhchen on 15/8/5
 */
public class EventInfoAdapter extends BaseRecyclerViewAdapter<EventInfoVo> {
    private Context mContext;
    private RelativeLayout containerView;
    private View collectView;

    public EventInfoAdapter(Context context, List<EventInfoVo> list) {
        super(context, list);
        this.mContext = context;
    }

    public RelativeLayout getContainerView() {
        return containerView;
    }

    public void setContainerView(RelativeLayout containerView) {
        this.containerView = containerView;
    }

    public View getCollectView() {
        return collectView;
    }

    public void setCollectView(View collectView) {
        this.collectView = collectView;
    }


    public void setDataList(List<EventInfoVo> dataList) {
        mList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemView(View view) {
        return new EventInfoAdapter.ViewHolder(view);
    }

    @Override
    public void bindData(RecyclerView.ViewHolder holder, final EventInfoVo infoVo, final int position) {
        EventInfoAdapter.ViewHolder viewHolder = (EventInfoAdapter.ViewHolder) holder;
        if (infoVo != null) {
            EventInfo model = infoVo.getEventInfo();
            viewHolder.tvNumb.setText(model.getNewsNum() + "条数据");
            if (model != null) {
                if (!TextUtils.isEmpty(model.getName())) {
                    viewHolder.title.setText(model.getName());
                }
                viewHolder.tvTime.setText(AbDateUtil.formatDateStrGetDay(model.getUpdateTime()));
            }
            if (model.getStatus() == 1) {
                viewHolder.imgNote.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imgNote.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(model.getPicUrl())) {
                GlideUtils.loadImageView(mContext, model.getPicUrl(), viewHolder.imgIcon);
            }
        }
        viewHolder.linCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (infoVo != null) {
                    new FavoriteEvent(mContext).addFavoritet(String.valueOf(infoVo.getFavoriteId()), new BaseSubscriber<BaseBean>(true) {
                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable e) {
                            ToastUtil.showToast(mContext, e.codeMessage);
                        }

                        @Override
                        public void onNext(BaseBean baseBean) {
                            ToastUtil.showToast(mContext, "收藏成功");
                            BesselAnimation besselAnimation = new BesselAnimation(mContext, containerView, view, collectView);
                            besselAnimation.startAnimation(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    remove(position);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                        }
                    });
                }

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_item;
    }

    public void remove(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void add(EventInfoVo model, int position) {
        mList.add(position, model);
        notifyItemInserted(position);
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.news_title)
        public TextView title;//标题
        @BindView(R.id.new_item_num)
        public TextView tvNumb;//新闻数量
        @BindView(R.id.new_item_time)
        public TextView tvTime;//新闻数量
        @BindView(R.id.homefragment_lin_collect)
        public LinearLayout linCollect;//收藏
        @BindView(R.id.home_fragment_item_note)
        public ImageView imgNote;//收藏
        @BindView(R.id.home_fragment_item_icon)
        public ImageView imgIcon;//pic


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}