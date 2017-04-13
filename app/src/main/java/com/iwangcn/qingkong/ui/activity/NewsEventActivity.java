package com.iwangcn.qingkong.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.adapter.NewsEventAdapter;
import com.iwangcn.qingkong.ui.base.BaseActivity;
import com.iwangcn.qingkong.ui.model.NewEventModel;
import com.iwangcn.qingkong.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新闻事件界面
 */
public class NewsEventActivity extends BaseActivity {
    @BindView(R.id.newsEvent_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.newsevent_activity_coordinator)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.coordinator_child)
    View mCoordinatorChild;

    private NewsEventAdapter mEventAdapter;
    private List<NewEventModel> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_event);
        ButterKnife.bind(this);
        initRecycleView();

        mCoordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(NewsEventActivity.this,"显示");
            }
        });
    }


    private void initRecycleView() {
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mEventAdapter = new NewsEventAdapter(this);
        for (int i = 0; i < 30; i++) {
            NewEventModel model = new NewEventModel();
            mList.add(model);
        }
        mEventAdapter.setDataList(mList);
        mRecyclerView.setAdapter(mEventAdapter);
    }
}
