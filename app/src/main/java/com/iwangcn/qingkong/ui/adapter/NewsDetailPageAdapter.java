package com.iwangcn.qingkong.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iwangcn.qingkong.ui.fragment.NewsInfoFragment;
import com.iwangcn.qingkong.ui.model.NewsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czh on 2017/4/21.
 */

public class NewsDetailPageAdapter extends FragmentStatePagerAdapter {
    private List<NewsInfo> mList;

    public NewsDetailPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setList(List<NewsInfo> mList) {
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsInfoFragment.newInstance(position,mList.get(position));
    }

    @Override
    public int getCount() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        return mList.size();
    }
}
