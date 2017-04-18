package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.HomeEvent;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.ui.activity.CollectActivity;
import com.iwangcn.qingkong.ui.activity.NewsEventActivity;
import com.iwangcn.qingkong.ui.activity.NewsSearchActivity;
import com.iwangcn.qingkong.ui.adapter.EventInfoAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.EventInfo;
import com.iwangcn.qingkong.ui.view.pullview.AbPullToRefreshView;
import com.iwangcn.qingkong.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    private List<EventInfo> mList;
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

    private void initData() {
        EventBus.getDefault().register(this);
        mAbPullToRefreshView.setOnHeaderRefreshListener(this);
        mAbPullToRefreshView.setOnFooterLoadListener(this);
        mHomeEvent = new HomeEvent(getActivity());
        mAdapter = new EventInfoAdapter(getActivity());
        mAdapter.setCollectView(mLin);
        mAdapter.setContainerView(mRellistView);
        new AsyncTask<Object, Object, List<EventInfo>>() {

            @Override
            protected List<EventInfo> doInBackground(Object... strings) {
                return mHomeEvent.getCacheNews();
            }

            @Override
            protected void onPostExecute(List<EventInfo> eventInfos) {
                mList = eventInfos;
                mAdapter.setDataList(mList);
                mListView.setAdapter(mAdapter);
            }
        }.execute();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NewsEventActivity.class);
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
        Intent intent = new Intent(getActivity(), CollectActivity.class);
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
        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    ToastUtil.showToast(getActivity(), "搜索");
                }
                return false;
            }
        });
    }
    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof HomeEvent) {
            mAbPullToRefreshView.onHeaderRefreshFinish();
            List<EventInfo> list = (List<EventInfo>) event.getObject();
            if(event.isMore()){
                mAbPullToRefreshView.onFooterLoadFinish();
                if (list.size() < NetConst.page) {//如果小于10条表示加载完成不能加载更多
                    mAbPullToRefreshView.setLoadMoreEnable(false);
                }else {
                    mAbPullToRefreshView.onHeaderRefreshFinish();
                    mList.clear();
                }
                mList.addAll(list);
                mAdapter.setDataList(mList);
            }
        }
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        mHomeEvent.getRefreshEventList();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        mHomeEvent.getMoreEvent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
