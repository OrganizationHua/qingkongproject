package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.HomeEvent;
import com.iwangcn.qingkong.business.LoadFailEvent;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.ui.activity.FavoriteActivity;
import com.iwangcn.qingkong.ui.activity.NewsListActivity;
import com.iwangcn.qingkong.ui.activity.NewsSearchActivity;
import com.iwangcn.qingkong.ui.adapter.EventInfoAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.EventInfoVo;
import com.iwangcn.qingkong.ui.view.pullview.AbPullToRefreshView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    @BindView(R.id.homefragment_edit_search)
    EditText mEditSearch;//输入框
    @BindView(R.id.home_rel_search_bg_mark)
    RelativeLayout mRelMark;//黑色蒙层
    @BindView(R.id.home_list_news)
    ListView mListView;//黑色蒙层
    @BindView(R.id.home_collect_icon)
    ImageView mCollectIcon;//收藏ImageView
    @BindView(R.id.rel_listView)
    RelativeLayout mRellistView;//listView容器
    @BindView(R.id.homeFragment_btn_collected)
    LinearLayout mLin;//listView容器

    @BindView(R.id.mPullRefreshView)
    AbPullToRefreshView mAbPullToRefreshView;

    private EventInfoAdapter mAdapter;
    private List<EventInfoVo> mList=new ArrayList<>();
    private HomeEvent mHomeEvent;

    @Override
    protected int layoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initEditText();
        initData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    private void initData() {
        mAbPullToRefreshView.setOnHeaderRefreshListener(this);
        mAbPullToRefreshView.setOnFooterLoadListener(this);
        mHomeEvent = new HomeEvent(getActivity());
        mAdapter = new EventInfoAdapter(getActivity());
        mAdapter.setCollectView(mLin);
        mAdapter.setContainerView(mRellistView);
        mAdapter.setDataList(mList);
        mListView.setAdapter(mAdapter);
        new AsyncTask<Object, Object, List<EventInfoVo>>() {

            @Override
            protected List<EventInfoVo> doInBackground(Object... strings) {
                return mHomeEvent.getCacheNews();
            }

            @Override
            protected void onPostExecute(List<EventInfoVo> eventInfos) {
                if(eventInfos!=null){
                    mList = eventInfos;
                    mAdapter.setDataList(mList);
                }
            }
        }.execute();
        mHomeEvent.getRefreshEventList();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NewsListActivity.class);
                intent.putExtra("EventInfo",mList.get(i).getEventInfo());
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.home_rel_search)//搜索按钮
    public void btnSearch() {
        Intent intent = new Intent(getActivity(), NewsSearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.homefragment_edit_search)//搜索按钮
    public void onEditSearch() {
        Intent intent = new Intent(getActivity(), NewsSearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.homeFragment_btn_collected)//收藏
    public void btnCollect() {
        Intent intent = new Intent(getActivity(), FavoriteActivity.class);
        startActivity(intent);
    }

    private void initEditText() {
        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    mRelMark.setVisibility(View.GONE);
                } else {
                    mRelMark.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof HomeEvent) {
            mAbPullToRefreshView.onHeaderRefreshFinish();
            List<EventInfoVo> list = (List<EventInfoVo>) event.getObject();
            if (list.size() < NetConst.page) {//如果小于page条表示加载完成不能加载更多
                mAbPullToRefreshView.setLoadMoreEnable(false);
            }
            if (event.isMore()) {
                mAbPullToRefreshView.onFooterLoadFinish();
            } else {
                mAbPullToRefreshView.onHeaderRefreshFinish();
                mList.clear();
            }
            mList.addAll(list);
            mAdapter.setDataList(mList);
        } else if (event instanceof LoadFailEvent) {
            mAbPullToRefreshView.onHeaderRefreshFinish();
            mAbPullToRefreshView.onFooterLoadFinish();
        }
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        mHomeEvent.getMoreEvent();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        mAbPullToRefreshView.setLoadMoreEnable(true);
        mHomeEvent.getRefreshEventList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
