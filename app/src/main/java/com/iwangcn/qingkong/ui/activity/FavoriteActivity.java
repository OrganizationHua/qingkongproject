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
import com.iwangcn.qingkong.ui.view.pullview.AbPullToRefreshView;
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

public class FavoriteActivity extends QkBaseActivity implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {

    @BindView(R.id.home_list_collect)
    DynamicListView mListView;//

    private FacoriteAdapter mAdapter;
    private Context mContext = this;
    @BindView(R.id.mPullRefreshView)
    AbPullToRefreshView mAbPullToRefreshView;

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
        mAbPullToRefreshView.setOnHeaderRefreshListener(this);
        mAbPullToRefreshView.setOnFooterLoadListener(this);
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
                mBusEvent.removeFavoritet(String.valueOf(info.getEventId()), new BaseSubscriber(true) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastUtil.showToast(mContext,"取消失败");
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
                Intent intent = new Intent(mContext, NewsListActivity.class);
                FavoriteInfo eventInfo= (FavoriteInfo) mAdapter.getItem(i);
                intent.putExtra("EventInfo",eventInfo.getEvent());
                startActivity(intent);
            }
        });
        mBusEvent.getRefreshEventList();
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof FavoriteEvent) {
            mAbPullToRefreshView.onHeaderRefreshFinish();
            List<FavoriteInfo> list = (List<FavoriteInfo>) event.getObject();
            if (list.size() < NetConst.page) {//如果小于page条表示加载完成不能加载更多
                mAbPullToRefreshView.setLoadMoreEnable(false);
            }
            if (event.isMore()) {
                mAbPullToRefreshView.onFooterLoadFinish();
            } else {
                mAbPullToRefreshView.onHeaderRefreshFinish();
                mAdapter.clear();
            }
            mAdapter.addAll(list);
        } else if (event instanceof LoadFailEvent) {
            mAbPullToRefreshView.onHeaderRefreshFinish();
            mAbPullToRefreshView.onFooterLoadFinish();
        }
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        mBusEvent.getMoreEvent();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        mAbPullToRefreshView.setLoadMoreEnable(true);
        mBusEvent.getRefreshEventList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
