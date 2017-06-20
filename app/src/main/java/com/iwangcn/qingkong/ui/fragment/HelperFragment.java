package com.iwangcn.qingkong.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.HelperEvent;
import com.iwangcn.qingkong.business.LoadFailEvent;
import com.iwangcn.qingkong.business.MoreTagEditEvent;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.ui.activity.TagFilterActivity;
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
    @BindView(R.id.systemLin_loding)
    RelativeLayout mLinLoading;//正在加载
    @BindView(R.id.system_no_data)
    RelativeLayout mSystemNoData;//加载无数据
    @BindView(R.id.home_list_news)
    RecyclerView mListView;


    @BindView(R.id.mReloadRefreshView)
    ReloadRefreshLayout mReloadRefreshView;

    private HelperRecyclerAdapter mNewsAdapter;
    private List<HelperInfo> mList = new ArrayList<>();
    private HelperEvent helperEvent;
    private String sourceType = "", tags = "";

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
        helperEvent.getRefreshEventList(sourceType, tags);
        mNewsAdapter = new HelperRecyclerAdapter(getActivity(), mList, helperEvent);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mListView.setAdapter(mNewsAdapter);
        new AsyncTask<Object, Object, List<HelperInfo>>() {

            @Override
            protected List<HelperInfo> doInBackground(Object... strings) {
                return helperEvent.getCacheHelper();
            }

            @Override
            protected void onPostExecute(List<HelperInfo> eventInfos) {
                if (eventInfos != null) {
                    mList.addAll(eventInfos);
                    mNewsAdapter.notifyDataSetChanged();
                } else {
                    mLinLoading.setVisibility(View.VISIBLE);
                }
                helperEvent.getRefreshEventList(sourceType, tags);
            }
        }.execute();
        mReloadRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(ReloadRefreshLayout refreshLayout) {
                mReloadRefreshView.setEnableRefresh(true);
                mSystemNoData.setVisibility(View.GONE);
                helperEvent.getRefreshEventList(sourceType, tags);
            }

            @Override
            public void onLoadMore(ReloadRefreshLayout refreshLayout) {
                helperEvent.getMoreEvent(sourceType, tags);
                mSystemNoData.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.home_collect_icon)//筛选
    public void btnCollect() {
        Intent intent = new Intent(getActivity(), TagFilterActivity.class).putExtra("from",1);
        startActivityForResult(intent, 100);
    }
    @OnClick(R.id.system_no_data_lin)//搜索按钮
    public void systemNoDataLin() {
        mSystemNoData.setVisibility(View.GONE);
        mLinLoading.setVisibility(View.VISIBLE);
        helperEvent.getRefreshEventList(sourceType, tags);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sourceType = "";
        tags = "";
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
//            ToastUtil.showToast(getActivity(), sourceType + tags);
                Bundle bundle = data.getExtras();
                sourceType = bundle.getString("sourceType");
                tags = bundle.getString("tags");
                helperEvent.getRefreshEventList(sourceType, tags);
            }
        }
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof HelperEvent) {
            if (helperEvent.getId() == 0) {
                mLinLoading.setVisibility(View.GONE);
                mReloadRefreshView.finishRefreshing();
                List<HelperInfo> list = (List<HelperInfo>) event.getObject();
                if (list == null || list.isEmpty()) {
                    if (event.isMore()) {
                        mSystemNoData.setVisibility(View.GONE);
                    } else {
                        mSystemNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    mSystemNoData.setVisibility(View.GONE);
                }
                if (list.size() < NetConst.page) {//如果小于page条表示加载完成不能加载更多
                    mReloadRefreshView.setEnableLoadmore(false);
                }
                if (event.isMore()) {
                    mReloadRefreshView.finishLoadmore();
                } else {
                    mReloadRefreshView.finishRefreshing();
                    mList.clear();

                }
                mList.addAll(list);
                mNewsAdapter.notifyDataSetChanged();
            } else if (helperEvent.getId() == 1) {//已跟进
                mList.remove((int) helperEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) helperEvent.getObject());
                mNewsAdapter.notifyItemRangeChanged(0,mList.size()-((int)helperEvent.getObject()));
            } else if (helperEvent.getId() == 2) {//与我无关
                mList.remove((int) helperEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) helperEvent.getObject());
                mNewsAdapter.notifyItemRangeChanged(0,mList.size()-((int)helperEvent.getObject()));
            }
        } else if (event instanceof LoadFailEvent) {
            mReloadRefreshView.finishRefreshing();
            mReloadRefreshView.finishLoadmore();
        } else if(event instanceof MoreTagEditEvent){
            if(event.getId()==MoreTagEditEvent.TAG_UPDATE_HELP){
                helperEvent.getRefreshEventList(sourceType, tags);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
