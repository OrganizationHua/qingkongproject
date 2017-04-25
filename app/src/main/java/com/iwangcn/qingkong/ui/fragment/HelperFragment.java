package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.HelperEvent;
import com.iwangcn.qingkong.business.LoadFailEvent;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.ui.activity.NewsDetailActivity;
import com.iwangcn.qingkong.ui.activity.TagEditActivity;
import com.iwangcn.qingkong.ui.adapter.BaseRecyclerViewAdapter;
import com.iwangcn.qingkong.ui.adapter.HelperRecyclerAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.HelperInfo;
import com.iwangcn.qingkong.ui.view.RecycleViewDivider;
import com.iwangcn.qingkong.ui.view.freshwidget.ReloadRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HelperFragment extends BaseFragment {

    @BindView(R.id.home_list_news)
    RecyclerView mListView;//黑色蒙层
    @BindView(R.id.home_collect_icon)
    ImageView mCollectIcon;//收藏ImageView
    @BindView(R.id.rel_listView)
    RelativeLayout mRellistView;//listView容器

    @BindView(R.id.mReloadRefreshView)
    ReloadRefreshLayout mReloadRefreshView;
    private HelperRecyclerAdapter mNewsAdapter;
    private List<HelperInfo> mList;
    private HelperEvent helperEvent;
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
        helperEvent=new HelperEvent(getContext());
        helperEvent.getRefreshEventList();
        mList = new ArrayList<>();
//        for (int i = 0; i < 15; i++) {
//            HelperModel model = new HelperModel();
//            model.setTitle("当地时间6日，国家主席习近平在美国佛罗里达州海湖庄园同美国总统特朗普举行中美元首会晤。两国元首进行了深入、友好、长时间的会晤");
//            model.setTime("三天前");
//            model.setFrom("腾讯新闻");
//            mList.add(model);
//        }
        mNewsAdapter = new HelperRecyclerAdapter(getActivity(), mList);
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

    @OnClick(R.id.home_collect_icon)//收藏
    public void btnCollect() {
        Intent intent = new Intent(getActivity(), TagEditActivity.class);
        startActivity(intent);
    }
    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof HelperEvent) {
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
