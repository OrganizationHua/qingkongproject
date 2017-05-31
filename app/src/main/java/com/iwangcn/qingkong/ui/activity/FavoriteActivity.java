package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.FavoriteEvent;
import com.iwangcn.qingkong.business.LoadFailEvent;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.ui.adapter.FacoriteAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.FavoriteInfo;
import com.iwangcn.qingkong.ui.model.FavoriteStateModel;
import com.iwangcn.qingkong.ui.view.freshwidget.RefreshListenerAdapter;
import com.iwangcn.qingkong.ui.view.freshwidget.ReloadRefreshLayout;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by czh on 2017/4/9.
 */

public class FavoriteActivity extends QkBaseActivity {

    @BindView(R.id.home_list_collect)
    DynamicListView mListView;//

    private FacoriteAdapter mAdapter;
    private Context mContext = this;
    @BindView(R.id.mPullRefreshView)
    ReloadRefreshLayout mAbPullToRefreshView;

    FavoriteEvent mBusEvent;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_collect;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        mBusEvent = new FavoriteEvent(this);
        setTitle(getResources().getString(R.string.toutiao_collect));
        mAbPullToRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(ReloadRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                mAbPullToRefreshView.setEnableLoadmore(true);
                mBusEvent.getRefreshEventList();
            }

            @Override
            public void onLoadMore(ReloadRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mBusEvent.getMoreEvent();
            }
        });

    }

    public void initData() {
        ArrayList<FavoriteInfo> mList = new ArrayList<>();
        mAdapter = new FacoriteAdapter(this);
        mAdapter.addAll(mList);
        SwingLeftInAnimationAdapter animAdapter = new SwingLeftInAnimationAdapter(mAdapter);
        animAdapter.setAbsListView(mListView);
        assert animAdapter.getViewAnimator() != null;
        animAdapter.getViewAnimator().setInitialDelayMillis(300);
        mListView.setAdapter(animAdapter);
        mAdapter.setCancleCollcetListener(new FacoriteAdapter.OnClickCancleCollectListener() {
            @Override
            public void onClickCancleCollect(final int position) {
                FavoriteInfo info = (FavoriteInfo) mAdapter.getItem(position);
                mBusEvent.removeFavoritet(String.valueOf(info.getAutoId()), new BaseSubscriber(true) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastUtil.showToast(mContext, e.codeMessage);
                    }

                    @Override
                    public void onNext(Object o) {
                        //动画更新
                        mListView.enableSwipeToDismiss(new OnDismissCallback() {
                            @Override
                            public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    mAdapter.remove(position);
                                }
                            }
                        });
                        mListView.dismiss(position);
                        mListView.disableSwipeToDismiss();
                    }
                });

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FavoriteInfo eventInfo = (FavoriteInfo) mAdapter.getItem(i);
                FavoriteStateModel favoriteStateModel = new FavoriteStateModel();
                favoriteStateModel.setEventId(eventInfo.getEventId());
                favoriteStateModel.setFavoriteFlag(1);
                Intent intent = new Intent(mContext, NewsListActivity.class);
                intent.putExtra("EventInfo", eventInfo.getEvent());
                intent.putExtra("FavoriteStateModel", favoriteStateModel);
                startActivity(intent);
            }
        });
        mBusEvent.getRefreshEventList();
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof FavoriteEvent) {
            mAbPullToRefreshView.finishRefreshing();
            List<FavoriteInfo> list = (List<FavoriteInfo>) event.getObject();
            if (list.size() < NetConst.page) {//如果小于page条表示加载完成不能加载更多
                mAbPullToRefreshView.setEnableLoadmore(false);
            }
            if (event.isMore()) {
                mAbPullToRefreshView.finishLoadmore();
            } else {
                mAbPullToRefreshView.finishRefreshing();
                mAdapter.clear();
            }
            mAdapter.addAll(list);
        } else if (event instanceof LoadFailEvent) {
            mAbPullToRefreshView.finishLoadmore();
            mAbPullToRefreshView.finishRefreshing();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
