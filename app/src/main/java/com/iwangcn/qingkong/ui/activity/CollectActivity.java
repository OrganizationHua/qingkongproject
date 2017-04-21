package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.adapter.CollectAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.NewsInfo;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by czh on 2017/4/9.
 */

public class CollectActivity extends QkBaseActivity {

    @BindView(R.id.home_list_collect)
    DynamicListView mListView;//

    private CollectAdapter collectAdapter;
    private List<NewsInfo> mList;
    private Context mContext = this;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_collect;
    }

    @Override
    public void initView() {
        setTitle(getResources().getString(R.string.toutiao_collect));
    }

    public void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            NewsInfo model = new NewsInfo();
            model.setTitle("当地时间6日，国家主席习近平在美国佛罗里达州海湖庄园同美国总统特朗普举行中美元首会晤。两国元首进行了深入、友好、长时间的会晤");
            model.setNumb(i + "条");
            model.setPubtime(222222);
            mList.add(model);

        }
        collectAdapter = new CollectAdapter(this);
        collectAdapter.addAll(mList);
        SwingLeftInAnimationAdapter animAdapter = new SwingLeftInAnimationAdapter(collectAdapter);
        animAdapter.setAbsListView(mListView);
        assert animAdapter.getViewAnimator() != null;
        animAdapter.getViewAnimator().setInitialDelayMillis(300);
        mListView.setAdapter(animAdapter);
        collectAdapter.setCancleCollcetListener(new CollectAdapter.OnClickCancleCollectListener() {
            @Override
            public void onClickCancleCollect(int position) {
                mListView.enableSwipeToDismiss(new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            collectAdapter.remove(position);
                        }
                    }
                });
                mListView.dismiss(position);
                mListView.disableSwipeToDismiss();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, NewsEventActivity.class);
                startActivity(intent);
            }
        });
    }
}
