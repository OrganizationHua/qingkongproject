package com.iwangcn.qingkong.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.fragment.FollowFragment;
import com.iwangcn.qingkong.ui.fragment.HelperFragment;
import com.iwangcn.qingkong.ui.fragment.HomeFragment;
import com.iwangcn.qingkong.ui.fragment.MineFragment;

/**
 * Created by czh on 2017/4/5.
 * tablayout Adapter
 */

public class TabLayoutAdapter extends FragmentPagerAdapter {
    /**
     * 标题栏
     */
    private static String[] mTitles = null;

    private static final int[] mImgDrable = {R.drawable.tab_btn_toutiao_selector,R.drawable.tab_btn_zhushou_selector,R.drawable.tab_btn_genjin_selector,R.drawable.tab_btn_wode_selector};
    private Context mContext;

    public TabLayoutAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        mTitles = this.mContext.getResources().getStringArray(R.array.home_tablayout_tilte);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment baseFragment = null;
        switch (position) {
            case 0:
                baseFragment = new HomeFragment();
                break;
            case 1:
                baseFragment = new HelperFragment();
                break;
            case 2:
                baseFragment = new FollowFragment();
                break;
            case 3:
                baseFragment = new MineFragment();
                break;
            default:
                baseFragment = new HomeFragment();
                break;
        }
        return baseFragment;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //自定义返回 标题栏的方法
    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tablayout_item, null);
        ImageView image = (ImageView) view.findViewById(R.id.tab_btn_default);
        TextView title = (TextView) view.findViewById(R.id.tab_btn_title);
        TextView notify = (TextView) view.findViewById(R.id.tab_unread_notify);
        image.setImageDrawable(this.mContext.getResources().getDrawable(mImgDrable[position]));
        if(position==0){
            image.setSelected(true);
            title.setSelected(true);
        }
        title.setText(mTitles[position]);
        return view;
    }
}