package com.iwangcn.qingkong.ui.fragment;

import android.content.Intent;
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
import com.iwangcn.qingkong.ui.activity.CollectActivity;
import com.iwangcn.qingkong.ui.activity.NewsEventActivity;
import com.iwangcn.qingkong.ui.activity.NewsSearchActivity;
import com.iwangcn.qingkong.ui.adapter.NewsAdapter;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.NewsInfo;
import com.iwangcn.qingkong.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {
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

    private NewsAdapter mNewsAdapter;
    private List<NewsInfo> mList;

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
        mList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            NewsInfo model = new NewsInfo();
            model.setTitle("当地时间6日，国家主席习近平在美国佛罗里达州海湖庄园同美国总统特朗普举行中美元首会晤。两国元首进行了深入、友好、长时间的会晤");
            model.setNumb("333条");
            model.setPubtime("2014-9999");
            mList.add(model);
        }
        mNewsAdapter = new NewsAdapter(getActivity());
        mNewsAdapter.setDataList(mList);
        mNewsAdapter.setCollectView(mLin);
        mNewsAdapter.setContainerView(mRellistView);
        mListView.setAdapter(mNewsAdapter);
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
}
