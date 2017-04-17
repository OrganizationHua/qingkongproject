package com.iwangcn.qingkong.ui.activity;

import android.widget.ListView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.adapter.CollectAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.NewsInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by czh on 2017/4/9.
 */

public class CollectActivity extends QkBaseActivity {

    @BindView(R.id.home_list_collect)
    ListView mListView;//

    private CollectAdapter collectAdapter;
    private List<NewsInfo> mList;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_collect;
    }

    @Override
    public void initView() {
        setTitle(getResources().getString(R.string.toutiao_collect));
    }

    public void initData() {
        mList=new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            NewsInfo model = new NewsInfo();
            model.setTitle("当地时间6日，国家主席习近平在美国佛罗里达州海湖庄园同美国总统特朗普举行中美元首会晤。两国元首进行了深入、友好、长时间的会晤");
            model.setNumb("333条");
            model.setPubtime("2014-9999");
            mList.add(model);
        }
        collectAdapter = new CollectAdapter(this);
        collectAdapter.setDataList(mList);
        mListView.setAdapter(collectAdapter);
    }

}
