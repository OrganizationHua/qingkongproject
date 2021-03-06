package com.iwangcn.qingkong.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.FavoriteEvent;
import com.iwangcn.qingkong.business.FollowDetailEvent;
import com.iwangcn.qingkong.business.LoadFailEvent;
import com.iwangcn.qingkong.business.NewsListBus;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.ui.adapter.NewsListAdapter;
import com.iwangcn.qingkong.ui.base.BaseActivity;
import com.iwangcn.qingkong.ui.model.EventDataVo;
import com.iwangcn.qingkong.ui.model.EventInfo;
import com.iwangcn.qingkong.ui.model.EventInfoVo;
import com.iwangcn.qingkong.ui.view.freshwidget.RefreshListenerAdapter;
import com.iwangcn.qingkong.ui.view.freshwidget.ReloadRefreshLayout;
import com.iwangcn.qingkong.utils.AbDateUtil;
import com.iwangcn.qingkong.utils.GlideUtils;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新闻事件界面
 */
public class NewsListActivity extends BaseActivity {
    @BindView(R.id.newsEvent_recycler_view)
    ListView mListView;
    @BindView(R.id.mPullRefreshView)
    ReloadRefreshLayout mAbPullToRefreshView;
    @BindView(R.id.base_img_right)
    ImageView mCollcetImage;//收藏icon

    private Activity mContext = this;
    private NewsListAdapter mAdapter;
    private List<EventDataVo> mList = new ArrayList<>();

