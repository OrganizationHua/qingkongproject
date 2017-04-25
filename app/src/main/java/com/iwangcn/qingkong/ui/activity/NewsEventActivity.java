package com.iwangcn.qingkong.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.adapter.NewsEventAdapter;
import com.iwangcn.qingkong.ui.base.BaseActivity;
import com.iwangcn.qingkong.ui.model.EventInfo;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新闻事件界面
 */
public class NewsEventActivity extends BaseActivity {
    @BindView(R.id.newsEvent_recycler_view)
    ListView mListView;
    private Activity mContext = this;
    private NewsEventAdapter mAdapter;
    private List<EventInfo> mList = new ArrayList<>();
    private final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_event);
        ButterKnife.bind(this);
        initView();
    }


    public void initView() {
        // setTitle("新闻标题");
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
                mContext.startActivityForResult(intent, REQUEST_CODE);
            }
        });
        ViewCompat.setNestedScrollingEnabled(mListView, true);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                int position = data.getIntExtra("position", 0);
                mListView.setSelection(position);
                for (int i = 0; i < mList.size(); i++) {
                    if (i == position) {
                        mList.get(i).setSelect(true);
                    } else {
                        mList.get(i).setSelect(false);
                    }
                }
                mAdapter.setDataList(mList);
                mListView.setSelection(position);
            }

        }
    }
}


