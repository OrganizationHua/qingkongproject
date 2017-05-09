package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.HeadLineFollowEvent;
import com.iwangcn.qingkong.business.HelperFollowEvent;
import com.iwangcn.qingkong.business.LoadFailEvent;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.ui.activity.FollowDetailActivity;
import com.iwangcn.qingkong.ui.adapter.BaseRecyclerViewAdapter;
import com.iwangcn.qingkong.ui.adapter.HeadLineFollowRecyclerAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.HelperInfo;
import com.iwangcn.qingkong.ui.view.freshwidget.RefreshListenerAdapter;
import com.iwangcn.qingkong.ui.view.freshwidget.ReloadRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HeadLineFollowFragment extends BaseFragment {

    @BindView(R.id.home_list_news)
    RecyclerView mListView;

    @BindView(R.id.mReloadRefreshView)
    ReloadRefreshLayout mReloadRefreshView;

    private HeadLineFollowRecyclerAdapter mNewsAdapter;
    private List<HelperInfo> mList = new ArrayList<>();
    private HeadLineFollowEvent headLineFollowEvent;
    private int type;
    public static HeadLineFollowFragment newInstance(int type){
        HeadLineFollowFragment myFragment = new HeadLineFollowFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
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
        initData();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    private void initData() {
        headLineFollowEvent = new HeadLineFollowEvent(getContext());
        headLineFollowEvent.getRefreshEventList();
        mNewsAdapter = new HeadLineFollowRecyclerAdapter(getActivity(),mList,type,headLineFollowEvent);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mListView.setAdapter(mNewsAdapter);
        mNewsAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClickListener(RecyclerView.ViewHolder viewHolder,int position) {
                String url = mList.get(position).getUrl();
                Intent intent = new Intent(getActivity(), FollowDetailActivity.class).putExtra("url", url != null ? url : "");
                startActivity(intent);
            }
        });
        mReloadRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(ReloadRefreshLayout refreshLayout) {
                mReloadRefreshView.setEnableRefresh(true);
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
        if (event instanceof HelperFollowEvent) {
            if (headLineFollowEvent.getId() == 0) {
                mReloadRefreshView.finishRefreshing();
                List<HelperInfo> list = (List<HelperInfo>) event.getObject();
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
            } else if (headLineFollowEvent.getId() == 1) {//取消跟进
                mList.remove((int) headLineFollowEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) headLineFollowEvent.getObject());
            } else if (headLineFollowEvent.getId() == 2) {//置顶
                mList.remove((int) headLineFollowEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) headLineFollowEvent.getObject());
            }else if (headLineFollowEvent.getId() == 3) {////取消置顶
                mList.remove((int) headLineFollowEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) headLineFollowEvent.getObject());
            } else if (headLineFollowEvent.getId() == 4) {//已处理
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
