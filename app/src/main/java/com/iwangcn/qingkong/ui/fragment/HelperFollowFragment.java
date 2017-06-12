package com.iwangcn.qingkong.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.HelperFollowEvent;
import com.iwangcn.qingkong.business.LoadFailEvent;
import com.iwangcn.qingkong.business.MoreTagEditEvent;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.ui.activity.TagFilterActivity;
import com.iwangcn.qingkong.ui.adapter.HelperFollowRecyclerAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.HelperListModel;
import com.iwangcn.qingkong.ui.view.freshwidget.RefreshListenerAdapter;
import com.iwangcn.qingkong.ui.view.freshwidget.ReloadRefreshLayout;
import com.iwangcn.qingkong.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HelperFollowFragment extends BaseFragment {
    @BindView(R.id.systemLin_loding)
    RelativeLayout mLinLoading;//正在加载
    @BindView(R.id.system_no_data)
    RelativeLayout mNoData;//暂时没有数据

    @BindView(R.id.home_list_news)
    RecyclerView mListView;

    @BindView(R.id.mReloadRefreshView)
    ReloadRefreshLayout mReloadRefreshView;

    private HelperFollowRecyclerAdapter mNewsAdapter;
    private List<HelperListModel> mList = new ArrayList<>();
    private HelperFollowEvent helperFollowEvent;
    private int type;
    private String sourceType = "";
    private String tags = "";

    public static HelperFollowFragment newInstance(int type) {
        HelperFollowFragment myFragment = new HelperFollowFragment();
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
        initData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    private void initData() {
        helperFollowEvent = new HelperFollowEvent(getContext(), type);
        helperFollowEvent.getRefreshEventList(sourceType, tags);
        mNewsAdapter = new HelperFollowRecyclerAdapter(getActivity(), mList, type, helperFollowEvent);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mListView.setAdapter(mNewsAdapter);
        new AsyncTask<Object, Object, List<HelperListModel>>() {

            @Override
            protected List<HelperListModel> doInBackground(Object... strings) {
                return helperFollowEvent.getCache();
            }

            @Override
            protected void onPostExecute(List<HelperListModel> eventInfos) {
                if (eventInfos != null) {

                    mList.addAll(eventInfos);
                    mNewsAdapter.notifyDataSetChanged();
                } else {
                    mLinLoading.setVisibility(View.VISIBLE);
                }
                helperFollowEvent.getRefreshEventList(sourceType,tags);
            }
        }.execute();
        mReloadRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(ReloadRefreshLayout refreshLayout) {
                mReloadRefreshView.setEnableRefresh(true);
                helperFollowEvent.getRefreshEventList(sourceType, tags);
                mNoData.setVisibility(View.GONE);
            }

            @Override
            public void onLoadMore(ReloadRefreshLayout refreshLayout) {
                helperFollowEvent.getMoreEvent(sourceType, tags);
                mNoData.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sourceType = "";
        tags = "";
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            sourceType = bundle.getInt("sourceType") + "";
            tags = bundle.getString("tags");
            helperFollowEvent.getRefreshEventList(sourceType, tags);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartActivityFromHelp(String tab) {

        if (type == 0) {
            if (TextUtils.equals(tab, "1")) {
                Intent intent = new Intent(getActivity(), TagFilterActivity.class);
                startActivityForResult(intent, 300);
            }
        } else if (type == 1) {
            if (TextUtils.equals(tab, "3")) {
                Intent intent = new Intent(getActivity(), TagFilterActivity.class);
                startActivityForResult(intent, 500);
            }
        }

    }
    @OnClick(R.id.system_no_data_lin)//搜索按钮
    public void systemNoDataLin() {
        mNoData.setVisibility(View.GONE);
        mLinLoading.setVisibility(View.VISIBLE);
        helperFollowEvent.getRefreshEventList(sourceType,tags);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainx(Event event) {
        if (event instanceof HelperFollowEvent) {
            if (helperFollowEvent.getId() == 0) {
                mLinLoading.setVisibility(View.GONE);
                mReloadRefreshView.finishRefreshing();
                List<HelperListModel> list = (List<HelperListModel>) event.getObject();
                if (list == null || list.isEmpty()) {
                    if (event.isMore()) {
                        mNoData.setVisibility(View.GONE);
                    } else {
                        mNoData.setVisibility(View.VISIBLE);
                    }
                } else {
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
                if (list != null) {
                    mList.addAll(list);
                    mNewsAdapter.notifyDataSetChanged();
                }
            } else if (helperFollowEvent.getId() == 1) {//取消跟进
                mList.remove((int) helperFollowEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) helperFollowEvent.getObject());
            } else if (helperFollowEvent.getId() == 2) {//置顶
                ToastUtil.showToast(getActivity(), "已置顶");
                mList.get((int) helperFollowEvent.getObject()).getHelperProcess().setTop(1);
                mNewsAdapter.notifyItemChanged((int) helperFollowEvent.getObject());
            } else if (helperFollowEvent.getId() == 3) {////取消置顶
                ToastUtil.showToast(getActivity(), "已取消置顶");
                mList.get((int) helperFollowEvent.getObject()).getHelperProcess().setTop(0);
                mNewsAdapter.notifyItemChanged((int) helperFollowEvent.getObject());
            } else if (helperFollowEvent.getId() == 4) {//已处理
                mList.remove((int) helperFollowEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) helperFollowEvent.getObject());
            } else if (helperFollowEvent.getId() == 5) {//重新处理
                mList.remove((int) helperFollowEvent.getObject());
                mNewsAdapter.notifyItemRemoved((int) helperFollowEvent.getObject());
            } else if(event instanceof MoreTagEditEvent){
                if(event.getId()==MoreTagEditEvent.TAG_UPDATE_HELP){
                    helperFollowEvent.getRefreshEventList(sourceType, tags);
                }
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
