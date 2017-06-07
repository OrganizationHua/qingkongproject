package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.HeadLineFollowEvent;
import com.iwangcn.qingkong.business.LoadFailEvent;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.ui.activity.FollowDetailActivity;
import com.iwangcn.qingkong.ui.adapter.BaseRecyclerViewAdapter;
import com.iwangcn.qingkong.ui.adapter.HeadLineFollowRecyclerAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.HeadLineModel;
import com.iwangcn.qingkong.ui.view.freshwidget.RefreshListenerAdapter;
import com.iwangcn.qingkong.ui.view.freshwidget.ReloadRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HeadLineFollowFragment extends BaseFragment {
    @BindView(R.id.system_no_data)
    RelativeLayout mNoData;//暂时没有数据

    @BindView(R.id.home_list_news)
    RecyclerView mListView;

    @BindView(R.id.mReloadRefreshView)
    ReloadRefreshLayout mReloadRefreshView;

    private HeadLineFollowRecyclerAdapter mNewsAdapter;
    private List<HeadLineModel> mList = new ArrayList<>();
    private HeadLineFollowEvent headLineFollowEvent;
    private int type;

    public static HeadLineFollowFragment newInstance(int type) {
        HeadLineFollowFragment myFragment = new HeadLineFollowFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_helper_fflow;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        type = bundle.getInt("type");

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    private void initData() {
        headLineFollowEvent = new HeadLineFollowEvent(getContext(), type);
        headLineFollowEvent.getRefreshEventList();
        mNewsAdapter = new HeadLineFollowRecyclerAdapter(getActivity(), mList, type, headLineFollowEvent);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mListView.setAdapter(mNewsAdapter);
        mNewsAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClickListener(RecyclerView.ViewHolder viewHolder, int position) {
                HeadLineModel data = mList.get(position);
                int type = 1;
                Intent intent = new Intent(getActivity(), FollowDetailActivity.class)
                        .putExtra("data", data)
                        .putExtra("type", type);
                startActivity(intent);
            }
        });
        mReloadRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(ReloadRefreshLayout refreshLayout) {
                mReloadRefreshView.setEnableRefresh(true);
                mNoData.setVisibility(View.GONE);
                headLineFollowEvent.getRefreshEventList();
            }

            @Override
            public void onLoadMore(ReloadRefreshLayout refreshLayout) {
                headLineFollowEvent.getMoreEvent();
            }
        });
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof HeadLineFollowEvent) {
            if (headLineFollowEvent.getId() == 0) {
                mReloadRefreshView.finishRefreshing();
                List<HeadLineModel> list = (List<HeadLineModel>) event.getObject();
                if (list == null || list.isEmpty()) {
                    mNoData.setVisibility(View.VISIBLE);
                } else {
                    mNoData.setVisibility(View.GONE);
                }
                if (list == null || list.size() < NetConst.page) {//如果小于page条表示加载完成不能加载更多
                    mReloadRefreshView.finishLoadmore();
                }
                if (event.isMore()) {
                    mReloadRefreshView.setEnableLoadmore(false);
                } else {
                    mReloadRefreshView.setEnableRefresh(true);
                    mList.clear();

                }
                if (list != null) {
                    mList.addAll(list);
                    mNewsAdapter.notifyDataSetChanged();
                }
            } else if (headLineFollowEvent.getId() == 1) {//取消跟进
                mList.remove((int) headLineFollowEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) headLineFollowEvent.getObject());
            } else if (headLineFollowEvent.getId() == 2) {//置顶
                mList.remove((int) headLineFollowEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) headLineFollowEvent.getObject());
            } else if (headLineFollowEvent.getId() == 3) {////取消置顶
                mList.remove((int) headLineFollowEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) headLineFollowEvent.getObject());
            } else if (headLineFollowEvent.getId() == 4) {//已处理
                mList.remove((int) headLineFollowEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) headLineFollowEvent.getObject());
            } else if (headLineFollowEvent.getId() == 5) {//重新处理
                mList.remove((int) headLineFollowEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) headLineFollowEvent.getObject());
            }
        } else if (event instanceof LoadFailEvent) {
            mReloadRefreshView.finishRefreshing();
            mReloadRefreshView.finishLoadmore();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
