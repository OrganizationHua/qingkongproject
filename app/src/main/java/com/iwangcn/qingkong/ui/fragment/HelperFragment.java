package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.activity.CollectActivity;
import com.iwangcn.qingkong.ui.activity.NewsDetailActivity;
import com.iwangcn.qingkong.ui.adapter.HelperAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.HelperModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HelperFragment extends BaseFragment {

    @BindView(R.id.home_list_news)
    ListView mListView;//黑色蒙层
    @BindView(R.id.home_collect_icon)
    ImageView mCollectIcon;//收藏ImageView
    @BindView(R.id.rel_listView)
    RelativeLayout mRellistView;//listView容器

    private HelperAdapter mNewsAdapter;
    private List<HelperModel> mList;

    @Override
    protected int layoutResID() {
        return R.layout.fragment_helper;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        initData();
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            HelperModel model = new HelperModel();
            model.setTitle("当地时间6日，国家主席习近平在美国佛罗里达州海湖庄园同美国总统特朗普举行中美元首会晤。两国元首进行了深入、友好、长时间的会晤");
            model.setTime("三天前");
            model.setFrom("腾讯新闻");
            mList.add(model);
        }
        mNewsAdapter = new HelperAdapter(getActivity());
        mNewsAdapter.setDataList(mList);

        mListView.setAdapter(mNewsAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.home_collect_icon)//收藏
    public void btnCollect() {
        Intent intent = new Intent(getActivity(), CollectActivity.class);
        startActivity(intent);
    }


}