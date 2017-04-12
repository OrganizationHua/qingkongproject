package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.activity.NewsDetailActivity;
import com.iwangcn.qingkong.ui.adapter.HeadLineFollowAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.HelperModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HeadLineFollowFragment extends BaseFragment {

    @BindView(R.id.home_list_news)
    ListView mListView;//黑色蒙层

    private HeadLineFollowAdapter mNewsAdapter;
    private List<HelperModel> mList;

    @Override
    protected int layoutResID() {
        return R.layout.fragment_headline;
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
        mNewsAdapter = new HeadLineFollowAdapter(getActivity());
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


}
