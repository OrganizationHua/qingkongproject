package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.activity.NewsDetailActivity;
import com.iwangcn.qingkong.ui.adapter.BaseRecyclerViewAdapter;
import com.iwangcn.qingkong.ui.adapter.HelperFollowRecyclerAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.HelperModel;
import com.iwangcn.qingkong.ui.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HelperFollowFragment extends BaseFragment {

    @BindView(R.id.home_list_news)
    RecyclerView mListView;//黑色蒙层

    @BindView(R.id.rel_listView)
    RelativeLayout mRellistView;//listView容器

    private HelperFollowRecyclerAdapter mNewsAdapter;
    private List<HelperModel> mList;
    private int type;
    public static HelperFollowFragment newInstance(int type){
        HelperFollowFragment myFragment = new HelperFollowFragment();
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

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            HelperModel model = new HelperModel();
            model.setTitle("当地时间6日，国家主席习近平在美国佛罗里达州海湖庄园同美国总统特朗普举行中美元首会晤。两国元首进行了深入、友好、长时间的会晤");
            model.setTime("三天前");
            model.setFrom("腾讯新闻");
            mList.add(model);
        }

        mNewsAdapter = new HelperFollowRecyclerAdapter(getActivity(),mList,type);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mListView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL,R.drawable.divider_shape));
        mListView.setAdapter(mNewsAdapter);
        mNewsAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClickListener(RecyclerView.ViewHolder viewHolder) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                startActivity(intent);
            }
        });

    }



}
