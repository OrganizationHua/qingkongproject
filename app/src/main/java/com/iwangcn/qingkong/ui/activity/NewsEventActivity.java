package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.adapter.NewsEventAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.EventInfo;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 新闻事件界面
 */
public class NewsEventActivity extends QkBaseActivity {
    @BindView(R.id.newsEvent_recycler_view)
    ListView mListView;
    private Context mContext = this;
    private NewsEventAdapter mAdapter;
    private List<EventInfo> mList = new ArrayList<>();

    @Override
    public int layoutChildResID() {
        return R.layout.activity_news_event;
    }

    @Override
    public void initView() {
        setTitle("新闻标题");
        initListView();
    }

    private void initListView() {
        mAdapter = new NewsEventAdapter(this);
        for (int i = 0; i < 30; i++) {
            EventInfo model = new EventInfo();
            if (i == 0) {
                model.setSelect(true);
            } else {
                model.setSelect(false);
            }
            mList.add(model);
        }
        mAdapter.setDataList(mList);
        ScaleInAnimationAdapter mAnimAdapter = new ScaleInAnimationAdapter(mAdapter);
        mAnimAdapter.setAbsListView(mListView);
        mListView.setAdapter(mAnimAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                  onScrollListener(i);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        });
    }
    private void onScrollListener(int firstVisibleItem){
        for(int i=0;i<mList.size();i++){
            if(i==firstVisibleItem){
                mList.get(i).setSelect(true);
            }else{
                mList.get(i).setSelect(false);
            }
        }
        mAdapter.setDataList(mList);
    }
}