    private EventInfoVo mIntentEventInfo;//上个界面传递过来的事件
    private final int REQUEST_CODE = 10;
    private NewsListBus mEventBus;
    private FavoriteEvent mFavoriteEvent;//收藏
    private String tags = "", sourceType = "1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_event);
        ButterKnife.bind(this);
        initView();
    }


    public void initView() {
        EventBus.getDefault().register(this);
        mEventBus = new NewsListBus(this);
        mFavoriteEvent = new FavoriteEvent(this);
        mIntentEventInfo = (EventInfoVo) getIntent().getSerializableExtra("EventInfoVo");
        if (mIntentEventInfo != null) {
            if (mIntentEventInfo.getFavoriteFlag() == 0) {
                mCollcetImage.setSelected(false);
            } else {
                mCollcetImage.setSelected(true);
            }
        }
        initListView();
        mAbPullToRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(ReloadRefreshLayout refreshLayout) {
                mEventBus.getRefreshEventList(mIntentEventInfo.getEventInfo(), true, tags, sourceType);
            }

            @Override
            public void onLoadMore(ReloadRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mEventBus.getMoreEvent(mIntentEventInfo.getEventInfo(), tags, sourceType);
            }
        });
    }

    private View initListHeadView() {
        View headView = LayoutInflater.from(this).inflate(R.layout.news_list_head_item, null);
        ImageView imageIcon = (ImageView) headView.findViewById(R.id.home_fragment_item_icon);
        TextView tvTitle = (TextView) headView.findViewById(R.id.news_title);
        TextView tvNumb = (TextView) headView.findViewById(R.id.new_item_num);
        TextView tvTime = (TextView) headView.findViewById(R.id.new_item_time);
        TextView tvBrief = (TextView) headView.findViewById(R.id.tv_brief);
        EventInfo eventInfo = mIntentEventInfo.getEventInfo();
        if (eventInfo != null) {
            if (!TextUtils.isEmpty(eventInfo.getPicUrl())) {
                GlideUtils.loadImageView(this, eventInfo.getPicUrl(), imageIcon);
            }
            if (!TextUtils.isEmpty(eventInfo.getDisc())) {
                tvBrief.setText(eventInfo.getDisc());
            }
            if (!TextUtils.isEmpty(eventInfo.getName())) {
                tvTitle.setText(eventInfo.getName());
            }
            tvNumb.setText(mIntentEventInfo.getInfoCount() + "条数据");
        }
        tvTime.setText(AbDateUtil.getStringByFormat(mIntentEventInfo.getLastestInfoTime(), "yyyy-MM-dd"));
        return headView;
    }

    private void initListView() {
        mListView.addHeaderView(initListHeadView());
        mAdapter = new NewsListAdapter(this);
        mAdapter.setDataList(mList);
        AlphaInAnimationAdapter mAnimAdapter = new AlphaInAnimationAdapter(mAdapter);
        mAnimAdapter.setAbsListView(mListView);
        mListView.setAdapter(mAnimAdapter);
        ViewCompat.setNestedScrollingEnabled(mListView, true);
        mEventBus.getRefreshEventList(mIntentEventInfo.getEventInfo(), true, tags, sourceType);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) return;
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("eventDataVoList", (Serializable) mList);
                intent.putExtra("frontPageposition", (int) (i - 1));
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                int position = data.getIntExtra("position", 0);
                mListView.setSelection(position);
                for (int i = 0; i < mList.size(); i++) {
                    if (i == position) {
                        mList.get(i).setSelect(true);
                    } else {
                        mList.get(i).setSelect(false);
                    }
                }
                mAdapter.setDataList(mList);
                mListView.setSelection(position);
            } else if (requestCode == 100) {
                Bundle bundle = data.getExtras();
                sourceType = bundle.getString("sourceType");
                tags = bundle.getString("tags");
                new NewsListBus(this).getRefreshEventList(mIntentEventInfo.getEventInfo(), true, tags, sourceType);
            }

        }
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof NewsListBus) {
            mAbPullToRefreshView.finishRefreshing();
            List<EventDataVo> list = (List<EventDataVo>) event.getObject();
            if (list.size() < NetConst.page) {//如果小于page条表示加载完成不能加载更多
                mAbPullToRefreshView.setEnableLoadmore(false);
            }
            if (event.isMore()) {
                mAbPullToRefreshView.finishLoadmore();
            } else {
                if (list.size() == 0) {
                    ToastUtil.showToast(this, "暂无相关数据");
                } else {
                    list.get(0).setSelect(true);
                }
                mAbPullToRefreshView.finishRefreshing();
                mList.clear();
            }
            mList.addAll(list);
            mAdapter.setDataList(mList);
        } else if (event instanceof LoadFailEvent) {
            mAbPullToRefreshView.finishLoadmore();
            mAbPullToRefreshView.finishRefreshing();
        } else if (event instanceof FollowDetailEvent) {
            mEventBus.getRefreshEventList(mIntentEventInfo.getEventInfo(), false, tags, sourceType);
        }
    }

    @OnClick(R.id.base_act_right_lin)//选择器按钮
    public void onBtnFilter() {
        //    ToastUtil.showToast(this, "暂不支持筛选功能");
        Intent intent = new Intent(this, TagFilterActivity.class).putExtra("from", 6);
        startActivityForResult(intent, 100);
//       Intent intent = new Intent(this, MoreTagEditActivity.class);
//        startActivityForResult(intent, 100);
    }

    @OnClick(R.id.base_img_right_lin)//收藏
    public void btnCollected(View view) {
        if (mIntentEventInfo != null) {
            int favoriteFlag = mIntentEventInfo.getFavoriteFlag();
            if (mIntentEventInfo.getFavoriteFlag() == 0) {
                mFavoriteEvent.addFavoritet(String.valueOf(mIntentEventInfo.getEventInfo().getAutoId()), new BaseSubscriber(true) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastUtil.showToast(mContext, e.codeMessage);
                    }

                    @Override
                    public void onNext(Object o) {
                        ToastUtil.showToast(mContext, "收藏成功");
                        mCollcetImage.setSelected(true);
                        mIntentEventInfo.setFavoriteFlag(1);
                        FavoriteEvent favoriteEvent = new FavoriteEvent();
                        favoriteEvent.setId(FavoriteEvent.FAVORITE_FINISH);
                        EventBus.getDefault().post(favoriteEvent);
                    }
                });
            } else {
                mFavoriteEvent.removeFavoritet(String.valueOf(mIntentEventInfo.getEventInfo().getAutoId()), new BaseSubscriber(true) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastUtil.showToast(mContext, e.codeMessage);
                    }

                    @Override
                    public void onNext(Object o) {
                        ToastUtil.showToast(mContext, "取消收藏成功");
                        mCollcetImage.setSelected(false);
                        mIntentEventInfo.setFavoriteFlag(0);
                        FavoriteEvent favoriteEvent = new FavoriteEvent();
                        favoriteEvent.setId(FavoriteEvent.FAVORITE_FINISH);
                        EventBus.getDefault().post(favoriteEvent);
                    }
                });
            }
        }
    }

    @OnClick(R.id.base_act_left_lin)//返回键
    public void goBack() {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}


