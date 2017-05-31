package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.HomeEvent;
import com.iwangcn.qingkong.business.LoadFailEvent;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.ui.activity.FavoriteActivity;
import com.iwangcn.qingkong.ui.activity.NewsListActivity;
import com.iwangcn.qingkong.ui.activity.NewsSearchActivity;
import com.iwangcn.qingkong.ui.adapter.BaseRecyclerViewAdapter;
import com.iwangcn.qingkong.ui.adapter.EventInfoAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.EventInfoVo;
import com.iwangcn.qingkong.ui.model.FavoriteStateModel;
import com.iwangcn.qingkong.ui.view.freshwidget.RefreshListenerAdapter;
import com.iwangcn.qingkong.ui.view.freshwidget.ReloadRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.systemLin_loding)
    RelativeLayout mLinLoading;//再次刷新

    @BindView(R.id.home_list_news)
    RecyclerView mListView;//黑色蒙层
    @BindView(R.id.home_collect_icon)
    ImageView mCollectIcon;//收藏ImageView
    @BindView(R.id.rel_listView)
    RelativeLayout mRellistView;//listView容器
    @BindView(R.id.homeFragment_btn_collected)
    LinearLayout mLin;//listView容器

    @BindView(R.id.mPullRefreshView)
    ReloadRefreshLayout mAbPullToRefreshView;

    private EventInfoAdapter mAdapter;
    private List<EventInfoVo> mList = new ArrayList<>();
    private HomeEvent mHomeEvent;

    @Override
    protected int layoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    private void initData() {
        mHomeEvent = new HomeEvent(getActivity());
        mAdapter = new EventInfoAdapter(getActivity(), mList);
        mAdapter.setCollectView(mLin);
        mAdapter.setContainerView(mRellistView);

        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SlideInLeftAnimator animator = new SlideInLeftAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        mListView.setItemAnimator(animator);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaAdapter.setFirstOnly(true);
        alphaAdapter.setDuration(500);
        alphaAdapter.setInterpolator(new OvershootInterpolator(.5f));
        mListView.setAdapter(alphaAdapter);

        new AsyncTask<Object, Object, List<EventInfoVo>>() {

            @Override
            protected List<EventInfoVo> doInBackground(Object... strings) {
                return mHomeEvent.getCacheNews();
            }

            @Override
            protected void onPostExecute(List<EventInfoVo> eventInfos) {
                if (eventInfos != null) {
                    mList = eventInfos;
                    mAdapter.setDataList(mList);
                } else {
                    mLinLoading.setVisibility(View.VISIBLE);
                }
                mHomeEvent.getRefreshEventList();
            }
        }.execute();
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClickListener(RecyclerView.ViewHolder viewHolder, int i) {
                FavoriteStateModel favoriteStateModel = new FavoriteStateModel();
                favoriteStateModel.setEventId(mList.get(i).getFavoriteId());
                favoriteStateModel.setFavoriteFlag(mList.get(i).getFavoriteFlag());
                Intent intent = new Intent(getActivity(), NewsListActivity.class);
                intent.putExtra("EventInfo", mList.get(i).getEventInfo());
                intent.putExtra("FavoriteStateModel", favoriteStateModel);
                startActivity(intent);
            }
        });
        mAbPullToRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(ReloadRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                mAbPullToRefreshView.setEnableLoadmore(true);
                mHomeEvent.getRefreshEventList();
            }

            @Override
            public void onLoadMore(ReloadRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mHomeEvent.getMoreEvent();
            }
        });
    }

    @OnClick(R.id.homefragment_edit_search)//搜索按钮
    public void onEditSearch() {
        Intent intent = new Intent(getActivity(), NewsSearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.homeFragment_btn_collected)//收藏
    public void btnCollect() {
        Intent intent = new Intent(getActivity(), FavoriteActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof HomeEvent) {
            mLinLoading.setVisibility(View.GONE);
            mAbPullToRefreshView.finishRefreshing();
            List<EventInfoVo> list = (List<EventInfoVo>) event.getObject();
            if (list.size() < NetConst.page) {//如果小于page条表示加载完成不能加载更多
                mAbPullToRefreshView.setEnableLoadmore(false);
            }
            if (event.isMore()) {
                mAbPullToRefreshView.finishLoadmore();
            } else {
                mAbPullToRefreshView.finishRefreshing();
                mList.clear();
            }
            mList.addAll(list);
            mAdapter.setDataList(mList);
        } else if (event instanceof LoadFailEvent) {
            mAbPullToRefreshView.finishLoadmore();
            mAbPullToRefreshView.finishRefreshing();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
