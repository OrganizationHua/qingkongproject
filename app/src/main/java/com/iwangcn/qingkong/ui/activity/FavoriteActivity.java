package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.adapter.FacoriteAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.FavoriteInfo;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by czh on 2017/4/9.
 */

public class FavoriteActivity extends QkBaseActivity {

    @BindView(R.id.home_list_collect)
    DynamicListView mListView;//

    private FacoriteAdapter mAdapter;
    private List<FavoriteInfo> mList;
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

        mAdapter = new FacoriteAdapter(this);
        mAdapter.addAll(mList);
        SwingLeftInAnimationAdapter animAdapter = new SwingLeftInAnimationAdapter(mAdapter);
        animAdapter.setAbsListView(mListView);
        assert animAdapter.getViewAnimator() != null;
        animAdapter.getViewAnimator().setInitialDelayMillis(300);
        mListView.setAdapter(animAdapter);
        mAdapter.setCancleCollcetListener(new FacoriteAdapter.OnClickCancleCollectListener() {
            @Override
            public void onClickCancleCollect(int position) {
                mListView.enableSwipeToDismiss(new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            mAdapter.remove(position);
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
