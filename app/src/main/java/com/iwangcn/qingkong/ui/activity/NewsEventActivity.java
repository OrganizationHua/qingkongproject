package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.adapter.NewsEventAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.EventInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 新闻事件界面
 */
public class NewsEventActivity extends QkBaseActivity {
    @BindView(R.id.newsEvent_recycler_view)
    ListView mRecyclerView;
    private Context mContext = this;
    private NewsEventAdapter mEventAdapter;
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
        mEventAdapter = new NewsEventAdapter(this);
        for (int i = 0; i < 30; i++) {
            EventInfo model = new EventInfo();
            if (i == 0) {
                model.setSelect(true);
            } else {
                model.setSelect(false);
            }
            mList.add(model);
        }
        mEventAdapter.setDataList(mList);
        mRecyclerView.setAdapter(mEventAdapter);
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                mContext.startActivity(intent);
            }
        });;
    }
}
