package com.iwangcn.qingkong.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.HelperEvent;
import com.iwangcn.qingkong.business.LoadFailEvent;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.ui.adapter.HelperRecyclerAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.HelperInfo;
import com.iwangcn.qingkong.ui.model.HelperListModel;
import com.iwangcn.qingkong.ui.view.freshwidget.RefreshListenerAdapter;
import com.iwangcn.qingkong.ui.view.freshwidget.ReloadRefreshLayout;
import com.iwangcn.qingkong.utils.AbDateUtil;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 留言列表界面
 */
public class MessageListActivity extends QkBaseActivity {
    @BindView(R.id.news_title)
    TextView mNewsTitle;
    @BindView(R.id.news_from)
    TextView mNewsFrom;
    @BindView(R.id.news_time)
    TextView mNewsTime;
    @BindView(R.id.tag_flowlayout)
    public TagFlowLayout tagFlowLayout;//标签

    @BindView(R.id.home_list_news)
    RecyclerView mListView;


    @BindView(R.id.mReloadRefreshView)
    ReloadRefreshLayout mReloadRefreshView;
    private HelperRecyclerAdapter mNewsAdapter;
    private List<HelperInfo> mList = new ArrayList<>();
    private HelperEvent helperEvent;
    @Override
    public int layoutChildResID() {
        return R.layout.activity_message_list;
    }

    private HelperListModel.HelperInfo helperInfo;

    @Override
    public void initView() {
        setTitle("留言");
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        helperInfo = (HelperListModel.HelperInfo) getIntent().getSerializableExtra("message");
        initTag(helperInfo);
        helperEvent = new HelperEvent(this);
        helperEvent.getRefreshEventList();
        mNewsAdapter = new HelperRecyclerAdapter(this, mList, helperEvent);
        mListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mListView.setAdapter(mNewsAdapter);
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
    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof HelperEvent) {
            Log.e("fjg","====");
            if (helperEvent.getId() == 0) {
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
    @OnClick(R.id.base_act_right_lin)//APP信息
    public void onBtnWebView() {
        ToastUtil.showToast(this, "查看原文");
    }

    public void initTag(HelperListModel.HelperInfo helperInfo) {
        if (helperInfo != null) {
            Log.e("fjg====", helperInfo.getTitle());
            mNewsTitle.setText(helperInfo.getTitle());
            mNewsFrom.setText(helperInfo.getSource());
            mNewsTime.setText(AbDateUtil.formatDateStrGetDay(helperInfo.getUpdateTime()));
            TagAdapter<String> tagAdapter = new TagAdapter<String>(Arrays.asList(helperInfo.getLabels().split(","))) {
                @Override
                public View getView(FlowLayout parent, int position, String o) {

                    TextView tv = (TextView) LayoutInflater.from(MessageListActivity.this).inflate(R.layout.tv,
                            parent, false);
                    tv.setText(o);
                    return tv;
                }
            };
            tagFlowLayout.setAdapter(tagAdapter);
        }
    }

}