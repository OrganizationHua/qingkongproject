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
    private ArrayList<Fragment> mFragmentList;

    public NewsDetailPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setList(List<NewsInfo> mList) {
        this.mList = mList;
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0, size = mList.size(); i < size; i++) {
            fragments.add(NewsInfoFragment.newInstance(i, mList.get(i)));
        }
        setFragmentList(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        //return NewsInfoFragment.newInstance(position,mList.get(position));
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        return mList.size();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    private void setFragmentList(ArrayList<Fragment> fragmentList) {
        if (this.mFragmentList != null) {
            mFragmentList.clear();
        }
        this.mFragmentList = fragmentList;
        notifyDataSetChanged();
    }

}
