package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.fragment.HeadLineFollowFragment;
import com.iwangcn.qingkong.ui.fragment.HelperFollowFragment;

/**
 * Created by czh on 2017/4/5.
 * tablayout Adapter
 */

public class FollowTabAdapter extends FragmentPagerAdapter {
    /**
     * 标题栏
     */
    private static String[] mTitles = null;
    private Context mContext;
    private int type;
    public FollowTabAdapter(Context context, FragmentManager fm, int type) {
        super(fm);
        this.mContext = context;
        this.type=type;
        mTitles = this.mContext.getResources().getStringArray(R.array.follow_tablayout_tilte);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment baseFragment = null;
        switch (position) {
            case 0:
                baseFragment = HeadLineFollowFragment.newInstance(type);
                break;
            case 1:
                baseFragment =  HelperFollowFragment.newInstance(type);
                break;
            default:
                baseFragment = new HeadLineFollowFragment();
                break;
        }
        return baseFragment;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }


}