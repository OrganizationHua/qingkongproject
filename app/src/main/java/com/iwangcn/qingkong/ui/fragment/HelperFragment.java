package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.HelperEvent;
import com.iwangcn.qingkong.business.LoadFailEvent;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.ui.activity.TagEditActivity;
import com.iwangcn.qingkong.ui.adapter.HelperRecyclerAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.HelperInfo;
import com.iwangcn.qingkong.ui.view.freshwidget.RefreshListenerAdapter;
import com.iwangcn.qingkong.ui.view.freshwidget.ReloadRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HelperFragment extends BaseFragment {
    @BindView(R.id.system_no_data)
    RelativeLayout mNoData;//暂时没有数据
    @BindView(R.id.home_list_news)
    RecyclerView mListView;


    @BindView(R.id.mReloadRefreshView)
    ReloadRefreshLayout mReloadRefreshView;

    private HelperRecyclerAdapter mNewsAdapter;
    private List<HelperInfo> mList = new ArrayList<>();
    private HelperEvent helperEvent;

    @Override
    protected int layoutResID() {
        return R.layout.fragment_helper;
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
        helperEvent = new HelperEvent(getContext());
        helperEvent.getRefreshEventList();
        mNewsAdapter = new HelperRecyclerAdapter(getActivity(), mList, helperEvent);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mListView.setAdapter(mNewsAdapter);
//        mNewsAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerItemClickListener() {
//            @Override
//            public void onItemClickListener(RecyclerView.ViewHolder viewHolder, int pos) {
//                String url = mList.get(pos).getUrl();
//                Intent intent = new Intent(getActivity(), FollowDetailActivity.class).putExtra("url", url != null ? url : "");
//                startActivity(intent);
//            }
//        });
        mReloadRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(ReloadRefreshLayout refreshLayout) {
                mReloadRefreshView.setEnableRefresh(true);
                helperEvent.getRefreshEventList();
            }

            @Override
            public void onLoadMore(ReloadRefreshLayout refreshLayout) {
                helperEvent.getMoreEvent();
            }
        });
    }

    @OnClick(R.id.home_collect_icon)//收藏
    public void btnCollect() {
        Intent intent = new Intent(getActivity(), TagEditActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof HelperEvent) {
            Log.e("fjg", "====");
            if (helperEvent.getId() == 0) {
                mReloadRefreshView.finishRefreshing();
                List<HelperInfo> list = (List<HelperInfo>) event.getObject();
                if (list == null || list.isEmpty()) {
                    mNoData.setVisibility(View.VISIBLE);
                }else{
                    mNoData.setVisibility(View.GONE);
                }
                if (list.size() < NetConst.page) {//如果小于page条表示加载完成不能加载更多
                    mReloadRefreshView.finishLoadmore();
                }
                if (event.isMore()) {
                    mReloadRefreshView.setEnableLoadmore(false);
                } else {
                    mReloadRefreshView.setEnableRefresh(true);
                    mList.clear();

                }
                mList.addAll(list);
                mNewsAdapter.notifyDataSetChanged();
            } else if (helperEvent.getId() == 1) {//已跟进
                mList.remove((int) helperEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) helperEvent.getObject());
            } else if (helperEvent.getId() == 2) {//与我无关
                mList.remove((int) helperEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) helperEvent.getObject());
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
